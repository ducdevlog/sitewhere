/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.spi.extended.event.kafka;


import com.sitewhere.spi.extended.event.request.IExtendedRequest;

import java.io.Serializable;

public interface IExtendedRequestPayload extends Serializable {
	/**
	 * Get unique event source id.
	 *
	 * @return
	 */
	public String getSourceId();

	/**
	 * Get device token.
	 *
	 * @return
	 */
	public String getDeviceToken();

	/**
	 * Get id of originating event.
	 *
	 * @return
	 */
	public String getOriginator();

	/**
	 * Get extended request.
	 *
	 * @return
	 */
	public IExtendedRequest getExtendedRequest();
}
