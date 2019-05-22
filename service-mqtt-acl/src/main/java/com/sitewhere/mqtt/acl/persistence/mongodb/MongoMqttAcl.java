/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.mqtt.acl.persistence.mongodb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sitewhere.mongodb.common.MongoPersistentEntity;
import com.sitewhere.rest.model.mqtt.MqttAcl;
import com.sitewhere.spi.mqtt.IMqttAcl;
import org.bson.Document;

import com.sitewhere.mongodb.MongoConverter;


/**
 * Used to load or save assignment state to MongoDB.
 *
 * @author dadams
 */
public class MongoMqttAcl implements MongoConverter<IMqttAcl> {

    /**
     * Property for id
     */
    public static final String PROP_ID = "_id";

    /**
     * Property for username
     */
    public static final String PROP_USERNAME = "username";

    /**
     * Property for client id
     */
    public static final String PROP_CLIENT_ID = "clientid";

    /**
     * Property for publish
     */
    public static final String PROP_PUBLISH = "publish";

    /**
     * Property for subscribe
     */
    public static final String PROP_SUBSCRIBE = "subscribe";

    /**
     * Property for pubsub
     */
    public static final String PROP_PUB_SUB = "pubsub";

    /*
     * (non-Javadoc)
     *
     * @see com.sitewhere.mongodb.MongoConverter#convert(java.lang.Object)
     */
    @Override
    public Document convert(IMqttAcl source) {
        return MongoMqttAcl.toDocument(source);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sitewhere.mongodb.MongoConverter#convert(org.bson.Document)
     */
    @Override
    public IMqttAcl convert(Document source) {
        return MongoMqttAcl.fromDocument(source);
    }

    /**
     * Copy information from SPI into Mongo {@link Document}.
     *
     * @param source
     * @param target
     */
    public static void toDocument(IMqttAcl source, Document target) {
        target.append(PROP_ID, source.getId());
        target.append(PROP_USERNAME, source.getUsername());
        target.append(PROP_CLIENT_ID, source.getClientId());
        target.append(PROP_PUBLISH, source.getPublish());
        target.append(PROP_SUBSCRIBE, source.getSubscribe());
        target.append(PROP_PUB_SUB, source.getPubSub());
    }

    /**
     * Copy information from Mongo {@link Document} to model object.
     *
     * @param source
     * @param target
     */
    @SuppressWarnings("unchecked")
    public static void fromDocument(Document source, MqttAcl target) {
        UUID id = (UUID) source.get(PROP_ID);
        String username = (String) source.get(PROP_USERNAME);
        String clientId = (String) source.get(PROP_CLIENT_ID);
        Object publish = source.get(PROP_PUBLISH);
        Object subscribe = source.get(PROP_SUBSCRIBE);
        Object pubSub = source.get(PROP_PUB_SUB);

        target.setId(id);
        target.setUsername(username);
        target.setClientId(clientId);
        target.setPubSub((List<String>) pubSub);
        target.setPublish((List<String>) publish);
        target.setSubscribe((List<String>) subscribe);

        MongoPersistentEntity.fromDocument(source, target);
    }

    /**
     * Convert SPI object to Mongo {@link Document}.
     *
     * @param source
     * @return
     */
    public static Document toDocument(IMqttAcl source) {
        Document result = new Document();
        MongoMqttAcl.toDocument(source, result);
        return result;
    }

    /**
     * Convert a {@link Document} into the SPI equivalent.
     *
     * @param source
     * @return
     */
    public static MqttAcl fromDocument(Document source) {
        MqttAcl result = new MqttAcl();
        MongoMqttAcl.fromDocument(source, result);
        return result;
    }
}