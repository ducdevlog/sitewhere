/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.spi.certificate;

import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.certificate.request.ICertificateCreateRequest;
import com.sitewhere.spi.server.lifecycle.ITenantEngineLifecycleComponent;

public interface ICertificateManagement extends ITenantEngineLifecycleComponent {
    /**
     * Create device state.
     *
     * @param request
     * @return
     * @throws SiteWhereException
     */
    public ICertificate createCertificateRoot(ICertificateCreateRequest request) throws SiteWhereException;

    public ICertificate createCertificate(ICertificateCreateRequest request) throws SiteWhereException;

    public void revokeCertificate(IX509RevokedCertificate ix509RevokedCertificate) throws SiteWhereException;

    public ICertificate verifyCertificate(String serialNumber) throws SiteWhereException;

    public ICertificate getCertificate(String serialNumber) throws SiteWhereException;
}
