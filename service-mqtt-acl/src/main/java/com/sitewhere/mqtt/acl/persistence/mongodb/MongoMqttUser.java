/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.mqtt.acl.persistence.mongodb;

import com.sitewhere.mongodb.MongoConverter;
import com.sitewhere.rest.model.mqtt.MqttAcl;
import com.sitewhere.spi.mqtt.IMqttAcl;
import org.bson.Document;


/**
 * Used to load or save assignment state to MongoDB.
 *
 * @author dadams
 */
public class MongoMqttUser implements MongoConverter<IMqttAcl> {

    /**
     * Property for id
     */
    public static final String PROP_ID = "_id";

    /**
     * Property for username
     */
    public static final String PROP_USERNAME = "username";

    /**
     * Property for password
     */
    public static final String PROP_PASSWORD = "password";

    /**
     * Property for publish
     */
    public static final String PROP_PUBLISH = "is_superuser";

    /**
     * Property for subscribe
     */
    public static final String PROP_SUBSCRIBE = "created";

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
        return MongoMqttUser.toDocument(source);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sitewhere.mongodb.MongoConverter#convert(org.bson.Document)
     */
    @Override
    public IMqttAcl convert(Document source) {
        return MongoMqttUser.fromDocument(source);
    }

    /**
     * Copy information from SPI into Mongo {@link Document}.
     *
     * @param source
     * @param target
     */
    public static void toDocument(IMqttAcl source, Document target) {
        //todo implement
    }

    /**
     * Copy information from Mongo {@link Document} to model object.
     *
     * @param source
     * @param target
     */
    @SuppressWarnings("unchecked")
    public static void fromDocument(Document source, MqttAcl target) {
        //todo implement
    }

    /**
     * Convert SPI object to Mongo {@link Document}.
     *
     * @param source
     * @return
     */
    public static Document toDocument(IMqttAcl source) {
        Document result = new Document();
        MongoMqttUser.toDocument(source, result);
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
        MongoMqttUser.fromDocument(source, result);
        return result;
    }
}