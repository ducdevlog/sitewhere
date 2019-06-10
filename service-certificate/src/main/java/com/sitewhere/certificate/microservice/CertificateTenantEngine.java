/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.certificate.microservice;

import com.sitewhere.certificate.grpc.CertificateImpl;
import com.sitewhere.certificate.spi.microservice.ICertificateMicroservice;
import com.sitewhere.certificate.spi.microservice.ICertificateTenantEngine;
import com.sitewhere.grpc.service.CertificateGrpc;
import com.sitewhere.microservice.multitenant.MicroserviceTenantEngine;
import com.sitewhere.server.lifecycle.CompositeLifecycleStep;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.certificate.ICertificateManagement;
import com.sitewhere.spi.microservice.multitenant.IDatasetTemplate;
import com.sitewhere.spi.microservice.multitenant.IMicroserviceTenantEngine;
import com.sitewhere.spi.microservice.spring.CertificateManagementBeans;
import com.sitewhere.spi.server.lifecycle.ICompositeLifecycleStep;
import com.sitewhere.spi.server.lifecycle.ILifecycleProgressMonitor;
import com.sitewhere.spi.tenant.ITenant;

/**
 * Implementation of {@link IMicroserviceTenantEngine} that implements certificate
 * management functionality.
 *
 * @author Derek
 */
public class CertificateTenantEngine extends MicroserviceTenantEngine implements ICertificateTenantEngine {

    private ICertificateManagement certificateManagement;

    private CertificateGrpc.CertificateImplBase certificateImpl;

    public CertificateTenantEngine(ITenant tenant) {
        super(tenant);
    }

    /*
     * @see com.sitewhere.spi.microservice.multitenant.IMicroserviceTenantEngine#
     * tenantInitialize(com.sitewhere.spi.server.lifecycle.
     * ILifecycleProgressMonitor)
     */
    @Override
    public void tenantInitialize(ILifecycleProgressMonitor monitor) throws SiteWhereException {
        // Create management interfaces.
        ICertificateManagement implementation = (ICertificateManagement) getModuleContext()
                .getBean(CertificateManagementBeans.BEAN_CERTIFICATE_MANAGEMENT);
        this.certificateManagement = implementation;
        this.certificateImpl = new CertificateImpl((ICertificateMicroservice) getMicroservice(),
                getCertificateManagement());

        // Create step that will initialize components.
        ICompositeLifecycleStep init = new CompositeLifecycleStep("Initialize " + getComponentName());

        // Initialize discoverable lifecycle components.
        init.addStep(initializeDiscoverableBeans(getModuleContext()));

        // Initialize certificate management persistence.
        init.addInitializeStep(this, getCertificateManagement(), true);

        // Execute initialization steps.
        init.execute(monitor);
    }

    /*
     * @see com.sitewhere.spi.microservice.multitenant.IMicroserviceTenantEngine#
     * tenantStart(com.sitewhere.spi.server.lifecycle.ILifecycleProgressMonitor)
     */
    @Override
    public void tenantStart(ILifecycleProgressMonitor monitor) throws SiteWhereException {
        // Create step that will start components.
        ICompositeLifecycleStep start = new CompositeLifecycleStep("Start " + getComponentName());

        // Start discoverable lifecycle components.
        start.addStep(startDiscoverableBeans(getModuleContext()));

        // Start certificate management persistence.
        start.addStartStep(this, getCertificateManagement(), true);

        // Execute startup steps.
        start.execute(monitor);
    }

    /*
     * @see com.sitewhere.spi.microservice.multitenant.IMicroserviceTenantEngine#
     * tenantBootstrap(com.sitewhere.spi.microservice.multitenant.IDatasetTemplate,
     * com.sitewhere.spi.server.lifecycle.ILifecycleProgressMonitor)
     */
    @Override
    public void tenantBootstrap(IDatasetTemplate template, ILifecycleProgressMonitor monitor)
            throws SiteWhereException {
    }

    /*
     * @see com.sitewhere.spi.microservice.multitenant.IMicroserviceTenantEngine#
     * tenantStop(com.sitewhere.spi.server.lifecycle.ILifecycleProgressMonitor)
     */
    @Override
    public void tenantStop(ILifecycleProgressMonitor monitor) throws SiteWhereException {
        // Create step that will stop components.
        ICompositeLifecycleStep stop = new CompositeLifecycleStep("Stop " + getComponentName());

        // Stop certificate manager.
        stop.addStopStep(this, getCertificateManagement());

        // Stop discoverable lifecycle components.
        stop.addStep(stopDiscoverableBeans(getModuleContext()));

        // Execute shutdown steps.
        stop.execute(monitor);
    }

    @Override
    public ICertificateManagement getCertificateManagement() {
        return certificateManagement;
    }

    public void setCertificateManagement(ICertificateManagement certificateManagement) {
        this.certificateManagement = certificateManagement;
    }


    @Override
    public CertificateGrpc.CertificateImplBase getCertificateImpl() {
        return certificateImpl;
    }

    public void setCertificateImpl(CertificateGrpc.CertificateImplBase certificateImpl) {
        this.certificateImpl = certificateImpl;
    }
}