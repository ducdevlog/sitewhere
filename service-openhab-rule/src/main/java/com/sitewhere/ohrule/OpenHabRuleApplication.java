/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.ohrule;

import com.sitewhere.microservice.MicroserviceApplication;
import com.sitewhere.ohrule.spi.microservice.IOpenHabRuleMicroservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class OpenHabRuleApplication extends MicroserviceApplication<IOpenHabRuleMicroservice> {

	@Autowired
	private IOpenHabRuleMicroservice microservice;

	@Override
	public IOpenHabRuleMicroservice getMicroservice() {
		return microservice;
	}

	public static void main(String[] args) {
		SpringApplication.run(OpenHabRuleApplication.class, args);
	}
}
