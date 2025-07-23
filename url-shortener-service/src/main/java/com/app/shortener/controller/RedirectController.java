package com.app.shortener.controller;

import com.app.shortener.config.URLNotFoundException;
import com.app.shortener.dto.UrlShortenResponse;
import com.app.shortener.service.UrlShortenerService;
import javax.validation.constraints.NotNull;
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

    @GetMapping("/{shortUrl}")
    ResponseEntity<?> fetchURL(@PathVariable @NotNull String shortUrl) {

        UrlShortenResponse response = urlShortenerService.getURL(shortUrl);

        if (response == null || response.getOutputUrl() == null) {
            throw new URLNotFoundException();
        }

        ResponseEntity<?> responseEntity = ResponseEntity.status(302).header(
                "Location", response.getOutputUrl()
        ).build();

        return responseEntity;
    }
}
