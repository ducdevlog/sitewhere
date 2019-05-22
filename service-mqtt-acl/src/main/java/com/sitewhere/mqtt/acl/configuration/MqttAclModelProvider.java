/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.mqtt.acl.configuration;

import com.sitewhere.configuration.model.CommonDatastoreProvider;
import com.sitewhere.configuration.model.ConfigurationModelProvider;
import com.sitewhere.configuration.parser.IMqttAclManagementParser;
import com.sitewhere.rest.model.configuration.ElementNode;
import com.sitewhere.spi.microservice.configuration.model.IConfigurationRoleProvider;

/**
 * Configuration model provider for presence management microservice.
 *
 * @author Derek
 */
public class MqttAclModelProvider extends ConfigurationModelProvider {

    /*
     * @see com.sitewhere.spi.microservice.configuration.model.
     * IConfigurationModelProvider#getDefaultXmlNamespace()
     */
    @Override
    public String getDefaultXmlNamespace() {
        return "http://sitewhere.io/schema/sitewhere/microservice/mqtt-acl";
    }

    /*
     * @see com.sitewhere.spi.microservice.configuration.model.
     * IConfigurationModelProvider#getRootRole()
     */
    @Override
    public IConfigurationRoleProvider getRootRole() {
        return MqttAclRoles.MqttAcl;
    }

    /*
     * @see com.sitewhere.spi.microservice.configuration.model.
     * IConfigurationModelProvider#initializeElements()
     */
    @Override
    public void initializeElements() {
        addElement(createMqttAclElement());
    }

    /*
     * @see com.sitewhere.spi.microservice.configuration.model.
     * IConfigurationModelProvider#initializeRoles()
     */
    @Override
    public void initializeRoles() {
        for (MqttAclRoles role : MqttAclRoles.values()) {
            getRolesById().put(role.getRole().getKey().getId(), role.getRole());
        }
    }

    /*
     * @see com.sitewhere.configuration.model.ConfigurationModelProvider#
     * initializeDependencies()
     */
    @Override
    public void initializeDependencies() {
        getDependencies().add(new CommonDatastoreProvider());
    }

    /**
     * Create presence management element.
     *
     * @return
     */
    protected ElementNode createMqttAclElement() {
        ElementNode.Builder builder = new ElementNode.Builder("Mqtt Acl", IMqttAclManagementParser.ROOT,
                "question-circle", MqttAclRoleKeys.MqttAcl, this);

        builder.description("Records device state persistence and presence management.");

        return builder.build();
    }

}