/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.rest.model.mqtt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sitewhere.rest.model.common.PersistentEntity;
import com.sitewhere.spi.mqtt.IMqttUser;

import java.util.Date;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MqttUser extends PersistentEntity implements IMqttUser {

    public UUID id;
    private String username;
    private String password;
    private Boolean isSuperUser;
    private Date created = new Date();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSuperUser(Boolean superUser) {
        isSuperUser = superUser;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Boolean getSuperUser() {
        return isSuperUser;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
