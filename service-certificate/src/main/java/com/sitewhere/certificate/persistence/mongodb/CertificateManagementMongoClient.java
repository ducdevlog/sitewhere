/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.certificate.persistence.mongodb;

import com.mongodb.client.MongoCollection;
import com.sitewhere.configuration.instance.mongodb.MongoConfiguration;
import com.sitewhere.mongodb.MongoDbClient;
import com.sitewhere.spi.SiteWhereException;
import org.bson.Document;

/**
 * Mongo client for interacting with device state object model.
 *
 * @author Derek
 */
public class CertificateManagementMongoClient extends MongoDbClient implements ICertificateManagementMongoClient {

    /**
     * Injected name used for certificate collection
     */
    private String certificatesCollectionName = CertificateManagementMongoClient.DEFAULT_CERTIFICATES_COLLECTION_NAME;

    public CertificateManagementMongoClient(MongoConfiguration configuration) {
        super(configuration);
    }

    public String getCertificatesCollectionName() {
        return certificatesCollectionName;
    }

    public void setCertificatesCollectionName(String certificatesCollectionName) {
        this.certificatesCollectionName = certificatesCollectionName;
    }

    @Override
    public MongoCollection<Document> getCertificatesCollection() throws SiteWhereException {
        return getDatabase().getCollection(getCertificatesCollectionName());
    }
}