/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.mqtt.acl.spi.microservice;

import com.sitewhere.mqtt.acl.spi.grpc.IMqttAclGrpcServer;
import com.sitewhere.grpc.client.spi.client.IDeviceEventManagementApiDemux;
import com.sitewhere.grpc.client.spi.client.IDeviceManagementApiDemux;
import com.sitewhere.spi.microservice.MicroserviceIdentifier;
import com.sitewhere.spi.microservice.multitenant.IMultitenantMicroservice;

/**
 * Microservice that provides device state management functionality.
 *
 * @author Derek
 */
public interface IMqttAclMicroservice
        extends IMultitenantMicroservice<MicroserviceIdentifier, IMqttAclTenantEngine> {

    /**
     * Get device state GRPC server.
     *
     * @return
     */
    public IMqttAclGrpcServer getMqttAclGrpcServer();

    /**
     * Get device management API access via GRPC channel.
     *
     * @return
     */
}