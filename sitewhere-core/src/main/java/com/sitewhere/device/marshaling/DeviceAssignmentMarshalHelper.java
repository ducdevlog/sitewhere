/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.device.marshaling;

import com.sitewhere.rest.model.common.BrandedEntity;
import com.sitewhere.rest.model.common.Location;
import com.sitewhere.rest.model.device.marshaling.MarshaledArea;
import com.sitewhere.spi.area.IArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sitewhere.device.marshaling.invalid.InvalidAsset;
import com.sitewhere.rest.model.common.PersistentEntity;
import com.sitewhere.rest.model.device.marshaling.MarshaledDeviceAssignment;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.asset.IAsset;
import com.sitewhere.spi.asset.IAssetManagement;
import com.sitewhere.spi.device.IDevice;
import com.sitewhere.spi.device.IDeviceAssignment;
import com.sitewhere.spi.device.IDeviceManagement;

/**
 * Configurable helper class that allows DeviceAssignment model objects to be
 * created from IDeviceAssignment SPI objects.
 * 
 * @author dadams
 */
public class DeviceAssignmentMarshalHelper {

    /** Static logger instance */
    private static Logger LOGGER = LoggerFactory.getLogger(DeviceAssignmentMarshalHelper.class);

    /** Indicates whether device asset information is to be included */
    private boolean includeAsset = true;

    /** Indicates whether to include device information */
    private boolean includeDevice = false;

    /** Indicates whether to include customer information */
    private boolean includeCustomer = true;

    /** Indicates whether to include area information */
    private boolean includeArea = true;

    /** Indicates whether to include device type */
    private boolean includeDeviceType = true;

    /** Device management */
    private IDeviceManagement deviceManagement;

    /** Used to control marshaling of devices */
    private DeviceMarshalHelper deviceHelper;

    public DeviceAssignmentMarshalHelper(IDeviceManagement deviceManagement) {
	this.deviceManagement = deviceManagement;
    }

    /**
     * Convert the SPI object into a model object for marshaling.
     * 
     * @param source
     * @param assetManagement
     * @return
     * @throws SiteWhereException
     */
    public MarshaledDeviceAssignment convert(IDeviceAssignment source, IAssetManagement assetManagement)
	    throws SiteWhereException {
	MarshaledDeviceAssignment result = new MarshaledDeviceAssignment();
	result.setActiveDate(source.getActiveDate());
	result.setReleasedDate(source.getReleasedDate());
	result.setStatus(source.getStatus());
	PersistentEntity.copy(source, result);

	// If asset is assigned, look it up.
	result.setAssetId(source.getAssetId());
	if (source.getAssetId() != null) {
	    IAsset asset = assetManagement.getAsset(source.getAssetId());
	    if (asset == null) {
		LOGGER.warn("Device assignment has reference to non-existent asset.");
		asset = new InvalidAsset();
	    }
	    result.setAssetName(asset.getName());
	    result.setAssetImageUrl(asset.getImageUrl());
	    if (isIncludeAsset()) {
		result.setAsset(asset);
	    }
	}

	// If customer is assigned, look it up.
	result.setCustomerId(source.getCustomerId());
	if ((isIncludeCustomer()) && (source.getCustomerId() != null)) {
	    result.setCustomer(getDeviceManagement().getCustomer(source.getCustomerId()));
	}

	// If area is assigned, look it up.
	result.setAreaId(source.getAreaId());
	if ((isIncludeArea()) && (source.getAreaId() != null)) {
		MarshaledArea marshaledArea = new MarshaledArea();
		IArea area = getDeviceManagement().getArea(source.getAreaId());
		if (area != null) {
			PersistentEntity.copy(area, marshaledArea);
			marshaledArea.setAreaType(getDeviceManagement().getAreaType(area.getAreaTypeId()));
			marshaledArea.setAreaTypeId(area.getAreaTypeId());
			marshaledArea.setParentAreaId(area.getParentAreaId());
			marshaledArea.setName(area.getName());
			marshaledArea.setDescription(area.getDescription());
			marshaledArea.setBounds(Location.copy(area.getBounds()));
			marshaledArea.setGatewayId(area.getGatewayId());
			BrandedEntity.copy(area, marshaledArea);

			if (area.getParentAreaId() != null) {
				MarshaledArea marshaledParentArea = new MarshaledArea();
				IArea parentArea = getDeviceManagement().getArea(area.getParentAreaId());
				if (parentArea != null) {
					PersistentEntity.copy(parentArea, marshaledParentArea);
					marshaledParentArea.setAreaType(getDeviceManagement().getAreaType(parentArea.getAreaTypeId()));
					marshaledParentArea.setAreaTypeId(parentArea.getAreaTypeId());
					marshaledParentArea.setParentAreaId(parentArea.getParentAreaId());
					marshaledParentArea.setName(parentArea.getName());
					marshaledParentArea.setDescription(parentArea.getDescription());
					marshaledParentArea.setBounds(Location.copy(parentArea.getBounds()));
					marshaledParentArea.setGatewayId(parentArea.getGatewayId());
					BrandedEntity.copy(parentArea, marshaledParentArea);
				}
				marshaledArea.setParentArea(marshaledParentArea);
			}
		}
		result.setArea(marshaledArea);
	}

	// Add device information.
	result.setDeviceId(source.getDeviceId());
	if ((isIncludeDevice()) && (source.getDeviceId() != null)) {
	    IDevice device = getDeviceManagement().getDevice(source.getDeviceId());
	    if (device != null) {
		result.setDevice(getDeviceHelper().convert(device, assetManagement));
	    } else {
		LOGGER.error("Assignment references invalid device id.");
	    }
	}
	return result;
    }

    /**
     * Get the helper for marshaling device information.
     * 
     * @return
     */
    protected DeviceMarshalHelper getDeviceHelper() {
	if (deviceHelper == null) {
	    deviceHelper = new DeviceMarshalHelper(getDeviceManagement());
	    deviceHelper.setIncludeAssignment(false);
	    deviceHelper.setIncludeDeviceType(isIncludeDeviceType());
	}
	return deviceHelper;
    }

    public boolean isIncludeAsset() {
	return includeAsset;
    }

    public DeviceAssignmentMarshalHelper setIncludeAsset(boolean includeAsset) {
	this.includeAsset = includeAsset;
	return this;
    }

    public boolean isIncludeDevice() {
	return includeDevice;
    }

    public DeviceAssignmentMarshalHelper setIncludeDevice(boolean includeDevice) {
	this.includeDevice = includeDevice;
	return this;
    }

    public boolean isIncludeCustomer() {
	return includeCustomer;
    }

    public void setIncludeCustomer(boolean includeCustomer) {
	this.includeCustomer = includeCustomer;
    }

    public boolean isIncludeArea() {
	return includeArea;
    }

    public DeviceAssignmentMarshalHelper setIncludeArea(boolean includeArea) {
	this.includeArea = includeArea;
	return this;
    }

    public boolean isIncludeDeviceType() {
	return includeDeviceType;
    }

    public DeviceAssignmentMarshalHelper setIncludeDeviceType(boolean includeDeviceType) {
	this.includeDeviceType = includeDeviceType;
	return this;
    }

    public IDeviceManagement getDeviceManagement() {
	return deviceManagement;
    }

    public void setDeviceManagement(IDeviceManagement deviceManagement) {
	this.deviceManagement = deviceManagement;
    }
}