package com.vin.iot.platform.infrared.service;

import com.vin.iot.platform.infrared.domain.InfraredDeviceType;

import java.util.List;

public interface DeviceTypeService {
    List<InfraredDeviceType> getAllDeviceType();
}
