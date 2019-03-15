/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.spi.extended.event;

import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.extended.event.request.IExtendedCreateRequest;
import com.sitewhere.spi.server.lifecycle.ITenantEngineLifecycleComponent;

public interface IExtendedManagement extends ITenantEngineLifecycleComponent {
    /**
     * Create device state.
     *
     * @param request
     * @return
     * @throws SiteWhereException
     */
    public IExtended createExtended(IExtendedCreateRequest request) throws SiteWhereException;
}
