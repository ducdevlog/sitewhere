/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.grpc.client.extended;

import com.google.protobuf.InvalidProtocolBufferException;
import com.sitewhere.grpc.client.event.EventModelConverter;
import com.sitewhere.grpc.model.DeviceEventModel.GEnrichedEventPayload;
import com.sitewhere.grpc.model.DeviceEventModel.GInboundEventPayload;
import com.sitewhere.grpc.model.DeviceEventModel.GPersistedEventPayload;
import com.sitewhere.grpc.model.ExtendedModel.GInboundExtendedPayload;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.extended.event.kafka.IExtendedRequestPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Methods that support marshaling/unmarshaling event model payloads.
 *
 * @author Derek
 */
public class ExtendedModelMarshaler {

    /**
     * Static logger instance
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ExtendedModelMarshaler.class);

    /**
     * Build binary message for API inbound event payload.
     *
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static byte[] buildInboundExtendedPayloadMessage(IExtendedRequestPayload api) throws SiteWhereException {
        GInboundExtendedPayload grpc = ExtendedModelConverter.asGrpcInboundEventPayload(api);
        return buildInboundExtendedPayloadMessage(grpc);
    }

    /**
     * Build binary message for GRPC inbound event payload.
     *
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static byte[] buildInboundExtendedPayloadMessage(GInboundExtendedPayload grpc) throws SiteWhereException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            grpc.writeTo(output);
            return output.toByteArray();
        } catch (IOException e) {
            throw new SiteWhereException("Unable to build inbound extended payload message.", e);
        } finally {
            closeQuietly(output);
        }
    }

    /**
     * Parse message that contains an inbound event payload.
     *
     * @param payload
     * @return
     * @throws SiteWhereException
     */
    public static GInboundExtendedPayload parseInboundExtendedPayloadMessage(byte[] payload) throws SiteWhereException {
        try {
            return GInboundExtendedPayload.parseFrom(payload);
        } catch (InvalidProtocolBufferException e) {
            throw new SiteWhereException("Unable to parse inbound event payload message.", e);
        }
    }

    /**
     * Build binary message for GRPC persisted event payload.
     *
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static byte[] buildPersistedExtendedPayloadMessage(GPersistedEventPayload grpc) throws SiteWhereException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            grpc.writeTo(output);
            return output.toByteArray();
        } catch (IOException e) {
            throw new SiteWhereException("Unable to build persisted extended payload message.", e);
        } finally {
            closeQuietly(output);
        }
    }

    /**
     * Parse message that contains a persisted event payload.
     *
     * @param payload
     * @return
     * @throws SiteWhereException
     */
    public static GPersistedEventPayload parsePersistedExtendedPayloadMessage(byte[] payload) throws SiteWhereException {
        try {
            return GPersistedEventPayload.parseFrom(payload);
        } catch (InvalidProtocolBufferException e) {
            throw new SiteWhereException("Unable to parse inbound extended payload message.", e);
        }
    }


    protected static void closeQuietly(OutputStream output) {
        if (output != null) {
            try {
                output.close();
            } catch (IOException e) {
                LOGGER.error("Unable to close output stream.", e);
            }
        }
    }
}