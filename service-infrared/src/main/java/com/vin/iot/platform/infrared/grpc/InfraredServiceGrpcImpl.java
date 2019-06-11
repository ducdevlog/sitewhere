package com.vin.iot.platform.infrared.grpc;

import com.sitewhere.grpc.service.*;
import com.vin.iot.platform.infrared.domain.DeviceCodeset;
import com.vin.iot.platform.infrared.domain.DeviceType;
import com.vin.iot.platform.infrared.domain.DeviceTypeBrand;
import com.vin.iot.platform.infrared.domain.IrCodeRaw;
import com.vin.iot.platform.infrared.service.DeviceCodesetService;
import com.vin.iot.platform.infrared.service.DeviceTypeBrandService;
import com.vin.iot.platform.infrared.service.DeviceTypeService;
import com.vin.iot.platform.infrared.service.IrCodeRawService;
import org.apache.commons.collections.CollectionUtils;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@GRpcService
public class InfraredServiceGrpcImpl extends InfraredGrpc.InfraredImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(InfraredServiceGrpcImpl.class);

    @Autowired
    private DeviceTypeService deviceTypeService;
    @Autowired
    private DeviceTypeBrandService deviceTypeBrandService;
    @Autowired
    private DeviceCodesetService deviceCodesetService;
    @Autowired
    private IrCodeRawService irCodeRawService;

    public void getDeviceType(com.sitewhere.grpc.service.GGetDeviceTypeRequest request,
                              io.grpc.stub.StreamObserver<GGetDeviceTypeResponse> responseObserver) {
        LOGGER.info("server received {}", request);
        List<DeviceType> deviceTypes = deviceTypeService.getAllDeviceType();
        GGetDeviceTypeResponse.Builder response = GGetDeviceTypeResponse.newBuilder();
        if (CollectionUtils.isNotEmpty(deviceTypes)) {
            deviceTypes.stream().map(InfraredModelConverter::asGrpcDeviceType).forEach(response::addDeviceType);
        }
        LOGGER.info("server responded {}", response);
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    public void getDeviceTypeBrand(com.sitewhere.grpc.service.GGetDeviceTypeBrandRequest request,
                                   io.grpc.stub.StreamObserver<com.sitewhere.grpc.service.GGetDeviceTypeBrandResponse> responseObserver) {
        LOGGER.info("server received {}", request);
        List<DeviceTypeBrand> deviceTypeBrands = deviceTypeBrandService.getDeviceTypeBrandByType(request.getType());
        GGetDeviceTypeBrandResponse.Builder response = GGetDeviceTypeBrandResponse.newBuilder();
        if (CollectionUtils.isNotEmpty(deviceTypeBrands)) {
            deviceTypeBrands.stream().map(InfraredModelConverter::asGrpcDeviceTypeBrand).forEach(response::addDeviceTypeBrand);
        }
        LOGGER.info("server responded {}", response);
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    public void getDeviceCodeset(com.sitewhere.grpc.service.GGetDeviceCodesetRequest request,
                                 io.grpc.stub.StreamObserver<com.sitewhere.grpc.service.GGetDeviceCodesetResponse> responseObserver) {
        LOGGER.info("server received {}", request);
        List<DeviceCodeset> deviceCodesets = deviceCodesetService.getDeviceCodesetByDeviceTypeBrandId(request.getDeviceTypeBrandId());
        GGetDeviceCodesetResponse.Builder response = GGetDeviceCodesetResponse.newBuilder();
        if (CollectionUtils.isNotEmpty(deviceCodesets)) {
            deviceCodesets.stream().map(InfraredModelConverter::asGrpcDeviceCodeset).forEach(response::addDeviceCodeset);
        }
        LOGGER.info("server responded {}", response);
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    public void getIrCodeRaw(com.sitewhere.grpc.service.GGetIrCodeRawRequest request,
                             io.grpc.stub.StreamObserver<com.sitewhere.grpc.service.GGetIrCodeRawResponse> responseObserver) {
        LOGGER.info("server received {}", request);
        IrCodeRaw apiRequest = InfraredModelConverter.asApiGIrCodeRaw(request.getIrCodeRaw());
        List<IrCodeRaw> irCodeRaws = irCodeRawService.getIrCodeRaw(apiRequest);
        GGetIrCodeRawResponse.Builder response = GGetIrCodeRawResponse.newBuilder();
        if (CollectionUtils.isNotEmpty(irCodeRaws)) {
            irCodeRaws.stream().map(InfraredModelConverter::asGrpcIrCodeRaw).forEach(response::addIrCodeRaw);
        }
        LOGGER.info("server responded {}", response);
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
}
