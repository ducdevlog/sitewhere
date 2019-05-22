/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.mqtt.acl.grpc;

import com.sitewhere.mqtt.acl.spi.grpc.IMqttAclGrpcServer;
import com.sitewhere.mqtt.acl.spi.microservice.IMqttAclMicroservice;
import com.sitewhere.microservice.grpc.MultitenantGrpcServer;

/**
 * Hosts a GRPC server that handles device state requests.
 *
 * @author Derek
 */
public class MqttAclGrpcServer extends MultitenantGrpcServer implements IMqttAclGrpcServer {

    public MqttAclGrpcServer(IMqttAclMicroservice microservice) {
        super(new MqttAclRouter(microservice), microservice.getInstanceSettings().getGrpcPort());
    }
}