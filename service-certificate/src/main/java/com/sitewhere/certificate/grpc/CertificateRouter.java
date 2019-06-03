/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.certificate.grpc;

import com.sitewhere.certificate.spi.microservice.ICertificateMicroservice;
import com.sitewhere.certificate.spi.microservice.ICertificateTenantEngine;
import com.sitewhere.grpc.client.GrpcContextKeys;
import com.sitewhere.grpc.client.GrpcUtils;
import com.sitewhere.grpc.client.spi.server.IGrpcRouter;
import com.sitewhere.grpc.service.*;
import com.sitewhere.spi.microservice.multitenant.TenantEngineNotAvailableException;
import io.grpc.stub.StreamObserver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.UUID;

/**
 * Routes GRPC calls to service implementations in tenants.
 *
 * @author Derek
 */
public class CertificateRouter extends CertificateGrpc.CertificateImplBase
        implements IGrpcRouter<CertificateGrpc.CertificateImplBase> {

    /**
     * Static logger instance
     */
    @SuppressWarnings("unused")
    private static Log LOGGER = LogFactory.getLog(CertificateRouter.class);

    /**
     * Parent microservice
     */
    private ICertificateMicroservice microservice;

    public CertificateRouter(ICertificateMicroservice microservice) {
        this.microservice = microservice;
    }

    /*
     * @see com.sitewhere.spi.grpc.IGrpcRouter#getTenantImplementation()
     */
    @Override
    public CertificateGrpc.CertificateImplBase getTenantImplementation(StreamObserver<?> observer) {
        String tenantId = GrpcContextKeys.TENANT_ID_KEY.get();
        if (tenantId == null) {
            throw new RuntimeException("Tenant id not found in certificate request.");
        }
        try {
            ICertificateTenantEngine engine = getMicroservice().assureTenantEngineAvailable(UUID.fromString(tenantId));
            return engine.getCertificateImpl();
        } catch (TenantEngineNotAvailableException e) {
            observer.onError(GrpcUtils.convertServerException(e));
            return null;
        }
    }

    @Override
    public void createCertificate(GCreateCertificateRequest request, StreamObserver<GCreateCertificateResponse> responseObserver) {
        CertificateGrpc.CertificateImplBase engine = getTenantImplementation(responseObserver);
        if (engine != null) {
            engine.createCertificate(request, responseObserver);
        }
    }

    @Override
    public void createCertificateRoot(GCreateCertificateRootRequest request, StreamObserver<GCreateCertificateRootResponse> responseObserver) {
        CertificateGrpc.CertificateImplBase engine = getTenantImplementation(responseObserver);
        if (engine != null) {
            engine.createCertificateRoot(request, responseObserver);
        }
    }

    public void verifyCertificate(GVerifyCertificateRequest request, StreamObserver<GVerifyCertificateResponse> responseObserver) {
        CertificateGrpc.CertificateImplBase engine = getTenantImplementation(responseObserver);
        if (engine != null) {
            engine.verifyCertificate(request, responseObserver);
        }
    }

    public void getCertificate(GGetCertificateRequest request, StreamObserver<GGetCertificateResponse> responseObserver) {
        CertificateGrpc.CertificateImplBase engine = getTenantImplementation(responseObserver);
        if (engine != null) {
            engine.getCertificate(request, responseObserver);
        }
    }

    public void revokeCertificate(GRevokeCertificateRequest request, StreamObserver<GRevokeCertificateResponse> responseObserver) {
        CertificateGrpc.CertificateImplBase engine = getTenantImplementation(responseObserver);
        if (engine != null) {
            engine.revokeCertificate(request, responseObserver);
        }
    }

    protected ICertificateMicroservice getMicroservice() {
        return microservice;
    }

    protected void setMicroservice(ICertificateMicroservice microservice) {
        this.microservice = microservice;
    }
}
