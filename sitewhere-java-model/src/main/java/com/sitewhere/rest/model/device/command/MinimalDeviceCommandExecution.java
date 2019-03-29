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
		if (command.getCommand().getCreatedDate() != null) {
			minimalCommand.eventDate =
					Instant.ofEpochMilli(command.getCommand().getCreatedDate().getTime())
							.atZone(ZoneId.systemDefault())
							.toLocalDateTime();
		}
		return minimalCommand;
	}
}
