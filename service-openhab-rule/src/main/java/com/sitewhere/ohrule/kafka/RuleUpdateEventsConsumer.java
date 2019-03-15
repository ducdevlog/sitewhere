/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.ohrule.kafka;

import com.sitewhere.microservice.kafka.DirectKafkaConsumer;
import com.sitewhere.microservice.kafka.KafkaTopicNaming;
import com.sitewhere.ohrule.spi.kafka.IRuleUpdateEventsConsumer;
import com.sitewhere.spi.SiteWhereException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

/**
 * Listens on Kafka topic for rule update events, then persist them.
 *
 * @author Duc
 */
public class RuleUpdateEventsConsumer extends DirectKafkaConsumer
		implements IRuleUpdateEventsConsumer {

	/** Consumer id */
	private static String CONSUMER_ID = UUID.randomUUID().toString();

	/** Suffix rule update request type */
	private static String REQUEST_RULE_UPDATE = "update-rule";

	/** Suffix for group id */
	private static String GROUP_ID_SUFFIX = "event-source-extended-events." + REQUEST_RULE_UPDATE;

	/** Number of threads processing rule update */
	private static final int CONCURRENT_UPDATE_PROCESSING_THREADS = 2;

	/** Executor */
	private ExecutorService executor;

	@Override
	public void attemptToProcess(TopicPartition topicPartition, List<ConsumerRecord<String, byte[]>> records) throws SiteWhereException {

	}

	@Override
	public String getConsumerId() throws SiteWhereException {
		return CONSUMER_ID;
	}

	@Override
	public String getConsumerGroupId() throws SiteWhereException {
		return getMicroservice().getKafkaTopicNaming().getTenantPrefix(getTenantEngine().getTenant())
				+ GROUP_ID_SUFFIX;
	}

	@Override
	public List<String> getSourceTopicNames() throws SiteWhereException {
		List<String> topics = new ArrayList<>();
		topics.add(String.format(getMicroservice().getKafkaTopicNaming()
				.getEventSourceExtendedEventsTopic(getTenantEngine().getTenant()), REQUEST_RULE_UPDATE));
		return topics;
	}
}
