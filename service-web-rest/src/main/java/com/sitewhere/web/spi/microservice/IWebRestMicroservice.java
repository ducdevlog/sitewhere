/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.web.spi.microservice;

import com.sitewhere.grpc.client.spi.client.*;
import com.sitewhere.grpc.client.spi.provider.ITenantManagementDemuxProvider;
import com.sitewhere.grpc.client.spi.provider.IUserManagementDemuxProvider;
import com.sitewhere.spi.microservice.IFunctionIdentifier;
import com.sitewhere.spi.microservice.IGlobalMicroservice;
import com.sitewhere.spi.microservice.state.ITopologyStateAggregator;

/**
 * Microservice that provides web/REST functionality.
 * 
 * @author Derek
 */
public interface IWebRestMicroservice<T extends IFunctionIdentifier>
	extends IGlobalMicroservice<T>, IUserManagementDemuxProvider<T>, ITenantManagementDemuxProvider<T> {

    /**
     * Device management API access via GRPC channel.
     * 
     * @return
     */
    public IDeviceManagementApiDemux getDeviceManagementApiDemux();

    /**
     * Device event management API access via GRPC channel.
     * 
     * @return
     */
    public IDeviceEventManagementApiDemux getDeviceEventManagementApiDemux();

    /**
     * Asset management API demux.
     * 
     * @return
     */
    public IAssetManagementApiDemux getAssetManagementApiDemux();

    /**
     * Batch management API access via GRPC channel.
     * 
     * @return
     */
    public IBatchManagementApiDemux getBatchManagementApiDemux();

    /**
     * Schedule management API access via GRPC channel.
     * 
     * @return
     */
    public IScheduleManagementApiDemux getScheduleManagementApiDemux();

    /**
     * Label generation API access via GRPC channel.
     * 
     * @return
     */
    public ILabelGenerationApiDemux getLabelGenerationApiDemux();

    /**
     * Device state API access via GRPC channel.
     * 
     * @return
     */
    public IDeviceStateApiDemux getDeviceStateApiDemux();

    /**
     * Get topology state aggregator.
     * 
     * @return
     */
    public ITopologyStateAggregator getTopologyStateAggregator();

    public IMqttAclApiDemux getMqttAclApiDemux();

    public ICertificateApiDemux getCertificateApiDemux();
}