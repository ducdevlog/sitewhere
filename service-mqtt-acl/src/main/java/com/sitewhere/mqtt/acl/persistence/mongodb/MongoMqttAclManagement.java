/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.mqtt.acl.persistence.mongodb;

import com.mongodb.client.MongoCollection;
import com.sitewhere.mongodb.MongoPersistence;
import com.sitewhere.mqtt.acl.persistence.MqttAclPersistence;
import com.sitewhere.rest.model.mqtt.MqttAcl;
import com.sitewhere.spi.SiteWhereSystemException;
import com.sitewhere.spi.error.ErrorCode;
import com.sitewhere.spi.error.ErrorLevel;
import com.sitewhere.spi.mqtt.IMqttAcl;
import com.sitewhere.spi.mqtt.IMqttUser;
import com.sitewhere.spi.mqtt.event.IMqttAclManagement;
import com.sitewhere.spi.mqtt.request.IMqttAclCreateRequest;
import com.sitewhere.spi.mqtt.request.IMqttUserCreateRequest;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.IndexOptions;
import com.sitewhere.mongodb.IMongoConverterLookup;
import com.sitewhere.server.lifecycle.TenantEngineLifecycleComponent;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.server.lifecycle.ILifecycleProgressMonitor;
import com.sitewhere.spi.server.lifecycle.LifecycleComponentType;

/**
 * Device state management implementation that uses MongoDB for persistence.
 *
 * @author Derek
 */
public class MongoMqttAclManagement extends TenantEngineLifecycleComponent implements IMqttAclManagement {

    /**
     * Converter lookup
     */
    private static IMongoConverterLookup LOOKUP = new MongoConverters();

    /**
     * MongoDB client
     */
    private IMqttAclManagementMongoClient mongoClient;

    public MongoMqttAclManagement() {
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
        getMongoClient().getMqttAclCollection().createIndex(
                new BasicDBObject(MongoMqttAcl.PROP_USERNAME, 1), new IndexOptions().unique(true));
        getMongoClient().getMqttUserCollection()
                .createIndex(new Document(MongoMqttUser.PROP_USERNAME, 1));
    }

    public IMqttAclManagementMongoClient getMongoClient() {
        return mongoClient;
    }

    public void setMongoClient(IMqttAclManagementMongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }


    @Override
    public IMqttAcl createMqttAcl(IMqttAclCreateRequest request) throws SiteWhereException {
        Document existing = assertMqttAcl(request.getUsername());
        if (existing != null) {
            return MongoMqttAcl.fromDocument(existing);
        }
        MqttAcl state = MqttAclPersistence.mqttAclCreateLogic(request);

        MongoCollection<Document> states = getMongoClient().getMqttAclCollection();
        Document created = MongoMqttAcl.toDocument(state);
        MongoPersistence.insert(states, created, ErrorCode.DuplicateId);

        return MongoMqttAcl.fromDocument(created);
    }

    @Override
    public IMqttAcl deleteMqttAcl(String username) throws SiteWhereException {
        Document existing = assertMqttAcl(username);
        MongoCollection<Document> states = getMongoClient().getMqttAclCollection();
        MongoPersistence.delete(states, existing);
        return MongoMqttAcl.fromDocument(existing);
    }

    @Override
    public IMqttUser createMqttUser(IMqttUserCreateRequest request) throws SiteWhereException {
        Document existing = assertMqttUser(request.getUsername());
        if (existing != null) {
            return MongoMqttUser.fromDocument(existing);
        }

        IMqttUser state = MqttAclPersistence.mqttUserCreateLogic(request);

        MongoCollection<Document> states = getMongoClient().getMqttUserCollection();
        Document created = MongoMqttUser.toDocument(state);
        MongoPersistence.insert(states, created, ErrorCode.DuplicateId);

        return MongoMqttUser.fromDocument(created);
    }

    @Override
    public IMqttUser deleteMqttUser(String username) throws SiteWhereException {
        Document existing = assertMqttUser(username);
        MongoCollection<Document> states = getMongoClient().getMqttUserCollection();
        MongoPersistence.delete(states, existing);
        return MongoMqttUser.fromDocument(existing);
    }

    protected Document assertMqttAcl(String username) throws SiteWhereException {
        Document match = getMqttAclDocumentByUsername(username);
        if (match == null) {
            throw new SiteWhereSystemException(ErrorCode.MqttAclNotFound, ErrorLevel.ERROR);
        }
        return match;
    }

    private Document getMqttAclDocumentByUsername(String username) throws SiteWhereException {
        MongoCollection<Document> states = getMongoClient().getMqttAclCollection();
        Document query = new Document(MongoMqttAcl.PROP_USERNAME, username);
        return states.find(query).first();
    }

    protected Document assertMqttUser(String username) throws SiteWhereException {
        Document match = getMqttUserDocumentByUsername(username);
        if (match == null) {
            throw new SiteWhereSystemException(ErrorCode.MqttUserNotFound, ErrorLevel.ERROR);
        }
        return match;
    }

    private Document getMqttUserDocumentByUsername(String username) throws SiteWhereException {
        MongoCollection<Document> states = getMongoClient().getMqttUserCollection();
        Document query = new Document(MongoMqttUser.PROP_USERNAME, username);
        return states.find(query).first();
    }
}