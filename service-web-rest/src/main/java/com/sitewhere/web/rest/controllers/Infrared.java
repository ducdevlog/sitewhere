package com.sitewhere.web.rest.controllers;

import com.sitewhere.rest.model.infrared.IrCodeRaw;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.infrared.*;
import com.sitewhere.spi.user.SiteWhereRoles;
import com.sitewhere.web.annotation.SiteWhereCrossOrigin;
import com.sitewhere.web.rest.RestControllerBase;
import groovy.util.logging.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SiteWhereCrossOrigin
@RequestMapping(value = "/infrared")
@Api(value = "infrared")
@Slf4j
public class Infrared extends RestControllerBase {

    @RequestMapping(value = "/deviceType", method = RequestMethod.GET)
    @ApiOperation(value = "Get list Infrared Device Type")
    @Secured({ SiteWhereRoles.REST })
    public List<IInfraredDeviceType> getInfraredDeviceType() throws SiteWhereException {
        return getInfraredManagement().getInfraredDeviceType();
    }

    @RequestMapping(value = "/deviceTypeBrand", method = RequestMethod.GET)
    @ApiOperation(value = "Get list Infrared Device Type Brand")
    @Secured({ SiteWhereRoles.REST })
    public List<IInfraredDeviceTypeBrand> getInfraredDeviceTypeBrand(@ApiParam(value = "Infrared Device Type", required = true) @RequestParam String type) throws SiteWhereException {
        return getInfraredManagement().getInfraredDeviceTypeBrand(type);
    }

    @RequestMapping(value = "/deviceCodeset", method = RequestMethod.GET)
    @ApiOperation(value = "Get list Infrared Device Codeset")
    @Secured({ SiteWhereRoles.REST })
    public List<IInfraredDeviceCodeset> getInfraredDeviceCodeset(@ApiParam(value = "Infrared Device Type Brand Id", required = true) @RequestParam String deviceTypeBrandId) throws SiteWhereException {
        return getInfraredManagement().getInfraredDeviceCodeset(deviceTypeBrandId);
    }

    @RequestMapping(value = "/irCodeRaw", method = RequestMethod.GET)
    @ApiOperation(value = "Get list Infrared Code Raw")
    @Secured({ SiteWhereRoles.REST })
    public List<IIrCodeRaw> getInfraredDeviceCodeset(@RequestBody IrCodeRaw irCodeRaw) throws SiteWhereException {
        return getInfraredManagement().getIrCodeRaw(irCodeRaw);
    }

    private IInfraredManagement getInfraredManagement() {
        return getMicroservice().getInfraredApiDemux().getApiChannel();
    }
}
