package com.vin.iot.platform.infrared.service;

import com.vin.iot.platform.infrared.domain.InfraredDeviceCodeset;

import java.util.List;

public interface DeviceCodesetService {
    List<InfraredDeviceCodeset> getDeviceCodesetByDeviceTypeBrandId(String deviceTypeBrandId);

    InfraredDeviceCodeset createInfraredDeviceCodeset(InfraredDeviceCodeset infraredDeviceCodeset);
}
