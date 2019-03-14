package com.sitewhere.ohrule.configuration;

import com.sitewhere.spi.microservice.configuration.model.IRoleKey;

public enum OpenHabRuleRoleKeys implements IRoleKey {

	OpenHabRule("openhab_rule");

	private String id;

	OpenHabRuleRoleKeys(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}
}
