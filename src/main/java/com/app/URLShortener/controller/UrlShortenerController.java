package com.app.URLShortener.controller;

import com.app.URLShortener.dto.UrlShortenRequest;
import com.app.URLShortener.dto.UrlShortenResponse;
import com.app.URLShortener.service.UrlShortenerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bit.ly")
public class UrlShortenerController {

    @Autowired
    UrlShortenerService urlShortenerService;

    @PostMapping(path = "/shorten/url")
    ResponseEntity<?> shortenURL(@Valid @RequestBody UrlShortenRequest request) {
        UrlShortenResponse urlShortenResponse = urlShortenerService.shortenURL(request);
        return ResponseEntity.ok(urlShortenResponse);
    }

    @GetMapping("/{shortUrl}")
    ResponseEntity<?> fetchURL(@RequestParam @NotNull String shortUrl){
         UrlShortenResponse response = urlShortenerService.getURL(shortUrl);
         if(response==null){
             return ResponseEntity.status(400).build();
         }else{
             return ResponseEntity.ok(response);
         }
    }
}
