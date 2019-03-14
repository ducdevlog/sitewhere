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
