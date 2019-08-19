/*
 *
 *  Developed by Anhgv by VinGroup on 8/12/19, 11:25 AM
 *  Last modified 8/12/19, 11:25 AM.
 *  Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 *
 *
 */

package com.vin.iot.platform.infrared.service.impl;

import com.vin.iot.platform.infrared.domain.IrCodeRawLearn;
import com.vin.iot.platform.infrared.repository.IrCodeRawLearnRepository;
import com.vin.iot.platform.infrared.service.IrCodeRawLearnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author AnhGV
 */
@Service
public class IrCodeRawLearnServiceImpl implements IrCodeRawLearnService {
    @Autowired
    private IrCodeRawLearnRepository irCodeRawLearnRepository;

    @Override
    public void createIrCodeRawLearn(IrCodeRawLearn irCodeRawLearn) {
        irCodeRawLearnRepository.save(irCodeRawLearn);
    }
}
