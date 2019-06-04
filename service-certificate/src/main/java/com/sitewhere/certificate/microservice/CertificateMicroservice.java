/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.certificate.microservice;

import com.sitewhere.certificate.configuration.CertificateModelProvider;
import com.sitewhere.certificate.grpc.CertificateGrpcServer;
import com.sitewhere.certificate.spi.grpc.ICertificateGrpcServer;
import com.sitewhere.certificate.spi.microservice.ICertificateMicroservice;
import com.sitewhere.certificate.spi.microservice.ICertificateTenantEngine;
import com.sitewhere.grpc.client.ApiChannelNotAvailableException;
import com.sitewhere.microservice.multitenant.MultitenantMicroservice;
import com.sitewhere.server.lifecycle.CompositeLifecycleStep;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.messages.SiteWhereMessage;
import com.sitewhere.spi.microservice.MicroserviceIdentifier;
import com.sitewhere.spi.microservice.configuration.model.IConfigurationModel;
import com.sitewhere.spi.server.lifecycle.ICompositeLifecycleStep;
import com.sitewhere.spi.server.lifecycle.ILifecycleProgressMonitor;
import com.sitewhere.spi.tenant.ITenant;

/**
 * Microservice that provides certificate mangagement functionality.
 *
 * @author Derek
 */
public class CertificateMicroservice extends MultitenantMicroservice<MicroserviceIdentifier, ICertificateTenantEngine>
        implements ICertificateMicroservice {

    /**
     * Microservice name
     */
    private static final String NAME = "Certificate";

    @Override
    public ICertificateGrpcServer getCertificateGrpcServer() {
        return certificateGrpcServer;
    }

    public void setCertificateGrpcServer(ICertificateGrpcServer certificateGrpcServer) {
        this.certificateGrpcServer = certificateGrpcServer;
    }

    /**
     * Provides server for device management GRPC requests
     */
    private ICertificateGrpcServer certificateGrpcServer;

    /*
     * @see com.sitewhere.spi.microservice.IMicroservice#getName()
     */
    @Override
    public String getName() {
        return NAME;
    }

    /*
     * @see com.sitewhere.spi.microservice.IMicroservice#getIdentifier()
     */
    @Override
    public MicroserviceIdentifier getIdentifier() {
        return MicroserviceIdentifier.Certificate;
    }

    /*
     * @see com.sitewhere.spi.microservice.IMicroservice#isGlobal()
     */
    @Override
    public boolean isGlobal() {
        return false;
    }

    /*
     * @see com.sitewhere.spi.microservice.IMicroservice#buildConfigurationModel()
     */
    @Override
    public IConfigurationModel buildConfigurationModel() {
        return new CertificateModelProvider().buildModel();
    }

    /*
     * @see com.sitewhere.spi.microservice.multitenant.IMultitenantMicroservice#
     * createTenantEngine(com.sitewhere.spi.tenant.ITenant)
     */
    @Override
    public ICertificateTenantEngine createTenantEngine(ITenant tenant) throws SiteWhereException {
        return new CertificateTenantEngine(tenant);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sitewhere.microservice.Microservice#afterMicroserviceStarted()
     */
    @Override
    public void afterMicroserviceStarted() {
        try {
            waitForDependenciesAvailable();
            getLogger().debug("All required microservices detected as available.");
        } catch (ApiChannelNotAvailableException e) {
            getLogger().error(SiteWhereMessage.MICROSERVICE_NOT_AVAILABLE);
            getLogger().error("Microservice not available.", e);

        }
    }

    /**
     * Wait for required microservices to become available.
     *
     * @throws ApiChannelNotAvailableException
     */
    protected void waitForDependenciesAvailable() throws ApiChannelNotAvailableException {
    }

    /*
     * @see com.sitewhere.microservice.multitenant.MultitenantMicroservice#
     * microserviceInitialize(com.sitewhere.spi.server.lifecycle.
     * ILifecycleProgressMonitor)
     */
    @Override
    public void microserviceInitialize(ILifecycleProgressMonitor monitor) throws SiteWhereException {
        // Create GRPC components.
        createGrpcComponents();

        // Composite step for initializing microservice.
        ICompositeLifecycleStep init = new CompositeLifecycleStep("Initialize " + getName());

        // Initialize device state GRPC server.
        init.addInitializeStep(this, getCertificateGrpcServer(), true);

        // Execute initialization steps.
        init.execute(monitor);
    }

    /*
     * @see com.sitewhere.microservice.multitenant.MultitenantMicroservice#
     * microserviceStart(com.sitewhere.spi.server.lifecycle.
     * ILifecycleProgressMonitor)
     */
    @Override
    public void microserviceStart(ILifecycleProgressMonitor monitor) throws SiteWhereException {
        // Composite step for starting microservice.
        ICompositeLifecycleStep start = new CompositeLifecycleStep("Start " + getName());

        // Start device state GRPC server.
        start.addStartStep(this, getCertificateGrpcServer(), true);

        // Execute startup steps.
        start.execute(monitor);
    }

    /*
     * @see com.sitewhere.microservice.multitenant.MultitenantMicroservice#
     * microserviceStop(com.sitewhere.spi.server.lifecycle.
     * ILifecycleProgressMonitor)
     */
    @Override
    public void microserviceStop(ILifecycleProgressMonitor monitor) throws SiteWhereException {
        // Composite step for stopping microservice.
        ICompositeLifecycleStep stop = new CompositeLifecycleStep("Stop " + getName());

        // Stop device state GRPC server.
        stop.addStopStep(this, getCertificateGrpcServer());

        // Execute shutdown steps.
        stop.execute(monitor);
    }

    /**
     * Create GRPC components required by the microservice.
     */
    private void createGrpcComponents() {
        // Create device state GRPC server.
        this.certificateGrpcServer = new CertificateGrpcServer(this);
    }


}