package com.vin.iot.platform.infrared.service;

import com.vin.iot.platform.infrared.domain.IrCodeRaw;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface IrCodeRawService {
    Page<IrCodeRaw> getIrCodeRaw(IrCodeRaw irCodeRaw, int page, int size);

    void createIrCodeRaw(IrCodeRaw apiRequest);

    Page<Map> getIrCodeRawFilter(Map<String, String> irCodeRawFilter, int page, int size);

    Map createInfraredCodeRaw(Map<String, String> irCodeRawMapping);
}
