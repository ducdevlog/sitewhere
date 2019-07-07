/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.rest.model.device.request;

import com.sitewhere.rest.model.common.request.PersistentEntityCreateRequest;
import com.sitewhere.spi.device.request.IDeviceStatusCreateRequest;

/**
 * Model object implementing {@link IDeviceStatusCreateRequest}.
 * 
 * @author Derek
 */
public class DeviceStatusCreateRequest extends PersistentEntityCreateRequest implements IDeviceStatusCreateRequest {

    /** Serial version UID */
    private static final long serialVersionUID = -1667891345754538713L;

    /** Token for device type */
    private String deviceTypeToken;

    /** Status code */
    private String code;

    /** Display name */
    private String name;

    /** Background color */
    private String backgroundColor;

    /** Foreground color */
    private String foregroundColor;

    /** Border color */
    private String borderColor;

    /** Icon */
    private String icon;

    private String iconOnOff;

    private String icon1D;
    private String icon2DOn;
    private String icon2DOff;
    private String icon3DOn;
    private String icon3DOff;

    /*
     * @see com.sitewhere.spi.device.request.IDeviceStatusCreateRequest#
     * getDeviceTypeToken()
     */
    @Override
    public String getDeviceTypeToken() {
	return deviceTypeToken;
    }

    public void setDeviceTypeToken(String deviceTypeToken) {
	this.deviceTypeToken = deviceTypeToken;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.request.IDeviceStatusCreateRequest#getCode()
     */
    @Override
    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.request.IDeviceStatusCreateRequest#getName()
     */
    @Override
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.request.IDeviceStatusCreateRequest#
     * getBackgroundColor()
     */
    @Override
    public String getBackgroundColor() {
	return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
	this.backgroundColor = backgroundColor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.request.IDeviceStatusCreateRequest#
     * getForegroundColor()
     */
    @Override
    public String getForegroundColor() {
	return foregroundColor;
    }

    public void setForegroundColor(String foregroundColor) {
	this.foregroundColor = foregroundColor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.request.IDeviceStatusCreateRequest#
     * getBorderColor()
     */
    @Override
    public String getBorderColor() {
	return borderColor;
    }

    public void setBorderColor(String borderColor) {
	this.borderColor = borderColor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.request.IDeviceStatusCreateRequest#getIcon()
     */
    @Override
    public String getIcon() {
	return icon;
    }

    public void setIcon(String icon) {
	this.icon = icon;
    }

    @Override
    public String getIconOnOff() {
        return iconOnOff;
    }

    public void setIconOnOff(String iconOnOff) {
        this.iconOnOff = iconOnOff;
    }

    @Override
    public String getIcon1D() {
        return icon1D;
    }

    public void setIcon1D(String icon1D) {
        this.icon1D = icon1D;
    }

    @Override
    public String getIcon2DOn() {
        return icon2DOn;
    }

    public void setIcon2DOn(String icon2DOn) {
        this.icon2DOn = icon2DOn;
    }

    @Override
    public String getIcon2DOff() {
        return icon2DOff;
    }

    public void setIcon2DOff(String icon2DOff) {
        this.icon2DOff = icon2DOff;
    }

    @Override
    public String getIcon3DOn() {
        return icon3DOn;
    }

    public void setIcon3DOn(String icon3DOn) {
        this.icon3DOn = icon3DOn;
    }

    @Override
    public String getIcon3DOff() {
        return icon3DOff;
    }

    public void setIcon3DOff(String icon3DOff) {
        this.icon3DOff = icon3DOff;
    }
}