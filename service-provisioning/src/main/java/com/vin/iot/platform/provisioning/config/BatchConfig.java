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


import com.vin.iot.platform.provisioning.domain.DevicePending;
import com.vin.iot.platform.provisioning.domain.DomainPendingFieldSetMapper;
import com.vin.iot.platform.provisioning.repository.DevicePendingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

/**
 * @author anhgv
 *
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

	private static final Logger log = LoggerFactory.getLogger(BatchConfig.class);

	public static final String STEP_NAME = "processingStep";
	public static final String JOB_NAME = "processingJob";

	private final JobBuilderFactory jobBuilderFactory;

	private final StepBuilderFactory stepBuilderFactory;

	private final DevicePendingRepository devicePendingRepository;

	public BatchConfig(DevicePendingRepository devicePendingRepository, JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		this.devicePendingRepository = devicePendingRepository;
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
	}

	@Bean
	@StepScope
	public FlatFileItemReader<DevicePending> reader(
			@Value("file:///#{jobParameters['input.file.path']}") Resource resource) throws Exception {
		
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames("deviceToken", "hardwareId", "deviceType", "ssl");

		DefaultLineMapper<DevicePending> mapper = new DefaultLineMapper<>();
		mapper.setLineTokenizer(tokenizer);
		mapper.setFieldSetMapper(new DomainPendingFieldSetMapper());
		mapper.afterPropertiesSet();

		FlatFileItemReader<DevicePending> reader = new FlatFileItemReader<>();
		reader.setResource(resource);
		reader.setLinesToSkip(1);
		reader.setLineMapper(mapper);
		reader.afterPropertiesSet();
		
		return reader;
	}
	
	@Bean
	public ItemProcessor<DevicePending, DevicePending> processor(){
		return new ItemProcessor<DevicePending, DevicePending>(){
			private StepExecution stepExecution;

			@BeforeStep
			public void beforeStep(StepExecution stepExecution) {
				this.stepExecution = stepExecution;
			}

			@Override
			public DevicePending process(DevicePending item) throws Exception {
				log.info("Processing: " + item);
				item.setJobId(stepExecution.getJobExecution().getJobId());
				return item;
			}
			
		};
	}

	@Bean
	public RepositoryItemWriter<DevicePending> writer() {
		RepositoryItemWriter<DevicePending> writer = new RepositoryItemWriter<>();
		writer.setRepository(devicePendingRepository);
		writer.setMethodName("save");
		return writer;
	}

	@Bean
	public Step processingStepBean(ItemReader<DevicePending> reader, ItemProcessor<DevicePending, DevicePending> processor, ItemWriter<DevicePending> writer) {
		log.debug("Configuring Step: " + STEP_NAME);
		return stepBuilderFactory.get(STEP_NAME)
				.<DevicePending, DevicePending>chunk(5)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
	}

	@Bean
	public Job processingJobBean(Step processingStep, JobExecutionListener listener) {
		log.debug("Configuring Job: " + JOB_NAME);
		return jobBuilderFactory.get(JOB_NAME)
				.listener(listener)
				.incrementer(new RunIdIncrementer())
				.flow(processingStep)
				.end()
				.build();
	}

}
