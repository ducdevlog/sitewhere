package com.sitewhere.spi.certificate;

import java.util.Date;
import java.util.UUID;

/**
 * @author AnhGV
 * Date 6/3/2019
 */
public interface ICertificate {
    UUID getId();

    // Organization (O)
    String getOrganization();

    // Organizational Unit (OU)
    String getOrganizationalUnit();

    // Country (C)
    String getCountry();

    // State (S)
    String getState();

    // Locality (L)
    String getLocality();

    // Common Name (CN)
    String getCommonName();

    // Serial Number
    Long getSerialNumber();

    // Key size
    int getKeySize();

    // surname
    String getSurname();

    // givenName
    String getGivenName();

    // userId
    String getUserId();

    // start Date
    Date getStartDate();

    // end Date
    Date getEndDate();

    String getAliasUserId();

    String getPrivateKey();

    String getPublicKey();

    String getCertificateKey();
}
