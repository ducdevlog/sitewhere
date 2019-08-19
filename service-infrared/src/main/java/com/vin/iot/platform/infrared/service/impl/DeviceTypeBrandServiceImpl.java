package com.vin.iot.platform.infrared.service.impl;

import com.vin.iot.platform.infrared.domain.InfraredDeviceTypeBrand;
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
    public List<InfraredDeviceTypeBrand> getDeviceTypeBrandByType(String type) {
        return deviceTypeBrandRepository.findAllByType(type);
    }

    @Override
    public List<InfraredDeviceTypeBrand> getDeviceTypeBrandByTypeAndBrand(String type, String brand) {
        return deviceTypeBrandRepository.findByTypeAndBrand(type, brand);
    }

    @Override
    public InfraredDeviceTypeBrand createInfraredDeviceTypeBrand(InfraredDeviceTypeBrand infraredDeviceTypeBrand) {
        deviceTypeBrandRepository.save(infraredDeviceTypeBrand);
        return infraredDeviceTypeBrand;
    }

    @Override
    public Integer getMaxId() {
        InfraredDeviceTypeBrand infraredDeviceTypeBrand = deviceTypeBrandRepository.findFistOrderById();
        return Integer.valueOf(infraredDeviceTypeBrand.getId());
    }


}
