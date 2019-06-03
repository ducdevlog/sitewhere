/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.grpc.client.certificate;

import com.sitewhere.grpc.client.MultitenantGrpcChannel;
import com.sitewhere.grpc.service.CertificateGrpc;
import com.sitewhere.grpc.service.CertificateGrpc.CertificateBlockingStub;
import com.sitewhere.grpc.service.CertificateGrpc.CertificateStub;
import com.sitewhere.spi.tracing.ITracerProvider;

public class CertificateGrpcChannel extends MultitenantGrpcChannel<CertificateBlockingStub, CertificateStub> {

    public CertificateGrpcChannel(ITracerProvider tracerProvider, String hostname, int port) {
        super(tracerProvider, hostname, port);
    }

    @Override
    public CertificateBlockingStub createBlockingStub() {
        return CertificateGrpc.newBlockingStub(getChannel());
    }

    @Override
    public CertificateStub createAsyncStub() {
        return CertificateGrpc.newStub(getChannel());
    }
}
