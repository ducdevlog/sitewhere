package com.vin.iot.platform.infrared.service;

import com.vin.iot.platform.infrared.domain.InfraredDeviceCodeset;

import java.util.List;

public interface DeviceCodesetService {
    List<InfraredDeviceCodeset> getDeviceCodesetByDeviceTypeBrandId(String deviceTypeBrandId);

    List<InfraredDeviceCodeset> getDeviceCodesetByDeviceTypeBrandIdAndCodesetName(String deviceTypeBrandId, String codesetName);

    InfraredDeviceCodeset createInfraredDeviceCodeset(InfraredDeviceCodeset infraredDeviceCodeset);
}
