/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.grpc.client.mqtt;

import com.sitewhere.grpc.model.CommonModel;
import com.sitewhere.grpc.model.MqttAclModel;
import com.sitewhere.grpc.model.MqttAclModel.GMqttAcl;
import com.sitewhere.grpc.model.MqttAclModel.GMqttAclCreateRequest;
import com.sitewhere.grpc.model.MqttAclModel.GMqttUserCreateRequest;
import com.sitewhere.rest.model.mqtt.MqttAcl;
import com.sitewhere.rest.model.mqtt.MqttUser;
import com.sitewhere.rest.model.mqtt.request.MqttAclCreateRequest;
import com.sitewhere.rest.model.mqtt.request.MqttUserCreateRequest;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.mqtt.IMqttAcl;
import com.sitewhere.spi.mqtt.IMqttUser;
import com.sitewhere.spi.mqtt.request.IMqttAclCreateRequest;
import com.sitewhere.spi.mqtt.request.IMqttUserCreateRequest;

import java.util.stream.Collectors;

public class MqttAclModelConverter {

    public static IMqttAclCreateRequest asApiMqttAclCreateRequest(GMqttAclCreateRequest grpc)
            throws SiteWhereException {
        MqttAclCreateRequest api = new MqttAclCreateRequest();
        api.setUsername(grpc.getUsername());
        api.setClientId(grpc.getClientId());
        api.setPublish(grpc.getPublishList().stream().map(CommonModel.GOptionalString::getValue).collect(Collectors.toList()));
        api.setSubscribe(grpc.getSubscribeList().stream().map(CommonModel.GOptionalString::getValue).collect(Collectors.toList()));
        api.setPubSub(grpc.getPubsubList().stream().map(CommonModel.GOptionalString::getValue).collect(Collectors.toList()));
        return api;
    }

    public static GMqttAclCreateRequest asGrpcMqttAclCreateRequest(IMqttAclCreateRequest api)
            throws SiteWhereException {
        GMqttAclCreateRequest.Builder grpc = GMqttAclCreateRequest.newBuilder();
        grpc.setUsername(api.getUsername());
        grpc.setClientId(api.getClientId());
        api.getPublish().forEach(publish -> grpc.getPublishList().add(CommonModel.GOptionalString.newBuilder().setValue(publish).build()));
        api.getSubscribe().forEach(subscribe -> grpc.getSubscribeList().add(CommonModel.GOptionalString.newBuilder().setValue(subscribe).build()));
        api.getPubSub().forEach(pubSub -> grpc.getPubsubList().add(CommonModel.GOptionalString.newBuilder().setValue(pubSub).build()));
        return grpc.build();
    }

    public static IMqttUserCreateRequest asApiMqttUserCreateRequest(GMqttUserCreateRequest grpc)
            throws SiteWhereException {
        MqttUserCreateRequest api = new MqttUserCreateRequest();
        api.setUsername(grpc.getUsername());
        api.setPassword(grpc.getPassword());
        api.setSuperUser(grpc.getIsSuperuser().getValue());
        return api;
    }

    public static GMqttUserCreateRequest asGrpcMqttUserCreateRequest(IMqttUserCreateRequest api)
            throws SiteWhereException {
        GMqttUserCreateRequest.Builder grpc = GMqttUserCreateRequest.newBuilder();
        grpc.setUsername(api.getUsername());
        grpc.setPassword(api.getPassword());
        grpc.setIsSuperuser(CommonModel.GOptionalBoolean.newBuilder().setValue(api.getSuperUser()).build());
        return grpc.build();
    }

    public static GMqttAcl asGrpcMqttAcl(IMqttAcl api) throws SiteWhereException {
        GMqttAcl.Builder grpc = GMqttAcl.newBuilder();
        grpc.setUsername(api.getUsername());
        grpc.setClientId(api.getClientId());
        api.getPublish().forEach(publish -> grpc.getPublishList().add(CommonModel.GOptionalString.newBuilder().setValue(publish).build()));
        api.getSubscribe().forEach(subscribe -> grpc.getSubscribeList().add(CommonModel.GOptionalString.newBuilder().setValue(subscribe).build()));
        api.getPubSub().forEach(pubSub -> grpc.getPubsubList().add(CommonModel.GOptionalString.newBuilder().setValue(pubSub).build()));
        return grpc.build();
    }

    public static IMqttAcl asApiMqttAcl(GMqttAcl grpc) throws SiteWhereException {
        MqttAcl api = new MqttAcl();
        api.setUsername(grpc.getUsername());
        api.setClientId(grpc.getClientId());
        api.setPublish(grpc.getPublishList().stream().map(CommonModel.GOptionalString::getValue).collect(Collectors.toList()));
        api.setSubscribe(grpc.getSubscribeList().stream().map(CommonModel.GOptionalString::getValue).collect(Collectors.toList()));
        api.setPubSub(grpc.getPubsubList().stream().map(CommonModel.GOptionalString::getValue).collect(Collectors.toList()));
        return api;
    }

    public static MqttAclModel.GMqttUser asGrpcMqttUser(IMqttUser api)
            throws SiteWhereException {
        MqttAclModel.GMqttUser.Builder grpc = MqttAclModel.GMqttUser.newBuilder();
        grpc.setUsername(api.getUsername());
        grpc.setPassword(api.getPassword());
        grpc.setIsSuperuser(CommonModel.GOptionalBoolean.newBuilder().setValue(api.getSuperUser()).build());
        return grpc.build();
    }

    public static IMqttUser asApiMqttUser(MqttAclModel.GMqttUser grpc)
            throws SiteWhereException {
        MqttUser api = new MqttUser();
        api.setUsername(grpc.getUsername());
        api.setPassword(grpc.getPassword());
        api.setSuperUser(grpc.getIsSuperuser().getValue());
        return api;
    }
}
