package com.dyz.recordservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.dyz.filxeservice.client", "com.dyz.commentservice.client"})
@EnableTransactionManagement
public class RecordserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecordserviceApplication.class, args);
    }
}