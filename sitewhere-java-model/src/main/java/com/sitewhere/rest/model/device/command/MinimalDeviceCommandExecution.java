/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.rest.model.device.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sitewhere.spi.device.command.IDeviceCommandExecution;
import com.sitewhere.spi.device.command.IMinimalDeviceCommandExecution;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MinimalDeviceCommandExecution implements IMinimalDeviceCommandExecution {

	private static final String PROP_REQUEST_NAME = "name";

	private Map<String, Object> request;

	private String deviceToken;

	private LocalDateTime eventDate;

	@Override
	public Map<String, Object> getRequest() {
		return request;
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	@Override
	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	@Override
	public LocalDateTime getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDateTime eventDate) {
		this.eventDate = eventDate;
	}

	public static MinimalDeviceCommandExecution of(IDeviceCommandExecution command) {
		MinimalDeviceCommandExecution minimalCommand = new MinimalDeviceCommandExecution();
		minimalCommand.request = new HashMap<>();
		minimalCommand.request.put(PROP_REQUEST_NAME, command.getCommand().getName());
		minimalCommand.request.putAll(command.getParameters());
		return minimalCommand;
	}
}
