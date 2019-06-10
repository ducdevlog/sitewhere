/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.certificate.configuration;

import com.sitewhere.configuration.model.CommonDatastoreProvider;
import com.sitewhere.configuration.model.ConfigurationModelProvider;
import com.sitewhere.configuration.parser.ICertificateManagementParser;
import com.sitewhere.rest.model.configuration.ElementNode;
import com.sitewhere.spi.microservice.configuration.model.IConfigurationRoleProvider;

/**
 * Configuration model provider for presence management microservice.
 *
 * @author Derek
 */
public class CertificateModelProvider extends ConfigurationModelProvider {

    /*
     * @see com.sitewhere.spi.microservice.configuration.model.
     * IConfigurationModelProvider#getDefaultXmlNamespace()
     */
    @Override
    public String getDefaultXmlNamespace() {
        return "http://sitewhere.io/schema/sitewhere/microservice/certificate";
    }

    /*
     * @see com.sitewhere.spi.microservice.configuration.model.
     * IConfigurationModelProvider#getRootRole()
     */
    @Override
    public IConfigurationRoleProvider getRootRole() {
        return CertificateRoles.Certificate;
    }

    /*
     * @see com.sitewhere.spi.microservice.configuration.model.
     * IConfigurationModelProvider#initializeElements()
     */
    @Override
    public void initializeElements() {
        addElement(createCertificateElement());
    }

    /*
     * @see com.sitewhere.spi.microservice.configuration.model.
     * IConfigurationModelProvider#initializeRoles()
     */
    @Override
    public void initializeRoles() {
        for (CertificateRoles role : CertificateRoles.values()) {
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
    protected ElementNode createCertificateElement() {
        ElementNode.Builder builder = new ElementNode.Builder("Certificate", ICertificateManagementParser.ROOT,
                "question-circle", CertificateRoleKeys.Certificate, this);

        builder.description("Records certificate persistence.");

        return builder.build();
    }
}