package com.dyz.recordservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
public class RecordserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecordserviceApplication.class, args);
    }
}