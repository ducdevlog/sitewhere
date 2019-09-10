/*
 *
 *  Developed by Anhgv by VinGroup on 8/28/19, 5:21 PM
 *  Last modified 8/28/19, 5:21 PM.
 *  Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 *
 *
 */

package com.vin.iot.platform.infrared.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vin.iot.platform.infrared.aspect.LogAround;
import com.vin.iot.platform.infrared.service.IrCodeRawService;
import com.vin.iot.platform.infrared.utils.ResponseFactory;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("infrared")
public class IrCodeRawController {
    @Autowired
    private IrCodeRawService irCodeRawService;

    ObjectMapper mapper = new ObjectMapper();

    @ApiOperation(value = "Get Infrared Code Raw")
    @GetMapping(value = "/irCodeRaw", produces = "application/json")
    @LogAround(message = "Get Infrared Code Raw")
    public ResponseEntity getInfraredCodeRaw(
            @ApiParam(value = "Infrared Code Raw Filter", required = false) @RequestParam(required = false) String irCodeRawFilter,
            @ApiParam(value = "Page", required = false) @RequestParam(required = false, defaultValue = "0") int page,
            @ApiParam(value = "Size", required = false) @RequestParam(required = false, defaultValue = "500") int size) {
        try {
            Map map = mapper.readValue(irCodeRawFilter, Map.class);
            return ResponseFactory.success(irCodeRawService.getIrCodeRawFilter(map, page, size));
        } catch (IOException e) {
            log.error("Error parser Json", new Throwable(e));
        }
        return ResponseFactory.error(HttpStatus.BAD_REQUEST, "", 404);
    }

    @ApiOperation(value = "Save Infrared Code Raw")
    @PostMapping(value = "/irCodeRaw", produces = "application/json")
    @LogAround(message = "Save Infrared Code Raw")
    public ResponseEntity createInfraredCodeRaw(
            @ApiParam(value = "Infrared Code Raw Filter", required = false) @RequestParam(required = false) String irCodeRawFilter) {
        try {
            Map map = mapper.readValue(irCodeRawFilter, Map.class);
            return ResponseFactory.success(irCodeRawService.createInfraredCodeRaw(map));
        } catch (IOException e) {
            log.error("Error parser Json", new Throwable(e));
        }
        return ResponseFactory.error(HttpStatus.BAD_REQUEST, "", 404);

    }

}
