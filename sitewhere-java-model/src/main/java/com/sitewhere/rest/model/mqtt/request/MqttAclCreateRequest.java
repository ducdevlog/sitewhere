/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.rest.model.mqtt.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sitewhere.spi.mqtt.request.IMqttAclCreateRequest;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MqttAclCreateRequest implements IMqttAclCreateRequest {
    private String username;
    private String clientId;
    private List<String> publish;
    private List<String> subscribe;
    private List<String> pubSub;

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public List<String> getPublish() {
        return publish;
    }

    public void setPublish(List<String> publish) {
        this.publish = publish;
    }

    @Override
    public List<String> getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(List<String> subscribe) {
        this.subscribe = subscribe;
    }

    @Override
    public List<String> getPubSub() {
        return pubSub;
    }

    public void setPubSub(List<String> pubSub) {
        this.pubSub = pubSub;
    }
}
