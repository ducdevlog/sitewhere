package com.vin.iot.platform.infrared.service;

import com.vin.iot.platform.infrared.domain.IrCodeRaw;

import java.util.List;

public interface IrCodeRawService {
    List<IrCodeRaw> getIrCodeRaw(IrCodeRaw irCodeRaw);
}
