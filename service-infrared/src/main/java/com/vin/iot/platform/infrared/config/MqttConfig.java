/*
 *
 *  Developed by Anhgv by VinGroup on 8/14/19, 10:57 AM
 *  Last modified 7/3/19, 11:05 AM.
 *  Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 *
 *
 */
package com.vin.iot.platform.infrared.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vin.iot.platform.infrared.config.dto.InfraredLearningDto;
import com.vin.iot.platform.infrared.domain.InfraredDeviceCodeset;
import com.vin.iot.platform.infrared.domain.InfraredDeviceTypeBrand;
import com.vin.iot.platform.infrared.domain.IrCodeRawLearn;
import com.vin.iot.platform.infrared.service.DeviceCodesetService;
import com.vin.iot.platform.infrared.service.DeviceTypeBrandService;
import com.vin.iot.platform.infrared.service.IrCodeRawLearnService;
import com.vin.iot.platform.infrared.service.IrCodeRawService;
import com.vin.iot.platform.infrared.utils.SSLLoaderUtils;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Configuration
@IntegrationComponentScan
@Slf4j
public class MqttConfig {
    private final SiteWhereClientConfiguration siteWhereClientConfiguration;

    private final DeviceTypeBrandService deviceTypeBrandService;

    private final DeviceCodesetService deviceCodesetService;

    private final IrCodeRawService irCodeRawService;

    private final IrCodeRawLearnService irCodeRawLearnService;

    private static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);

    public MqttConfig(SiteWhereClientConfiguration siteWhereClientConfiguration, DeviceTypeBrandService deviceTypeBrandService, DeviceCodesetService deviceCodesetService, IrCodeRawService irCodeRawService, IrCodeRawLearnService irCodeRawLearnService) {
        this.siteWhereClientConfiguration = siteWhereClientConfiguration;
        this.deviceTypeBrandService = deviceTypeBrandService;
        this.deviceCodesetService = deviceCodesetService;
        this.irCodeRawService = irCodeRawService;
        this.irCodeRawLearnService = irCodeRawLearnService;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{siteWhereClientConfiguration.getMqttServer()});
        options.setUserName(siteWhereClientConfiguration.getMqttUsername());
        options.setPassword(siteWhereClientConfiguration.getMqttPassword().toCharArray());
        options.setMaxInflight(65535);
        try {
            options.setSocketFactory(SSLLoaderUtils.getSSLContext("/certificate/ca.crt", "/certificate/client.crt", "/certificate/client.key", ""));
        } catch (Exception e) {
            log.error("Error load certificate", e);
            e.printStackTrace();
        }
        factory.setConnectionOptions(options);
        return factory;
    }

    /*
     * MQTT Outbound
     * */
    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("ir_consumer" + MqttAsyncClient.generateClientId(), mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic("testTopic");
        return messageHandler;
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    /*
     * MQTT Inbound
     * */
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("ir_producer" + MqttAsyncClient.generateClientId(), mqttClientFactory(), "testTopic");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return message -> {
            String topicName = (String) message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC);
            byte[] json = (byte[]) message.getPayload();
            String jsonStr = new String(json);
            log.debug("Received message from topic {}: {}", topicName, jsonStr);
            if (StringUtils.hasText(jsonStr)) {
                assert topicName != null;
                if (topicName.startsWith("VinIot/Smarthome/Infrared/Learn/")) {
                    try {
                        IrCodeRawLearn irCodeRawLearn = objectMapper.readValue(jsonStr, IrCodeRawLearn.class);
                        irCodeRawLearnService.createIrCodeRawLearn(irCodeRawLearn);
                    } catch (IOException e) {
                        log.warn("Failed to deserialize received message from topic {}: {}. Message: {}",
                                topicName, e.getMessage(), jsonStr);
                    }
                } else if (topicName.startsWith("VinIot/OpenHab/Infrared/Learn/")) {
                    try {
                        InfraredLearningDto infraredLearningDto = objectMapper.readValue(jsonStr, InfraredLearningDto.class);
                        List<InfraredDeviceTypeBrand> infraredDeviceTypeBrands = deviceTypeBrandService.getDeviceTypeBrandByTypeAndBrand(infraredLearningDto.getTypeCode(), infraredLearningDto.getBrandName());
                        String idMax;
                        if (CollectionUtils.isEmpty(infraredDeviceTypeBrands)) {
                            idMax = String.valueOf(deviceTypeBrandService.getMaxId() + 1);
                            InfraredDeviceTypeBrand infraredDeviceTypeBrand = new InfraredDeviceTypeBrand(idMax, infraredLearningDto.getTypeCode(), infraredLearningDto.getBrandName());
                            deviceTypeBrandService.createInfraredDeviceTypeBrand(infraredDeviceTypeBrand);
                        } else {
                            idMax = infraredDeviceTypeBrands.get(0).getId();
                        }
                        String codeSet = "1R_VSM_" + (new Date()).getTime();
                        deviceCodesetService.createInfraredDeviceCodeset(new InfraredDeviceCodeset(null, idMax, codeSet));
                        if (infraredLearningDto.getIrCodeRaws() != null && infraredLearningDto.getIrCodeRaws().size() > 0) {
                            infraredLearningDto.getIrCodeRaws().forEach(irCodeRaw -> {
                                irCodeRaw.setCodesetName(codeSet);
                                irCodeRaw.setAreaToken(infraredLearningDto.getHomeToken());
                                irCodeRawService.createIrCodeRaw(irCodeRaw);
                            });
                        }
                    } catch (IOException | IllegalArgumentException e) {
                        log.warn("Failed to deserialize received message from topic {}: {}. Message: {}",
                                topicName, e.getMessage(), jsonStr);
                    }
                }
            }

        };
    }
}
