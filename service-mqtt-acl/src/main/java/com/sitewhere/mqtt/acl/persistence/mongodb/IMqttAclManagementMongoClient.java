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
import com.sitewhere.spi.SiteWhereException;

/**
 * Mongo client that provides device state management collections.
 *
 * @author Derek
 */
public interface IMqttAclManagementMongoClient {

    /**
     * Default collection name for device state
     */
    public static final String DEFAULT_MQTT_ACL_COLLECTION_NAME = "mqtt_acl";
    public static final String DEFAULT_MQTT_USER_COLLECTION_NAME = "mqtt_user";

    /**
     * Collection for device states.
     *
     * @return
     * @throws SiteWhereException
     */
    public MongoCollection<Document> getMqttAclCollection() throws SiteWhereException;
    public MongoCollection<Document> getMqttUserCollection() throws SiteWhereException;
}
