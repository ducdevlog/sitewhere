package com.sitewhere.ohrule.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class OpenHabRuleNamespaceHandler extends NamespaceHandlerSupport {
	@Override
	public void init() {
		registerBeanDefinitionParser("openhab-rule", null);
	}
}
