/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.spi.extended.event.request;

import com.sitewhere.spi.common.IMetadataProvider;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

public interface IExtendedRequest extends IMetadataProvider, Serializable {
	String getRequestType();
	Object getData();
	Date getUpdateDate();
}
