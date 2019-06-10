/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.certificate.grpc;

import com.sitewhere.certificate.spi.microservice.ICertificateMicroservice;
import com.sitewhere.grpc.client.GrpcUtils;
import com.sitewhere.grpc.client.certificate.CertificateModelConverter;
import com.sitewhere.grpc.client.spi.server.IGrpcApiImplementation;
import com.sitewhere.grpc.service.*;
import com.sitewhere.spi.certificate.ICertificate;
import com.sitewhere.spi.certificate.ICertificateManagement;
import com.sitewhere.spi.certificate.IX509RevokedCertificate;
import com.sitewhere.spi.certificate.request.ICertificateCreateRequest;
import com.sitewhere.spi.microservice.IMicroservice;
import io.grpc.stub.StreamObserver;

/**
 * Implements server logic for certificate GRPC requests.
 *
 * @author Derek
 */
public class CertificateImpl extends CertificateGrpc.CertificateImplBase implements IGrpcApiImplementation {

    /**
     * Parent microservice
     */
    private ICertificateMicroservice microservice;

    /**
     * Certificate management persistence
     */
    private ICertificateManagement certificateManagement;

    public CertificateImpl(ICertificateMicroservice microservice, ICertificateManagement certificateManagement) {
        this.microservice = microservice;
        this.certificateManagement = certificateManagement;
    }

    public void createCertificate(GCreateCertificateRequest request, StreamObserver<GCreateCertificateResponse> responseObserver) {
        try {
            GrpcUtils.handleServerMethodEntry(this, CertificateGrpc.getCreateCertificateMethod());
            ICertificateCreateRequest apiRequest = CertificateModelConverter.asApiCertificateCreateRequest(request.getRequest());
            ICertificate apiResult = getCertificateManagement().createCertificate(apiRequest);
            GCreateCertificateResponse.Builder response = GCreateCertificateResponse.newBuilder();
            response.setCertificate(CertificateModelConverter.asGrpcCertificate(apiResult));
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();
        } catch (Throwable e) {
            GrpcUtils.handleServerMethodException(CertificateGrpc.getCreateCertificateMethod(), e, responseObserver);
        } finally {
            GrpcUtils.handleServerMethodExit(CertificateGrpc.getCreateCertificateMethod());
        }
    }

    public void createCertificateRoot(GCreateCertificateRootRequest request, StreamObserver<GCreateCertificateRootResponse> responseObserver) {
        try {
            GrpcUtils.handleServerMethodEntry(this, CertificateGrpc.getCreateCertificateRootMethod());
            ICertificateCreateRequest apiRequest = CertificateModelConverter.asApiCertificateCreateRequest(request.getRequest());
            ICertificate apiResult = getCertificateManagement().createCertificateRoot(apiRequest);
            GCreateCertificateRootResponse.Builder response = GCreateCertificateRootResponse.newBuilder();
            response.setCertificate(CertificateModelConverter.asGrpcCertificate(apiResult));
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();
        } catch (Throwable e) {
            GrpcUtils.handleServerMethodException(CertificateGrpc.getCreateCertificateRootMethod(), e, responseObserver);
        } finally {
            GrpcUtils.handleServerMethodExit(CertificateGrpc.getCreateCertificateRootMethod());
        }
    }

    public void verifyCertificate(GVerifyCertificateRequest request, StreamObserver<GVerifyCertificateResponse> responseObserver) {
        try {
            GrpcUtils.handleServerMethodEntry(this, CertificateGrpc.getVerifyCertificateMethod());
            ICertificate apiResult = getCertificateManagement().verifyCertificate(request.getSerialNumber());
            GVerifyCertificateResponse.Builder response = GVerifyCertificateResponse.newBuilder();
            response.setCertificate(CertificateModelConverter.asGrpcCertificate(apiResult));
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();
        } catch (Throwable e) {
            GrpcUtils.handleServerMethodException(CertificateGrpc.getVerifyCertificateMethod(), e, responseObserver);
        } finally {
            GrpcUtils.handleServerMethodExit(CertificateGrpc.getVerifyCertificateMethod());
        }
    }

    public void getCertificate(GGetCertificateRequest request, StreamObserver<GGetCertificateResponse> responseObserver) {
        try {
            GrpcUtils.handleServerMethodEntry(this, CertificateGrpc.getGetCertificateMethod());
            ICertificate apiResult = getCertificateManagement().getCertificate(request.getSerialNumber());
            GGetCertificateResponse.Builder response = GGetCertificateResponse.newBuilder();
            response.setCertificate(CertificateModelConverter.asGrpcCertificate(apiResult));
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();
        } catch (Throwable e) {
            GrpcUtils.handleServerMethodException(CertificateGrpc.getGetCertificateMethod(), e, responseObserver);
        } finally {
            GrpcUtils.handleServerMethodExit(CertificateGrpc.getGetCertificateMethod());
        }
    }

    public void revokeCertificate(GRevokeCertificateRequest request, StreamObserver<GRevokeCertificateResponse> responseObserver) {
        try {
            GrpcUtils.handleServerMethodEntry(this, CertificateGrpc.getGetCertificateMethod());
            IX509RevokedCertificate apiRequest = CertificateModelConverter.asApiX509RevokedCertificateCreateRequest(request.getRequest());
            getCertificateManagement().revokeCertificate(apiRequest);
            GRevokeCertificateResponse.Builder response = GRevokeCertificateResponse.newBuilder();
            response.setX509RevokedCertificate(CertificateModelConverter.asGrpcX509RevokedCertificate(apiRequest));
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();
        } catch (Throwable e) {
            GrpcUtils.handleServerMethodException(CertificateGrpc.getGetCertificateMethod(), e, responseObserver);
        } finally {
            GrpcUtils.handleServerMethodExit(CertificateGrpc.getGetCertificateMethod());
        }
    }


    @Override
    public IMicroservice<?> getMicroservice() {
        return microservice;
    }

    protected ICertificateManagement getCertificateManagement() {
        return certificateManagement;
    }
}