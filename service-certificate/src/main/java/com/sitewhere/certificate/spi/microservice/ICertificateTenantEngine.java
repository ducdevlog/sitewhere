/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.certificate.spi.microservice;

import com.sitewhere.grpc.service.CertificateGrpc;
import com.sitewhere.spi.certificate.ICertificateManagement;
import com.sitewhere.spi.microservice.multitenant.IMicroserviceTenantEngine;

/**
 * Extends {@link IMicroserviceTenantEngine} with features specific to device
 * state management.
 *
 * @author Derek
 */
public interface ICertificateTenantEngine extends IMicroserviceTenantEngine {

    public ICertificateManagement getCertificateManagement();

    public CertificateGrpc.CertificateImplBase getCertificateImpl();
}