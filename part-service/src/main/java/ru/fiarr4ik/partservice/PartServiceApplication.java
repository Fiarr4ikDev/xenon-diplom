package ru.fiarr4ik.partservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
@SpringBootApplication
@EnableDiscoveryClient
public class PartServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PartServiceApplication.class, args);
    }

}
