package com.app.URLShortener.controller;

import com.app.URLShortener.dto.UrlShortenResponse;
import com.app.URLShortener.service.UrlShortenerService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.CompletableFuture;

@Controller
public class RedirectController {

    @Autowired
    UrlShortenerService urlShortenerService;

    @Async
    @GetMapping("/{shortUrl}")
    CompletableFuture<ResponseEntity<?>> fetchURL(@PathVariable @NotNull String shortUrl) {

        UrlShortenResponse response = urlShortenerService.getURL(shortUrl);
        ResponseEntity<?> responseEntity;
        if (response == null) {
            responseEntity =  ResponseEntity.status(400).build();
        } else {
            responseEntity = ResponseEntity.status(302).header(
                    "Location", response.getOutputUrl()
            ).build();
        }

        return CompletableFuture.completedFuture(responseEntity);
    }
}
