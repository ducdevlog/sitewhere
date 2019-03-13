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
