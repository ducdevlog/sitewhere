package com.vin.iot.platform.infrared.repository;

import com.vin.iot.platform.infrared.domain.InfraredDeviceType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceTypeRepository extends MongoRepository<InfraredDeviceType, String> {
}
