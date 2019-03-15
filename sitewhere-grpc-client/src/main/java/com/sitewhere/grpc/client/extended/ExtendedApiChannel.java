/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.grpc.client.extended;

import com.sitewhere.grpc.client.GrpcUtils;
import com.sitewhere.grpc.client.MultitenantApiChannel;
import com.sitewhere.grpc.client.devicestate.DeviceStateModelConverter;
import com.sitewhere.grpc.client.spi.IApiDemux;
import com.sitewhere.grpc.client.spi.client.IExtendedApiChannel;
import com.sitewhere.grpc.service.*;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.device.state.IDeviceState;
import com.sitewhere.spi.extended.event.IExtended;
import com.sitewhere.spi.extended.event.request.IExtendedCreateRequest;
import com.sitewhere.spi.tracing.ITracerProvider;

/**
 * Supports SiteWhere device state APIs on top of a
 * {@link ExtendedApiChannel}.
 * 
 * @author Derek
 */
public class ExtendedApiChannel extends MultitenantApiChannel<ExtendedGrpcChannel>
	implements IExtendedApiChannel<ExtendedGrpcChannel> {

    public ExtendedApiChannel(IApiDemux<?> demux, String host, int port) {
	super(demux, host, port);
    }


	@Override
	public ExtendedGrpcChannel createGrpcChannel(ITracerProvider tracerProvider, String host, int port) {
		return new ExtendedGrpcChannel(tracerProvider, host, port);
	}


	@Override
	public IExtended createExtended(IExtendedCreateRequest request) throws SiteWhereException {
		return null;
	}
}