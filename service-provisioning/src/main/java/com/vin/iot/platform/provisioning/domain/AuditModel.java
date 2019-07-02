/*
 *
 *  Developed by Anhgv by VinGroup on 7/2/19, 10:37 AM
 *  Last modified 7/2/19, 10:37 AM.
 *  Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 *
 *
 */

package com.vin.iot.platform.provisioning.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author AnhGV
 */
@Data
class AuditModel {
    @CreatedDate
    private Date createdAt = new Date();

    @CreatedBy
    private String createdBy;


}
