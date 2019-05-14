/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.spi.mqtt.event;

import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.mqtt.IMqttAcl;
import com.sitewhere.spi.mqtt.IMqttUser;
import com.sitewhere.spi.mqtt.request.IMqttAclCreateRequest;
import com.sitewhere.spi.mqtt.request.IMqttUserCreateRequest;
import com.sitewhere.spi.server.lifecycle.ITenantEngineLifecycleComponent;

public interface IMqttAclManagement extends ITenantEngineLifecycleComponent {
    /**
     * Create device state.
     *
     * @param request
     * @return
     * @throws SiteWhereException
     */
    public IMqttAcl createMqttAcl(IMqttAclCreateRequest request) throws SiteWhereException;
    public IMqttUser createMqttUser (IMqttUserCreateRequest request) throws SiteWhereException;
}
