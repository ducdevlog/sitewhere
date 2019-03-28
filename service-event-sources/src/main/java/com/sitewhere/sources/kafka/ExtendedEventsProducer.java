/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.sources.kafka;

import com.sitewhere.microservice.kafka.AckPolicy;
import com.sitewhere.microservice.kafka.MicroserviceKafkaProducer;
import com.sitewhere.spi.SiteWhereException;

public class ExtendedEventsProducer extends MicroserviceKafkaProducer {

	private String requestType = "default";

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public ExtendedEventsProducer() {
		super(AckPolicy.FireAndForget);
	}

	@Override
	public String getTargetTopicName() throws SiteWhereException {
		String templateTopic =
				getMicroservice().getKafkaTopicNaming()
						.getEventSourceExtendedEventsTopic(getTenantEngine().getTenant());
		return String.format(templateTopic, getRequestType());
	}
}
