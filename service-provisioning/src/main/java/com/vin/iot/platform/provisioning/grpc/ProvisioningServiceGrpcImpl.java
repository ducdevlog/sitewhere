package com.vin.iot.platform.provisioning.grpc;

import com.sitewhere.grpc.service.GGetDevicePendingResponse;
import com.sitewhere.grpc.service.GUpdateDevicePendingResponse;
import com.sitewhere.grpc.service.ProvisioningGrpc;
import com.vin.iot.platform.provisioning.domain.DevicePending;
import com.vin.iot.platform.provisioning.service.impl.DevicePendingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.lognet.springboot.grpc.GRpcService;

import java.util.List;

@GRpcService
@Slf4j
public class ProvisioningServiceGrpcImpl extends ProvisioningGrpc.ProvisioningImplBase {

    private final DevicePendingServiceImpl devicePendingService;

    public ProvisioningServiceGrpcImpl(DevicePendingServiceImpl devicePendingService) {
        this.devicePendingService = devicePendingService;
    }

    public void getDevicePending(com.sitewhere.grpc.service.GGetDevicePendingRequest request,
                                 io.grpc.stub.StreamObserver<com.sitewhere.grpc.service.GGetDevicePendingResponse> responseObserver) {
        log.info("server received {}", request);
        List<DevicePending> devicePendings = devicePendingService.findByHardwareId(request.getHardwareId());
        GGetDevicePendingResponse.Builder response = GGetDevicePendingResponse.newBuilder();
        if (CollectionUtils.isNotEmpty(devicePendings)) {
            devicePendings.stream().map(ProvisioningModelConverter::asGrpcProvisioningDeviceType).forEach(response::addDevicePending);
        }
        log.info("server responded {}", response);
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    public void updateDevicePending(com.sitewhere.grpc.service.GUpdateDevicePendingRequest request,
                                    io.grpc.stub.StreamObserver<com.sitewhere.grpc.service.GUpdateDevicePendingResponse> responseObserver) {
        log.info("server received {}", request);
        DevicePending devicePending = devicePendingService.updateDevicePending(request.getHardwareId(), request.getStatus());
        GUpdateDevicePendingResponse.Builder response = GUpdateDevicePendingResponse.newBuilder();
        if (devicePending != null) {
            response.setDevicePending(ProvisioningModelConverter.asGrpcProvisioningDeviceType(devicePending));
        }
        log.info("server responded {}", response);
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
}
