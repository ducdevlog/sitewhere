/*
 *
 *  Developed by Anhgv by VinGroup on 8/14/19, 11:09 AM
 *  Last modified 7/2/19, 5:25 PM.
 *  Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 *
 *
 */

package com.vin.iot.platform.infrared.config;

import lombok.Getter;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@RefreshScope
public class SiteWhereClientConfiguration {

    @Value("${sitewhere.cfg.mqtt.server:ssl://171.244.38.134:8883}")
    private String mqttServer;

    @Value("${sitewhere.cfg.mqtt.username:admin}")
    private String mqttUsername;

    @Value("${sitewhere.cfg.mqtt.password:123456}")
    private String mqttPassword;
}
