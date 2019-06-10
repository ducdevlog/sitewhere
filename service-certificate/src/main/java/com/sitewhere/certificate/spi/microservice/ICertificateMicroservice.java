/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.certificate.spi.microservice;

import com.sitewhere.certificate.spi.grpc.ICertificateGrpcServer;
import com.sitewhere.grpc.client.spi.client.IDeviceEventManagementApiDemux;
import com.sitewhere.grpc.client.spi.client.IDeviceManagementApiDemux;
import com.sitewhere.grpc.service.CertificateGrpc;
import com.sitewhere.spi.microservice.MicroserviceIdentifier;
import com.sitewhere.spi.microservice.multitenant.IMultitenantMicroservice;

/**
 * Microservice that provides device state management functionality.
 *
 * @author Derek
 */
public interface ICertificateMicroservice
        extends IMultitenantMicroservice<MicroserviceIdentifier, ICertificateTenantEngine> {

    /**
     * Get device state GRPC server.
     *
     * @return
     */
    public ICertificateGrpcServer getCertificateGrpcServer();

}