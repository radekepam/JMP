package com.example.two;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwoApplication.class, args);
    }

}
