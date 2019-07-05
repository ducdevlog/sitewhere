package com.vin.iot.platform.infrared.repository;

import com.vin.iot.platform.infrared.domain.IrCodeRaw;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IrCodeRawRepository extends PagingAndSortingRepository<IrCodeRaw, String>, IrCodeRawRepositoryCustom {

}
