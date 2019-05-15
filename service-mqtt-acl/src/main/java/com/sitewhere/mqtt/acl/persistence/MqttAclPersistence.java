/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.mqtt.acl.persistence;

import java.util.UUID;

import com.sitewhere.rest.model.mqtt.MqttAcl;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.device.IDevice;
import com.sitewhere.spi.device.IDeviceAssignment;
import com.sitewhere.spi.mqtt.request.IMqttAclCreateRequest;

/**
 * Common methods needed by device state management implementations.
 *
 * @author Derek
 */
public class MqttAclPersistence {

    /**
     * Common logic for creating new device state object and populating it from
     * request.
     *
     * @param request
     * @param device
     * @param deviceAssignment
     * @return
     * @throws SiteWhereException
     */
    public static MqttAcl deviceStateCreateLogic(IMqttAclCreateRequest request, IDevice device,
                                                 IDeviceAssignment deviceAssignment) throws SiteWhereException {
        MqttAcl state = new MqttAcl();
        return state;
    }

    /**
     * Common logic for updating an existing device state object.
     *
     * @param request
     * @param target
     * @throws SiteWhereException
     */
    public static void deviceStateUpdateLogic(IMqttAclCreateRequest request, MqttAcl target)
            throws SiteWhereException {
    }
}
