/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.grpc.client.certificate;

import com.sitewhere.grpc.client.GrpcUtils;
import com.sitewhere.grpc.client.MultitenantApiChannel;
import com.sitewhere.grpc.client.spi.IApiDemux;
import com.sitewhere.grpc.client.spi.client.ICertificateApiChannel;
import com.sitewhere.grpc.service.*;
import com.sitewhere.rest.model.certificate.Certificate;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.certificate.ICertificate;
import com.sitewhere.spi.certificate.request.ICertificateCreateRequest;
import com.sitewhere.spi.tracing.ITracerProvider;

public class CertificateApiChannel extends MultitenantApiChannel<CertificateGrpcChannel>
	implements ICertificateApiChannel<CertificateGrpcChannel> {

    public CertificateApiChannel(IApiDemux<?> demux, String hostname, int port) {
        super(demux, hostname, port);
    }

    @Override
    public CertificateGrpcChannel createGrpcChannel(ITracerProvider tracerProvider, String host, int port) {
        return new CertificateGrpcChannel(tracerProvider, host, port);
    }


    @Override
    public ICertificate createCertificateRoot(ICertificateCreateRequest request) throws SiteWhereException {
        try {
            GrpcUtils.handleClientMethodEntry(this, CertificateGrpc.getCreateCertificateRootMethod());
            GCreateCertificateRootRequest.Builder grequest = GCreateCertificateRootRequest.newBuilder();
            grequest.setRequest(CertificateModelConverter.asGrpcCertificateCreateRequest(request));
            GCreateCertificateRootResponse gresponse = getGrpcChannel().getBlockingStub().createCertificateRoot(grequest.build());
            ICertificate response = (gresponse.hasCertificate())
                    ? CertificateModelConverter.asApiCertificate(gresponse.getCertificate())
                    : null;
            GrpcUtils.logClientMethodResponse(CertificateGrpc.getCreateCertificateRootMethod(), response);
            return response;
        } catch (Throwable t) {
            throw GrpcUtils.handleClientMethodException(CertificateGrpc.getCreateCertificateRootMethod(), t);
        }
    }

    @Override
    public ICertificate createCertificate(ICertificateCreateRequest request) throws SiteWhereException {
        try {
            GrpcUtils.handleClientMethodEntry(this, CertificateGrpc.getCreateCertificateMethod());
            GCreateCertificateRequest.Builder grequest = GCreateCertificateRequest.newBuilder();
            grequest.setRequest(CertificateModelConverter.asGrpcCertificateCreateRequest(request));
            GCreateCertificateResponse gresponse = getGrpcChannel().getBlockingStub().createCertificate(grequest.build());
            ICertificate response = (gresponse.hasCertificate())
                    ? CertificateModelConverter.asApiCertificate(gresponse.getCertificate())
                    : null;
            GrpcUtils.logClientMethodResponse(CertificateGrpc.getCreateCertificateRootMethod(), response);
            return response;
        } catch (Throwable t) {
            throw GrpcUtils.handleClientMethodException(CertificateGrpc.getCreateCertificateRootMethod(), t);
        }
    }

    @Override
    public Certificate revokeCertificate(String serialNumber) throws SiteWhereException {
        return null;
    }
}
