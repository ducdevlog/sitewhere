/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

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
