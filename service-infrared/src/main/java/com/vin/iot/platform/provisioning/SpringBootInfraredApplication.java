package com.vin.iot.platform.infrared;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringBootInfraredApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootInfraredApplication.class, args);
    }
}