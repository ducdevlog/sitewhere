/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

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
