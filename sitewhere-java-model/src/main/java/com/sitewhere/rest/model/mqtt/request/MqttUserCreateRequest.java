/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.rest.model.mqtt.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sitewhere.spi.mqtt.request.IMqttUserCreateRequest;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MqttUserCreateRequest implements IMqttUserCreateRequest {
    private String username;
    private String password;
    private Boolean isSuperUser;

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getSuperUser() {
        return isSuperUser;
    }

    public void setSuperUser(Boolean superUser) {
        isSuperUser = superUser;
    }
}
