package com.nexus.mobility;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class IntersphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntersphereApplication.class, args);
    }
}
