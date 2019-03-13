package com.sitewhere.rest.model.extended.event.kafka;

import com.sitewhere.spi.extended.event.kafka.IExtendedRequestPayload;
import com.sitewhere.spi.extended.event.request.IExtendedRequest;

public class ExtendedRequestPayload implements IExtendedRequestPayload {

	/** Event source id */
	private String sourceId;

	/** Device token */
	private String deviceToken;

	/** Id of event originator */
	private String originator;

	/** Event create request */
	private IExtendedRequest extendedRequest;

	@Override
	public String getSourceId() {
		return sourceId;
	}

	@Override
	public String getDeviceToken() {
		return deviceToken;
	}

	@Override
	public String getOriginator() {
		return originator;
	}

	@Override
	public IExtendedRequest getExtendedRequest() {
		return extendedRequest;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public void setOriginator(String originator) {
		this.originator = originator;
	}

	public void setExtendedRequest(IExtendedRequest extendedRequest) {
		this.extendedRequest = extendedRequest;
	}
}
