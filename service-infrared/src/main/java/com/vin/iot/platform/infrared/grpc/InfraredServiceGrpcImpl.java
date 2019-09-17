package com.vin.iot.platform.infrared.grpc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Any;
import com.sitewhere.grpc.model.InfraredModel;
import com.sitewhere.grpc.service.*;
import com.vin.iot.platform.infrared.domain.*;
import com.vin.iot.platform.infrared.service.*;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private IrCodeRawLearnService irCodeRawLearnService;

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

    public void createInfraredDeviceTypeBrand(com.sitewhere.grpc.service.GCreateInfraredDeviceTypeBrandRequest request,
                                              io.grpc.stub.StreamObserver<com.sitewhere.grpc.service.GCreateInfraredDeviceTypeBrandResponse> responseObserver) {
        log.info("server received {}", request);
        InfraredDeviceTypeBrand infraredDeviceTypeBrand = deviceTypeBrandService.createInfraredDeviceTypeBrand(InfraredModelConverter.asApiInfraredDeviceTypeBrand(request.getInfraredDeviceTypeBrand()));
        GCreateInfraredDeviceTypeBrandResponse.Builder response = GCreateInfraredDeviceTypeBrandResponse.newBuilder();
        response.setInfraredDeviceTypeBrand(InfraredModelConverter.asGrpcInfraredDeviceTypeBrand(infraredDeviceTypeBrand));
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

    public void createInfraredDeviceCodeset(com.sitewhere.grpc.service.GCreateInfraredDeviceCodesetRequest request,
                                            io.grpc.stub.StreamObserver<com.sitewhere.grpc.service.GCreateInfraredDeviceCodesetResponse> responseObserver) {
        log.info("server received {}", request);
        InfraredDeviceCodeset infraredDeviceCodeset = deviceCodesetService.createInfraredDeviceCodeset(InfraredModelConverter.asApiInfraredDeviceCodeset(request.getInfraredDeviceCodeset()));
        GCreateInfraredDeviceCodesetResponse.Builder response = GCreateInfraredDeviceCodesetResponse.newBuilder();
        response.setInfraredDeviceCodeset(InfraredModelConverter.asGrpcInfraredDeviceCodeset(infraredDeviceCodeset));
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

    public void getIrCodeRawFilter(GIrCodeRawFilterRequest request,
                                   StreamObserver<GIrCodeRawFilterResponse> responseObserver) {
        log.info("server received {}", request);
        ObjectMapper objectMapper = new ObjectMapper();
        Map filter = null;
        try {
            filter = objectMapper.readValue(request.getIrCodeRawFilter(), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Page<Map<String, Object>> irCodeRaws = irCodeRawService.getIrCodeRawFilter(filter, request.getPage(), request.getSize());
        GIrCodeRawFilterResponse.Builder response = GIrCodeRawFilterResponse.newBuilder();
        for (Map<String, Object> irCodeRaw : irCodeRaws.getContent()) {
            InfraredModel.GIrCodeRawMap.Builder builder = InfraredModel.GIrCodeRawMap.newBuilder();
            for (Map.Entry<String, Object> entry : irCodeRaw.entrySet()) {
                if (entry.getValue() instanceof Integer) {
                    builder.putDataFilter(entry.getKey(), Any.pack(InfraredModel.GOptionalInteger.newBuilder().setValue((Integer) entry.getValue()).build()));
                } else if (entry.getValue() instanceof Double) {
                    builder.putDataFilter(entry.getKey(), Any.pack(InfraredModel.GOptionalDouble.newBuilder().setValue((Double) entry.getValue()).build()));
                } else if (entry.getValue() instanceof Boolean) {
                    builder.putDataFilter(entry.getKey(), Any.pack(InfraredModel.GOptionalBoolean.newBuilder().setValue((Boolean) entry.getValue()).build()));
                } else if (entry.getValue() instanceof String) {
                    builder.putDataFilter(entry.getKey(), Any.pack(InfraredModel.GOptionalString.newBuilder().setValue((String) entry.getValue()).build()));
                }
            }
            response.addGIrCodeRawMap(builder);
        }
        log.info("server responded {}", response);
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    public void createInfraredCodeRaw(com.sitewhere.grpc.service.GCreateInfraredCodeRawRequest request,
                                      io.grpc.stub.StreamObserver<com.sitewhere.grpc.service.GCreateInfraredCodeRawResponse> responseObserver) {
        log.info("server received {}", request);
        ObjectMapper objectMapper = new ObjectMapper();
        Map filter = null;
        try {
            filter = objectMapper.readValue(request.getIrCodeRaw(), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> irCodeRaw = irCodeRawService.createInfraredCodeRaw(filter);
        GCreateInfraredCodeRawResponse.Builder response = GCreateInfraredCodeRawResponse.newBuilder();
        InfraredModel.GIrCodeRawMap.Builder builder = InfraredModel.GIrCodeRawMap.newBuilder();
        for (Map.Entry<String, Object> entry : irCodeRaw.entrySet()) {
            if (entry.getValue() instanceof Integer) {
                builder.putDataFilter(entry.getKey(), Any.pack(InfraredModel.GOptionalInteger.newBuilder().setValue((Integer) entry.getValue()).build()));
            } else if (entry.getValue() instanceof Double) {
                builder.putDataFilter(entry.getKey(), Any.pack(InfraredModel.GOptionalDouble.newBuilder().setValue((Double) entry.getValue()).build()));
            } else if (entry.getValue() instanceof Boolean) {
                builder.putDataFilter(entry.getKey(), Any.pack(InfraredModel.GOptionalBoolean.newBuilder().setValue((Boolean) entry.getValue()).build()));
            } else if (entry.getValue() instanceof String) {
                builder.putDataFilter(entry.getKey(), Any.pack(InfraredModel.GOptionalString.newBuilder().setValue((String) entry.getValue()).build()));
            }
        }
        response.setGIrCodeRawMap(builder);
        log.info("server responded {}", response);
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    public void createIrCodeRaw(com.sitewhere.grpc.service.GIrCodeRawCreateRequest request,
                                io.grpc.stub.StreamObserver<com.sitewhere.grpc.service.GIrCodeRawCreateResponse> responseObserver) {
        log.info("server received {}", request);
        IrCodeRaw apiRequest = InfraredModelConverter.asApiGIrCodeRaw(request.getIrCodeRaw());
        irCodeRawService.createIrCodeRaw(apiRequest);
        GIrCodeRawCreateResponse.Builder response = GIrCodeRawCreateResponse.newBuilder();
        response.setIrCodeRaw(InfraredModelConverter.asGrpcIrCodeRaw(apiRequest));
        log.info("server responded {}", response);
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    public void createIrCodeRawLearn(com.sitewhere.grpc.service.GIrCodeRawLearnCreateRequest request,
                                     io.grpc.stub.StreamObserver<com.sitewhere.grpc.service.GIrCodeRawLearnCreateResponse> responseObserver) {
        log.info("server received {}", request);
        IrCodeRawLearn apiRequest = InfraredModelConverter.asApiGIrCodeRawLearn(request.getIrCodeRawLearn());
        irCodeRawLearnService.createIrCodeRawLearn(apiRequest);
        GIrCodeRawLearnCreateResponse.Builder response = GIrCodeRawLearnCreateResponse.newBuilder();
        log.info("server responded {}", response);
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
}
