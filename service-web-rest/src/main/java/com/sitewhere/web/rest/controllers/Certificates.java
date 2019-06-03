package com.sitewhere.web.rest.controllers;

import com.sitewhere.rest.model.certificate.request.CertificateCreateRequest;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.certificate.ICertificate;
import com.sitewhere.spi.certificate.ICertificateManagement;
import com.sitewhere.spi.user.SiteWhereRoles;
import com.sitewhere.web.annotation.SiteWhereCrossOrigin;
import com.sitewhere.web.rest.RestControllerBase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author AnhGV
 * Date 6/3/2019
 */
@RestController
@SiteWhereCrossOrigin
@RequestMapping(value = "/certificates")
@Api(value = "certificates")
public class Certificates  extends RestControllerBase {

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create new certificate")
    @Secured({ SiteWhereRoles.REST })
    public ICertificate createCertificate(@RequestBody CertificateCreateRequest request, HttpServletRequest servletRequest)
            throws SiteWhereException {
        return getCertificateManager().createCertificate(request);
    }

    @RequestMapping(value = "/root",method = RequestMethod.POST)
    @ApiOperation(value = "Create new certificate root")
    @Secured({ SiteWhereRoles.REST })
    public ICertificate createCertificateRoot(@RequestBody CertificateCreateRequest request, HttpServletRequest servletRequest)
            throws SiteWhereException {
        return getCertificateManager().createCertificateRoot(request);
    }

    private ICertificateManagement getCertificateManager() {
        return getMicroservice().getCertificateApiDemux().getApiChannel();
    }
}
