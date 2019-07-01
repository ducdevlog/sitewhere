package com.vin.iot.platform.infrared.repository;

import com.vin.iot.platform.infrared.domain.IrCodeRaw;

import java.util.List;

public interface IrCodeRawRepositoryCustom {
    List<IrCodeRaw> getIrCodeRaws(IrCodeRaw irCodeRaw);
}
