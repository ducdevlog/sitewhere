package com.sitewhere.rest.model.certificate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sitewhere.spi.certificate.ICertificate;

import java.util.Date;
import java.util.UUID;

/**
 * @author AnhGV
 * Date 6/3/2019
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Certificate implements ICertificate {
    private UUID id;
    // Organization (O)
    private String organization;
    // Organizational Unit (OU)
    private String organizationalUnit;
    // Country (C)
    private String country;
    // State (S)
    private String state;
    // Locality (L)
    private String locality;
    // Common Name (CN)
    private String commonName;
    // Serial Number
    private String serialNumber;
    // Key size
    private int keySize;
    // surname
    private String surname;
    // givenName
    private String givenName;
    // userId
    private String userId;
    // start Date
    private Date startDate;
    // end Date
    private Date endDate;

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public String getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(String organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    @Override
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    @Override
    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    @Override
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public int getKeySize() {
        return keySize;
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
