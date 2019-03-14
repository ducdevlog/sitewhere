package com.sitewhere.ohrule.spi;

import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.extended.event.kafka.IExtendedRequestPayload;
import com.sitewhere.spi.server.lifecycle.ITenantEngineLifecycleComponent;

public interface IOpenHabRuleManager extends ITenantEngineLifecycleComponent {
	String getMongoHost();
	Integer getMongoPort();
	String getDatabase();

	void handleUpdateRule(IExtendedRequestPayload extendedRequestPayload) throws SiteWhereException;
}
