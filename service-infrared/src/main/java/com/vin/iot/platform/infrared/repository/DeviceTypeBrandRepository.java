package com.vin.iot.platform.infrared.repository;

import com.vin.iot.platform.infrared.domain.DeviceTypeBrand;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeviceTypeBrandRepository extends MongoRepository<DeviceTypeBrand, String> {
    List<DeviceTypeBrand> findAllByType(String type);
}
