/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.rest.model.search.device;

import com.sitewhere.rest.model.search.SearchCriteria;
import com.sitewhere.spi.search.device.IDeviceTypeSearchCriteria;

public class DeviceTypeSearchCriteria extends SearchCriteria implements IDeviceTypeSearchCriteria {
    private String parentDeviceTypeToken;

    public DeviceTypeSearchCriteria(int pageNumber, int pageSize) {
        super(pageNumber, pageSize);
    }

    @Override
    public String getParentDeviceTypeToken() {
        return parentDeviceTypeToken;
    }

    public void setParentDeviceTypeToken(String parentDeviceTypeToken) {
        this.parentDeviceTypeToken = parentDeviceTypeToken;
    }
}
