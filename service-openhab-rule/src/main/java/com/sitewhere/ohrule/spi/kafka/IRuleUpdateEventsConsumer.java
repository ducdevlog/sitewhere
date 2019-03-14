package com.sitewhere.ohrule.spi.kafka;

import com.sitewhere.spi.microservice.kafka.IMicroserviceKafkaConsumer;

/**
 * Kafka consumer that receives events for openhab rule update.
 *
 * @author duc
 */
public interface IRuleUpdateEventsConsumer extends IMicroserviceKafkaConsumer {
}
