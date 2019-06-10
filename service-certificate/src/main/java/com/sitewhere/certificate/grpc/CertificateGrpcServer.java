/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.certificate.grpc;

import com.sitewhere.certificate.spi.grpc.ICertificateGrpcServer;
import com.sitewhere.certificate.spi.microservice.ICertificateMicroservice;
import com.sitewhere.microservice.grpc.MultitenantGrpcServer;

/**
 * Hosts a GRPC server that handles device state requests.
 *
 * @author Derek
 */
public class CertificateGrpcServer extends MultitenantGrpcServer implements ICertificateGrpcServer {

    public CertificateGrpcServer(ICertificateMicroservice microservice) {
        super(new CertificateRouter(microservice), microservice.getInstanceSettings().getGrpcPort());
    }
}