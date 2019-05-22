/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.mqtt.acl.spi.microservice;

import com.sitewhere.grpc.service.MqttAclGrpc;
import com.sitewhere.spi.microservice.multitenant.IMicroserviceTenantEngine;
import com.sitewhere.spi.mqtt.event.IMqttAclManagement;

/**
 * Extends {@link IMicroserviceTenantEngine} with features specific to device
 * state management.
 *
 * @author Derek
 */
public interface IMqttAclTenantEngine extends IMicroserviceTenantEngine {

    /**
     * Get associated device state management implementation.
     *
     * @return
     */
    public IMqttAclManagement getMqttAclManagement();

    /**
     * Get implementation class that wraps device state with GRPC conversions.
     *
     * @return
     */
    public MqttAclGrpc.MqttAclImplBase getMqttAclImpl();
}