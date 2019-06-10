/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.certificate.spring;

import com.sitewhere.certificate.persistence.mongodb.CertificateManagementMongoClient;
import com.sitewhere.certificate.persistence.mongodb.MongoCertificateManagement;
import com.sitewhere.configuration.datastore.DatastoreConfigurationChoice;
import com.sitewhere.configuration.datastore.DatastoreConfigurationParser;
import com.sitewhere.configuration.parser.ICertificateManagementParser.Elements;
import com.sitewhere.spi.microservice.spring.CertificateManagementBeans;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.List;

/**
 * Parses elements related to device presence management.
 *
 * @author Derek
 */
public class CertificateParser extends AbstractBeanDefinitionParser {

    /**
     * Static logger instance
     */
    @SuppressWarnings("unused")
    private static Log LOGGER = LogFactory.getLog(CertificateParser.class);

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
                case CertificateDatastore: {
                    parseCertificateDatastore(child, context);
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
    protected void parseCertificateDatastore(Element element, ParserContext context) {
        DatastoreConfigurationChoice config = DatastoreConfigurationParser.parseCertificateDatastoreChoice(element,
                context);
        switch (config.getType()) {
            case MongoDB: {
                BeanDefinitionBuilder client = BeanDefinitionBuilder
                        .rootBeanDefinition(CertificateManagementMongoClient.class);
                client.addConstructorArgValue(config.getConfiguration());
                context.getRegistry().registerBeanDefinition(CertificateManagementBeans.BEAN_MONGODB_CLIENT,
                        client.getBeanDefinition());
                break;
            }
            case MongoDBReference: {
                BeanDefinitionBuilder client = BeanDefinitionBuilder
                        .rootBeanDefinition(CertificateManagementMongoClient.class);
                client.addConstructorArgReference((String) config.getConfiguration());
                context.getRegistry().registerBeanDefinition(CertificateManagementBeans.BEAN_MONGODB_CLIENT,
                        client.getBeanDefinition());
                break;
            }
            default: {
                throw new RuntimeException("Invalid datastore configured: " + config.getType());
            }
        }

        // Build device management implementation.
        BeanDefinitionBuilder management = BeanDefinitionBuilder.rootBeanDefinition(MongoCertificateManagement.class);
        management.addPropertyReference("mongoClient", CertificateManagementBeans.BEAN_MONGODB_CLIENT);

        context.getRegistry().registerBeanDefinition(CertificateManagementBeans.BEAN_CERTIFICATE_MANAGEMENT,
                management.getBeanDefinition());
    }
}