/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.commands.encoding.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sitewhere.commands.encoding.EncodedCommandExecution;
import com.sitewhere.commands.spi.ICommandExecutionEncoder;
import com.sitewhere.commands.spi.microservice.ICommandDeliveryMicroservice;
import com.sitewhere.common.MarshalUtils;
import com.sitewhere.rest.model.device.command.DeviceCommandExecution;
import com.sitewhere.rest.model.device.command.MinimalDeviceCommandExecution;
import com.sitewhere.rest.model.infrared.IrCodeRaw;
import com.sitewhere.server.lifecycle.TenantEngineLifecycleComponent;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.device.*;
import com.sitewhere.spi.device.command.IDeviceCommandExecution;
import com.sitewhere.spi.device.command.ISystemCommand;
import com.sitewhere.spi.infrared.IInfraredManagement;
import com.sitewhere.spi.infrared.IIrCodeRaw;
import com.sitewhere.spi.server.lifecycle.LifecycleComponentType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.regex.Pattern.compile;

/**
 * Implementation of {@link ICommandExecutionEncoder} that sends commands in
 * JSON format.
 * 
 * @author Derek
 */
public class JsonCommandExecutionEncoder extends TenantEngineLifecycleComponent
	implements ICommandExecutionEncoder<byte[]> {

	public static final String DEVICE_TYPE_IR = "ir";
	public static final String IR_VALUE_CONTENT = "value";

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
		// Check device type IR
		IDeviceType deviceType = getDeviceManagement().getDeviceType(command.getCommand().getDeviceTypeId());
		if (DEVICE_TYPE_IR.equalsIgnoreCase(deviceType.getToken()) && StringUtils.isEmpty(nested.getGateway().getGatewayId())) {
			String value = command.getInvocation().getParameterValues().get(IR_VALUE_CONTENT);
			Map<String, String> mapValue = Pattern.compile("\\s*;\\s*")
					.splitAsStream(value)
					.map(s -> s.split("\\s*,\\s*"))
					.collect(Collectors.toMap(a -> a[0], a -> a.length > 1 ? a[1] : ""));
			IrCodeRaw irCodeRawTemp = new IrCodeRaw();
			if (mapValue.size() > 0) {
				if (mapValue.containsKey("ID")) irCodeRawTemp.setId(mapValue.get("ID"));
				if (mapValue.containsKey("CODESET_NAME")) irCodeRawTemp.setId(mapValue.get("CODESET_NAME"));
				if (mapValue.containsKey("FUNCTION_NAME")) irCodeRawTemp.setId(mapValue.get("FUNCTION_NAME"));
				if (mapValue.containsKey("POWER")) irCodeRawTemp.setId(mapValue.get("POWER"));
				if (mapValue.containsKey("MODE")) irCodeRawTemp.setId(mapValue.get("MODE"));
				if (mapValue.containsKey("FAN")) irCodeRawTemp.setId(mapValue.get("FAN"));
				if (mapValue.containsKey("TEMP")) irCodeRawTemp.setId(mapValue.get("TEMP"));
				if (mapValue.containsKey("TIMER")) irCodeRawTemp.setId(mapValue.get("TIMER"));
				if (mapValue.containsKey("TIMER_DELAY")) irCodeRawTemp.setId(mapValue.get("TIMER_DELAY"));
				if (mapValue.containsKey("LED")) irCodeRawTemp.setId(mapValue.get("LED"));
				if (mapValue.containsKey("COMFORT")) irCodeRawTemp.setId(mapValue.get("COMFORT"));
				if (mapValue.containsKey("ECONO")) irCodeRawTemp.setId(mapValue.get("ECONO"));
				if (mapValue.containsKey("POWERFUL")) irCodeRawTemp.setId(mapValue.get("POWERFUL"));
			}
			List<IIrCodeRaw> irCodeRaws = getInfraredManagement().getIrCodeRaw(irCodeRawTemp);
			if (CollectionUtils.isNotEmpty(irCodeRaws)) {
				command.getInvocation().getParameterValues().put(IR_VALUE_CONTENT, irCodeRaws.get(0).getIrFreqKhz() + ", " + irCodeRaws.get(0).getIrCode());
			}
		}

		if (command.getCommand().getReversedMessageType() != ReversedMessageType.MINIMAL || StringUtils.isNotEmpty(nested.getGateway().getGatewayId())) {
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

	private IDeviceManagement getDeviceManagement() {
		return ((ICommandDeliveryMicroservice) getMicroservice()).getDeviceManagementApiDemux().getApiChannel();
	}

	private IInfraredManagement getInfraredManagement() {
		return ((ICommandDeliveryMicroservice) getMicroservice()).getInfraredApiDemux().getApiChannel();
	}
}