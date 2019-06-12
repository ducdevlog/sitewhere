package com.sitewhere.rest.model.infrared;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sitewhere.spi.infrared.IInfraredDeviceTypeBrand;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfraredDeviceTypeBrand implements IInfraredDeviceTypeBrand {
    private String id;
    private String type;
    private String brand;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}