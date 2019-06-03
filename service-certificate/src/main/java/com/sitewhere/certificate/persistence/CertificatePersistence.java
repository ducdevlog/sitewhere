/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.certificate.persistence;

import com.sitewhere.rest.model.certificate.Certificate;
import com.sitewhere.rest.model.device.state.DeviceState;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.certificate.ICertificate;
import com.sitewhere.spi.certificate.request.ICertificateCreateRequest;
import com.sitewhere.spi.device.IDevice;
import com.sitewhere.spi.device.IDeviceAssignment;
import com.sitewhere.spi.device.state.request.IDeviceStateCreateRequest;

import java.util.UUID;

/**
 * Common methods needed by device state management implementations.
 *
 * @author Derek
 */
public class CertificatePersistence {

    /**
     * Common logic for creating new device state object and populating it from
     * request.
     *
     * @param request
     * @return
     * @throws SiteWhereException
     */
    public static Certificate certificateCreateLogic(ICertificateCreateRequest request) throws SiteWhereException {
        Certificate state = new Certificate();
        state.setId(UUID.randomUUID());
        state.setOrganization(request.getOrganization());
        state.setOrganizationalUnit(request.getOrganizationalUnit());
        state.setCountry(request.getCountry());
        state.setState(request.getState());
        state.setLocality(request.getLocality());
        state.setCommonName(request.getCommonName());
        state.setSerialNumber(request.getSerialNumber());
        state.setKeySize(request.getKeySize());
        state.setSurname(request.getSurname());
        state.setGivenName(request.getGivenName());
        state.setUserId(request.getUserId());
        return state;
    }

    /**
     * Common logic for updating an existing device state object.
     *
     * @param request
     * @param target
     * @throws SiteWhereException
     */
    public static void deviceStateUpdateLogic(IDeviceStateCreateRequest request, DeviceState target)
            throws SiteWhereException {
        target.setLastInteractionDate(request.getLastInteractionDate());
        target.setPresenceMissingDate(request.getPresenceMissingDate());
        if (request.getDeviceTypeId() != null) {
            target.setDeviceTypeId(request.getDeviceTypeId());
        }
        if (request.getCustomerId() != null) {
            target.setCustomerId(request.getCustomerId());
        }
        if (request.getAreaId() != null) {
            target.setAreaId(request.getAreaId());
        }
        if (request.getAssetId() != null) {
            target.setAssetId(request.getAssetId());
        }
        if (request.getLastLocationEventId() != null) {
            target.setLastLocationEventId(request.getLastLocationEventId());
        }
        if (request.getLastMeasurementEventIds() != null) {
            target.getLastMeasurementEventIds().putAll(request.getLastMeasurementEventIds());
        }
        if (request.getLastAlertEventIds() != null) {
            target.getLastAlertEventIds().putAll(request.getLastAlertEventIds());
        }
    }
}
