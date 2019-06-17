package com.vin.iot.platform.infrared;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringBootInfraredApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringBootInfraredApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}