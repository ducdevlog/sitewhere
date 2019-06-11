package com.vin.iot.platform.infrared.service;

import com.vin.iot.platform.infrared.domain.DeviceTypeBrand;

import java.util.List;

public interface DeviceTypeBrandService {
    List<DeviceTypeBrand> getDeviceTypeBrandByType(String type);
}
