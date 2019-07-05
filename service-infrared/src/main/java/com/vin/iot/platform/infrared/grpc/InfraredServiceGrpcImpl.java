package com.vin.iot.platform.infrared.grpc;

import com.sitewhere.grpc.service.*;
import com.vin.iot.platform.infrared.domain.InfraredDeviceCodeset;
import com.vin.iot.platform.infrared.domain.InfraredDeviceType;
import com.vin.iot.platform.infrared.domain.InfraredDeviceTypeBrand;
import com.vin.iot.platform.infrared.domain.IrCodeRaw;
import com.vin.iot.platform.infrared.service.DeviceCodesetService;
import com.vin.iot.platform.infrared.service.DeviceTypeBrandService;
import com.vin.iot.platform.infrared.service.DeviceTypeService;
import com.vin.iot.platform.infrared.service.IrCodeRawService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

@GRpcService
@Slf4j
public class InfraredServiceGrpcImpl extends InfraredGrpc.InfraredImplBase {
    
    @Autowired
    private DeviceTypeService deviceTypeService;
    @Autowired
    private DeviceTypeBrandService deviceTypeBrandService;
    @Autowired
    private DeviceCodesetService deviceCodesetService;
    @Autowired
    private IrCodeRawService irCodeRawService;

    public void getInfraredDeviceType(com.sitewhere.grpc.service.GGetInfraredDeviceTypeRequest request,
                              io.grpc.stub.StreamObserver<GGetInfraredDeviceTypeResponse> responseObserver) {
        log.info("server received {}", request);
        List<InfraredDeviceType> infraredDeviceTypes = deviceTypeService.getAllDeviceType();
        GGetInfraredDeviceTypeResponse.Builder response = GGetInfraredDeviceTypeResponse.newBuilder();
        if (CollectionUtils.isNotEmpty(infraredDeviceTypes)) {
            infraredDeviceTypes.stream().map(InfraredModelConverter::asGrpcInfraredDeviceType).forEach(response::addInfraredDeviceType);
        }
        log.info("server responded {}", response);
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    public void getInfraredDeviceTypeBrand(com.sitewhere.grpc.service.GGetInfraredDeviceTypeBrandRequest request,
                                   io.grpc.stub.StreamObserver<com.sitewhere.grpc.service.GGetInfraredDeviceTypeBrandResponse> responseObserver) {
        log.info("server received {}", request);
        List<InfraredDeviceTypeBrand> infraredDeviceTypeBrands = deviceTypeBrandService.getDeviceTypeBrandByType(request.getType());
        GGetInfraredDeviceTypeBrandResponse.Builder response = GGetInfraredDeviceTypeBrandResponse.newBuilder();
        if (CollectionUtils.isNotEmpty(infraredDeviceTypeBrands)) {
            infraredDeviceTypeBrands.stream().map(InfraredModelConverter::asGrpcInfraredDeviceTypeBrand).forEach(response::addInfraredDeviceTypeBrand);
        }
        log.info("server responded {}", response);
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    public void getInfraredDeviceCodeset(com.sitewhere.grpc.service.GGetInfraredDeviceCodesetRequest request,
                                 io.grpc.stub.StreamObserver<com.sitewhere.grpc.service.GGetInfraredDeviceCodesetResponse> responseObserver) {
        log.info("server received {}", request);
        List<InfraredDeviceCodeset> infraredDeviceCodesets = deviceCodesetService.getDeviceCodesetByDeviceTypeBrandId(request.getDeviceTypeBrandId());
        GGetInfraredDeviceCodesetResponse.Builder response = GGetInfraredDeviceCodesetResponse.newBuilder();
        if (CollectionUtils.isNotEmpty(infraredDeviceCodesets)) {
            infraredDeviceCodesets.stream().map(InfraredModelConverter::asGrpcInfraredDeviceCodeset).forEach(response::addInfraredDeviceCodeset);
        }
        log.info("server responded {}", response);
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    public void getIrCodeRaw(com.sitewhere.grpc.service.GIrCodeRawRequest request,
                             io.grpc.stub.StreamObserver<com.sitewhere.grpc.service.GIrCodeRawResponse> responseObserver) {
        log.info("server received {}", request);
        IrCodeRaw apiRequest = InfraredModelConverter.asApiGIrCodeRaw(request.getIrCodeRaw());
        Page<IrCodeRaw> irCodeRaws = irCodeRawService.getIrCodeRaw(apiRequest, request.getPage(), request.getSize());
        GIrCodeRawResponse.Builder response = GIrCodeRawResponse.newBuilder();
        if (irCodeRaws != null) {
            response.setResults(Math.toIntExact(irCodeRaws.getTotalElements()));
            irCodeRaws.stream().map(InfraredModelConverter::asGrpcIrCodeRaw).forEach(response::addIrCodeRaw);
        }
        log.info("server responded {}", response);
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
}
