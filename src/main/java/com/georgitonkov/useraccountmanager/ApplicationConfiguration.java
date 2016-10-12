package com.georgitonkov.useraccountmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.georgitonkov.useraccountmanager.controller.AccountServiceController;

@SpringBootApplication
public class ApplicationConfiguration {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ApplicationConfiguration.class, args);
    }
}
