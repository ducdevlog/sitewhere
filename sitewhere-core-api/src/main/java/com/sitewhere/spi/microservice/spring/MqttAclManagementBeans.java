/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.spi.microservice.spring;

public class MqttAclManagementBeans {
    /** Bean id for device mangement MongoDB client */
    public static final String BEAN_MONGODB_CLIENT = "mongoClient";

    /** Bean id for device state management in server configuration */
    public static final String BEAN_MQTT_ACL_MANAGEMENT = "mqttAclManagement";
}
