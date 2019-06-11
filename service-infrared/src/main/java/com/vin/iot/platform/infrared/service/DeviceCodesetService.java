package com.vin.iot.platform.infrared.service;

import com.vin.iot.platform.infrared.domain.DeviceCodeset;

import java.util.List;

public interface DeviceCodesetService {
    List<DeviceCodeset> getDeviceCodesetByDeviceTypeBrandId(String deviceTypeBrandId);
}
