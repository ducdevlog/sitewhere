package com.sitewhere.rest.model.infrared;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sitewhere.spi.infrared.IInfraredDeviceCodeset;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfraredDeviceCodeset implements IInfraredDeviceCodeset {
    private String id;
    private String deviceTypeBrandId;
    private String codesetName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceTypeBrandId() {
        return deviceTypeBrandId;
    }

    public void setDeviceTypeBrandId(String deviceTypeBrandId) {
        this.deviceTypeBrandId = deviceTypeBrandId;
    }

    public String getCodesetName() {
        return codesetName;
    }

    public void setCodesetName(String codesetName) {
        this.codesetName = codesetName;
    }
}