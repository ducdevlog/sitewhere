/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.grpc.client.mqtt;

import com.sitewhere.grpc.client.MultitenantGrpcChannel;
import com.sitewhere.grpc.service.MqttAclGrpc;
import com.sitewhere.grpc.service.MqttAclGrpc.MqttAclStub;
import com.sitewhere.grpc.service.MqttAclGrpc.MqttAclBlockingStub;
import com.sitewhere.spi.tracing.ITracerProvider;

public class MqttAclGrpcChannel  extends MultitenantGrpcChannel<MqttAclBlockingStub, MqttAclStub> {

    public MqttAclGrpcChannel(ITracerProvider tracerProvider, String hostname, int port) {
        super(tracerProvider, hostname, port);
    }

    @Override
    public MqttAclBlockingStub createBlockingStub() {
        return MqttAclGrpc.newBlockingStub(getChannel());
    }

    @Override
    public MqttAclStub createAsyncStub() {
        return MqttAclGrpc.newStub(getChannel());
    }
}
