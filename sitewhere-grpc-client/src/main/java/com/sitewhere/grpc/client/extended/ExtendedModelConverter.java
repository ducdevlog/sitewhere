/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.grpc.client.extended;

import com.sitewhere.grpc.model.CommonModel.GOptionalString;
import com.sitewhere.grpc.model.ExtendedModel.GInboundExtendedPayload;
import com.sitewhere.grpc.model.ExtendedModel.GExtendedCreateRequest;
import com.sitewhere.rest.model.extended.event.kafka.ExtendedRequestPayload;
import com.sitewhere.rest.model.extended.event.request.ExtendedCreateRequest;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.extended.event.kafka.IExtendedRequestPayload;
import com.sitewhere.spi.extended.event.request.IExtendedCreateRequest;

/**
 * Convert Extended entities between SiteWhere API model and GRPC model.
 * 
 * @author Derek
 */
public class ExtendedModelConverter {

    /**
     * Convert device state create request from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
	public static IExtendedCreateRequest asApiExtendedCreateRequest(GExtendedCreateRequest grpc)
			throws SiteWhereException {
		ExtendedCreateRequest api = new ExtendedCreateRequest();
		api.setRequestType(grpc.hasRequestType() ? grpc.getRequestType().getValue() : null);
		api.setData(grpc.hasData() ? grpc.getData().getValue() : null);
		return api;
	}

    /**
     * Convert device state create request from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GExtendedCreateRequest asGrpcExtendedCreateRequest(IExtendedCreateRequest api)
	    throws SiteWhereException {
		GExtendedCreateRequest.Builder grpc = GExtendedCreateRequest.newBuilder();
		if (api.getRequestType() != null) {
			grpc.setRequestType(GOptionalString.newBuilder().setValue(api.getRequestType()));
		}
		if (api.getData() != null) {
			grpc.setData(GOptionalString.newBuilder().setValue(api.getData()).build());
		}
		return grpc.build();
    }

	/**
	 * Convert inbound event payload from GRPC to API.
	 *
	 * @param grpc
	 * @return
	 * @throws SiteWhereException
	 */
	public static ExtendedRequestPayload asApiInboundEventPayload(GInboundExtendedPayload grpc) throws SiteWhereException {
		ExtendedRequestPayload api = new ExtendedRequestPayload();
		api.setSourceId(grpc.getSourceId());
		api.setDeviceToken(grpc.getDeviceToken());
		api.setOriginator(grpc.hasOriginator() ? grpc.getOriginator().getValue() : null);
		api.setExtendedRequest(ExtendedModelConverter.asApiExtendedCreateRequest(grpc.getEvent()));
		return api;
	}

	/**
	 * Convert inbound event payload from API to GRPC.
	 *
	 * @param api
	 * @return
	 * @throws SiteWhereException
	 */
	public static GInboundExtendedPayload asGrpcInboundEventPayload(IExtendedRequestPayload api) throws SiteWhereException {
		GInboundExtendedPayload.Builder grpc = GInboundExtendedPayload.newBuilder();
		grpc.setSourceId(api.getSourceId());
		grpc.setDeviceToken(api.getDeviceToken());
		if (api.getOriginator() != null) {
			grpc.setOriginator(GOptionalString.newBuilder().setValue(api.getOriginator()));
		}
		grpc.setEvent(ExtendedModelConverter.asGrpcExtendedCreateRequest(api.getExtendedRequest()));
		return grpc.build();
	}
}
