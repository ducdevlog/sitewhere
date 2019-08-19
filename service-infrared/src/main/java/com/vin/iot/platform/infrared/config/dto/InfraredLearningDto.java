/*
 *
 *  Developed by Anhgv by VinGroup on 8/19/19, 12:36 PM
 *  Last modified 8/19/19, 12:36 PM.
 *  Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 *
 *
 */

package com.vin.iot.platform.infrared.config.dto;

import com.vin.iot.platform.infrared.domain.IrCodeRaw;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfraredLearningDto {
    private String gatewayId;
    private String typeCode;
    private String brandName;
    private String homeToken;
    private List<IrCodeRaw> lstData;
}
