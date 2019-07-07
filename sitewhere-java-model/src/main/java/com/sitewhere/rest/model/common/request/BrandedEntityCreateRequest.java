/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.rest.model.common.request;

import com.sitewhere.spi.common.request.IBrandedEntityCreateRequest;

/**
 * Contains information used to supply branding information for a persistent
 * entity.
 * 
 * @author Derek
 */
public class BrandedEntityCreateRequest extends PersistentEntityCreateRequest implements IBrandedEntityCreateRequest {

    /** Serial version UID */
    private static final long serialVersionUID = -4035409913746835268L;

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

    /** Image URL */
    private String imageUrl;

    /*
     * @see com.sitewhere.spi.common.IColorProvider#getBackgroundColor()
     */
    @Override
    public String getBackgroundColor() {
	return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
	this.backgroundColor = backgroundColor;
    }

    /*
     * @see com.sitewhere.spi.common.IColorProvider#getForegroundColor()
     */
    @Override
    public String getForegroundColor() {
	return foregroundColor;
    }

    public void setForegroundColor(String foregroundColor) {
	this.foregroundColor = foregroundColor;
    }

    /*
     * @see com.sitewhere.spi.common.IColorProvider#getBorderColor()
     */
    @Override
    public String getBorderColor() {
	return borderColor;
    }

    public void setBorderColor(String borderColor) {
	this.borderColor = borderColor;
    }

    /*
     * @see com.sitewhere.spi.common.IIconProvider#getIcon()
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

    public String getIcon1D() {
        return icon1D;
    }

    public void setIcon1D(String icon1D) {
        this.icon1D = icon1D;
    }

    public String getIcon2DOn() {
        return icon2DOn;
    }

    public void setIcon2DOn(String icon2DOn) {
        this.icon2DOn = icon2DOn;
    }

    public String getIcon2DOff() {
        return icon2DOff;
    }

    public void setIcon2DOff(String icon2DOff) {
        this.icon2DOff = icon2DOff;
    }

    public String getIcon3DOn() {
        return icon3DOn;
    }

    public void setIcon3DOn(String icon3DOn) {
        this.icon3DOn = icon3DOn;
    }

    public String getIcon3DOff() {
        return icon3DOff;
    }

    public void setIcon3DOff(String icon3DOff) {
        this.icon3DOff = icon3DOff;
    }

    /*
     * @see com.sitewhere.spi.common.IImageProvider#getImageUrl()
     */
    @Override
    public String getImageUrl() {
	return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
    }
}
