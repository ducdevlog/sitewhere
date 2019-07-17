/*
 *
 *  Developed by Anhgv by VinGroup on 7/17/19, 10:11 AM
 *  Last modified 7/17/19, 9:59 AM.
 *  Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 *
 *
 */
package com.sitewhere.sources.mqtt;

import com.codahale.metrics.Meter;
import com.sitewhere.communication.mqtt.MqttLifecycleComponent;
import com.sitewhere.sources.messages.EventSourcesMessages;
import com.sitewhere.sources.spi.IInboundEventReceiver;
import com.sitewhere.sources.spi.IInboundEventSource;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.error.ErrorCode;
import com.sitewhere.spi.server.lifecycle.ILifecycleProgressMonitor;
import com.sitewhere.spi.server.lifecycle.LifecycleComponentType;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.fusesource.mqtt.client.QoS;

import java.io.EOFException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implementation of {@link IInboundEventReceiver} that subscribes to an MQTT
 * topic and pulls the message contents into SiteWhere for processing.
 * 
 * @author Derek
 */
public class MqttPahoInboundEventReceiver extends MqttLifecycleComponent implements IInboundEventReceiver<byte[]> {

    /** Default subscribed topic name */
    public static final String DEFAULT_TOPIC = "SiteWhere/input/protobuf";

    /** Number of threads used for processing events */
    public static final int DEFAULT_NUM_THREADS = 5;

    /** MQTT Topic Quality of Service */
    public static final QoS DEFAULT_QoS = QoS.AT_LEAST_ONCE;

    /** Parent event source */
    private IInboundEventSource<byte[]> eventSource;

    /** Topic name */
    private String topic = DEFAULT_TOPIC;

    /** Number of threads used for processing */
    private int numThreads = DEFAULT_NUM_THREADS;

    /** Used to execute MQTT subscribe in separate thread */
    private ExecutorService subscriptionExecutor;

    /** Used to process MQTT events in a thread pool */
    private ExecutorService processorsExecutor;

    /** Meter for counting received events */
    private Meter receivedEvents;

    public MqttPahoInboundEventReceiver() {
	super(LifecycleComponentType.InboundEventReceiver);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sitewhere.device.communication.mqtt.MqttLifecycleComponent#start(com.
     * sitewhere.spi.server.lifecycle.ILifecycleProgressMonitor)
     */
    @Override
    public void start(ILifecycleProgressMonitor monitor) throws SiteWhereException {
		super.start(monitor);
		getLogger().info("Receiver connected to MQTT broker at '" + getHostname() + ":" + getPort() + "'...");

		// Subscribe to chosen topic.
		try {
			getMqttClient().setCallback(new MqttCallback() {

				/*
				 * @see
				 * org.eclipse.paho.client.mqttv3.MqttCallback#messageArrived(
				 * java.lang.String, org.eclipse.paho.client.mqttv3.MqttMessage)
				 */
				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					onEventPayloadReceived(message.getPayload(), null);
				}

				/*
				 * @see
				 * org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(
				 * org.eclipse.paho.client.mqttv3.IMqttDeliveryToken)
				 */
				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
				}

				/*
				 * @see
				 * org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(
				 * java.lang.Throwable)
				 */
				@Override
				public void connectionLost(Throwable cause) {
					getLogger().info("Detected lost connection.", cause);
				}
			});
			getMqttClient().subscribe(getTopic(), 1);
			getLogger().info("Subscribed to events on MQTT topic: " + getTopic() + " with QOS " + getQos());
		} catch (MqttException e) {
			throw new SiteWhereException(ErrorCode.Error, "Exception while attempting to subscribe to MQTT topic: " + getTopic(), e);
		}
    }

    /**
     * Transform configuration to MQTT QoS
     * 
     * @param qos
     * @return
     */
    private static QoS qosFromConfig(String qos) {
	if ("0".equals(qos) || "AT_MOST_ONCE".equals(qos))
	    return QoS.AT_MOST_ONCE;
	if ("1".equals(qos) || "AT_LEAST_ONCE".equals(qos))
	    return QoS.AT_LEAST_ONCE;
	if ("2".equals(qos) || "EXACTLY_ONCE".equals(qos))
	    return QoS.EXACTLY_ONCE;
	return DEFAULT_QoS;
    }

    /**
     * Get prefix appended to metrics.
     * 
     * @return
     */
    protected String getMetricPrefix() {
	return getEventSource().getSourceId() + ".MqttEventReceiver.";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.communication.IInboundEventReceiver#
     * getDisplayName()
     */
    @Override
    public String getDisplayName() {
	return getProtocol() + "://" + getHostname() + ":" + getPort() + "/" + getTopic();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.communication.IInboundEventReceiver#
     * onEventPayloadReceived (java.lang.Object, java.util.Map)
     */
    @Override
    public void onEventPayloadReceived(byte[] payload, Map<String, Object> metadata) {
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.device.communication.mqtt.MqttLifecycleComponent#stop(com.
     * sitewhere.spi.server.lifecycle.ILifecycleProgressMonitor)
     */
    @Override
    public void stop(ILifecycleProgressMonitor monitor) throws SiteWhereException {
		if ((getMqttClient() != null) && (getMqttClient().isConnected())) {
			try {
				getMqttClient().disconnect();
			} catch (MqttException e) {
				getLogger().error("Error shutting down MQTT device event receiver.", e);
			}
		}
		super.stop(monitor);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.communication.IInboundEventReceiver#
     * getEventSource()
     */
    public IInboundEventSource<byte[]> getEventSource() {
	return eventSource;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.communication.IInboundEventReceiver#
     * setEventSource(com .sitewhere.spi.device.communication.IInboundEventSource)
     */
    public void setEventSource(IInboundEventSource<byte[]> eventSource) {
	this.eventSource = eventSource;
    }

    public String getTopic() {
	return topic;
    }

    public void setTopic(String topic) {
	this.topic = topic;
    }

    public int getNumThreads() {
	return numThreads;
    }

    public void setNumThreads(int numThreads) {
	this.numThreads = numThreads;
    }

    protected Meter getReceivedEvents() {
	return receivedEvents;
    }

    protected void setReceivedEvents(Meter receivedEvents) {
	this.receivedEvents = receivedEvents;
    }

    /** Used for naming consumer threads */
    private class SubscribersThreadFactory implements ThreadFactory {

	/** Counts threads */
	private AtomicInteger counter = new AtomicInteger();

	public Thread newThread(Runnable r) {
	    return new Thread(r, "SiteWhere MQTT(" + getEventSource().getSourceId() + " - " + getTopic() + ") Receiver "
		    + counter.incrementAndGet());
	}
    }

    /** Used for naming processor threads */
    private class ProcessorsThreadFactory implements ThreadFactory {

	/** Counts threads */
	private AtomicInteger counter = new AtomicInteger();

	public Thread newThread(Runnable r) {
	    return new Thread(r, "SiteWhere MQTT(" + getEventSource().getSourceId() + " - " + getTopic()
		    + ") Processor " + counter.incrementAndGet());
	}
    }
}