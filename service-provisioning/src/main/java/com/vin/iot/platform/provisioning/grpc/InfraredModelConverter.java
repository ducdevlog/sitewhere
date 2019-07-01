//package com.vin.iot.platform.infrared.grpc;
//
//import com.sitewhere.grpc.model.InfraredModel;
//import com.vin.iot.platform.infrared.domain.InfraredDeviceCodeset;
//import com.vin.iot.platform.infrared.domain.InfraredDeviceType;
//import com.vin.iot.platform.infrared.domain.InfraredDeviceTypeBrand;
//import com.vin.iot.platform.infrared.domain.IrCodeRaw;
//import org.apache.commons.lang3.StringUtils;
//
//public class InfraredModelConverter {
//
//    public static InfraredModel.GInfraredDeviceType asGrpcInfraredDeviceType(InfraredDeviceType api) {
//        InfraredModel.GInfraredDeviceType.Builder grpc = InfraredModel.GInfraredDeviceType.newBuilder();
//        grpc.setId(api.getId());
//        grpc.setTypeName(api.getTypeName());
//        return grpc.build();
//    }
//
//    public static InfraredDeviceType asApiInfraredDeviceType(InfraredModel.GInfraredDeviceType grpc) {
//        InfraredDeviceType api = new InfraredDeviceType();
//        api.setId(grpc.getId());
//        api.setTypeName(grpc.getTypeName());
//        return api;
//    }
//
//    public static InfraredModel.GInfraredDeviceTypeBrand asGrpcInfraredDeviceTypeBrand(InfraredDeviceTypeBrand api) {
//        InfraredModel.GInfraredDeviceTypeBrand.Builder grpc = InfraredModel.GInfraredDeviceTypeBrand.newBuilder();
//        grpc.setId(api.getId());
//        grpc.setType(api.getType());
//        grpc.setBrand(api.getBrand());
//        return grpc.build();
//    }
//
//    public static InfraredDeviceTypeBrand asApiInfraredDeviceTypeBrand(InfraredModel.GInfraredDeviceTypeBrand grpc) {
//        InfraredDeviceTypeBrand api = new InfraredDeviceTypeBrand();
//        api.setId(grpc.getId());
//        api.setType(grpc.getType());
//        api.setBrand(grpc.getBrand());
//        return api;
//    }
//
//    public static InfraredModel.GInfraredDeviceCodeset asGrpcInfraredDeviceCodeset(InfraredDeviceCodeset api) {
//        InfraredModel.GInfraredDeviceCodeset.Builder grpc = InfraredModel.GInfraredDeviceCodeset.newBuilder();
//        grpc.setId(api.getId());
//        grpc.setDeviceTypeBrandId(api.getDeviceTypeBrandId());
//        grpc.setCodesetName(api.getCodesetName());
//        return grpc.build();
//    }
//
//    public static InfraredDeviceCodeset asApiInfraredDeviceCodeset(InfraredModel.GInfraredDeviceCodeset grpc) {
//        InfraredDeviceCodeset api = new InfraredDeviceCodeset();
//        api.setId(grpc.getId());
//        api.setDeviceTypeBrandId(grpc.getDeviceTypeBrandId());
//        api.setCodesetName(grpc.getCodesetName());
//        return api;
//    }
//
//    public static InfraredModel.GIrCodeRaw asGrpcIrCodeRaw(IrCodeRaw api) {
//        InfraredModel.GIrCodeRaw.Builder grpc = InfraredModel.GIrCodeRaw.newBuilder();
//        if (StringUtils.isNoneEmpty(api.getId())) grpc.setId(api.getId());
//        if (StringUtils.isNoneEmpty(api.getCodesetName())) grpc.setCodesetName(api.getCodesetName());
//        if (StringUtils.isNoneEmpty(api.getFunctionName())) grpc.setFunctionName(api.getFunctionName());
//        if (StringUtils.isNoneEmpty(api.getPower())) grpc.setPower(api.getPower());
//        if (StringUtils.isNoneEmpty(api.getMode())) grpc.setMode(api.getMode());
//        if (StringUtils.isNoneEmpty(api.getFan())) grpc.setFan(api.getFan());
//        if (StringUtils.isNoneEmpty(api.getTemp())) grpc.setTemp(api.getTemp());
//        if (StringUtils.isNoneEmpty(api.getSwing())) grpc.setSwing(api.getSwing());
//        if (StringUtils.isNoneEmpty(api.getTimer())) grpc.setTimer(api.getTimer());
//        if (StringUtils.isNoneEmpty(api.getTimerDelay())) grpc.setTimerDelay(api.getTimerDelay());
//        if (StringUtils.isNoneEmpty(api.getLed())) grpc.setLed(api.getLed());
//        if (StringUtils.isNoneEmpty(api.getComfort())) grpc.setComfort(api.getComfort());
//        if (StringUtils.isNoneEmpty(api.getEcono())) grpc.setEcono(api.getEcono());
//        if (StringUtils.isNoneEmpty(api.getPowerful())) grpc.setPowerful(api.getPowerful());
//        if (StringUtils.isNoneEmpty(api.getIrFreqKhz())) grpc.setIrFreqKhz(api.getIrFreqKhz());
//        if (StringUtils.isNoneEmpty(api.getIrCode())) grpc.setIrCode(api.getIrCode());
//        return grpc.build();
//    }
//
//    public static IrCodeRaw asApiGIrCodeRaw(InfraredModel.GIrCodeRaw grpc) {
//        IrCodeRaw api = new IrCodeRaw();
//        api.setId(grpc.getId());
//        api.setCodesetName(grpc.getCodesetName());
//        api.setFunctionName(grpc.getFunctionName());
//        api.setPower(grpc.getPower());
//        api.setMode(grpc.getMode());
//        api.setFan(grpc.getFan());
//        api.setTemp(grpc.getTemp());
//        api.setSwing(grpc.getSwing());
//        api.setTimer(grpc.getTimer());
//        api.setTimerDelay(grpc.getTimerDelay());
//        api.setLed(grpc.getLed());
//        api.setComfort(grpc.getComfort());
//        api.setEcono(grpc.getEcono());
//        api.setPowerful(grpc.getPowerful());
//        api.setIrFreqKhz(grpc.getIrFreqKhz());
//        api.setIrCode(grpc.getIrCode());
//        return api;
//    }
//}
