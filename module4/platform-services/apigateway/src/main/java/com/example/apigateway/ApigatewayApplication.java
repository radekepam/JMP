package com.example.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class ApigatewayApplication {

    public static void main(String[] args) {
        System.out.println("Aplikacja uruchomila sie");
        SpringApplication.run(ApigatewayApplication.class, args);
    }

}
