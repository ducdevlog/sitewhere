/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.certificate.microservice;

import com.sitewhere.certificate.spi.microservice.ICertificateMicroservice;
import com.sitewhere.microservice.instance.InstanceSettings;
import com.sitewhere.spi.microservice.instance.IInstanceSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring bean configuration for microservice.
 *
 * @author Derek
 */
@Configuration
public class CertificateMicroserviceConfiguration {

    @Bean
    public ICertificateMicroservice deviceStateMicroservice() {
        return new CertificateMicroservice();
    }

    @Bean
    public IInstanceSettings instanceSettings() {
        return new InstanceSettings();
    }
}