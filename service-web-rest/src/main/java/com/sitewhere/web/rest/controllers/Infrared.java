package com.sitewhere.web.rest.controllers;

import com.sitewhere.rest.model.infrared.IrCodeRaw;
import com.sitewhere.rest.model.search.SearchResults;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.infrared.*;
import com.sitewhere.spi.search.ISearchResults;
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
    public ISearchResults<IIrCodeRaw> getInfraredDeviceCodeset(
            @ApiParam(value = "Infrared Codeset Name", required = false) @RequestParam(required = false) String codesetName,
            @ApiParam(value = "Infrared Function Name", required = false) @RequestParam(required = false) String functionName,
            @ApiParam(value = "Infrared power", required = false) @RequestParam(required = false) String power,
            @ApiParam(value = "Infrared mode", required = false) @RequestParam(required = false) String mode,
            @ApiParam(value = "Infrared fan", required = false) @RequestParam(required = false) String fan,
            @ApiParam(value = "Infrared temp", required = false) @RequestParam(required = false) String temp,
            @ApiParam(value = "Infrared swing", required = false) @RequestParam(required = false) String swing,
            @ApiParam(value = "Infrared timer", required = false) @RequestParam(required = false) String timer,
            @ApiParam(value = "Infrared timer Delay", required = false) @RequestParam(required = false) String timerDelay,
            @ApiParam(value = "Infrared led", required = false) @RequestParam(required = false) String led,
            @ApiParam(value = "Infrared comfort", required = false) @RequestParam(required = false) String comfort,
            @ApiParam(value = "Infrared econo", required = false) @RequestParam(required = false) String econo,
            @ApiParam(value = "Infrared powerful", required = false) @RequestParam(required = false) String powerful,
            @ApiParam(value = "Infrared sleep", required = false) @RequestParam(required = false) String sleep,
            @ApiParam(value = "Infrared sleepMins", required = false) @RequestParam(required = false) String sleepMins,
            @ApiParam(value = "Infrared sleepMins", required = false) @RequestParam(required = false, defaultValue = "0") int page,
            @ApiParam(value = "Infrared sleepMins", required = false) @RequestParam(required = false, defaultValue = "200") int size
    ) throws SiteWhereException {
        IrCodeRaw irCodeRaw = new IrCodeRaw(codesetName, functionName, power, mode, fan, temp, swing, timer, timerDelay, led, comfort, econo, powerful, sleep, sleepMins);
        return getInfraredManagement().getIrCodeRaw(irCodeRaw, page, size);
    }

    private IInfraredManagement getInfraredManagement() {
        return getMicroservice().getInfraredApiDemux().getApiChannel();
    }
}
