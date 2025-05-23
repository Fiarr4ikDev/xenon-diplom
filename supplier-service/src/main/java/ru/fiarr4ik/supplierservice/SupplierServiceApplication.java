package ru.fiarr4ik.supplierservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SupplierServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(SupplierServiceApplication.class, args);

    }

}
