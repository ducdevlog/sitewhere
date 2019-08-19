package com.vin.iot.platform.infrared.repository;

import com.vin.iot.platform.infrared.domain.InfraredDeviceTypeBrand;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeviceTypeBrandRepository extends MongoRepository<InfraredDeviceTypeBrand, String> {
    List<InfraredDeviceTypeBrand> findAllByType(String type);

    List<InfraredDeviceTypeBrand> findByTypeAndBrand(String type, String brand);

    InfraredDeviceTypeBrand findTopByOrderByIdDesc();
}
