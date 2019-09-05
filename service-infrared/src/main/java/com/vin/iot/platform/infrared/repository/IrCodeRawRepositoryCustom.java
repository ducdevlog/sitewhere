package com.vin.iot.platform.infrared.repository;

import com.vin.iot.platform.infrared.domain.IrCodeRaw;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface IrCodeRawRepositoryCustom {
    Page<IrCodeRaw> getIrCodeRaws(IrCodeRaw irCodeRaw, int page, int size);

    Page<Map> getIrCodeRawFilter(Map<String, String> irCodeRawFilter, int page, int size);

    Map saveIrCodeRaw(Map<String, String> irCodeRawMapping);
}
