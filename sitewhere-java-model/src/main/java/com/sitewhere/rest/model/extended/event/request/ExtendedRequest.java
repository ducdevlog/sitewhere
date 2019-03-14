/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.rest.model.extended.event.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sitewhere.rest.model.common.MetadataProvider;
import com.sitewhere.spi.extended.event.request.IExtendedRequest;

import java.time.Instant;
import java.util.Date;


@JsonInclude(Include.NON_NULL)
public class ExtendedRequest extends MetadataProvider
		implements IExtendedRequest {

	private String requestType;
	private Object data;
	private Date updateDate;

	@Override
	public String getRequestType() {
		return requestType;
	}

	@Override
	public Object getData() {
		return data;
	}

	@Override
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
