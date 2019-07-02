/*
 *
 *  Developed by Anhgv by VinGroup on 7/1/19, 11:59 AM
 *  Last modified 7/1/19, 11:59 AM.
 *  Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 *
 *
 */

package com.vin.iot.platform.provisioning.service;

import com.vin.iot.platform.provisioning.domain.DevicePending;

import java.util.List;

/**
 * @author AnhGV
 */
public interface DevicePendingService {
    List<DevicePending> findByHardwareId(String hardwareId);

    DevicePending updateDevicePending(String hardwareId, String status);
}
