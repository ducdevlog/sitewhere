/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.mqtt.acl.persistence.mongodb;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.sitewhere.configuration.instance.mongodb.MongoConfiguration;
import com.sitewhere.mongodb.MongoDbClient;
import com.sitewhere.spi.SiteWhereException;

/**
 * Mongo client for interacting with device state object model.
 *
 * @author Derek
 */
public class MqttAclManagementMongoClient extends MongoDbClient implements IMqttAclManagementMongoClient {

    /**
     * Injected name used for device states collection
     */
    private String mqttAclCollectionName = DEFAULT_MQTT_ACL_COLLECTION_NAME;
    private String mqttUserCollectionName = DEFAULT_MQTT_USER_COLLECTION_NAME;

    public MqttAclManagementMongoClient(MongoConfiguration configuration) {
        super(configuration);
    }

    /*
     * @see com.sitewhere.devicestate.persistence.mongodb.
     * IMqttAclManagementMongoClient#getMqttAclCollection()
     */
    @Override
    public MongoCollection<Document> getMqttAclCollection() throws SiteWhereException {
        return getDatabase().getCollection(getMqttAclCollectionName());
    }

    @Override
    public MongoCollection<Document> getMqttUserCollection() throws SiteWhereException {
        return getDatabase().getCollection(getMqttUserCollectionName());
    }

    public String getMqttAclCollectionName() {
        return mqttAclCollectionName;
    }

    public void setMqttAclCollectionName(String mqttAclCollectionName) {
        this.mqttAclCollectionName = mqttAclCollectionName;
    }

    public String getMqttUserCollectionName() {
        return mqttUserCollectionName;
    }

    public void setMqttUserCollectionName(String mqttUserCollectionName) {
        this.mqttUserCollectionName = mqttUserCollectionName;
    }
}