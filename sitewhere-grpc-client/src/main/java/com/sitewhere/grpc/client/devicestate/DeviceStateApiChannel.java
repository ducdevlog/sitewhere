/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.grpc.client.devicestate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.sitewhere.grpc.client.GrpcUtils;
import com.sitewhere.grpc.client.MultitenantApiChannel;
import com.sitewhere.grpc.client.common.converter.CommonModelConverter;
import com.sitewhere.grpc.client.spi.IApiDemux;
import com.sitewhere.grpc.client.spi.client.IDeviceStateApiChannel;
import com.sitewhere.grpc.model.DeviceStateModel;
import com.sitewhere.grpc.service.*;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.device.state.IDeviceState;
import com.sitewhere.spi.device.state.request.IDeviceStateCreateRequest;
import com.sitewhere.spi.search.ISearchResults;
import com.sitewhere.spi.search.device.IDeviceStateSearchCriteria;
import com.sitewhere.spi.tracing.ITracerProvider;

/**
 * Supports SiteWhere device state APIs on top of a
 * {@link DeviceStateGrpcChannel}.
 * 
 * @author Derek
 */
public class DeviceStateApiChannel extends MultitenantApiChannel<DeviceStateGrpcChannel>
	implements IDeviceStateApiChannel<DeviceStateGrpcChannel> {

    public DeviceStateApiChannel(IApiDemux<?> demux, String host, int port) {
	super(demux, host, port);
    }

    /*
     * @see
     * com.sitewhere.grpc.client.spi.IApiChannel#createGrpcChannel(com.sitewhere.spi
     * .tracing.ITracerProvider, java.lang.String, int)
     */
    @Override
    public DeviceStateGrpcChannel createGrpcChannel(ITracerProvider tracerProvider, String host, int port) {
	return new DeviceStateGrpcChannel(tracerProvider, host, port);
    }

    /*
     * @see
     * com.sitewhere.spi.device.state.IDeviceStateManagement#createDeviceState(com.
     * sitewhere.spi.device.state.request.IDeviceStateCreateRequest)
     */
    @Override
    public IDeviceState createDeviceState(IDeviceStateCreateRequest request) throws SiteWhereException {
	try {
	    GrpcUtils.handleClientMethodEntry(this, DeviceStateGrpc.getCreateDeviceStateMethod());
	    GCreateDeviceStateRequest.Builder grequest = GCreateDeviceStateRequest.newBuilder();
	    grequest.setRequest(DeviceStateModelConverter.asGrpcDeviceStateCreateRequest(request));
	    GCreateDeviceStateResponse gresponse = getGrpcChannel().getBlockingStub()
		    .createDeviceState(grequest.build());
	    IDeviceState response = (gresponse.hasDeviceState())
		    ? DeviceStateModelConverter.asApiDeviceState(gresponse.getDeviceState())
		    : null;
	    GrpcUtils.logClientMethodResponse(DeviceStateGrpc.getCreateDeviceStateMethod(), response);
	    return response;
	} catch (Throwable t) {
	    throw GrpcUtils.handleClientMethodException(DeviceStateGrpc.getCreateDeviceStateMethod(), t);
	}
    }

    /*
     * @see
     * com.sitewhere.spi.device.state.IDeviceStateManagement#getDeviceState(java.
     * util.UUID)
     */
    @Override
    public IDeviceState getDeviceState(UUID id) throws SiteWhereException {
	try {
	    GrpcUtils.handleClientMethodEntry(this, DeviceStateGrpc.getGetDeviceStateMethod());
	    GGetDeviceStateRequest.Builder grequest = GGetDeviceStateRequest.newBuilder();
	    grequest.setId(CommonModelConverter.asGrpcUuid(id));
	    GGetDeviceStateResponse gresponse = getGrpcChannel().getBlockingStub().getDeviceState(grequest.build());
	    IDeviceState response = (gresponse.hasDeviceState())
		    ? DeviceStateModelConverter.asApiDeviceState(gresponse.getDeviceState())
		    : null;
	    GrpcUtils.logClientMethodResponse(DeviceStateGrpc.getGetDeviceStateMethod(), response);
	    return response;
	} catch (Throwable t) {
	    throw GrpcUtils.handleClientMethodException(DeviceStateGrpc.getGetDeviceStateMethod(), t);
	}
    }

    /*
     * @see com.sitewhere.spi.device.state.IDeviceStateManagement#
     * getDeviceStateByDeviceAssignmentId(java.util.UUID)
     */
    @Override
    public IDeviceState getDeviceStateByDeviceAssignmentId(UUID assignmentId) throws SiteWhereException {
	try {
	    GrpcUtils.handleClientMethodEntry(this, DeviceStateGrpc.getGetDeviceStateByDeviceAssignmentIdMethod());
	    GGetDeviceStateByDeviceAssignmentIdRequest.Builder grequest = GGetDeviceStateByDeviceAssignmentIdRequest
		    .newBuilder();
	    grequest.setDeviceAssignmentId(CommonModelConverter.asGrpcUuid(assignmentId));
	    GGetDeviceStateByDeviceAssignmentIdResponse gresponse = getGrpcChannel().getBlockingStub()
		    .getDeviceStateByDeviceAssignmentId(grequest.build());
	    IDeviceState response = (gresponse.hasDeviceState())
		    ? DeviceStateModelConverter.asApiDeviceState(gresponse.getDeviceState())
		    : null;
	    GrpcUtils.logClientMethodResponse(DeviceStateGrpc.getGetDeviceStateByDeviceAssignmentIdMethod(), response);
	    return response;
	} catch (Throwable t) {
	    throw GrpcUtils.handleClientMethodException(DeviceStateGrpc.getGetDeviceStateByDeviceAssignmentIdMethod(),
		    t);
	}
    }

    /*
     * @see
     * com.sitewhere.spi.device.state.IDeviceStateManagement#searchDeviceStates(com.
     * sitewhere.spi.search.device.IDeviceStateSearchCriteria)
     */
    @Override
    public ISearchResults<IDeviceState> searchDeviceStates(IDeviceStateSearchCriteria criteria)
	    throws SiteWhereException {
	try {
	    GrpcUtils.handleClientMethodEntry(this, DeviceStateGrpc.getSearchDeviceStatesMethod());
	    GSearchDeviceStatesRequest.Builder grequest = GSearchDeviceStatesRequest.newBuilder();
	    grequest.setCriteria(DeviceStateModelConverter.asGrpcDeviceStateSearchCriteria(criteria));
	    GSearchDeviceStatesResponse gresponse = getGrpcChannel().getBlockingStub()
		    .searchDeviceStates(grequest.build());
	    ISearchResults<IDeviceState> results = DeviceStateModelConverter
		    .asApiDeviceStateSearchResults(gresponse.getResults());
	    GrpcUtils.logClientMethodResponse(DeviceStateGrpc.getSearchDeviceStatesMethod(), results);
	    return results;
	} catch (Throwable t) {
	    throw GrpcUtils.handleClientMethodException(DeviceStateGrpc.getSearchDeviceStatesMethod(), t);
	}
    }

	@Override
	public List<IDeviceState> getStatusDeviceStates(IDeviceStateSearchCriteria criteria) throws SiteWhereException {
		try {
			GrpcUtils.handleClientMethodEntry(this, DeviceStateGrpc.getGetDeviceStateMethod());
			GGetDeviceStatesRequest.Builder grequest = GGetDeviceStatesRequest.newBuilder();
			grequest.setCriteria(DeviceStateModelConverter.asGrpcDeviceStateSearchCriteria(criteria));
			GGetDeviceStatesResponse gresponse = getGrpcChannel().getBlockingStub()
					.getDeviceStates(grequest.build());
			List<IDeviceState> deviceStates = new ArrayList<>();
			for (DeviceStateModel.GDeviceState gDeviceState : gresponse.getResultsList()) {
				deviceStates.add(DeviceStateModelConverter.asApiDeviceState(gDeviceState));
			}
			GrpcUtils.logClientMethodResponse(DeviceStateGrpc.getGetDeviceStateMethod(), gresponse.getResultsList());
			return deviceStates;
		} catch (Throwable t) {
			throw GrpcUtils.handleClientMethodException(DeviceStateGrpc.getGetDeviceStateMethod(), t);
		}
	}

	/*
     * @see
     * com.sitewhere.spi.device.state.IDeviceStateManagement#updateDeviceState(java.
     * util.UUID, com.sitewhere.spi.device.state.request.IDeviceStateCreateRequest)
     */
    @Override
    public IDeviceState updateDeviceState(UUID id, IDeviceStateCreateRequest request) throws SiteWhereException {
	try {
	    GrpcUtils.handleClientMethodEntry(this, DeviceStateGrpc.getUpdateDeviceStateMethod());
	    GUpdateDeviceStateRequest.Builder grequest = GUpdateDeviceStateRequest.newBuilder();
	    grequest.setId(CommonModelConverter.asGrpcUuid(id));
	    grequest.setRequest(DeviceStateModelConverter.asGrpcDeviceStateCreateRequest(request));
	    GUpdateDeviceStateResponse gresponse = getGrpcChannel().getBlockingStub()
		    .updateDeviceState(grequest.build());
	    IDeviceState response = (gresponse.hasDeviceState())
		    ? DeviceStateModelConverter.asApiDeviceState(gresponse.getDeviceState())
		    : null;
	    GrpcUtils.logClientMethodResponse(DeviceStateGrpc.getUpdateDeviceStateMethod(), response);
	    return response;
	} catch (Throwable t) {
	    throw GrpcUtils.handleClientMethodException(DeviceStateGrpc.getUpdateDeviceStateMethod(), t);
	}
    }

    /*
     * @see
     * com.sitewhere.spi.device.state.IDeviceStateManagement#deleteDeviceState(java.
     * util.UUID)
     */
    @Override
    public IDeviceState deleteDeviceState(UUID id) throws SiteWhereException {
	try {
	    GrpcUtils.handleClientMethodEntry(this, DeviceStateGrpc.getDeleteDeviceStateMethod());
	    GDeleteDeviceStateRequest.Builder grequest = GDeleteDeviceStateRequest.newBuilder();
	    grequest.setId(CommonModelConverter.asGrpcUuid(id));
	    GDeleteDeviceStateResponse gresponse = getGrpcChannel().getBlockingStub()
		    .deleteDeviceState(grequest.build());
	    IDeviceState response = (gresponse.hasDeviceState())
		    ? DeviceStateModelConverter.asApiDeviceState(gresponse.getDeviceState())
		    : null;
	    GrpcUtils.logClientMethodResponse(DeviceStateGrpc.getDeleteDeviceStateMethod(), response);
	    return response;
	} catch (Throwable t) {
	    throw GrpcUtils.handleClientMethodException(DeviceStateGrpc.getDeleteDeviceStateMethod(), t);
	}
    }
}