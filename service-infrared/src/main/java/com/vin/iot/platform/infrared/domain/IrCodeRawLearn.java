/*
 *
 *  Developed by Anhgv by VinGroup on 8/12/19, 11:10 AM
 *  Last modified 8/12/19, 10:20 AM.
 *  Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 *
 *
 */

package com.vin.iot.platform.infrared.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "ir_code_raw_learn")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IrCodeRawLearn {
    @Field("ID")
    private String id;
    @Field("CODESET_NAME")
    private String codesetName;
    @Field("FUNCTION_NAME")
    private String functionName;
    @Field("POWER")
    private String power;
    @Field("MODE")
    private String mode;
    @Field("FAN")
    private String fan;
    @Field("TEMP")
    private String temp;
    @Field("SWING")
    private String swing;
    @Field("TIMER")
    private String timer;
    @Field("TIMER_MINS")
    private String timerMins;
    @Field("SLEEP")
    private String sleep;
    @Field("SLEEP_MINS")
    private String sleepMins;
    @Field("LED")
    private String led;
    @Field("COMFORT")
    private String comfort;
    @Field("ECONO")
    private String econo;
    @Field("POWERFUL")
    private String powerful;
    @Field("IR_FREQ")
    private String irFreq;
    @Field("IR_CODE")
    private String irCode;
    @Field("TYPE_CODE")
    private String typeCode;
    @Field("BRAND_NAME")
    private String brandName;
    @Field("CLIENT_ID")
    private String clientId;
    @Field("AREA_TOKEN")
    private String areaToken;
}
