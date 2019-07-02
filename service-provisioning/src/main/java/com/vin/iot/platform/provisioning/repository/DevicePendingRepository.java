/*
 *
 *  Developed by Anhgv by VinGroup on 7/1/19, 11:58 AM
 *  Last modified 7/1/19, 11:58 AM.
 *  Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 *
 *
 */

package com.vin.iot.platform.provisioning.repository;

import com.vin.iot.platform.provisioning.domain.DevicePending;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author AnhGV
 */
@Repository
public interface DevicePendingRepository extends MongoRepository<DevicePending, String> {
    List<DevicePending> findAllByHardwareId(@Param("hardwareId") String hardwareId);

    DevicePending findFirstByHardwareIdOrderByCreatedAtAsc(String hardwareId, String status);
}
