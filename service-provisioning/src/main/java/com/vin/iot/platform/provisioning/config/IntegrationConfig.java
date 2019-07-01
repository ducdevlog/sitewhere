/*
 *
 *  Developed by Anhgv by VinGroup on 7/1/19, 11:34 AM
 *  Last modified 7/1/19, 11:09 AM.
 *  Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 *
 *
 */

package com.vin.iot.platform.provisioning.config;

import com.vin.iot.platform.provisioning.repository.DevicePendingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.batch.integration.launch.JobLaunchingMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.*;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.filters.AcceptOnceFileListFilter;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.gateway.GatewayProxyFactoryBean;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.MessageChannel;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * @author anhgv
 */
@Configuration
@EnableIntegration
public class IntegrationConfig {

    private static final Long INPUT_DIRECTORY_POOL_RATE = (long) 1000;
    private static final String INPUT_DIRECTORY = "D://provisioning/input";
    private static final String PROCESSED_DIRECTORY = "D://provisioning/processed";
    private static final Logger log = LoggerFactory.getLogger(IntegrationConfig.class);
    private final JobLauncher jobLauncher;

    private final JobRegistry jobRegistry;

    private final DevicePendingRepository devicePendingRepository;

	public IntegrationConfig(JobLauncher jobLauncher, JobRegistry jobRegistry, DevicePendingRepository devicePendingRepository) {
		this.jobLauncher = jobLauncher;
		this.jobRegistry = jobRegistry;
		this.devicePendingRepository = devicePendingRepository;
	}

	@Bean
    public JobRegistryBeanPostProcessor jobRegistryInitializer() {
        JobRegistryBeanPostProcessor initializer = new JobRegistryBeanPostProcessor();
        initializer.setJobRegistry(jobRegistry);
        return initializer;
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(INPUT_DIRECTORY_POOL_RATE).get();
    }

    @Bean
    public MessageChannel fileInputChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public MessageChannel jobRequestChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public MessageChannel jobExecutionChannel() {
        return MessageChannels.queue().get();
    }

    @Bean
    public MessageChannel jobStatusChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public MessageChannel jobRestartChannel() {
        return MessageChannels.queue().get();
    }

    @Bean
    public MessageChannel jobExecutionNotifiedChannel() {
        return MessageChannels.queue().get();
    }

    @Bean
    public MessageChannel jobCompletedChannel() {
        return MessageChannels.queue().get();
    }

    @Bean
    public IntegrationFlow processFilesFlow() {
        return IntegrationFlows.from(fileReadingMessageSource(), c -> c.poller(poller()))
                .channel(fileInputChannel())
                .<File, JobLaunchRequest>transform(this::transformFileToRequest)
                .channel(jobRequestChannel())
                .handle(jobRequestHandler())
                .<JobExecution, String>transform(this::transformJobExecutionToStatus)
                .channel(jobStatusChannel())
                .get();
    }

    @Bean
    public IntegrationFlow processExecutionsFlow() {
        return IntegrationFlows.from(jobExecutionChannel())
                .route(JobExecution.class, e -> e.getStatus().equals(BatchStatus.FAILED),
                        m -> m.subFlowMapping(true, f -> f.channel(jobRestartChannel()))
                                .subFlowMapping(false, f -> f.channel(jobExecutionNotifiedChannel())))
                .get();
    }

    @Bean
    public IntegrationFlow processExecutionRestartsFlow() {
        return IntegrationFlows.from(jobRestartChannel())
                .delay("wait5sec", (DelayerEndpointSpec e) -> e.defaultDelay(5000))
                .handle(e -> jobRestarter((JobExecution) e.getPayload()))
                .get();
    }

    @Bean
    public IntegrationFlow processNotifiedExecutionFlow() {
        return IntegrationFlows.from(jobExecutionNotifiedChannel())
                .route(JobExecution.class, e -> e.getStatus().equals(BatchStatus.COMPLETED),
                        m -> m.subFlowMapping(true, f -> f.channel(jobCompletedChannel()))
                                .subFlowMapping(false, f -> f.<JobExecution, String>transform(this::transformJobExecutionToStatus)
                                        .channel(jobStatusChannel())))

                .get();
    }

    @Bean
    public IntegrationFlow processExecutionCompletedFlow() {
        return IntegrationFlows.from(jobCompletedChannel())
                .transform(jobExecutionToFileTransformer())
                .handle(processedFileWritingHandler())
                .channel(jobStatusChannel())
                .get();
    }

    @Bean
    public GatewayProxyFactoryBean jobExecutionsListener() {
        GatewayProxyFactoryBean factoryBean = new GatewayProxyFactoryBean(JobExecutionListener.class);
        factoryBean.setDefaultRequestChannel(jobExecutionChannel());
        return factoryBean;
    }

    @Bean
    public MessageSource<File> fileReadingMessageSource() {
        CompositeFileListFilter<File> filters = new CompositeFileListFilter<>();
        filters.addFilter(new SimplePatternFileListFilter("*.txt"));
        filters.addFilter(new AcceptOnceFileListFilter<>());

        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setAutoCreateDirectory(true);
        source.setDirectory(new File(INPUT_DIRECTORY));
        source.setFilter(filters);

        return source;
    }

    @Bean
    public JobLaunchingMessageHandler jobRequestHandler() {
        return new JobLaunchingMessageHandler(jobLauncher);
    }

    @Bean
    public FileWritingMessageHandler processedFileWritingHandler() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(PROCESSED_DIRECTORY));
        handler.setAutoCreateDirectory(true);
        handler.setDeleteSourceFiles(true);
        handler.setFileExistsMode(FileExistsMode.REPLACE);
        return handler;
    }

    private void jobRestarter(JobExecution execution) {
        log.info("Restarting job...");
        try {
            Job job = jobRegistry.getJob(execution.getJobInstance().getJobName());
			jobLauncher.run(job, execution.getJobParameters());
		} catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public GenericTransformer<JobExecution, File> jobExecutionToFileTransformer() {
        return source -> {
			String path = source.getJobParameters().getString("input.file.path");
			return new File(path);
		};
    }

    private JobLaunchRequest transformFileToRequest(File file) {
        log.info("Creating request");

        Job job = getJobByFileName(file);
		assert job != null;
		log.info("Job = " + job.getName());

        JobParametersBuilder paramsBuilder = new JobParametersBuilder();
        paramsBuilder.addDate("start.date", new Date());
        paramsBuilder.addString("input.file.path", file.getPath());

        log.info("Parameters = " + paramsBuilder.toString());

		return new JobLaunchRequest(job, paramsBuilder.toJobParameters());
    }

    private String transformJobExecutionToStatus(JobExecution execution) {
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss.SS");
        StringBuilder builder = new StringBuilder();

        builder.append(execution.getJobInstance().getJobName());

        BatchStatus evaluatedStatus = endingBatchStatus(execution);
        if (evaluatedStatus == BatchStatus.COMPLETED || evaluatedStatus.compareTo(BatchStatus.STARTED) > 0) {
            builder.append(" has completed with a status of ").append(execution.getStatus().name()).append(" at ").append(formatter.format(new Date()));

            builder.append(" with  processed records.");
        } else {
            builder.append(" has started at ").append(formatter.format(new Date()));
        }

        return builder.toString();
    }

    private BatchStatus endingBatchStatus(JobExecution execution) {
        BatchStatus status = execution.getStatus();
        Collection<StepExecution> stepExecutions = execution.getStepExecutions();

        if (stepExecutions.size() > 0) {
            for (StepExecution stepExecution : stepExecutions) {
                if (stepExecution.getStatus().equals(BatchStatus.FAILED)) {
                    status = BatchStatus.FAILED;
                    break;
                } else {
                    status = BatchStatus.COMPLETED;
                }
            }
        }

        return status;
    }

    private Job getJobByFileName(File file) {
        try {
            return jobRegistry.getJob(BatchConfig.JOB_NAME);
        } catch (NoSuchJobException e) {
            log.error(e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }
}
