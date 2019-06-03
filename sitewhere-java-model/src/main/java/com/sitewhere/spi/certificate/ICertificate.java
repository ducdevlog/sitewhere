package com.sitewhere.spi.certificate;

import java.util.Date;

/**
 * @author AnhGV
 * Date 6/3/2019
 */
public interface ICertificate {
    // Organization (O)
    public String getOrganization();
    // Organizational Unit (OU)
    public String getOrganizationalUnit();
    // Country (C)
    public String getCountry();
    // State (S)
    public String getState();
    // Locality (L)
    public String getLocality();
    // Common Name (CN)
    public String getCommonName();
    // Serial Number
    public String getSerialNumber();
    // Key size
    public int getKeySize();
    // surname
    public String getSurname();
    // givenName
    public String getGivenName();
    // userId
    public String getUserId();
    // start Date
    public Date getStartDate();
    // end Date
    public Date getEndDate();
}
