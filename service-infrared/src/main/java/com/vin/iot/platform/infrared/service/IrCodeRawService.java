package com.vin.iot.platform.infrared.service;

import com.vin.iot.platform.infrared.domain.IrCodeRaw;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IrCodeRawService {
    Page<IrCodeRaw> getIrCodeRaw(IrCodeRaw irCodeRaw, int page, int size);
}
