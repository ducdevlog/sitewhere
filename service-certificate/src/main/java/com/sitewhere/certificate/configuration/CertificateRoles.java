/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.certificate.configuration;

import com.sitewhere.configuration.ConfigurationRole;
import com.sitewhere.configuration.model.CommonDatastoreRoleKeys;
import com.sitewhere.spi.microservice.configuration.model.IConfigurationRole;
import com.sitewhere.spi.microservice.configuration.model.IConfigurationRoleProvider;
import com.sitewhere.spi.microservice.configuration.model.IRoleKey;

/**
 * Configuration roles available for presence management microservice.
 *
 * @author Derek
 */
public enum CertificateRoles implements IConfigurationRoleProvider {

    /**
     * Root certificate role.
     */
    Certificate(ConfigurationRole.build(
            CertificateRoleKeys.Certificate, "Certificate", false, false, false, new IRoleKey[]{
                    CommonDatastoreRoleKeys.Datastore},
            new IRoleKey[0], true));

    private ConfigurationRole role;

    private CertificateRoles(ConfigurationRole role) {
        this.role = role;
    }

    /*
     * @see
     * com.sitewhere.spi.microservice.configuration.model.IConfigurationRoleProvider
     * #getRole()
     */
    @Override
    public IConfigurationRole getRole() {
        return role;
    }
}