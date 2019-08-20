package com.vin.iot.platform.infrared.repository;

import com.vin.iot.platform.infrared.domain.InfraredDeviceCodeset;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeviceCodesetRepository extends MongoRepository<InfraredDeviceCodeset, String> {
    List<InfraredDeviceCodeset> findAllByDeviceTypeBrandId(String deviceTypeBrandId);

    List<InfraredDeviceCodeset> findAllByDeviceTypeBrandIdAndCodesetName(String deviceTypeBrandId, String codesetName);
}
