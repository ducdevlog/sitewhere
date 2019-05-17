/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.mqtt.acl.grpc;

import com.sitewhere.common.MarshalUtils;
import com.sitewhere.grpc.client.GrpcUtils;
import com.sitewhere.grpc.client.mqtt.MqttAclModelConverter;
import com.sitewhere.grpc.client.spi.server.IGrpcApiImplementation;
import com.sitewhere.grpc.service.*;
import com.sitewhere.mqtt.acl.spi.microservice.IMqttAclMicroservice;
import com.sitewhere.spi.microservice.IMicroservice;
import com.sitewhere.spi.mqtt.IMqttAcl;
import com.sitewhere.spi.mqtt.IMqttUser;
import com.sitewhere.spi.mqtt.event.IMqttAclManagement;
import com.sitewhere.spi.mqtt.request.IMqttAclCreateRequest;
import com.sitewhere.spi.mqtt.request.IMqttUserCreateRequest;
import io.grpc.stub.StreamObserver;

/**
 * Implements server logic for device state GRPC requests.
 *
 * @author Derek
 */
public class MqttAclImpl extends MqttAclGrpc.MqttAclImplBase implements IGrpcApiImplementation {

    /**
     * Parent microservice
     */
    private IMqttAclMicroservice microservice;

    /**
     * Device state management persistence
     */
    private IMqttAclManagement mqttAclManagement;

    public MqttAclImpl(IMqttAclMicroservice microservice, IMqttAclManagement mqttAclManagement) {
        this.microservice = microservice;
        this.mqttAclManagement = mqttAclManagement;
    }

    /*
     * @see com.sitewhere.grpc.service.MqttAclGrpc.MqttAclImplBase#
     * createMqttAcl(com.sitewhere.grpc.service.GCreateMqttAclRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void createMqttAcl(GCreateMqttAclRequest request,
                                  StreamObserver<GCreateMqttAclResponse> responseObserver) {
        try {
            System.out.println("*******************************************");
            System.out.println(MarshalUtils.marshalJsonAsPrettyString(request));
            GrpcUtils.handleServerMethodEntry(this, MqttAclGrpc.getCreateMqttAclMethod());
            IMqttAclCreateRequest apiRequest = MqttAclModelConverter
                    .asApiMqttAclCreateRequest(request.getRequest());
            IMqttAcl apiResult = getMqttAclManagement().createMqttAcl(apiRequest);
            GCreateMqttAclResponse.Builder response = GCreateMqttAclResponse.newBuilder();
            response.setMqttAcl(MqttAclModelConverter.asGrpcMqttAcl(apiResult));
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();
        } catch (Throwable e) {
            GrpcUtils.handleServerMethodException(MqttAclGrpc.getCreateMqttAclMethod(), e, responseObserver);
        } finally {
            GrpcUtils.handleServerMethodExit(MqttAclGrpc.getCreateMqttAclMethod());
        }
    }

    @Override
    public void createMqttUser(GCreateMqttUserRequest request,
                              StreamObserver<GCreateMqttUserResponse> responseObserver) {
        try {
            GrpcUtils.handleServerMethodEntry(this, MqttAclGrpc.getCreateMqttUserMethod());
            IMqttUserCreateRequest apiRequest = MqttAclModelConverter
                    .asApiMqttUserCreateRequest(request.getRequest());
            IMqttUser apiResult = getMqttAclManagement().createMqttUser(apiRequest);
            GCreateMqttUserResponse.Builder response = GCreateMqttUserResponse.newBuilder();
            response.setMqttUser(MqttAclModelConverter.asGrpcMqttUser(apiResult));
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();
        } catch (Throwable e) {
            GrpcUtils.handleServerMethodException(MqttAclGrpc.getCreateMqttUserMethod(), e, responseObserver);
        } finally {
            GrpcUtils.handleServerMethodExit(MqttAclGrpc.getCreateMqttUserMethod());
        }
    }

    @Override
    public IMicroservice<?> getMicroservice() {
        return microservice;
    }

    protected IMqttAclManagement getMqttAclManagement() {
        return mqttAclManagement;
    }
}