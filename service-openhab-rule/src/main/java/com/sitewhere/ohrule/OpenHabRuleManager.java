/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.ohrule;

import com.sitewhere.ohrule.spi.IOpenHabRuleManager;
import com.sitewhere.server.lifecycle.TenantEngineLifecycleComponent;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.extended.event.kafka.IExtendedRequestPayload;
import com.sitewhere.spi.server.lifecycle.ILifecycleProgressMonitor;
import com.sitewhere.spi.server.lifecycle.LifecycleComponentType;

public class OpenHabRuleManager  extends TenantEngineLifecycleComponent
		implements IOpenHabRuleManager {

	private String mongoHost = "localhost";

	private int mongoPort = 27017;

	private String database = "openhab_rules";

	public OpenHabRuleManager() {
		super(LifecycleComponentType.OpenHabRuleManager);
	}

	@Override
	public void start(ILifecycleProgressMonitor monitor) throws SiteWhereException {
		super.start(monitor);
	}

	@Override
	public void handleUpdateRule(IExtendedRequestPayload extendedRequestPayload) throws SiteWhereException {

	}

	@Override
	public String getMongoHost() {
		return mongoHost;
	}

	@Override
	public Integer getMongoPort() {
		return mongoPort;
	}

	@Override
	public String getDatabase() {
		return database;
	}
}
