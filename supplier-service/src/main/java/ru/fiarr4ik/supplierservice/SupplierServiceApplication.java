package ru.fiarr4ik.supplierservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
@SpringBootApplication
@EnableDiscoveryClient
public class SupplierServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(SupplierServiceApplication.class, args);

    }

}
