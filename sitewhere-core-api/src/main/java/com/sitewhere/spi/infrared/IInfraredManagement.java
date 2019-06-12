/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.spi.infrared;

import com.sitewhere.rest.model.infrared.IrCodeRaw;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.certificate.ICertificate;
import com.sitewhere.spi.certificate.IX509RevokedCertificate;
import com.sitewhere.spi.certificate.request.ICertificateCreateRequest;
import com.sitewhere.spi.server.lifecycle.ITenantEngineLifecycleComponent;

import java.util.List;

public interface IInfraredManagement extends ITenantEngineLifecycleComponent {

    public List<IInfraredDeviceType> getInfraredDeviceType() throws SiteWhereException;

    public List<IInfraredDeviceTypeBrand> getInfraredDeviceTypeBrand(String type) throws SiteWhereException;

    public List<IInfraredDeviceCodeset> getInfraredDeviceCodeset(String deviceTypeBrandId) throws SiteWhereException;

    public List<IIrCodeRaw> getIrCodeRaw(IrCodeRaw irCodeRaw) throws SiteWhereException;
}