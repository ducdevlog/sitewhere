/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.mqtt.acl.microservice;

import com.sitewhere.mqtt.acl.grpc.MqttAclImpl;
import com.sitewhere.mqtt.acl.spi.microservice.IMqttAclMicroservice;
import com.sitewhere.mqtt.acl.spi.microservice.IMqttAclTenantEngine;
import com.sitewhere.grpc.service.MqttAclGrpc;
import com.sitewhere.microservice.multitenant.MicroserviceTenantEngine;
import com.sitewhere.server.lifecycle.CompositeLifecycleStep;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.microservice.multitenant.IDatasetTemplate;
import com.sitewhere.spi.microservice.multitenant.IMicroserviceTenantEngine;
import com.sitewhere.spi.microservice.spring.MqttAclManagementBeans;
import com.sitewhere.spi.mqtt.event.IMqttAclManagement;
import com.sitewhere.spi.server.lifecycle.ICompositeLifecycleStep;
import com.sitewhere.spi.server.lifecycle.ILifecycleProgressMonitor;
import com.sitewhere.spi.tenant.ITenant;

/**
 * Implementation of {@link IMicroserviceTenantEngine} that implements device
 * state management functionality.
 *
 * @author Derek
 */
public class MqttAclTenantEngine extends MicroserviceTenantEngine implements IMqttAclTenantEngine {

    /**
     * Device state management persistence API
     */
    private IMqttAclManagement mqttAclManagement;

    /**
     * Responds to device state GRPC requests
     */
    private MqttAclGrpc.MqttAclImplBase mqttAclImpl;

    public MqttAclTenantEngine(ITenant tenant) {
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
        IMqttAclManagement implementation = (IMqttAclManagement) getModuleContext()
                .getBean(MqttAclManagementBeans.BEAN_MQTT_ACL_MANAGEMENT);
        this.mqttAclManagement = implementation;
        this.mqttAclImpl = new MqttAclImpl((IMqttAclMicroservice) getMicroservice(),
                getMqttAclManagement());

        // Create step that will initialize components.
        ICompositeLifecycleStep init = new CompositeLifecycleStep("Initialize " + getComponentName());

        // Initialize discoverable lifecycle components.
        init.addStep(initializeDiscoverableBeans(getModuleContext()));

        // Initialize device state management persistence.
        init.addInitializeStep(this, getMqttAclManagement(), true);

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

        // Start device state management persistence.
        start.addStartStep(this, getMqttAclManagement(), true);

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

        // Stop device state management persistence.
        stop.addStopStep(this, getMqttAclManagement());

        // Stop discoverable lifecycle components.
        stop.addStep(stopDiscoverableBeans(getModuleContext()));

        // Execute shutdown steps.
        stop.execute(monitor);
    }

    /*
     * @see IMqttAclTenantEngine#
     * getMqttAclManagement()
     */
    @Override
    public IMqttAclManagement getMqttAclManagement() {
        return mqttAclManagement;
    }

    protected void setMqttAclManagement(IMqttAclManagement deviceStateManagement) {
        this.mqttAclManagement = deviceStateManagement;
    }

    /*
     * @see IMqttAclTenantEngine#
     * getMqttAclImpl()
     */
    @Override
    public MqttAclGrpc.MqttAclImplBase getMqttAclImpl() {
        return mqttAclImpl;
    }

    protected void setMqttAclImpl(MqttAclGrpc.MqttAclImplBase deviceStateImpl) {
        this.mqttAclImpl = deviceStateImpl;
    }
}