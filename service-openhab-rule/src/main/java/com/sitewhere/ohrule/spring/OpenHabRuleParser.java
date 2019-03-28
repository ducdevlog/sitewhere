/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.ohrule.spring;

import com.sitewhere.configuration.parser.IOpenHabRuleParser;
import com.sitewhere.ohrule.OpenHabRuleManager;
import com.sitewhere.spi.microservice.spring.OpenHabRuleBeans;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import java.util.List;

public class OpenHabRuleParser extends AbstractBeanDefinitionParser {
	@Override
	protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
		List<Element> children = DomUtils.getChildElements(element);
		for (Element child : children) {
			IOpenHabRuleParser.Elements type = IOpenHabRuleParser.Elements.getByLocalName(child.getLocalName());
			if (type == null) {
				throw new RuntimeException("Unknown device registration element: " + child.getLocalName());
			}
			switch (type) {
				case DefaultOpenHabRuleManager: {
					parseDefaultOpenHabRuleManager(child, parserContext);
					break;
				}
			}
		}
		return null;
	}

	/**
	 * Parse information for the default registration manager.
	 *
	 * @param element
	 * @param context
	 */
	protected void parseDefaultOpenHabRuleManager(Element element, ParserContext context) {
		BeanDefinitionBuilder manager = BeanDefinitionBuilder.rootBeanDefinition(OpenHabRuleManager.class);

		Attr mongoHost = element.getAttributeNode("mongoHost");
		if (mongoHost != null) {
			manager.addPropertyValue("mongoHost", mongoHost.getValue());
		}

		Attr mongoPort = element.getAttributeNode("mongoPort");
		if (mongoPort != null) {
			manager.addPropertyValue("mongoPort", mongoPort.getValue());
		}

		Attr database = element.getAttributeNode("database");
		if (database != null) {
			manager.addPropertyValue("database", database.getValue());
		}

		context.getRegistry().registerBeanDefinition(OpenHabRuleBeans.BEAN_OPENHAB_RULE_MANAGER,
				manager.getBeanDefinition());
	}
}
