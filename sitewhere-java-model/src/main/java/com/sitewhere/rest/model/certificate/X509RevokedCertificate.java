package com.sitewhere.rest.model.certificate;

import com.sitewhere.spi.certificate.IX509RevokedCertificate;

/**
 * @author AnhGV
 * Date 6/3/2019
 */
public class X509RevokedCertificate implements IX509RevokedCertificate {
    private byte[] x509Bytes;
    private Long serialNumber;
    private boolean ca;

    public byte[] getX509Bytes() {
        return x509Bytes;
    }

    public void setX509Bytes(byte[] x509Bytes) {
        this.x509Bytes = x509Bytes;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public boolean isCa() {
        return ca;
    }

    public void setCa(boolean ca) {
        this.ca = ca;
    }
}
