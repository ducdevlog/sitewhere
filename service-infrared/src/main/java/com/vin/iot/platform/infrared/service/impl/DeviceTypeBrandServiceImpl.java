package com.vin.iot.platform.infrared.service.impl;

import com.vin.iot.platform.infrared.domain.DeviceTypeBrand;
import com.vin.iot.platform.infrared.repository.DeviceTypeBrandRepository;
import com.vin.iot.platform.infrared.service.DeviceTypeBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceTypeBrandServiceImpl implements DeviceTypeBrandService {
    @Autowired
    private DeviceTypeBrandRepository deviceTypeBrandRepository;

    @Override
    public List<DeviceTypeBrand> getDeviceTypeBrandByType(String type) {
        return deviceTypeBrandRepository.findAllByType(type);
    }
}
