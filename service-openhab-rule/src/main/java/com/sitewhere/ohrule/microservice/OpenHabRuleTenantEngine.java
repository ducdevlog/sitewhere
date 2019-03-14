/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.ohrule.microservice;

import com.sitewhere.microservice.multitenant.MicroserviceTenantEngine;
import com.sitewhere.ohrule.kafka.RuleUpdateEventsConsumer;
import com.sitewhere.ohrule.spi.kafka.IRuleUpdateEventsConsumer;
import com.sitewhere.ohrule.spi.microservice.IOpenHabRuleTenantEngine;
import com.sitewhere.server.lifecycle.CompositeLifecycleStep;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.microservice.multitenant.IDatasetTemplate;
import com.sitewhere.spi.microservice.multitenant.IMicroserviceTenantEngine;
import com.sitewhere.spi.server.lifecycle.ICompositeLifecycleStep;
import com.sitewhere.spi.server.lifecycle.ILifecycleProgressMonitor;
import com.sitewhere.spi.tenant.ITenant;

/**
 * Extends {@link IMicroserviceTenantEngine} with features specific to device
 * registration.
 * 
 * @author Derek
 */
public class OpenHabRuleTenantEngine extends MicroserviceTenantEngine
		implements IOpenHabRuleTenantEngine {

	private IRuleUpdateEventsConsumer ruleUpdateEventsConsumer;

	public OpenHabRuleTenantEngine(ITenant tenant) {
		super(tenant);
	}

	@Override
	public void tenantInitialize(ILifecycleProgressMonitor monitor) throws SiteWhereException {
		// Unregistered events consumer.
		this.ruleUpdateEventsConsumer = new RuleUpdateEventsConsumer();

		// Create step that will initialize components.
		ICompositeLifecycleStep init = new CompositeLifecycleStep("Initialize " + getComponentName());

		// Initialize discoverable lifecycle components.
		init.addStep(initializeDiscoverableBeans(getModuleContext()));

		// Initialize rule update events consumer.
		init.addInitializeStep(this, getRuleUpdateEventsConsumer(), true);

		// Execute initialization steps.
		init.execute(monitor);
	}

	@Override
	public void tenantStart(ILifecycleProgressMonitor monitor) throws SiteWhereException {
		// Create step that will start components.
		ICompositeLifecycleStep start = new CompositeLifecycleStep("Start " + getComponentName());

		// Start rule update events consumer.
		start.addStartStep(this, getRuleUpdateEventsConsumer(), true);

		// Execute startup steps.
		start.execute(monitor);
	}

	@Override
	public void tenantBootstrap(IDatasetTemplate template, ILifecycleProgressMonitor monitor) throws SiteWhereException {

	}

	@Override
	public void tenantStop(ILifecycleProgressMonitor monitor) throws SiteWhereException {
		// Create step that will stop components.
		ICompositeLifecycleStep stop = new CompositeLifecycleStep("Stop " + getComponentName());

		// Stop rule update events consumer.
		stop.addStopStep(this, getRuleUpdateEventsConsumer());

		// Execute shutdown steps.
		stop.execute(monitor);
	}

	@Override
	public IRuleUpdateEventsConsumer getRuleUpdateEventsConsumer() {
		return ruleUpdateEventsConsumer;
	}
}