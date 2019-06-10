/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.certificate.persistence.mongodb;

import com.mongodb.client.MongoCollection;
import com.sitewhere.spi.SiteWhereException;
import org.bson.Document;

/**
 * Mongo client that provides device state management collections.
 *
 * @author Derek
 */
public interface ICertificateManagementMongoClient {

    /**
     * Default collection name for certificate
     */
    public static final String DEFAULT_CERTIFICATES_COLLECTION_NAME = "certificates";

    /**
     * Collection for certificates.
     *
     * @return
     * @throws SiteWhereException
     */
    public MongoCollection<Document> getCertificatesCollection() throws SiteWhereException;
}
