/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.media.persistence.cassandra;

import java.util.UUID;

import com.sitewhere.cassandra.CassandraClient;
import com.sitewhere.server.lifecycle.TenantEngineLifecycleComponent;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.device.streaming.IDeviceStreamData;
import com.sitewhere.spi.device.streaming.IDeviceStreamDataManagement;
import com.sitewhere.spi.device.streaming.request.IDeviceStreamDataCreateRequest;
import com.sitewhere.spi.error.ErrorCode;
import com.sitewhere.spi.search.IDateRangeSearchCriteria;
import com.sitewhere.spi.search.ISearchResults;
import com.sitewhere.spi.server.lifecycle.ILifecycleProgressMonitor;
import com.sitewhere.spi.server.lifecycle.LifecycleComponentType;

/**
 * Implementation of {@link IDeviceStreamDataManagement} that stores data in
 * Apache Cassandra.
 * 
 * @author Derek
 */
public class CassandraDeviceStreamManagement extends TenantEngineLifecycleComponent
	implements IDeviceStreamDataManagement {

    /** Configured Cassandra client */
    private CassandraClient client;

    public CassandraDeviceStreamManagement() {
	super(LifecycleComponentType.DataStore);
    }

    /*
     * @see
     * com.sitewhere.server.lifecycle.LifecycleComponent#start(com.sitewhere.spi.
     * server.lifecycle.ILifecycleProgressMonitor)
     */
    @Override
    public void start(ILifecycleProgressMonitor monitor) throws SiteWhereException {
	if (getClient() == null) {
	    throw new SiteWhereException(ErrorCode.Error, "No Cassandra client configured.");
	}
	getClient().start(monitor);
    }

    /*
     * @see com.sitewhere.spi.device.streaming.IDeviceStreamDataManagement#
     * addDeviceStreamData(java.util.UUID,
     * com.sitewhere.spi.device.streaming.request.IDeviceStreamDataCreateRequest)
     */
    @Override
    public IDeviceStreamData addDeviceStreamData(UUID streamId, IDeviceStreamDataCreateRequest request)
	    throws SiteWhereException {
	throw new SiteWhereException(ErrorCode.ErrorStreamMedia, "Not implemented.");
    }

    /*
     * @see com.sitewhere.spi.device.streaming.IDeviceStreamDataManagement#
     * getDeviceStreamData(java.util.UUID, long)
     */
    @Override
    public IDeviceStreamData getDeviceStreamData(UUID streamId, long sequenceNumber) throws SiteWhereException {
	throw new SiteWhereException(ErrorCode.ErrorStreamMedia, "Not implemented.");
    }

    /*
     * @see com.sitewhere.spi.device.streaming.IDeviceStreamDataManagement#
     * listDeviceStreamDataForAssignment(java.util.UUID,
     * com.sitewhere.spi.search.IDateRangeSearchCriteria)
     */
    @Override
    public ISearchResults<IDeviceStreamData> listDeviceStreamDataForAssignment(UUID streamId,
	    IDateRangeSearchCriteria criteria) throws SiteWhereException {
	throw new SiteWhereException(ErrorCode.ErrorStreamMedia, "Not implemented.");
    }

    public CassandraClient getClient() {
	return client;
    }

    public void setClient(CassandraClient client) {
	this.client = client;
    }
}
