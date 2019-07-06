/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.event.grpc;

import java.util.List;

import com.sitewhere.event.spi.microservice.IEventManagementMicroservice;
import com.sitewhere.grpc.client.GrpcUtils;
import com.sitewhere.grpc.client.common.converter.CommonModelConverter;
import com.sitewhere.grpc.client.event.EventModelConverter;
import com.sitewhere.grpc.client.spi.server.IGrpcApiImplementation;
import com.sitewhere.grpc.model.DeviceEventModel.GDeviceAlertSearchResults;
import com.sitewhere.grpc.model.DeviceEventModel.GDeviceCommandInvocationSearchResults;
import com.sitewhere.grpc.model.DeviceEventModel.GDeviceCommandResponseSearchResults;
import com.sitewhere.grpc.model.DeviceEventModel.GDeviceLocationSearchResults;
import com.sitewhere.grpc.model.DeviceEventModel.GDeviceMeasurementSearchResults;
import com.sitewhere.grpc.model.DeviceEventModel.GDeviceStateChangeSearchResults;
import com.sitewhere.grpc.service.*;
import com.sitewhere.rest.model.device.event.DeviceEventBatch;
import com.sitewhere.rest.model.device.event.DeviceEventStatistic;
import com.sitewhere.spi.device.event.*;
import com.sitewhere.spi.device.event.request.IDeviceAlertCreateRequest;
import com.sitewhere.spi.device.event.request.IDeviceCommandInvocationCreateRequest;
import com.sitewhere.spi.device.event.request.IDeviceCommandResponseCreateRequest;
import com.sitewhere.spi.device.event.request.IDeviceLocationCreateRequest;
import com.sitewhere.spi.device.event.request.IDeviceMeasurementCreateRequest;
import com.sitewhere.spi.device.event.request.IDeviceStateChangeCreateRequest;
import com.sitewhere.spi.microservice.IMicroservice;
import com.sitewhere.spi.search.ISearchResults;

import io.grpc.stub.StreamObserver;
import org.springframework.util.CollectionUtils;

/**
 * Implements server logic for device event management GRPC requests.
 * 
 * @author Derek
 */
public class EventManagementImpl extends DeviceEventManagementGrpc.DeviceEventManagementImplBase
	implements IGrpcApiImplementation {

    /** Parent microservice */
    private IEventManagementMicroservice microservice;

    /** Device management persistence */
    private IDeviceEventManagement deviceEventManagement;

    public EventManagementImpl(IEventManagementMicroservice microservice,
	    IDeviceEventManagement deviceEventManagement) {
	this.microservice = microservice;
	this.deviceEventManagement = deviceEventManagement;
    }

    public IDeviceEventManagement getDeviceEventManagement() {
	return deviceEventManagement;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.grpc.service.DeviceEventManagementGrpc.
     * DeviceEventManagementImplBase#addDeviceEventBatch(com.sitewhere.grpc.
     * service.GAddDeviceEventBatchRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void addDeviceEventBatch(GAddDeviceEventBatchRequest request,
	    StreamObserver<GAddDeviceEventBatchResponse> responseObserver) {
	try {
	    GrpcUtils.handleServerMethodEntry(this, DeviceEventManagementGrpc.getAddDeviceEventBatchMethod());
	    DeviceEventBatch apiRequest = EventModelConverter.asApiDeviceEventBatch(request.getRequest());
	    IDeviceEventBatchResponse apiResult = getDeviceEventManagement()
		    .addDeviceEventBatch(CommonModelConverter.asApiUuid(request.getDeviceAssignmentId()), apiRequest);
	    GAddDeviceEventBatchResponse.Builder response = GAddDeviceEventBatchResponse.newBuilder();
	    response.setResponse(EventModelConverter.asGrpcDeviceEventBatchResponse(apiResult));
	    responseObserver.onNext(response.build());
	    responseObserver.onCompleted();
	} catch (Throwable e) {
	    GrpcUtils.handleServerMethodException(DeviceEventManagementGrpc.getAddDeviceEventBatchMethod(), e,
		    responseObserver);
	} finally {
	    GrpcUtils.handleServerMethodExit(DeviceEventManagementGrpc.getAddDeviceEventBatchMethod());
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.grpc.service.DeviceEventManagementGrpc.
     * DeviceEventManagementImplBase#getDeviceEventById(com.sitewhere.grpc.
     * service.GGetDeviceEventByIdRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void getDeviceEventById(GGetDeviceEventByIdRequest request,
	    StreamObserver<GGetDeviceEventByIdResponse> responseObserver) {
	try {
	    GrpcUtils.handleServerMethodEntry(this, DeviceEventManagementGrpc.getGetDeviceEventByIdMethod());
	    IDeviceEvent apiResult = getDeviceEventManagement()
		    .getDeviceEventById(CommonModelConverter.asApiUuid(request.getEventId()));
	    GGetDeviceEventByIdResponse.Builder response = GGetDeviceEventByIdResponse.newBuilder();
	    if (apiResult != null) {
		response.setEvent(EventModelConverter.asGrpcGenericDeviceEvent(apiResult));
	    }
	    responseObserver.onNext(response.build());
	    responseObserver.onCompleted();
	} catch (Throwable e) {
	    GrpcUtils.handleServerMethodException(DeviceEventManagementGrpc.getGetDeviceEventByIdMethod(), e,
		    responseObserver);
	} finally {
	    GrpcUtils.handleServerMethodExit(DeviceEventManagementGrpc.getGetDeviceEventByIdMethod());
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.grpc.service.DeviceEventManagementGrpc.
     * DeviceEventManagementImplBase#getDeviceEventByAlternateId(com.sitewhere.
     * grpc.service.GGetDeviceEventByAlternateIdRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void getDeviceEventByAlternateId(GGetDeviceEventByAlternateIdRequest request,
	    StreamObserver<GGetDeviceEventByAlternateIdResponse> responseObserver) {
	try {
	    GrpcUtils.handleServerMethodEntry(this, DeviceEventManagementGrpc.getGetDeviceEventByAlternateIdMethod());
	    IDeviceEvent apiResult = getDeviceEventManagement().getDeviceEventByAlternateId(request.getAlternateId());
	    GGetDeviceEventByAlternateIdResponse.Builder response = GGetDeviceEventByAlternateIdResponse.newBuilder();
	    if (apiResult != null) {
		response.setEvent(EventModelConverter.asGrpcGenericDeviceEvent(apiResult));
	    }
	    responseObserver.onNext(response.build());
	    responseObserver.onCompleted();
	} catch (Throwable e) {
	    GrpcUtils.handleServerMethodException(DeviceEventManagementGrpc.getGetDeviceEventByAlternateIdMethod(), e,
		    responseObserver);
	} finally {
	    GrpcUtils.handleServerMethodExit(DeviceEventManagementGrpc.getGetDeviceEventByAlternateIdMethod());
	}
    }

    /*
     * @see com.sitewhere.grpc.service.DeviceEventManagementGrpc.
     * DeviceEventManagementImplBase#addMeasurements(com.sitewhere.grpc.service.
     * GAddMeasurementsRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void addMeasurements(GAddMeasurementsRequest request,
	    StreamObserver<GAddMeasurementsResponse> responseObserver) {
	try {
	    GrpcUtils.handleServerMethodEntry(this, DeviceEventManagementGrpc.getAddMeasurementsMethod());
	    List<IDeviceMeasurement> apiResult = getDeviceEventManagement().addDeviceMeasurements(
		    CommonModelConverter.asApiUuid(request.getDeviceAssignmentId()),
		    EventModelConverter.asApiDeviceMeasurementCreateRequests(request.getRequestsList())
			    .toArray(new IDeviceMeasurementCreateRequest[0]));
	    GAddMeasurementsResponse.Builder response = GAddMeasurementsResponse.newBuilder();
	    if (apiResult != null) {
		response.addAllMeasurements(EventModelConverter.asGrpcDeviceMeasurements(apiResult));
	    }
	    responseObserver.onNext(response.build());
	    responseObserver.onCompleted();
	} catch (Throwable e) {
	    GrpcUtils.handleServerMethodException(DeviceEventManagementGrpc.getAddMeasurementsMethod(), e,
		    responseObserver);
	} finally {
	    GrpcUtils.handleServerMethodExit(DeviceEventManagementGrpc.getAddMeasurementsMethod());
	}
    }

    /*
     * @see com.sitewhere.grpc.service.DeviceEventManagementGrpc.
     * DeviceEventManagementImplBase#listMeasurementsForIndex(com.sitewhere.grpc.
     * service.GListMeasurementsForIndexRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void listMeasurementsForIndex(GListMeasurementsForIndexRequest request,
	    StreamObserver<GListMeasurementsForIndexResponse> responseObserver) {
	try {
	    GrpcUtils.handleServerMethodEntry(this, DeviceEventManagementGrpc.getListMeasurementsForIndexMethod());
	    ISearchResults<IDeviceMeasurement> apiResult = getDeviceEventManagement().listDeviceMeasurementsForIndex(
		    EventModelConverter.asApiDeviceEventIndex(request.getIndex()),
		    CommonModelConverter.asApiUuids(request.getEntityIdsList()),
		    CommonModelConverter.asDateRangeSearchCriteria(request.getCriteria()));
	    GListMeasurementsForIndexResponse.Builder response = GListMeasurementsForIndexResponse.newBuilder();
	    GDeviceMeasurementSearchResults.Builder results = GDeviceMeasurementSearchResults.newBuilder();
	    for (IDeviceMeasurement api : apiResult.getResults()) {
		results.addMeasurements(EventModelConverter.asGrpcDeviceMeasurement(api));
	    }
	    results.setCount(apiResult.getNumResults());
	    response.setResults(results.build());
	    responseObserver.onNext(response.build());
	    responseObserver.onCompleted();
	} catch (Throwable e) {
	    GrpcUtils.handleServerMethodException(DeviceEventManagementGrpc.getListMeasurementsForIndexMethod(), e,
		    responseObserver);
	} finally {
	    GrpcUtils.handleServerMethodExit(DeviceEventManagementGrpc.getListMeasurementsForIndexMethod());
	}
    }

    /*
     * @see com.sitewhere.grpc.service.DeviceEventManagementGrpc.
     * DeviceEventManagementImplBase#addLocations(com.sitewhere.grpc.service.
     * GAddLocationsRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void addLocations(GAddLocationsRequest request, StreamObserver<GAddLocationsResponse> responseObserver) {
	try {
	    GrpcUtils.handleServerMethodEntry(this, DeviceEventManagementGrpc.getAddLocationsMethod());
	    List<IDeviceLocation> apiResult = getDeviceEventManagement().addDeviceLocations(
		    CommonModelConverter.asApiUuid(request.getDeviceAssignmentId()),
		    EventModelConverter.asApiDeviceLocationCreateRequests(request.getRequestsList())
			    .toArray(new IDeviceLocationCreateRequest[0]));
	    GAddLocationsResponse.Builder response = GAddLocationsResponse.newBuilder();
	    if (apiResult != null) {
		response.addAllLocations(EventModelConverter.asGrpcDeviceLocations(apiResult));
	    }
	    responseObserver.onNext(response.build());
	    responseObserver.onCompleted();
	} catch (Throwable e) {
	    GrpcUtils.handleServerMethodException(DeviceEventManagementGrpc.getAddLocationsMethod(), e,
		    responseObserver);
	} finally {
	    GrpcUtils.handleServerMethodExit(DeviceEventManagementGrpc.getAddLocationsMethod());
	}
    }

    /*
     * @see com.sitewhere.grpc.service.DeviceEventManagementGrpc.
     * DeviceEventManagementImplBase#listLocationsForIndex(com.sitewhere.grpc.
     * service.GListLocationsForIndexRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void listLocationsForIndex(GListLocationsForIndexRequest request,
	    StreamObserver<GListLocationsForIndexResponse> responseObserver) {
	try {
	    GrpcUtils.handleServerMethodEntry(this, DeviceEventManagementGrpc.getListLocationsForIndexMethod());
	    ISearchResults<IDeviceLocation> apiResult = getDeviceEventManagement().listDeviceLocationsForIndex(
		    EventModelConverter.asApiDeviceEventIndex(request.getIndex()),
		    CommonModelConverter.asApiUuids(request.getEntityIdsList()),
		    CommonModelConverter.asDateRangeSearchCriteria(request.getCriteria()));
	    GListLocationsForIndexResponse.Builder response = GListLocationsForIndexResponse.newBuilder();
	    GDeviceLocationSearchResults.Builder results = GDeviceLocationSearchResults.newBuilder();
	    for (IDeviceLocation api : apiResult.getResults()) {
		results.addLocations(EventModelConverter.asGrpcDeviceLocation(api));
	    }
	    results.setCount(apiResult.getNumResults());
	    response.setResults(results.build());
	    responseObserver.onNext(response.build());
	    responseObserver.onCompleted();
	} catch (Throwable e) {
	    GrpcUtils.handleServerMethodException(DeviceEventManagementGrpc.getListLocationsForIndexMethod(), e,
		    responseObserver);
	} finally {
	    GrpcUtils.handleServerMethodExit(DeviceEventManagementGrpc.getListLocationsForIndexMethod());
	}
    }

    /*
     * @see com.sitewhere.grpc.service.DeviceEventManagementGrpc.
     * DeviceEventManagementImplBase#addAlerts(com.sitewhere.grpc.service.
     * GAddAlertsRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void addAlerts(GAddAlertsRequest request, StreamObserver<GAddAlertsResponse> responseObserver) {
	try {
	    GrpcUtils.handleServerMethodEntry(this, DeviceEventManagementGrpc.getAddAlertsMethod());
	    List<IDeviceAlert> apiResult = getDeviceEventManagement().addDeviceAlerts(
		    CommonModelConverter.asApiUuid(request.getDeviceAssignmentId()),
		    EventModelConverter.asApiDeviceAlertCreateRequests(request.getRequestsList())
			    .toArray(new IDeviceAlertCreateRequest[0]));
	    GAddAlertsResponse.Builder response = GAddAlertsResponse.newBuilder();
	    if (apiResult != null) {
		response.addAllAlerts(EventModelConverter.asGrpcDeviceAlerts(apiResult));
	    }
	    responseObserver.onNext(response.build());
	    responseObserver.onCompleted();
	} catch (Throwable e) {
	    GrpcUtils.handleServerMethodException(DeviceEventManagementGrpc.getAddAlertsMethod(), e, responseObserver);
	} finally {
	    GrpcUtils.handleServerMethodExit(DeviceEventManagementGrpc.getAddAlertsMethod());
	}
    }

    /*
     * @see com.sitewhere.grpc.service.DeviceEventManagementGrpc.
     * DeviceEventManagementImplBase#listAlertsForIndex(com.sitewhere.grpc.service.
     * GListAlertsForIndexRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void listAlertsForIndex(GListAlertsForIndexRequest request,
	    StreamObserver<GListAlertsForIndexResponse> responseObserver) {
	try {
	    GrpcUtils.handleServerMethodEntry(this, DeviceEventManagementGrpc.getListAlertsForIndexMethod());
	    ISearchResults<IDeviceAlert> apiResult = getDeviceEventManagement().listDeviceAlertsForIndex(
		    EventModelConverter.asApiDeviceEventIndex(request.getIndex()),
		    CommonModelConverter.asApiUuids(request.getEntityIdsList()),
		    CommonModelConverter.asDateRangeSearchCriteria(request.getCriteria()));
	    GListAlertsForIndexResponse.Builder response = GListAlertsForIndexResponse.newBuilder();
	    GDeviceAlertSearchResults.Builder results = GDeviceAlertSearchResults.newBuilder();
	    for (IDeviceAlert api : apiResult.getResults()) {
		results.addAlerts(EventModelConverter.asGrpcDeviceAlert(api));
	    }
	    results.setCount(apiResult.getNumResults());
	    response.setResults(results.build());
	    responseObserver.onNext(response.build());
	    responseObserver.onCompleted();
	} catch (Throwable e) {
	    GrpcUtils.handleServerMethodException(DeviceEventManagementGrpc.getListAlertsForIndexMethod(), e,
		    responseObserver);
	} finally {
	    GrpcUtils.handleServerMethodExit(DeviceEventManagementGrpc.getListAlertsForIndexMethod());
	}
    }

    /*
     * @see com.sitewhere.grpc.service.DeviceEventManagementGrpc.
     * DeviceEventManagementImplBase#addCommandInvocations(com.sitewhere.grpc.
     * service.GAddCommandInvocationsRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void addCommandInvocations(GAddCommandInvocationsRequest request,
	    StreamObserver<GAddCommandInvocationsResponse> responseObserver) {
	try {
	    GrpcUtils.handleServerMethodEntry(this, DeviceEventManagementGrpc.getAddCommandInvocationsMethod());
	    List<IDeviceCommandInvocation> apiResult = getDeviceEventManagement().addDeviceCommandInvocations(
		    CommonModelConverter.asApiUuid(request.getDeviceAssignmentId()),
		    EventModelConverter.asApiDeviceCommandInvocationCreateRequests(request.getRequestsList())
			    .toArray(new IDeviceCommandInvocationCreateRequest[0]));
	    GAddCommandInvocationsResponse.Builder response = GAddCommandInvocationsResponse.newBuilder();
	    if (apiResult != null) {
		response.addAllInvocations(EventModelConverter.asGrpcDeviceCommandInvocations(apiResult));
	    }
	    responseObserver.onNext(response.build());
	    responseObserver.onCompleted();
	} catch (Throwable e) {
	    GrpcUtils.handleServerMethodException(DeviceEventManagementGrpc.getAddCommandInvocationsMethod(), e,
		    responseObserver);
	} finally {
	    GrpcUtils.handleServerMethodExit(DeviceEventManagementGrpc.getAddCommandInvocationsMethod());
	}
    }

    /*
     * @see com.sitewhere.grpc.service.DeviceEventManagementGrpc.
     * DeviceEventManagementImplBase#listCommandInvocationsForIndex(com.sitewhere.
     * grpc.service.GListCommandInvocationsForIndexRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void listCommandInvocationsForIndex(GListCommandInvocationsForIndexRequest request,
	    StreamObserver<GListCommandInvocationsForIndexResponse> responseObserver) {
	try {
	    GrpcUtils.handleServerMethodEntry(this,
		    DeviceEventManagementGrpc.getListCommandInvocationsForIndexMethod());
	    ISearchResults<IDeviceCommandInvocation> apiResult = getDeviceEventManagement()
		    .listDeviceCommandInvocationsForIndex(EventModelConverter.asApiDeviceEventIndex(request.getIndex()),
			    CommonModelConverter.asApiUuids(request.getEntityIdsList()),
			    CommonModelConverter.asDateRangeSearchCriteria(request.getCriteria()));
	    GListCommandInvocationsForIndexResponse.Builder response = GListCommandInvocationsForIndexResponse
		    .newBuilder();
	    GDeviceCommandInvocationSearchResults.Builder results = GDeviceCommandInvocationSearchResults.newBuilder();
	    for (IDeviceCommandInvocation api : apiResult.getResults()) {
		results.addInvocations(EventModelConverter.asGrpcDeviceCommandInvocation(api));
	    }
	    results.setCount(apiResult.getNumResults());
	    response.setResults(results.build());
	    responseObserver.onNext(response.build());
	    responseObserver.onCompleted();
	} catch (Throwable e) {
	    GrpcUtils.handleServerMethodException(DeviceEventManagementGrpc.getListCommandInvocationsForIndexMethod(),
		    e, responseObserver);
	} finally {
	    GrpcUtils.handleServerMethodExit(DeviceEventManagementGrpc.getListCommandInvocationsForIndexMethod());
	}
    }

    /*
     * @see com.sitewhere.grpc.service.DeviceEventManagementGrpc.
     * DeviceEventManagementImplBase#addCommandResponses(com.sitewhere.grpc.service.
     * GAddCommandResponsesRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void addCommandResponses(GAddCommandResponsesRequest request,
	    StreamObserver<GAddCommandResponsesResponse> responseObserver) {
	try {
	    GrpcUtils.handleServerMethodEntry(this, DeviceEventManagementGrpc.getAddCommandResponsesMethod());
	    List<IDeviceCommandResponse> apiResult = getDeviceEventManagement().addDeviceCommandResponses(
		    CommonModelConverter.asApiUuid(request.getDeviceAssignmentId()),
		    EventModelConverter.asApiDeviceCommandResponseCreateRequests(request.getRequestsList())
			    .toArray(new IDeviceCommandResponseCreateRequest[0]));
	    GAddCommandResponsesResponse.Builder response = GAddCommandResponsesResponse.newBuilder();
	    if (apiResult != null) {
		response.addAllResponses(EventModelConverter.asGrpcDeviceCommandResponses(apiResult));
	    }
	    responseObserver.onNext(response.build());
	    responseObserver.onCompleted();
	} catch (Throwable e) {
	    GrpcUtils.handleServerMethodException(DeviceEventManagementGrpc.getAddCommandResponsesMethod(), e,
		    responseObserver);
	} finally {
	    GrpcUtils.handleServerMethodExit(DeviceEventManagementGrpc.getAddCommandResponsesMethod());
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.grpc.service.DeviceEventManagementGrpc.
     * DeviceEventManagementImplBase#listCommandResponsesForInvocation(com.
     * sitewhere.grpc.service.GListCommandResponsesForInvocationRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void listCommandResponsesForInvocation(GListCommandResponsesForInvocationRequest request,
	    StreamObserver<GListCommandResponsesForInvocationResponse> responseObserver) {
	try {
	    GrpcUtils.handleServerMethodEntry(this,
		    DeviceEventManagementGrpc.getListCommandResponsesForInvocationMethod());
	    ISearchResults<IDeviceCommandResponse> apiResult = getDeviceEventManagement()
		    .listDeviceCommandInvocationResponses(
			    CommonModelConverter.asApiUuid(request.getInvocationEventId()));
	    GListCommandResponsesForInvocationResponse.Builder response = GListCommandResponsesForInvocationResponse
		    .newBuilder();
	    GDeviceCommandResponseSearchResults.Builder results = GDeviceCommandResponseSearchResults.newBuilder();
	    for (IDeviceCommandResponse api : apiResult.getResults()) {
		results.addResponses(EventModelConverter.asGrpcDeviceCommandResponse(api));
	    }
	    results.setCount(apiResult.getNumResults());
	    response.setResults(results.build());
	    responseObserver.onNext(response.build());
	    responseObserver.onCompleted();
	} catch (Throwable e) {
	    GrpcUtils.handleServerMethodException(
		    DeviceEventManagementGrpc.getListCommandResponsesForInvocationMethod(), e, responseObserver);
	} finally {
	    GrpcUtils.handleServerMethodExit(DeviceEventManagementGrpc.getListCommandResponsesForInvocationMethod());
	}
    }

    /*
     * @see com.sitewhere.grpc.service.DeviceEventManagementGrpc.
     * DeviceEventManagementImplBase#listCommandResponsesForIndex(com.sitewhere.grpc
     * .service.GListCommandResponsesForIndexRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void listCommandResponsesForIndex(GListCommandResponsesForIndexRequest request,
	    StreamObserver<GListCommandResponsesForIndexResponse> responseObserver) {
	try {
	    GrpcUtils.handleServerMethodEntry(this, DeviceEventManagementGrpc.getListCommandResponsesForIndexMethod());
	    ISearchResults<IDeviceCommandResponse> apiResult = getDeviceEventManagement()
		    .listDeviceCommandResponsesForIndex(EventModelConverter.asApiDeviceEventIndex(request.getIndex()),
			    CommonModelConverter.asApiUuids(request.getEntityIdsList()),
			    CommonModelConverter.asDateRangeSearchCriteria(request.getCriteria()));
	    GListCommandResponsesForIndexResponse.Builder response = GListCommandResponsesForIndexResponse.newBuilder();
	    GDeviceCommandResponseSearchResults.Builder results = GDeviceCommandResponseSearchResults.newBuilder();
	    for (IDeviceCommandResponse api : apiResult.getResults()) {
		results.addResponses(EventModelConverter.asGrpcDeviceCommandResponse(api));
	    }
	    results.setCount(apiResult.getNumResults());
	    response.setResults(results.build());
	    responseObserver.onNext(response.build());
	    responseObserver.onCompleted();
	} catch (Throwable e) {
	    GrpcUtils.handleServerMethodException(DeviceEventManagementGrpc.getListCommandResponsesForIndexMethod(), e,
		    responseObserver);
	} finally {
	    GrpcUtils.handleServerMethodExit(DeviceEventManagementGrpc.getListCommandResponsesForIndexMethod());
	}
    }

    /*
     * @see com.sitewhere.grpc.service.DeviceEventManagementGrpc.
     * DeviceEventManagementImplBase#addStateChanges(com.sitewhere.grpc.service.
     * GAddStateChangesRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void addStateChanges(GAddStateChangesRequest request,
	    StreamObserver<GAddStateChangesResponse> responseObserver) {
	try {
	    GrpcUtils.handleServerMethodEntry(this, DeviceEventManagementGrpc.getAddStateChangesMethod());
	    List<IDeviceStateChange> apiResult = getDeviceEventManagement().addDeviceStateChanges(
		    CommonModelConverter.asApiUuid(request.getDeviceAssignmentId()),
		    EventModelConverter.asApiDeviceStateChangeCreateRequests(request.getRequestsList())
			    .toArray(new IDeviceStateChangeCreateRequest[0]));
	    GAddStateChangesResponse.Builder response = GAddStateChangesResponse.newBuilder();
	    if (apiResult != null) {
		response.addAllStateChanges(EventModelConverter.asGrpcDeviceStateChanges(apiResult));
	    }
	    responseObserver.onNext(response.build());
	    responseObserver.onCompleted();
	} catch (Throwable e) {
	    GrpcUtils.handleServerMethodException(DeviceEventManagementGrpc.getAddStateChangesMethod(), e,
		    responseObserver);
	} finally {
	    GrpcUtils.handleServerMethodExit(DeviceEventManagementGrpc.getAddStateChangesMethod());
	}
    }

    /*
     * @see com.sitewhere.grpc.service.DeviceEventManagementGrpc.
     * DeviceEventManagementImplBase#listStateChangesForIndex(com.sitewhere.grpc.
     * service.GListStateChangesForIndexRequest, io.grpc.stub.StreamObserver)
     */
    @Override
    public void listStateChangesForIndex(GListStateChangesForIndexRequest request,
	    StreamObserver<GListStateChangesForIndexResponse> responseObserver) {
	try {
	    GrpcUtils.handleServerMethodEntry(this, DeviceEventManagementGrpc.getListStateChangesForIndexMethod());
	    ISearchResults<IDeviceStateChange> apiResult = getDeviceEventManagement().listDeviceStateChangesForIndex(
		    EventModelConverter.asApiDeviceEventIndex(request.getIndex()),
		    CommonModelConverter.asApiUuids(request.getEntityIdsList()),
		    CommonModelConverter.asDateRangeSearchCriteria(request.getCriteria()));
	    GListStateChangesForIndexResponse.Builder response = GListStateChangesForIndexResponse.newBuilder();
	    GDeviceStateChangeSearchResults.Builder results = GDeviceStateChangeSearchResults.newBuilder();
	    for (IDeviceStateChange api : apiResult.getResults()) {
		results.addStateChanges(EventModelConverter.asGrpcDeviceStateChange(api));
	    }
	    results.setCount(apiResult.getNumResults());
	    response.setResults(results.build());
	    responseObserver.onNext(response.build());
	    responseObserver.onCompleted();
	} catch (Throwable e) {
	    GrpcUtils.handleServerMethodException(DeviceEventManagementGrpc.getListStateChangesForIndexMethod(), e,
		    responseObserver);
	} finally {
	    GrpcUtils.handleServerMethodExit(DeviceEventManagementGrpc.getListStateChangesForIndexMethod());
	}
    }

	@Override
	public void getDeviceEventStaticsById(GListDeviceEventStatisticRequest request, StreamObserver<GListDeviceEventStatisticResponse> responseObserver) {
		try {
			GrpcUtils.handleServerMethodEntry(this, DeviceEventManagementGrpc.getGetDeviceEventStaticsByIdMethod());
			List<DeviceEventStatistic> apiResults = getDeviceEventManagement().getDeviceEventStaticsById(
                    CommonModelConverter.asApiUuid(request.getDeviceAssignmentId()),
					request.getFilterType(),
					request.getDateType(),
					CommonModelConverter.asApiDate(request.getStartDate()),
					CommonModelConverter.asApiDate(request.getEndDate()));
			GListDeviceEventStatisticResponse.Builder response = GListDeviceEventStatisticResponse.newBuilder();
			if (!CollectionUtils.isEmpty(apiResults)) {
				apiResults.stream().map(EventModelConverter::asGrpcDeviceEventStatistic).forEach(response::addResults);
			}
			responseObserver.onNext(response.build());
			responseObserver.onCompleted();
		} catch (Throwable e) {
			GrpcUtils.handleServerMethodException(DeviceEventManagementGrpc.getGetDeviceEventStaticsByIdMethod(), e,
					responseObserver);
		} finally {
			GrpcUtils.handleServerMethodExit(DeviceEventManagementGrpc.getGetDeviceEventStaticsByIdMethod());
		}
	}

    /*
     * @see
     * com.sitewhere.grpc.client.spi.server.IGrpcApiImplementation#getMicroservice()
     */
    @Override
    public IMicroservice<?> getMicroservice() {
	return microservice;
    }

    public void setDeviceEventManagement(IDeviceEventManagement deviceEventManagement) {
	this.deviceEventManagement = deviceEventManagement;
    }
}