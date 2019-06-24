/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.commands.encoding.protobuf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.protobuf.ByteString;
import com.sitewhere.commands.spi.ICommandExecutionEncoder;
import com.sitewhere.commands.spi.microservice.ICommandDeliveryMicroservice;
import com.sitewhere.common.MarshalUtils;
import com.sitewhere.communication.protobuf.ProtobufMessageBuilder;
import com.sitewhere.communication.protobuf.proto.SiteWhere.Device.Command;
import com.sitewhere.communication.protobuf.proto.SiteWhere.Device.DeviceStreamAck;
import com.sitewhere.communication.protobuf.proto.SiteWhere.Device.DeviceStreamAckState;
import com.sitewhere.communication.protobuf.proto.SiteWhere.Device.Header;
import com.sitewhere.communication.protobuf.proto.SiteWhere.Device.RegistrationAck;
import com.sitewhere.communication.protobuf.proto.SiteWhere.Device.RegistrationAckError;
import com.sitewhere.communication.protobuf.proto.SiteWhere.Device.RegistrationAckState;
import com.sitewhere.communication.protobuf.proto.SiteWhere.DeviceEvent.DeviceStreamData;
import com.sitewhere.communication.protobuf.proto.SiteWhere.GOptionalFixed64;
import com.sitewhere.communication.protobuf.proto.SiteWhere.GOptionalString;
import com.sitewhere.core.DataUtils;
import com.sitewhere.server.lifecycle.TenantEngineLifecycleComponent;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.device.IDeviceAssignment;
import com.sitewhere.spi.device.IDeviceManagement;
import com.sitewhere.spi.device.IDeviceNestingContext;
import com.sitewhere.spi.device.command.IDeviceCommandExecution;
import com.sitewhere.spi.device.command.IDeviceStreamAckCommand;
import com.sitewhere.spi.device.command.IRegistrationAckCommand;
import com.sitewhere.spi.device.command.IRegistrationFailureCommand;
import com.sitewhere.spi.device.command.ISendDeviceStreamDataCommand;
import com.sitewhere.spi.device.command.ISystemCommand;
import com.sitewhere.spi.error.ErrorCode;
import com.sitewhere.spi.server.lifecycle.LifecycleComponentType;

/**
 * Implementation of {@link ICommandExecutionEncoder} that uses Google Protocol
 * Buffers to encode the execution.
 * 
 * @author Derek
 */
public class ProtobufExecutionEncoder extends TenantEngineLifecycleComponent
	implements ICommandExecutionEncoder<byte[]> {

    public ProtobufExecutionEncoder() {
	super(LifecycleComponentType.CommandExecutionEncoder);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.communication.ICommandExecutionEncoder#encode(
     * com.sitewhere .spi.device.command.IDeviceCommandExecution,
     * com.sitewhere.spi.device.IDeviceNestingContext,
     * com.sitewhere.spi.device.IDeviceAssignment)
     */
    @Override
    public byte[] encode(IDeviceCommandExecution execution, IDeviceNestingContext nested, IDeviceAssignment assignment)
	    throws SiteWhereException {
	byte[] encoded = ProtobufMessageBuilder.createMessage(execution, nested, assignment,
		getTenantEngine().getTenant(), getDeviceManagement());
	getLogger().debug("Protobuf message: 0x" + DataUtils.bytesToHex(encoded));
	return encoded;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.communication.ICommandExecutionEncoder#
     * encodeSystemCommand (com.sitewhere.spi.device.command.ISystemCommand,
     * com.sitewhere.spi.device.IDeviceNestingContext,
     * com.sitewhere.spi.device.IDeviceAssignment)
     */
    @Override
    public byte[] encodeSystemCommand(ISystemCommand command, IDeviceNestingContext nested,
	    IDeviceAssignment assignment) throws SiteWhereException {
	switch (command.getType()) {
	case RegistrationAck: {
	    IRegistrationAckCommand ack = (IRegistrationAckCommand) command;
	    RegistrationAck.Builder builder = RegistrationAck.newBuilder();
	    switch (ack.getReason()) {
	    case AlreadyRegistered: {
		builder.setState(RegistrationAckState.ALREADY_REGISTERED);
		break;
	    }
	    case NewRegistration: {
		builder.setState(RegistrationAckState.NEW_REGISTRATION);
		break;
	    }
	    }
	    return encodeRegistrationAck(builder.build());
	}
	case RegistrationFailure: {
	    IRegistrationFailureCommand fail = (IRegistrationFailureCommand) command;
	    RegistrationAck.Builder builder = RegistrationAck.newBuilder();
	    builder.setState(RegistrationAckState.REGISTRATION_ERROR);
	    builder.setErrorMessage(GOptionalString.newBuilder().setValue(fail.getErrorMessage()));
	    switch (fail.getReason()) {
	    case NewDevicesNotAllowed: {
		builder.setErrorType(RegistrationAckError.NEW_DEVICES_NOT_ALLOWED);
		break;
	    }
	    case InvalidDeviceTypeToken: {
		builder.setErrorType(RegistrationAckError.INVALID_SPECIFICATION);
		break;
	    }
	    case SiteTokenRequired: {
		builder.setErrorType(RegistrationAckError.SITE_TOKEN_REQUIRED);
		break;
	    }
	    }
	    return encodeRegistrationAck(builder.build());
	}
	case DeviceStreamAck: {
	    IDeviceStreamAckCommand ack = (IDeviceStreamAckCommand) command;
	    DeviceStreamAck.Builder builder = DeviceStreamAck.newBuilder();
	    switch (ack.getStatus()) {
	    case DeviceStreamCreated: {
		builder.setState(DeviceStreamAckState.STREAM_CREATED);
		break;
	    }
	    case DeviceStreamExists: {
		builder.setState(DeviceStreamAckState.STREAM_EXISTS);
		break;
	    }
	    case DeviceStreamFailed: {
		builder.setState(DeviceStreamAckState.STREAM_FAILED);
		break;
	    }
	    }
	    return encodeDeviceStreamAck(builder.build());
	}
	case SendDeviceStreamData: {
	    ISendDeviceStreamDataCommand send = (ISendDeviceStreamDataCommand) command;
	    DeviceStreamData.Builder builder = DeviceStreamData.newBuilder();
	    builder.setDeviceToken(GOptionalString.newBuilder().setValue(send.getDeviceToken()));
	    builder.setSequenceNumber(GOptionalFixed64.newBuilder().setValue(send.getSequenceNumber()));
	    builder.setData(ByteString.copyFrom(send.getData()));
	    return encodeSendDeviceStreamData(builder.build());
	}
	case DeviceMappingAck: {
	    String json = MarshalUtils.marshalJsonAsPrettyString(command);
	    getLogger().warn("No protocol buffer encoding implemented for sending device mapping acknowledgement.");
	    getLogger().info("JSON representation of command is:\n" + json + "\n");
	    return new byte[0];
	}
	}
	throw new SiteWhereException(ErrorCode.Error, "Unable to encode command: " + command.getClass().getName());
    }

    /**
     * Encode {@link RegistrationAck} as a byte array.
     * 
     * @param ack
     * @return
     * @throws SiteWhereException
     */
    protected byte[] encodeRegistrationAck(RegistrationAck ack) throws SiteWhereException {
	try {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    // Header header =
	    // Header.newBuilder().setCommand(Command.ACK_REGISTRATION).build();
	    // header.writeDelimitedTo(out);

	    ((RegistrationAck) ack).writeDelimitedTo(out);
	    out.close();
	    return out.toByteArray();
	} catch (IOException e) {
	    throw new SiteWhereException(ErrorCode.Error, "Unable to marshal registration ack to protobuf.", e);
	}
    }

    /**
     * Encode {@link DeviceStreamAck} as a byte array.
     * 
     * @param ack
     * @return
     * @throws SiteWhereException
     */
    protected byte[] encodeDeviceStreamAck(DeviceStreamAck ack) throws SiteWhereException {
	try {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    // Header header =
	    // Header.newBuilder().setCommand(Command.ACK_DEVICE_STREAM).build();
	    // header.writeDelimitedTo(out);
	    ack.writeDelimitedTo(out);
	    out.close();
	    return out.toByteArray();
	} catch (IOException e) {
	    throw new SiteWhereException(ErrorCode.Error, "Unable to marshal device stream ack to protobuf.", e);
	}
    }

    /**
     * Encode {@link DeviceStreamData} as byte array.
     * 
     * @param data
     * @return
     * @throws SiteWhereException
     */
    protected byte[] encodeSendDeviceStreamData(DeviceStreamData data) throws SiteWhereException {
	try {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    Header header = Header.newBuilder().setCommand(Command.RECEIVE_DEVICE_STREAM_DATA).build();
	    header.writeDelimitedTo(out);
	    data.writeDelimitedTo(out);
	    out.close();
	    return out.toByteArray();
	} catch (IOException e) {
	    throw new SiteWhereException(ErrorCode.Error, "Unable to marshal device stream data chunk to protobuf.", e);
	}
    }

    private IDeviceManagement getDeviceManagement() {
	return ((ICommandDeliveryMicroservice) getMicroservice()).getDeviceManagementApiDemux().getApiChannel();
    }
}