package com.sitewhere.grpc.client.certificate;

import com.google.protobuf.ByteString;
import com.sitewhere.grpc.model.CertificateModel;
import com.sitewhere.rest.model.certificate.Certificate;
import com.sitewhere.rest.model.certificate.X509RevokedCertificate;
import com.sitewhere.rest.model.certificate.request.CertificateCreateRequest;
import com.sitewhere.spi.certificate.IX509RevokedCertificate;
import com.sitewhere.spi.certificate.request.ICertificateCreateRequest;

import java.util.Date;

public class CertificateModelConverter {

    public static CertificateModel.GCertificateCreateRequest asGrpcCertificateCreateRequest(ICertificateCreateRequest api) {
        CertificateModel.GCertificateCreateRequest.Builder grpc = CertificateModel.GCertificateCreateRequest.newBuilder();
        grpc.setOrganization(api.getOrganization());
        grpc.setOrganizationalUnit(api.getOrganizationalUnit());
        grpc.setCountry(api.getCountry());
        grpc.setState(api.getState());
        grpc.setLocality(api.getLocality());
        grpc.setCommonName(api.getCommonName());
        grpc.setSerialNumber(api.getSerialNumber());
        return grpc.build();
    }

    public static ICertificateCreateRequest asApiCertificate(CertificateModel.GCertificateCreateRequest grpc) {
        CertificateCreateRequest api = new CertificateCreateRequest();
        api.setOrganization(grpc.getOrganization());
        api.setOrganizationalUnit(grpc.getOrganizationalUnit());
        api.setCountry(grpc.getCountry());
        api.setState(grpc.getState());
        api.setLocality(grpc.getLocality());
        api.setCommonName(grpc.getCommonName());
        api.setSerialNumber(grpc.getSerialNumber());
        return api;
    }

    public static CertificateModel.GCertificate asGrpcCertificate(Certificate api) {
        CertificateModel.GCertificate.Builder grpc = CertificateModel.GCertificate.newBuilder();
        grpc.setOrganization(api.getOrganization());
        grpc.setOrganizationalUnit(api.getOrganizationalUnit());
        grpc.setCountry(api.getCountry());
        grpc.setState(api.getState());
        grpc.setLocality(api.getLocality());
        grpc.setCommonName(api.getCommonName());
        grpc.setSerialNumber(api.getSerialNumber());
        grpc.setStartDate(api.getStartDate().getTime());
        grpc.setEndDate(api.getEndDate().getTime());
        return grpc.build();
    }

    public static Certificate asApiCertificate(CertificateModel.GCertificate grpc) {
        Certificate api = new Certificate();
        api.setOrganization(grpc.getOrganization());
        api.setOrganizationalUnit(grpc.getOrganizationalUnit());
        api.setCountry(grpc.getCountry());
        api.setState(grpc.getState());
        api.setLocality(grpc.getLocality());
        api.setCommonName(grpc.getCommonName());
        api.setSerialNumber(grpc.getSerialNumber());
        api.setStartDate(grpc.getStartDate() > 0 ? new Date(grpc.getStartDate()) : null);
        api.setEndDate(grpc.getEndDate() > 0 ? new Date(grpc.getEndDate()) : null);
        return api;
    }

    public static CertificateModel.GX509RevokedCertificateCreateRequest asGrpcX509RevokedCertificateCreateRequest(X509RevokedCertificate api) {
        CertificateModel.GX509RevokedCertificateCreateRequest.Builder grpc = CertificateModel.GX509RevokedCertificateCreateRequest.newBuilder();
        grpc.setSerialNumber(api.getSerialNumber());
        grpc.setX509Bytes(ByteString.copyFrom(api.getX509Bytes()));
        grpc.setCa(api.isCa());
        return grpc.build();
    }

    public static IX509RevokedCertificate asApiX509RevokedCertificateCreateRequest(CertificateModel.GX509RevokedCertificateCreateRequest grpc) {
        X509RevokedCertificate api = new X509RevokedCertificate();
        api.setSerialNumber(grpc.getSerialNumber());
        api.setX509Bytes(grpc.getX509Bytes().toByteArray());
        api.setCa(grpc.getCa());
        return api;
    }

    public static CertificateModel.GX509RevokedCertificate asGrpcX509RevokedCertificate(IX509RevokedCertificate api) {
        CertificateModel.GX509RevokedCertificate.Builder grpc = CertificateModel.GX509RevokedCertificate.newBuilder();
        grpc.setSerialNumber(api.getSerialNumber());
        grpc.setX509Bytes(ByteString.copyFrom(api.getX509Bytes()));
        grpc.setCa(api.isCa());
        return grpc.build();
    }
}
