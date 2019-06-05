/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.certificate.persistence.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import com.sitewhere.certificate.persistence.CertificatePersistence;
import com.sitewhere.mongodb.IMongoConverterLookup;
import com.sitewhere.mongodb.MongoPersistence;
import com.sitewhere.rest.model.certificate.Certificate;
import com.sitewhere.server.lifecycle.TenantEngineLifecycleComponent;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.certificate.ICertificate;
import com.sitewhere.spi.certificate.ICertificateManagement;
import com.sitewhere.spi.certificate.IX509RevokedCertificate;
import com.sitewhere.spi.certificate.request.ICertificateCreateRequest;
import com.sitewhere.spi.error.ErrorCode;
import com.sitewhere.spi.server.lifecycle.ILifecycleProgressMonitor;
import com.sitewhere.spi.server.lifecycle.LifecycleComponentType;
import org.bson.Document;

/**
 * Device state management implementation that uses MongoDB for persistence.
 *
 * @author Derek
 */
public class MongoCertificateManagement extends TenantEngineLifecycleComponent implements ICertificateManagement {

    /**
     * Converter lookup
     */
    private static IMongoConverterLookup LOOKUP = new MongoConverters();

    /**
     * MongoDB client
     */
    private ICertificateManagementMongoClient mongoClient;

    public MongoCertificateManagement() {
        super(LifecycleComponentType.DataStore);
    }

    /*
     * @see
     * com.sitewhere.server.lifecycle.LifecycleComponent#start(com.sitewhere.spi.
     * server.lifecycle.ILifecycleProgressMonitor)
     */
    @Override
    public void start(ILifecycleProgressMonitor monitor) throws SiteWhereException {
        // Ensure that collection indexes exist.
        ensureIndexes();
    }

    /**
     * Ensure that expected collection indexes exist.
     *
     * @throws SiteWhereException
     */
    protected void ensureIndexes() throws SiteWhereException {
        getMongoClient().getCertificatesCollection().createIndex(
                new BasicDBObject(MongoCertificate.PROP_SERIAL_NUMBER, 1), new IndexOptions().unique(true));
    }

    public ICertificateManagementMongoClient getMongoClient() {
        return mongoClient;
    }

    public void setMongoClient(ICertificateManagementMongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public ICertificate createCertificateRoot(ICertificateCreateRequest request) throws SiteWhereException {
        CertificatePersistence certificatePersistence = new CertificatePersistence();
        Certificate certificate = certificatePersistence.certificateCreateLogic(request);
        MongoCollection<Document> certificatesCollection = getMongoClient().getCertificatesCollection();
        Document created = MongoCertificate.toDocument(certificate);
        MongoPersistence.insert(certificatesCollection, created, ErrorCode.DuplicateId);
        return MongoCertificate.fromDocument(created);
    }

    @Override
    public ICertificate createCertificate(ICertificateCreateRequest request) throws SiteWhereException {
        CertificatePersistence certificatePersistence = new CertificatePersistence();
        Certificate certificate = certificatePersistence.certificateCreateLogic(request);
        MongoCollection<Document> certificatesCollection = getMongoClient().getCertificatesCollection();
        Document created = MongoCertificate.toDocument(certificate);
        MongoPersistence.insert(certificatesCollection, created, ErrorCode.DuplicateId);
        return MongoCertificate.fromDocument(created);
    }

    @Override
    public void revokeCertificate(IX509RevokedCertificate ix509RevokedCertificate) throws SiteWhereException {
    }

    @Override
    public ICertificate verifyCertificate(String serialNumber) throws SiteWhereException {
        return null;
    }

    @Override
    public ICertificate getCertificate(String serialNumber) throws SiteWhereException {
        return null;
    }
}