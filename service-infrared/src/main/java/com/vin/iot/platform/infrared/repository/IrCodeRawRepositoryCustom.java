package com.vin.iot.platform.infrared.repository;

import com.vin.iot.platform.infrared.domain.IrCodeRaw;
import org.springframework.data.domain.Page;

public interface IrCodeRawRepositoryCustom {
    Page<IrCodeRaw> getIrCodeRaws(IrCodeRaw irCodeRaw, int page, int size);
}
