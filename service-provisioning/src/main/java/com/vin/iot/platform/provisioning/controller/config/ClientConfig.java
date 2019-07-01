package com.vin.iot.platform.provisioning.controller.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ClientConfig {

    @Value("${sitewhere.client.user}")
    private String swUser;

    @Value("${sitewhere.client.password}")
    private String swPassword;

}