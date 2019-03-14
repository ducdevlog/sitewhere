/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.ohrule.microservice;

import com.sitewhere.microservice.multitenant.MultitenantMicroservice;
import com.sitewhere.ohrule.configuration.OpenHabRuleModelProvider;
import com.sitewhere.ohrule.spi.microservice.IOpenHabRuleMicroservice;
import com.sitewhere.ohrule.spi.microservice.IOpenHabRuleTenantEngine;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.microservice.MicroserviceIdentifier;
import com.sitewhere.spi.microservice.configuration.model.IConfigurationModel;
import com.sitewhere.spi.tenant.ITenant;
import org.springframework.stereotype.Component;

/**
 * Microservice that provides openhab's rule management functionality.
 *
 * @author Duc
 */
@Component
public class OpenHabRuleMicroservice
		extends MultitenantMicroservice<MicroserviceIdentifier, IOpenHabRuleTenantEngine>
		implements IOpenHabRuleMicroservice {

	/**
	 * Microservice name
	 */
	private static final String NAME = "OpenHAB Rule Service";

	@Override
	public IOpenHabRuleTenantEngine createTenantEngine(ITenant tenant) throws SiteWhereException {
		return new OpenHabRuleTenantEngine(tenant);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public MicroserviceIdentifier getIdentifier() {
		return MicroserviceIdentifier.OpenHabRule;
	}

	@Override
	public boolean isGlobal() {
		return false;
	}

	@Override
	public IConfigurationModel buildConfigurationModel() {
		return new OpenHabRuleModelProvider().buildModel();
	}
}