/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.mqtt.acl.spring;

import java.util.List;

import com.sitewhere.mqtt.acl.persistence.mongodb.MqttAclManagementMongoClient;
import com.sitewhere.mqtt.acl.persistence.mongodb.MongoMqttAclManagement;
import com.sitewhere.spi.microservice.spring.MqttAclManagementBeans;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import com.sitewhere.configuration.datastore.DatastoreConfigurationChoice;
import com.sitewhere.configuration.datastore.DatastoreConfigurationParser;
import com.sitewhere.configuration.parser.IMqttAclManagementParser.Elements;

/**
 * Parses elements related to device presence management.
 *
 * @author Derek
 */
public class MqttAclParser extends AbstractBeanDefinitionParser {

    /**
     * Static logger instance
     */
    @SuppressWarnings("unused")
    private static Log LOGGER = LogFactory.getLog(MqttAclParser.class);

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.beans.factory.xml.AbstractBeanDefinitionParser#
     * parseInternal (org.w3c.dom.Element,
     * org.springframework.beans.factory.xml.ParserContext)
     */
    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext context) {
        List<Element> dsChildren = DomUtils.getChildElements(element);
        for (Element child : dsChildren) {
            Elements type = Elements.getByLocalName(child.getLocalName());
            if (type == null) {
                throw new RuntimeException("Unknown device management element: " + child.getLocalName());
            }
            switch (type) {
                case MqttAclDatastore: {
                    parseMqttAclDatastore(child, context);
                    break;
                }
            }
        }
        return null;
    }

    /**
     * Parse device state datastore element.
     *
     * @param element
     * @param context
     */
    protected void parseMqttAclDatastore(Element element, ParserContext context) {
        DatastoreConfigurationChoice config = DatastoreConfigurationParser.parseMqttAclDatastoreChoice(element,
                context);
        switch (config.getType()) {
            case MongoDB: {
                BeanDefinitionBuilder client = BeanDefinitionBuilder
                        .rootBeanDefinition(MqttAclManagementMongoClient.class);
                client.addConstructorArgValue(config.getConfiguration());
                context.getRegistry().registerBeanDefinition(MqttAclManagementBeans.BEAN_MONGODB_CLIENT,
                        client.getBeanDefinition());
                break;
            }
            case MongoDBReference: {
                BeanDefinitionBuilder client = BeanDefinitionBuilder
                        .rootBeanDefinition(MqttAclManagementMongoClient.class);
                client.addConstructorArgReference((String) config.getConfiguration());
                context.getRegistry().registerBeanDefinition(MqttAclManagementBeans.BEAN_MONGODB_CLIENT,
                        client.getBeanDefinition());
                break;
            }
            default: {
                throw new RuntimeException("Invalid datastore configured: " + config.getType());
            }
        }

        // Build device management implementation.
        BeanDefinitionBuilder management = BeanDefinitionBuilder.rootBeanDefinition(MongoMqttAclManagement.class);
        management.addPropertyReference("mongoClient", MqttAclManagementBeans.BEAN_MONGODB_CLIENT);

        context.getRegistry().registerBeanDefinition(MqttAclManagementBeans.BEAN_MQTT_ACL_MANAGEMENT,
                management.getBeanDefinition());
    }

}