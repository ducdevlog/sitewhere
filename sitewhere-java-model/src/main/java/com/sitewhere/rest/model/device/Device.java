/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.rest.model.device;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sitewhere.rest.model.common.PersistentEntity;
import com.sitewhere.spi.device.IDevice;
import com.sitewhere.spi.device.IDeviceElementMapping;
import com.sitewhere.spi.device.IListItemName;
import com.sitewhere.spi.device.element.IDeviceElementSchema;

/**
 * Model object for device information.
 * 
 * @author dadams
 */
@JsonInclude(Include.NON_NULL)
public class Device extends PersistentEntity implements IDevice {

    /** Serialization version identifier */
    private static final long serialVersionUID = -5409798557113797549L;

    /** Device type id */
    private UUID deviceTypeId;

    /** Id for current assignment if assigned */
    private UUID deviceAssignmentId;

    /** Parent device id (if nested) */
    private UUID parentDeviceId;

    /** Mappings of {@link IDeviceElementSchema} paths to hardware ids */
    private List<DeviceElementMapping> deviceElementMappings = new ArrayList<DeviceElementMapping>();

    /** Comments */
    private String comments;

    /** Status indicator */
    private String status;


    /** Gateway id of Device*/
    private String gatewayId;

    /** Hardware id of Device*/
    private String hardwareId;

    /** Gateway map Item Channel Link*/
    private Map<String, List<String>> itemChannelLink;

    /** Gateway map Configuration Gateway*/
    private Map<String, String> configurationGateway;

    /** Action delete*/
    @JsonIgnore
    private boolean delete;

    private String areaName;

    private String areaToken;

    /*
     * @see com.sitewhere.spi.device.IDevice#getDeviceTypeId()
     */
    @Override
    public UUID getDeviceTypeId() {
	return deviceTypeId;
    }

    public void setDeviceTypeId(UUID deviceTypeId) {
	this.deviceTypeId = deviceTypeId;
    }

    /*
     * @see com.sitewhere.spi.device.IDevice#getDeviceAssignmentId()
     */
    @Override
    public UUID getDeviceAssignmentId() {
	return deviceAssignmentId;
    }

    public void setDeviceAssignmentId(UUID deviceAssignmentId) {
	this.deviceAssignmentId = deviceAssignmentId;
    }

    /*
     * @see com.sitewhere.spi.device.IDevice#getParentDeviceId()
     */
    @Override
    public UUID getParentDeviceId() {
	return parentDeviceId;
    }

    public void setParentDeviceId(UUID parentDeviceId) {
	this.parentDeviceId = parentDeviceId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.IDevice#getDeviceElementMappings()
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<IDeviceElementMapping> getDeviceElementMappings() {
	return (List<IDeviceElementMapping>) (List<? extends IDeviceElementMapping>) deviceElementMappings;
    }

    public void setDeviceElementMappings(List<DeviceElementMapping> deviceElementMappings) {
	this.deviceElementMappings = deviceElementMappings;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.IDevice#getComments()
     */
    @Override
    public String getComments() {
	return comments;
    }

    public void setComments(String comments) {
	this.comments = comments;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.IDevice#getStatus()
     */
    @Override
    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    @Override
    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    @Override
    public Map<String, List<String>> getItemChannelLink() {
        return itemChannelLink;
    }

    public void setItemChannelLink(Map<String, List<String>> itemChannelLink) {
        this.itemChannelLink = itemChannelLink;
    }

    @Override
    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public String getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
    }

    public Map<String, String> getConfigurationGateway() {
        return configurationGateway;
    }

    public void setConfigurationGateway(Map<String, String> configurationGateway) {
        this.configurationGateway = configurationGateway;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaToken() {
        return areaToken;
    }

    public void setAreaToken(String areaToken) {
        this.areaToken = areaToken;
    }
}