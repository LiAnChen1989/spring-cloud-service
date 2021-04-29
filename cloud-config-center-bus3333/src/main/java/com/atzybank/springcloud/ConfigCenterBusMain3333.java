package com.atzybank.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigCenterBusMain3333 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigCenterBusMain3333.class, args);
    }
}
