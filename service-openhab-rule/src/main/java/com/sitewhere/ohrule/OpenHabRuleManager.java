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
