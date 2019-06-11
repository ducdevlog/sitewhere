package com.vin.iot.platform.infrared.service.impl;

import com.vin.iot.platform.infrared.domain.DeviceType;
import com.vin.iot.platform.infrared.repository.DeviceTypeRepository;
import com.vin.iot.platform.infrared.service.DeviceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceTypeServiceImpl implements DeviceTypeService {
    @Autowired
    private DeviceTypeRepository deviceTypeRepository;

    @Override
    public List<DeviceType> getAllDeviceType() {
        return deviceTypeRepository.findAll();
    }
}
