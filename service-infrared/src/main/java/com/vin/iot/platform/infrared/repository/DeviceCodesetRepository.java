package com.vin.iot.platform.infrared.repository;

import com.vin.iot.platform.infrared.domain.DeviceCodeset;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeviceCodesetRepository extends MongoRepository<DeviceCodeset, String> {
    List<DeviceCodeset> findAllByDeviceTypeBrandId(String deviceTypeBrandId);
}
