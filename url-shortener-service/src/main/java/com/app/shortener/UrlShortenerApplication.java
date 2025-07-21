package com.app.shortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableEurekaClient
public class UrlShortenerApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(UrlShortenerApplication.class, args);
    }
}

