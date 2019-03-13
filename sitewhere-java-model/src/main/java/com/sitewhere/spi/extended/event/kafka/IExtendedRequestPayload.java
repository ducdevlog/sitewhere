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
