package com.sitewhere.ohrule.microservice;

import com.sitewhere.microservice.instance.InstanceSettings;
import com.sitewhere.spi.microservice.instance.IInstanceSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenHabRuleMicroserviceConfiguration {

	@Bean
	public IInstanceSettings instanceSettings() {
		return new InstanceSettings();
	}
}
