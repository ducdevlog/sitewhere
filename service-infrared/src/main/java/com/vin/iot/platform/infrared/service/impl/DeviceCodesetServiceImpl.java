package com.vin.iot.platform.infrared.service.impl;

import com.vin.iot.platform.infrared.domain.InfraredDeviceCodeset;
import com.vin.iot.platform.infrared.repository.DeviceCodesetRepository;
import com.vin.iot.platform.infrared.service.DeviceCodesetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceCodesetServiceImpl implements DeviceCodesetService {
    @Autowired
    private DeviceCodesetRepository deviceCodesetRepository;

    @Override
    public List<InfraredDeviceCodeset> getDeviceCodesetByDeviceTypeBrandId(String deviceTypeBrandId) {
        return deviceCodesetRepository.findAllByDeviceTypeBrandId(deviceTypeBrandId);
    }

    @Override
    public InfraredDeviceCodeset createInfraredDeviceCodeset(InfraredDeviceCodeset infraredDeviceCodeset) {
        return deviceCodesetRepository.save(infraredDeviceCodeset);
    }
}
