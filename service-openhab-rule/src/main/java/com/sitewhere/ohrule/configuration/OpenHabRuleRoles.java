package com.sitewhere.ohrule.configuration;

import com.sitewhere.configuration.ConfigurationRole;
import com.sitewhere.spi.microservice.configuration.model.IConfigurationRole;
import com.sitewhere.spi.microservice.configuration.model.IConfigurationRoleProvider;

public enum OpenHabRuleRoles implements IConfigurationRoleProvider {

	OpenHabRule(ConfigurationRole.build(
			OpenHabRuleRoleKeys.OpenHabRule,
			"openhab-rule", false, false, false
	));

	private ConfigurationRole role;

	OpenHabRuleRoles(ConfigurationRole role) {
		this.role = role;
	}

	@Override
	public IConfigurationRole getRole() {
		return role;
	}
}
