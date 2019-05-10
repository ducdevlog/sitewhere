/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.commands.encoding.json;

import com.sitewhere.commands.encoding.EncodedCommandExecution;
import com.sitewhere.commands.spi.ICommandExecutionEncoder;
import com.sitewhere.common.MarshalUtils;
import com.sitewhere.rest.model.device.command.DeviceCommandExecution;
import com.sitewhere.rest.model.device.command.MinimalDeviceCommandExecution;
import com.sitewhere.server.lifecycle.TenantEngineLifecycleComponent;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.device.IDeviceAssignment;
import com.sitewhere.spi.device.IDeviceNestingContext;
import com.sitewhere.spi.device.ReversedMessageType;
import com.sitewhere.spi.device.command.IDeviceCommandExecution;
import com.sitewhere.spi.device.command.ISystemCommand;
import com.sitewhere.spi.server.lifecycle.LifecycleComponentType;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

/**
 * Implementation of {@link ICommandExecutionEncoder} that sends commands in
 * JSON format.
 * 
 * @author Derek
 */
public class JsonCommandExecutionEncoder extends TenantEngineLifecycleComponent
	implements ICommandExecutionEncoder<byte[]> {

    public JsonCommandExecutionEncoder() {
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
    public byte[] encode(IDeviceCommandExecution command, IDeviceNestingContext nested, IDeviceAssignment assignment)
	    throws SiteWhereException {
		EncodedCommandExecution encoded = null;
		if (command.getCommand().getReversedMessageType() != ReversedMessageType.MINIMAL && StringUtils.isNotEmpty(nested.getGateway().getGatewayId())) {
			encoded = new EncodedCommandExecution(command, nested, assignment);
			return MarshalUtils.marshalJson(encoded);
		}
		MinimalDeviceCommandExecution minimalCommand = MinimalDeviceCommandExecution.of(command);
		getLogger().debug("Custom command being encoded:\n\n" + MarshalUtils.marshalJsonAsPrettyString(encoded));
		return MarshalUtils.marshalJson(minimalCommand);
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
	EncodedCommandExecution encoded = new EncodedCommandExecution(command, nested, assignment);
	getLogger().debug("System command being encoded:\n\n" + MarshalUtils.marshalJsonAsPrettyString(encoded));
	return MarshalUtils.marshalJson(encoded);
    }
}