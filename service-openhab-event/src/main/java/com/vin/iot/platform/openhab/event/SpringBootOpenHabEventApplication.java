package com.vin.iot.platform.openhab.event;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringBootOpenHabEventApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringBootOpenHabEventApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}