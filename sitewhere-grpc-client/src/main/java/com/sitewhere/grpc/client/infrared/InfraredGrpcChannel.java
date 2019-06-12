/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.grpc.client.infrared;

import com.sitewhere.grpc.client.MultitenantGrpcChannel;
import com.sitewhere.grpc.service.InfraredGrpc;
import com.sitewhere.grpc.service.InfraredGrpc.InfraredBlockingStub;
import com.sitewhere.grpc.service.InfraredGrpc.InfraredStub;
import com.sitewhere.spi.tracing.ITracerProvider;

public class InfraredGrpcChannel extends MultitenantGrpcChannel<InfraredBlockingStub, InfraredStub> {

    public InfraredGrpcChannel(ITracerProvider tracerProvider, String hostname, int port) {
        super(tracerProvider, hostname, port);
    }

    @Override
    public InfraredBlockingStub createBlockingStub() {
        return InfraredGrpc.newBlockingStub(getChannel());
    }

    @Override
    public InfraredStub createAsyncStub() {
        return InfraredGrpc.newStub(getChannel());
    }
}
