package com.vin.iot.platform.infrared.service;

import com.vin.iot.platform.infrared.domain.InfraredDeviceTypeBrand;

import java.util.List;

public interface DeviceTypeBrandService {
    List<InfraredDeviceTypeBrand> getDeviceTypeBrandByType(String type);

    InfraredDeviceTypeBrand createInfraredDeviceTypeBrand(InfraredDeviceTypeBrand asApiInfraredDeviceTypeBrand);
}
