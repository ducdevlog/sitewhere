/*
 *
 *  Developed by Anhgv by VinGroup on 7/1/19, 12:00 PM
 *  Last modified 7/1/19, 12:00 PM.
 *  Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 *
 *
 */

package com.vin.iot.platform.provisioning.service.impl;

import com.vin.iot.platform.provisioning.domain.DevicePending;
import com.vin.iot.platform.provisioning.repository.DevicePendingRepository;
import com.vin.iot.platform.provisioning.service.DevicePendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author AnhGV
 */
@Service
public class DevicePendingServiceImpl implements DevicePendingService {

    private final DevicePendingRepository devicePendingRepository;

    public DevicePendingServiceImpl(DevicePendingRepository devicePendingRepository) {
        this.devicePendingRepository = devicePendingRepository;
    }


    @Override
    public List<DevicePending> findByHardwareId(String hardwareId) {
        return devicePendingRepository.findAllByHardwareId(hardwareId);
    }

    @Override
    public DevicePending updateDevicePending(String hardwareId, String status) {
        DevicePending devicePending = devicePendingRepository.findFirstByHardwareIdOrderByCreatedAtAsc(hardwareId, "P");
        if (devicePending == null)
            return null;
        devicePending.setStatus(status);
        devicePendingRepository.save(devicePending);
        return devicePending;
    }
}
