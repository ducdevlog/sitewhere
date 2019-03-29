/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.commands.destination.mqtt;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;

import com.sitewhere.commands.spi.ICommandDeliveryParameterExtractor;
import com.sitewhere.server.lifecycle.TenantEngineLifecycleComponent;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.device.IDeviceAssignment;
import com.sitewhere.spi.device.IDeviceNestingContext;
import com.sitewhere.spi.device.command.IDeviceCommandExecution;
import com.sitewhere.spi.server.lifecycle.LifecycleComponentType;

/**
 * Implements {@link ICommandDeliveryParameterExtractor} for
 * {@link MqttParameters}, allowing expressions to be defined such that the
 * tenant id and device token may be included in the topic name to target a
 * specific device.
 * 
 * @author Derek
 */
@SuppressWarnings("deprecation")
public class DefaultMqttParameterExtractor extends TenantEngineLifecycleComponent
	implements ICommandDeliveryParameterExtractor<MqttParameters> {

    /** Default command topic */
    public static final String DEFAULT_COMMAND_TOPIC = "SiteWhere/${tenant}/${command}/${device}";

    /** Default system topic */
    public static final String DEFAULT_SYSTEM_TOPIC = "SiteWhere/${tenant}/system/${device}";

    /** Command topic prefix */
    private String commandTopicExpr = DEFAULT_COMMAND_TOPIC;

    /** System topic prefix */
    private String systemTopicExpr = DEFAULT_SYSTEM_TOPIC;

    public DefaultMqttParameterExtractor() {
	super(LifecycleComponentType.CommandParameterExtractor);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sitewhere.spi.device.communication.
     * ICommandDeliveryParameterExtractor#
     * extractDeliveryParameters(com.sitewhere.spi.device.IDeviceNestingContext,
     * com.sitewhere.spi.device.IDeviceAssignment,
     * com.sitewhere.spi.device.command.IDeviceCommandExecution)
     */
    @Override
    public MqttParameters extractDeliveryParameters(IDeviceNestingContext nesting, IDeviceAssignment assignment,
	    IDeviceCommandExecution execution) throws SiteWhereException {
	MqttParameters params = new MqttParameters();

	Map<String, String> values = new HashMap<>();
	values.put("tenant", getTenantEngine().getTenant().getToken());
	if (nesting.getGateway().getGatewayId() == null) {
        values.put("device", nesting.getGateway().getToken());
    } else {
        values.put("device", nesting.getGateway().getGatewayId());
    }
	values.put("assignment", assignment.getToken());

	String commandRoute = "command";
	if (StringUtils.isNotEmpty(execution.getCommand().getCommandRoute())) {
		commandRoute = execution.getCommand().getCommandRoute();
	}
	values.put("command", commandRoute);

	String commandTopic = StrSubstitutor.replace(getCommandTopicExpr(), values);
	params.setCommandTopic(commandTopic);

	String systemTopic = StrSubstitutor.replace(getSystemTopicExpr(), values);
	params.setSystemTopic(systemTopic);

	return params;
    }

    public String getCommandTopicExpr() {
	return commandTopicExpr;
    }

    public void setCommandTopicExpr(String commandTopicExpr) {
	this.commandTopicExpr = commandTopicExpr;
    }

    public String getSystemTopicExpr() {
	return systemTopicExpr;
    }

    public void setSystemTopicExpr(String systemTopicExpr) {
	this.systemTopicExpr = systemTopicExpr;
    }
}