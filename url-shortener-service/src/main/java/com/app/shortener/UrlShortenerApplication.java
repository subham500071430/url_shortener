package com.app.shortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class UrlShortenerApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(UrlShortenerApplication.class, args);
    }
}

