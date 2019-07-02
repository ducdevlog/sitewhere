/*
 *
 *  Developed by Anhgv by VinGroup on 7/1/19, 11:51 AM
 *  Last modified 7/1/19, 11:51 AM.
 *  Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 *
 *
 */

package com.vin.iot.platform.provisioning.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author AnhGV
 */
@Document(collection = "devicependings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DevicePending extends AuditModel{
    private String id;
    private String hardwareId;
    private String deviceType;
    private String status = "P";
    private boolean ssl;
    private String caUrl;
    private String privateUrl;
    private String publicUrl;
    private String deviceId;
    private String password;
    private Long jobId;
}
