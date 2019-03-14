package com.sitewhere.rest.model.extended.event.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sitewhere.rest.model.common.MetadataProvider;
import com.sitewhere.spi.extended.event.request.IExtendedRequest;


@JsonInclude(Include.NON_NULL)
public class ExtendedRequest extends MetadataProvider
		implements IExtendedRequest {

	private String requestType;
	private Object data;

	@Override
	public String getRequestType() {
		return requestType;
	}

	@Override
	public Object getData() {
		return data;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public void setData(String data) {
		this.data = data;
	}
}
