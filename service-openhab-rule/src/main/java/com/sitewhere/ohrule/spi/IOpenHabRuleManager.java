/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

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
