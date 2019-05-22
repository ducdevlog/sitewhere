/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.mqtt.acl.persistence.mongodb;

import com.sitewhere.mongodb.MongoConverter;
import com.sitewhere.mongodb.common.MongoPersistentEntity;
import com.sitewhere.rest.model.mqtt.MqttAcl;
import com.sitewhere.rest.model.mqtt.MqttUser;
import com.sitewhere.spi.mqtt.IMqttAcl;
import com.sitewhere.spi.mqtt.IMqttUser;
import org.bson.Document;

import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Used to load or save assignment state to MongoDB.
 *
 * @author dadams
 */
public class MongoMqttUser implements MongoConverter<IMqttUser> {

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
    public static final String PROP_IS_SUPERUSER = "is_superuser";

    /**
     * Property for subscribe
     */
    public static final String PROP_CREATED = "created";

    /*
     * (non-Javadoc)
     *
     * @see com.sitewhere.mongodb.MongoConverter#convert(java.lang.Object)
     */
    @Override
    public Document convert(IMqttUser source) {
        return MongoMqttUser.toDocument(source);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sitewhere.mongodb.MongoConverter#convert(org.bson.Document)
     */
    @Override
    public IMqttUser convert(Document source) {
        return MongoMqttUser.fromDocument(source);
    }

    /**
     * Copy information from SPI into Mongo {@link Document}.
     *
     * @param source
     * @param target
     */
    public static void toDocument(IMqttUser source, Document target) {
        target.append(PROP_ID, source.getId());
        target.append(PROP_USERNAME, source.getUsername());
        target.append(PROP_PASSWORD, source.getPassword());
        target.append(PROP_IS_SUPERUSER, source.getSuperUser());
        target.append(PROP_CREATED, source.getCreated());
    }

    /**
     * Copy information from Mongo {@link Document} to model object.
     *
     * @param source
     * @param target
     */
    @SuppressWarnings("unchecked")
    public static void fromDocument(Document source, MqttUser target) {
        UUID id = (UUID) source.get(PROP_ID);
        String username = (String) source.get(PROP_USERNAME);
        String password = (String) source.get(PROP_PASSWORD);
        Boolean isSuperUser = (Boolean) source.get(PROP_IS_SUPERUSER);
        Date created = (Date) source.get(PROP_CREATED);

        target.setId(id);
        target.setUsername(username);
        target.setPassword(password);
        target.setSuperUser(isSuperUser);
        target.setCreated(created);

        MongoPersistentEntity.fromDocument(source, target);
    }

    /**
     * Convert SPI object to Mongo {@link Document}.
     *
     * @param source
     * @return
     */
    public static Document toDocument(IMqttUser source) {
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
    public static MqttUser fromDocument(Document source) {
        MqttUser result = new MqttUser();
        MongoMqttUser.fromDocument(source, result);
        return result;
    }
}