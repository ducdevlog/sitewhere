/*
 *
 *  Developed by Anhgv by VinGroup on 8/15/19, 9:40 AM
 *  Last modified 6/13/19, 2:55 PM.
 *  Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 *
 *
 */

package com.vin.iot.platform.infrared.utils;

import com.vin.iot.platform.infrared.controller.dto.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseFactory {
    private ResponseFactory() {
    }

    public static <I> ResponseEntity<ServiceResponse<I>> success(I data) {
        ServiceResponse response = ServiceResponse.builder()
                .code(HttpStatus.OK.value())
                .success(true)
                .data(data).build();
        return ResponseEntity.ok(response);
    }

    public static <I> ResponseEntity<ServiceResponse<I>> success(HttpStatus status, I data) {
        ServiceResponse response = ServiceResponse.builder()
                .code(HttpStatus.OK.value())
                .success(true)
                .data(data).build();
        return new ResponseEntity<>(response, status);
    }
    
     public static ResponseEntity<ServiceResponse> success() {
        ServiceResponse response = ServiceResponse.builder()
                .code(HttpStatus.OK.value())
                .success(true)
                .data(null).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<ServiceResponse> error(HttpStatus status, String message, int code) {
        ServiceResponse response = ServiceResponse.builder()
                .code(code)
                .success(false)
                .message(message)
                .data(null).build();
        return new ResponseEntity<>(response, status);
    }
}
