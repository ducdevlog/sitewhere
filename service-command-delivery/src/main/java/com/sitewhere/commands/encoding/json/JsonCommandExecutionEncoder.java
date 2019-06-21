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
import com.sitewhere.rest.model.device.event.DeviceCommandInvocation;
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
import org.apache.zookeeper.Op;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
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
			ObjectMapper objectMapper = new ObjectMapper();
			IrCodeRaw irCodeRawTemp = new IrCodeRaw();
			IRDeviceCommandValue deviceCommandValue;
			try {
				deviceCommandValue = objectMapper.readValue(value, IRDeviceCommandValue.class);
				if (deviceCommandValue.getValues() != null && deviceCommandValue.getValues().size() > 0) {
					if (deviceCommandValue.getValues().containsKey("ID")) irCodeRawTemp.setId(deviceCommandValue.getValues().get("ID"));
					if (deviceCommandValue.getValues().containsKey("CODESET_NAME")) irCodeRawTemp.setCodesetName(deviceCommandValue.getValues().get("CODESET_NAME"));
					if (deviceCommandValue.getValues().containsKey("FUNCTION_NAME")) irCodeRawTemp.setFunctionName(deviceCommandValue.getValues().get("FUNCTION_NAME"));
					if (deviceCommandValue.getValues().containsKey("POWER")) irCodeRawTemp.setPower(deviceCommandValue.getValues().get("POWER"));
					if (deviceCommandValue.getValues().containsKey("MODE")) irCodeRawTemp.setMode(deviceCommandValue.getValues().get("MODE"));
					if (deviceCommandValue.getValues().containsKey("FAN")) irCodeRawTemp.setFan(deviceCommandValue.getValues().get("FAN"));
					if (deviceCommandValue.getValues().containsKey("TEMP")) irCodeRawTemp.setTemp(deviceCommandValue.getValues().get("TEMP"));
					if (deviceCommandValue.getValues().containsKey("TIMER")) irCodeRawTemp.setTimer(deviceCommandValue.getValues().get("TIMER"));
					if (deviceCommandValue.getValues().containsKey("TIMER_DELAY")) irCodeRawTemp.setTimerDelay(deviceCommandValue.getValues().get("TIMER_DELAY"));
					if (deviceCommandValue.getValues().containsKey("LED")) irCodeRawTemp.setLed(deviceCommandValue.getValues().get("LED"));
					if (deviceCommandValue.getValues().containsKey("COMFORT")) irCodeRawTemp.setComfort(deviceCommandValue.getValues().get("COMFORT"));
					if (deviceCommandValue.getValues().containsKey("ECONO")) irCodeRawTemp.setEcono(deviceCommandValue.getValues().get("ECONO"));
					if (deviceCommandValue.getValues().containsKey("POWERFUL")) irCodeRawTemp.setPowerful(deviceCommandValue.getValues().get("POWERFUL"));
				}
				List<IIrCodeRaw> irCodeRaws = getInfraredManagement().getIrCodeRaw(irCodeRawTemp);
				if (CollectionUtils.isNotEmpty(irCodeRaws)) {
					DeviceCommandInvocation deviceCommandInvocation = (DeviceCommandInvocation) command.getInvocation();
					HashMap<String, String> parameterValues = (HashMap<String, String>) deviceCommandInvocation.getParameterValues();
					parameterValues.put(IR_VALUE_CONTENT, irCodeRaws.get(0).getIrFreqKhz() + ", " + irCodeRaws.get(0).getIrCode());
					deviceCommandInvocation.setParameterValues(parameterValues);
					//command.getInvocation().getParameterValues().put(IR_VALUE_CONTENT, irCodeRaws.get(0).getIrFreqKhz() + ", " + irCodeRaws.get(0).getIrCode());
				}
			} catch (IOException e) {
				getLogger().error("Parser message IR");
				e.printStackTrace();
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