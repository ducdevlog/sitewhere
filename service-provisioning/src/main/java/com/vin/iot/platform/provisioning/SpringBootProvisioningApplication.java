/*
 *
 *  Developed by Anhgv by VinGroup on 7/1/19, 10:45 AM
 *  Last modified 6/18/19, 2:00 PM.
 *  Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 *
 *
 */

package com.vin.iot.platform.provisioning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringBootProvisioningApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootProvisioningApplication.class, args);
    }
}