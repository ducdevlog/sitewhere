/*
 *
 *  Developed by Anhgv by VinGroup on 8/15/19, 9:43 AM
 *  Last modified 7/2/19, 5:25 PM.
 *  Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 *
 *
 */

package com.vin.iot.platform.infrared.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class ServiceResponse<T> {

    @ApiModelProperty(notes = "result of request: true/false")
    private boolean success;
    @ApiModelProperty(notes = "error code")
    private int code;
    @ApiModelProperty(notes = "data of response")
    private T data;
    @ApiModelProperty(notes = "message")
    private String message;

    public static <T> ServiceResponse<T> success(HttpStatus status, String message, T data) {
        return ServiceResponse.<T>builder()
                .success(true)
                .code(status.value())
                .message(message)
                .data(data)
                .build();
    }

}
