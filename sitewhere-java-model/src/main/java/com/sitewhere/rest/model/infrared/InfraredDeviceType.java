package com.sitewhere.rest.model.infrared;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sitewhere.spi.infrared.IInfraredDeviceType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfraredDeviceType implements IInfraredDeviceType {
    private String id;

    private String typeName;

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
}
