package com.sitewhere.spi.certificate;

/**
 * @author AnhGV
 * Date 6/3/2019
 */
public interface IX509RevokedCertificate {
    public byte[] getX509Bytes();

    public Long getSerialNumber();

    public boolean isCa();
}
