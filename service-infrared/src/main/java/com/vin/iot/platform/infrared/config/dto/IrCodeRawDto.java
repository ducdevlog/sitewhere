/*
 *
 *  Developed by Anhgv by VinGroup on 8/20/19, 11:11 AM
 *  Last modified 8/20/19, 11:11 AM.
 *  Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 *
 *
 */

package com.vin.iot.platform.infrared.config.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IrCodeRawDto {
    @JsonProperty("ID")
    private String id;
    @JsonProperty("CODESET_NAME")
    private String codesetName;
    @JsonProperty("FUNCTION_NAME")
    private String functionName;
    @JsonProperty("POWER")
    private String power;
    @JsonProperty("MODE")
    private String mode;
    @JsonProperty("FAN")
    private String fan;
    @JsonProperty("TEMP")
    private String temp;
    @JsonProperty("SWING")
    private String swing;
    @JsonProperty("TIMER")
    private String timer;
    @JsonProperty("TIMER_MINS")
    private String timerMins;
    @JsonProperty("SLEEP")
    private String sleep;
    @JsonProperty("SLEEP_MINS")
    private String sleepMins;
    @JsonProperty("LED")
    private String led;
    @JsonProperty("COMFORT")
    private String comfort;
    @JsonProperty("ECONO")
    private String econo;
    @JsonProperty("POWERFUL")
    private String powerful;
    @JsonProperty("IR_FREQ")
    private String irFreq;
    @JsonProperty("IR_CODE")
    private String irCode;
    @JsonProperty("AREA_TOKEN")
    private String areaToken;
}
