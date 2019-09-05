package com.vin.iot.platform.infrared.service.impl;

import com.vin.iot.platform.infrared.domain.IrCodeRaw;
import com.vin.iot.platform.infrared.repository.IrCodeRawRepository;
import com.vin.iot.platform.infrared.service.IrCodeRawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IrCodeRawServiceImpl implements IrCodeRawService {
    @Autowired
    private IrCodeRawRepository irCodeRawRepository;

    @Override
    public Page<IrCodeRaw> getIrCodeRaw(IrCodeRaw irCodeRaw, int page, int size) {
        return irCodeRawRepository.getIrCodeRaws(irCodeRaw, page, size);
    }

    @Override
    public void createIrCodeRaw(IrCodeRaw irCodeRaw) {
        irCodeRawRepository.save(irCodeRaw);
    }

    @Override
    public Page<Map> getIrCodeRawFilter(Map<String, String> irCodeRawFilter, int page, int size) {
        return irCodeRawRepository.getIrCodeRawFilter(irCodeRawFilter, page, size);
    }

    @Override
    public Map createInfraredCodeRaw(Map<String, String> irCodeRawMapping) {
        return irCodeRawRepository.saveIrCodeRaw(irCodeRawMapping);
    }
}
