package com.sitewhere.spi.device.command;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

public interface IMinimalDeviceCommandExecution extends Serializable {
	Map<String, Object> getRequest();

	String getDeviceToken();

	LocalDateTime getEventDate();
}
