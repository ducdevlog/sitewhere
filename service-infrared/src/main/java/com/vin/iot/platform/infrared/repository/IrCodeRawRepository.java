package com.vin.iot.platform.infrared.repository;

import com.vin.iot.platform.infrared.domain.IrCodeRaw;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IrCodeRawRepository extends MongoRepository<IrCodeRaw, String>, IrCodeRawRepositoryCustom {

}
