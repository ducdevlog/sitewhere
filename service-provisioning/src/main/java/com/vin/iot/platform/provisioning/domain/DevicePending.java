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

/**
 * @author AnhGV
 */
@Document(collection = "device_pending")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DevicePending {
    @Field("id")
    private String id;
    @Field("device_token")
    private String deviceToken;
    @Field("hardware_id")
    private String hardwareId;
    @Field("device_type")
    private String deviceType;
    @Field("status")
    private String status = "P";
    @Field("ssl")
    private boolean ssl;
    @Field("ca_url")
    private boolean caUrl;
    @Field("private_url")
    private boolean privateUrl;
    @Field("public_url")
    private boolean publicUrl;
    @Field("device_id")
    private boolean deviceId;
    @Field("password")
    private boolean password;
    @Field("job_id")
    private Long jobId;
}
