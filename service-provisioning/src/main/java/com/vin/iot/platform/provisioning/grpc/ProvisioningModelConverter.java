package com.vin.iot.platform.provisioning.grpc;

import com.sitewhere.grpc.model.ProvisioningModel;
import com.vin.iot.platform.provisioning.domain.DevicePending;
import org.apache.commons.lang.StringUtils;

public class ProvisioningModelConverter {

    public static ProvisioningModel.GDevicePending asGrpcProvisioningDeviceType(DevicePending api) {
        ProvisioningModel.GDevicePending.Builder grpc = ProvisioningModel.GDevicePending.newBuilder();
        if (StringUtils.isNotEmpty(api.getId())) grpc.setId(api.getId());
        if (StringUtils.isNotEmpty(api.getHardwareId())) grpc.setHardwareId(api.getHardwareId());
        if (StringUtils.isNotEmpty(api.getDeviceType())) grpc.setDeviceType(api.getDeviceType());
        if (StringUtils.isNotEmpty(api.getStatus())) grpc.setStatus(api.getStatus());
        grpc.setSsl(api.isSsl());
        if (StringUtils.isNotEmpty(api.getCaUrl())) grpc.setCaUrl(api.getCaUrl());
        if (StringUtils.isNotEmpty(api.getPrivateUrl())) grpc.setPrivateUrl(api.getPrivateUrl());
        if (StringUtils.isNotEmpty(api.getPublicUrl())) grpc.setPublicUrl(api.getPublicUrl());
        if (StringUtils.isNotEmpty(api.getDeviceId())) grpc.setDeviceId(api.getDeviceId());
        if (StringUtils.isNotEmpty(api.getPassword())) grpc.setPassword(api.getPassword());
        return grpc.build();
    }

    public static DevicePending asApiInfraredDeviceType(ProvisioningModel.GDevicePending grpc) {
        DevicePending api = new DevicePending();
        api.setId(grpc.getId());
        api.setHardwareId(grpc.getHardwareId());
        api.setDeviceType(grpc.getDeviceType());
        api.setStatus(grpc.getStatus());
        api.setSsl(grpc.getSsl());
        api.setCaUrl(grpc.getCaUrl());
        api.setPrivateUrl(grpc.getPrivateUrl());
        api.setPublicUrl(grpc.getPublicUrl());
        api.setDeviceId(grpc.getDeviceId());
        api.setPassword(grpc.getPassword());
        return api;
    }

}
