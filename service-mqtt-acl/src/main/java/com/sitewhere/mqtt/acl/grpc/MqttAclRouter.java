/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.mqtt.acl.grpc;

import com.sitewhere.grpc.client.GrpcContextKeys;
import com.sitewhere.grpc.client.GrpcUtils;
import com.sitewhere.grpc.client.spi.server.IGrpcRouter;
import com.sitewhere.grpc.service.*;
import com.sitewhere.mqtt.acl.spi.microservice.IMqttAclMicroservice;
import com.sitewhere.mqtt.acl.spi.microservice.IMqttAclTenantEngine;
import com.sitewhere.spi.microservice.multitenant.TenantEngineNotAvailableException;
import io.grpc.stub.StreamObserver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.UUID;

/**
 * Routes GRPC calls to service implementations in tenants.
 *
 * @author Derek
 */
public class MqttAclRouter extends MqttAclGrpc.MqttAclImplBase
        implements IGrpcRouter<MqttAclGrpc.MqttAclImplBase> {

    /**
     * Static logger instance
     */
    @SuppressWarnings("unused")
    private static Log LOGGER = LogFactory.getLog(MqttAclRouter.class);

    /**
     * Parent microservice
     */
    private IMqttAclMicroservice microservice;

    public MqttAclRouter(IMqttAclMicroservice microservice) {
        this.microservice = microservice;
    }

    /*
     * @see com.sitewhere.spi.grpc.IGrpcRouter#getTenantImplementation()
     */
    @Override
    public MqttAclGrpc.MqttAclImplBase getTenantImplementation(StreamObserver<?> observer) {
        String tenantId = GrpcContextKeys.TENANT_ID_KEY.get();
        if (tenantId == null) {
            throw new RuntimeException("Tenant id not found in device state request.");
        }
        try {
            IMqttAclTenantEngine engine = getMicroservice().assureTenantEngineAvailable(UUID.fromString(tenantId));
            return engine.getMqttAclImpl();
        } catch (TenantEngineNotAvailableException e) {
            observer.onError(GrpcUtils.convertServerException(e));
            return null;
        }
    }

    /*
     * @see com.sitewhere.grpc.service.MqttAclGrpc.MqttAclImplBase#
     * createMqttAcl(com.sitewhere.grpc.service.GCreateMqttAclRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void createMqttAcl(GCreateMqttAclRequest request,
                                  StreamObserver<GCreateMqttAclResponse> responseObserver) {
        MqttAclGrpc.MqttAclImplBase engine = getTenantImplementation(responseObserver);
        if (engine != null) {
            engine.createMqttAcl(request, responseObserver);
        }
    }

    /*
     * @see
     * com.sitewhere.grpc.service.MqttAclGrpc.MqttAclImplBase#getMqttAcl
     * (com.sitewhere.grpc.service.GGetMqttAclRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void getMqttAcl(GGetMqttAclRequest request,
                               StreamObserver<GGetMqttAclResponse> responseObserver) {
        MqttAclGrpc.MqttAclImplBase engine = getTenantImplementation(responseObserver);
        if (engine != null) {
            engine.getMqttAcl(request, responseObserver);
        }
    }

    /*
     * @see com.sitewhere.grpc.service.MqttAclGrpc.MqttAclImplBase#
     * updateMqttAcl(com.sitewhere.grpc.service.GUpdateMqttAclRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void updateMqttAcl(GUpdateMqttAclRequest request,
                                  StreamObserver<GUpdateMqttAclResponse> responseObserver) {
        MqttAclGrpc.MqttAclImplBase engine = getTenantImplementation(responseObserver);
        if (engine != null) {
            engine.updateMqttAcl(request, responseObserver);
        }
    }

    /*
     * @see com.sitewhere.grpc.service.MqttAclGrpc.MqttAclImplBase#
     * deleteMqttAcl(com.sitewhere.grpc.service.GDeleteMqttAclRequest,
     * io.grpc.stub.StreamObserver)
     */
    @Override
    public void deleteMqttAcl(GDeleteMqttAclRequest request,
                                  StreamObserver<GDeleteMqttAclResponse> responseObserver) {
        MqttAclGrpc.MqttAclImplBase engine = getTenantImplementation(responseObserver);
        if (engine != null) {
            engine.deleteMqttAcl(request, responseObserver);
        }
    }

    @Override
    public void createMqttUser(GCreateMqttUserRequest request, StreamObserver<GCreateMqttUserResponse> responseObserver) {
        MqttAclGrpc.MqttAclImplBase engine = getTenantImplementation(responseObserver);
        if (engine != null) {
            engine.createMqttUser(request, responseObserver);
        }
    }

    @Override
    public void getMqttUser(GGetMqttUserRequest request, StreamObserver<GGetMqttUserResponse> responseObserver) {
        MqttAclGrpc.MqttAclImplBase engine = getTenantImplementation(responseObserver);
        if (engine != null) {
            engine.getMqttUser(request, responseObserver);
        }
    }

    public void updateMqttUser(GUpdateMqttUserRequest request, StreamObserver<GUpdateMqttUserResponse> responseObserver) {
        MqttAclGrpc.MqttAclImplBase engine = getTenantImplementation(responseObserver);
        if (engine != null) {
            engine.updateMqttUser(request, responseObserver);
        }
    }

    public void deleteMqttUser(GDeleteMqttUserRequest request, StreamObserver<GDeleteMqttUserResponse> responseObserver) {
        MqttAclGrpc.MqttAclImplBase engine = getTenantImplementation(responseObserver);
        if (engine != null) {
            engine.deleteMqttUser(request, responseObserver);
        }
    }

    protected IMqttAclMicroservice getMicroservice() {
        return microservice;
    }

    protected void setMicroservice(IMqttAclMicroservice microservice) {
        this.microservice = microservice;
    }
}
