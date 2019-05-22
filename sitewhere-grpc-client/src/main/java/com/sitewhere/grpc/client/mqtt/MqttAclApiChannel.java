/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.grpc.client.mqtt;

import com.sitewhere.grpc.client.GrpcUtils;
import com.sitewhere.grpc.client.MultitenantApiChannel;
import com.sitewhere.grpc.client.common.converter.CommonModelConverter;
import com.sitewhere.grpc.client.spi.IApiDemux;
import com.sitewhere.grpc.client.spi.client.IMqttAclApiChannel;
import com.sitewhere.grpc.model.CommonModel;
import com.sitewhere.grpc.service.*;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.mqtt.IMqttAcl;
import com.sitewhere.spi.mqtt.IMqttUser;
import com.sitewhere.spi.mqtt.request.IMqttAclCreateRequest;
import com.sitewhere.spi.mqtt.request.IMqttUserCreateRequest;
import com.sitewhere.spi.tracing.ITracerProvider;

public class MqttAclApiChannel extends MultitenantApiChannel<MqttAclGrpcChannel>
	implements IMqttAclApiChannel<MqttAclGrpcChannel> {

    public MqttAclApiChannel(IApiDemux<?> demux, String hostname, int port) {
        super(demux, hostname, port);
    }

    @Override
    public MqttAclGrpcChannel createGrpcChannel(ITracerProvider tracerProvider, String host, int port) {
        return new MqttAclGrpcChannel(tracerProvider, host, port);
    }

    @Override
    public IMqttAcl createMqttAcl(IMqttAclCreateRequest request) throws SiteWhereException {
        try {
            GrpcUtils.handleClientMethodEntry(this, MqttAclGrpc.getCreateMqttAclMethod());
            GCreateMqttAclRequest.Builder grequest = GCreateMqttAclRequest.newBuilder();
            grequest.setRequest(MqttAclModelConverter.asGrpcMqttAclCreateRequest(request));
            GCreateMqttAclResponse gresponse = getGrpcChannel().getBlockingStub().createMqttAcl(grequest.build());
            IMqttAcl response = (gresponse.hasMqttAcl())
                    ? MqttAclModelConverter.asApiMqttAcl(gresponse.getMqttAcl())
                    : null;
            GrpcUtils.logClientMethodResponse(MqttAclGrpc.getCreateMqttAclMethod(), response);
            return response;
        } catch (Throwable t) {
            throw GrpcUtils.handleClientMethodException(MqttAclGrpc.getCreateMqttAclMethod(), t);
        }
    }

    @Override
    public IMqttAcl deleteMqttAcl(String username) throws SiteWhereException {
        try {
            GrpcUtils.handleClientMethodEntry(this, MqttAclGrpc.getDeleteMqttAclMethod());
            GDeleteMqttAclRequest.Builder grequest = GDeleteMqttAclRequest.newBuilder();
            grequest.setUsername(CommonModel.GOptionalString.newBuilder().setValue(username));
            GDeleteMqttAclResponse gresponse = getGrpcChannel().getBlockingStub()
                    .deleteMqttAcl(grequest.build());
            IMqttAcl response = (gresponse.hasMqttAcl())
                    ? MqttAclModelConverter.asApiMqttAcl(gresponse.getMqttAcl())
                    : null;
            GrpcUtils.logClientMethodResponse(MqttAclGrpc.getDeleteMqttAclMethod(), response);
            return response;
        } catch (Throwable t) {
            throw GrpcUtils.handleClientMethodException(MqttAclGrpc.getDeleteMqttAclMethod(), t);
        }
    }

    @Override
    public IMqttUser createMqttUser(IMqttUserCreateRequest request) throws SiteWhereException {
        try {
            GrpcUtils.handleClientMethodEntry(this, MqttAclGrpc.getCreateMqttUserMethod());
            GCreateMqttUserRequest.Builder grequest = GCreateMqttUserRequest.newBuilder();
            grequest.setRequest(MqttAclModelConverter.asGrpcMqttUserCreateRequest(request));
            GCreateMqttUserResponse gresponse = getGrpcChannel().getBlockingStub().createMqttUser(grequest.build());
            IMqttUser response = (gresponse.hasMqttUser())
                    ? MqttAclModelConverter.asApiMqttUser(gresponse.getMqttUser())
                    : null;
            GrpcUtils.logClientMethodResponse(MqttAclGrpc.getCreateMqttUserMethod(), response);
            return response;
        } catch (Throwable t) {
            throw GrpcUtils.handleClientMethodException(MqttAclGrpc.getCreateMqttAclMethod(), t);
        }
    }

    @Override
    public IMqttUser deleteMqttUser(String username) throws SiteWhereException {
        try {
            GrpcUtils.handleClientMethodEntry(this, MqttAclGrpc.getDeleteMqttUserMethod());
            GDeleteMqttUserRequest.Builder grequest = GDeleteMqttUserRequest.newBuilder();
            grequest.setUsername(CommonModel.GOptionalString.newBuilder().setValue(username));
            GDeleteMqttUserResponse gresponse = getGrpcChannel().getBlockingStub()
                    .deleteMqttUser(grequest.build());
            IMqttUser response = (gresponse.hasMqttUser())
                    ? MqttAclModelConverter.asApiMqttUser(gresponse.getMqttUser())
                    : null;
            GrpcUtils.logClientMethodResponse(MqttAclGrpc.getDeleteMqttUserMethod(), response);
            return response;
        } catch (Throwable t) {
            throw GrpcUtils.handleClientMethodException(MqttAclGrpc.getDeleteMqttUserMethod(), t);
        }
    }
}