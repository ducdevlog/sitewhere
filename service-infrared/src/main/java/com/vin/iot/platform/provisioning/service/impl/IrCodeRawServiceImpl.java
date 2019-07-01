package com.vin.iot.platform.infrared.service.impl;

import com.vin.iot.platform.infrared.domain.IrCodeRaw;
import com.vin.iot.platform.infrared.repository.IrCodeRawRepository;
import com.vin.iot.platform.infrared.service.IrCodeRawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IrCodeRawServiceImpl implements IrCodeRawService {
    @Autowired
    private IrCodeRawRepository irCodeRawRepository;

    @Override
    public List<IrCodeRaw> getIrCodeRaw(IrCodeRaw irCodeRaw) {
        return irCodeRawRepository.getIrCodeRaws(irCodeRaw);
    }
}
