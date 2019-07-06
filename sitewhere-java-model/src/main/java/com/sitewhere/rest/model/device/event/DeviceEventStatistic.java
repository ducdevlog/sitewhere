/*
 *
 *  Developed by Anhgv by VinGroup on 7/5/19, 10:55 AM
 *  Last modified 7/5/19, 10:55 AM.
 *  Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 *
 *
 */

package com.sitewhere.rest.model.device.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sitewhere.rest.model.common.MetadataProvider;
import com.sitewhere.spi.device.event.IDeviceEventStatistic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author AnhGV
 */
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceEventStatistic extends MetadataProvider implements IDeviceEventStatistic, Serializable {
    private Double avgValue;
    private String eventDate;
    private int hour;
}
