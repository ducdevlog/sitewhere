/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.spi.mqtt.request;

import java.io.Serializable;

public interface IMqttUserCreateRequest extends Serializable {
    public String getUsername();
    public String getPassword();
    public Boolean getSuperUser();
}
