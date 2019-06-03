/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.certificate.persistence.mongodb;

import com.sitewhere.mongodb.MongoConverter;
import com.sitewhere.rest.model.certificate.Certificate;
import com.sitewhere.rest.model.device.state.DeviceState;
import com.sitewhere.spi.certificate.ICertificate;
import com.sitewhere.spi.device.state.IDeviceState;
import org.bson.Document;

import java.util.*;

/**
 * Used to load or save assignment state to MongoDB.
 *
 * @author dadams
 */
public class MongoCertificate implements MongoConverter<ICertificate> {

    /**
     * Property for id
     */
    public static final String PROP_ID = "_id";

    /**
     * Property for device id
     */
    public static final String PROP_ORGANIZATION = "organization";

    /**
     * Property for device type id
     */
    public static final String PROP_ORGANIZATIONAL_UNIT = "organizationalUnit";

    /**
     * Property for device assignment id
     */
    public static final String PROP_COUNTRY = "country";

    /**
     * Property for customer id
     */
    public static final String PROP_STATE = "state";

    /**
     * Property for area id
     */
    public static final String PROP_LOCALITY = "locality";

    /**
     * Property for asset id
     */
    public static final String PROP_COMMON_NAME = "commonName";

    /**
     * Property for last interaction date
     */
    public static final String PROP_SERIAL_NUMBER = "serialNumber";

    /**
     * Property for presence missing date
     */
    public static final String PROP_KEY_SIZE = "keySize";

    /**
     * Property for latest measurements
     */
    public static final String PROP_SURNAME = "surname";

    /**
     * Property for latest measurements
     */
    public static final String PROP_GIVEN_NAME = "givenName";

    /**
     * Attribute name for map element name
     */
    public static final String PROP_USER_ID = "userId";

    /**
     * Attribute name for map element value
     */
    public static final String PROP_START_DATE = "startDate";
    public static final String PROP_END_DATE = "endDate";

    /**
     * Copy information from SPI into Mongo {@link Document}.
     *
     * @param source
     * @param target
     */
    public static void toDocument(ICertificate source, Document target) {
        target.append(PROP_ID, source.getId());
        target.append(PROP_ORGANIZATION, source.getOrganization());
        target.append(PROP_ORGANIZATIONAL_UNIT, source.getOrganizationalUnit());
        target.append(PROP_COUNTRY, source.getCountry());
        target.append(PROP_STATE, source.getState());
        target.append(PROP_LOCALITY, source.getLocality());
        target.append(PROP_COMMON_NAME, source.getCommonName());
        target.append(PROP_SERIAL_NUMBER, source.getSerialNumber());
        target.append(PROP_KEY_SIZE, source.getKeySize());
        target.append(PROP_SURNAME, source.getSurname());
        target.append(PROP_GIVEN_NAME, source.getGivenName());
        target.append(PROP_USER_ID, source.getUserId());
        target.append(PROP_START_DATE, source.getStartDate());
        target.append(PROP_END_DATE, source.getEndDate());
    }

    /**
     * Copy information from Mongo {@link Document} to model object.
     *
     * @param source
     * @param target
     */
    @SuppressWarnings("unchecked")
    public static void fromDocument(Document source, Certificate target) {
        UUID id = (UUID) source.get(PROP_ID);
        String organization = (String) source.get(PROP_ORGANIZATION);
        String organizationalUnit = (String) source.get(PROP_ORGANIZATIONAL_UNIT);
        String country = (String) source.get(PROP_COUNTRY);
        String state = (String) source.get(PROP_STATE);
        String locality = (String) source.get(PROP_LOCALITY);
        String commonName = (String) source.get(PROP_COMMON_NAME);
        String serialNumber = (String) source.get(PROP_SERIAL_NUMBER);
        Integer keySize = (Integer) source.get(PROP_KEY_SIZE);
        String surname = (String) source.get(PROP_SURNAME);
        String givenName = (String) source.get(PROP_GIVEN_NAME);
        String userId = (String) source.get(PROP_USER_ID);
        Date startDate = (Date) source.get(PROP_START_DATE);
        Date endDate = (Date) source.get(PROP_END_DATE);

        target.setId(id);
        target.setOrganization(organization);
        target.setOrganizationalUnit(organizationalUnit);
        target.setCountry(country);
        target.setState(state);
        target.setLocality(locality);
        target.setCommonName(commonName);
        target.setSerialNumber(serialNumber);
        target.setKeySize(keySize);
        target.setSurname(surname);
        target.setGivenName(givenName);
        target.setUserId(userId);
        target.setStartDate(startDate);
        target.setEndDate(endDate);
    }

    /**
     * Convert SPI object to Mongo {@link Document}.
     *
     * @param source
     * @return
     */
    public static Document toDocument(ICertificate source) {
        Document result = new Document();
        MongoCertificate.toDocument(source, result);
        return result;
    }

    /**
     * Convert a {@link Document} into the SPI equivalent.
     *
     * @param source
     * @return
     */
    public static Certificate fromDocument(Document source) {
        Certificate result = new Certificate();
        MongoCertificate.fromDocument(source, result);
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sitewhere.mongodb.MongoConverter#convert(java.lang.Object)
     */
    @Override
    public Document convert(ICertificate source) {
        return MongoCertificate.toDocument(source);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sitewhere.mongodb.MongoConverter#convert(org.bson.Document)
     */
    @Override
    public ICertificate convert(Document source) {
        return MongoCertificate.fromDocument(source);
    }
}