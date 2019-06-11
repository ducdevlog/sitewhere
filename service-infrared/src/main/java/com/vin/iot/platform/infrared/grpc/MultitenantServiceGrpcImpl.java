package com.vin.iot.platform.infrared.grpc;

import com.sitewhere.grpc.service.GCheckTenantEngineAvailableRequest;
import com.sitewhere.grpc.service.GCheckTenantEngineAvailableResponse;
import com.sitewhere.grpc.service.MultitenantManagementGrpc;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GRpcService
public class MultitenantServiceGrpcImpl  extends MultitenantManagementGrpc.MultitenantManagementImplBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(MultitenantServiceGrpcImpl.class);

    public void checkTenantEngineAvailable(GCheckTenantEngineAvailableRequest request,
                                           io.grpc.stub.StreamObserver<GCheckTenantEngineAvailableResponse> responseObserver) {
        LOGGER.info("server received {}", request);
        GCheckTenantEngineAvailableResponse.Builder response = GCheckTenantEngineAvailableResponse.newBuilder();
        response.setAvailable(true);
        LOGGER.info("server responded");
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
}
