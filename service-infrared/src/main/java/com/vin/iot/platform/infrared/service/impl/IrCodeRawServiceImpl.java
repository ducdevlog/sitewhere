package com.vin.iot.platform.infrared.service.impl;

import com.vin.iot.platform.infrared.domain.IrCodeRaw;
import com.vin.iot.platform.infrared.repository.IrCodeRawRepository;
import com.vin.iot.platform.infrared.service.IrCodeRawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IrCodeRawServiceImpl implements IrCodeRawService {
    @Autowired
    private IrCodeRawRepository irCodeRawRepository;

    @Override
    public Page<IrCodeRaw> getIrCodeRaw(IrCodeRaw irCodeRaw, int page, int size) {
        return irCodeRawRepository.getIrCodeRaws(irCodeRaw, page, size);
    }
}
