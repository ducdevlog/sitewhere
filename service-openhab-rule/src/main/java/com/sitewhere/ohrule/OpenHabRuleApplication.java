package com.sitewhere.ohrule;

import com.sitewhere.microservice.MicroserviceApplication;
import com.sitewhere.ohrule.spi.microservice.IOpenHabRuleMicroservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class OpenHabRuleApplication extends MicroserviceApplication<IOpenHabRuleMicroservice> {

	@Autowired
	private IOpenHabRuleMicroservice microservice;

	@Override
	public IOpenHabRuleMicroservice getMicroservice() {
		return microservice;
	}

	public static void main(String[] args) {
		SpringApplication.run(OpenHabRuleApplication.class, args);
	}
}
