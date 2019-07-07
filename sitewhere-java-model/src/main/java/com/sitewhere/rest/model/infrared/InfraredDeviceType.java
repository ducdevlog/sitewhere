package com.sitewhere.rest.model.infrared;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sitewhere.spi.infrared.IInfraredDeviceType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfraredDeviceType implements IInfraredDeviceType {
    private String id;

    private String typeName;

    private String imageUrl;

    private String iconOnOff;
    private String icon1D;
    private String icon2DOn;
    private String icon2DOff;
    private String icon3DOn;
    private String icon3DOff;
    private String typeCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

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

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
