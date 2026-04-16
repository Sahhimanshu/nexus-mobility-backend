package com.nexus.mobility;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class NexusMobilityApplication {

    public static void main(String[] args) {
        SpringApplication.run(NexusMobilityApplication.class, args);
    }
}
