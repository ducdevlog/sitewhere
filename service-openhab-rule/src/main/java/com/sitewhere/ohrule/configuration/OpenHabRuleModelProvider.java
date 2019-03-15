/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.ohrule.configuration;

import com.sitewhere.configuration.model.ConfigurationModelProvider;
import com.sitewhere.configuration.parser.IOpenHabRuleParser;
import com.sitewhere.rest.model.configuration.ElementNode;
import com.sitewhere.spi.microservice.configuration.model.IConfigurationRoleProvider;

public class OpenHabRuleModelProvider extends ConfigurationModelProvider {

	@Override
	public String getDefaultXmlNamespace() {
		return "http://sitewhere.io/schema/sitewhere/microservice/device-registration";
	}

	@Override
	public IConfigurationRoleProvider getRootRole() {
		return OpenHabRuleRoles.OpenHabRule;
	}

	@Override
	public void initializeElements() {
		addElement(createOpenHabRuleElement());
	}

	@Override
	public void initializeRoles() {
		for (OpenHabRuleRoles role : OpenHabRuleRoles.values()) {
			getRolesById().put(role.getRole().getKey().getId(), role.getRole());
		}
	}

	protected ElementNode createOpenHabRuleElement() {
		ElementNode.Builder builder = new ElementNode.Builder("OpenHAB rule", IOpenHabRuleParser.ROOT,
				"user-plus", OpenHabRuleRoleKeys.OpenHabRule, this);

		builder.description("Manage the rule like openhab");

		return builder.build();
	}
}
