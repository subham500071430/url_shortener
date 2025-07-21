package com.app.shortener.controller;

import com.app.shortener.dto.UrlShortenRequest;
import com.app.shortener.dto.UrlShortenResponse;
import com.app.shortener.service.UrlShortenerService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("bit.ly")
public class UrlShortenerController {

    @Autowired
    UrlShortenerService urlShortenerService;

    @Async
    @PostMapping(path = "/shorten/url")
    CompletableFuture<ResponseEntity<?>> shortenURL(@Valid @RequestBody UrlShortenRequest request) {
        UrlShortenResponse urlShortenResponse = urlShortenerService.shortenURL(request);
        return CompletableFuture.completedFuture(ResponseEntity.ok(urlShortenResponse));
    }
}
