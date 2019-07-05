package com.sitewhere.rest.model.infrared;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sitewhere.spi.infrared.IInfraredDeviceType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfraredDeviceType implements IInfraredDeviceType {
    private String id;

    private String typeName;

    private String imageUrl;

    private String iconUrl;
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

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
