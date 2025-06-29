package com.app.URLShortener.controller;

import com.app.URLShortener.dto.UrlShortenResponse;
import com.app.URLShortener.service.UrlShortenerService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RedirectController {

    @Autowired
    UrlShortenerService urlShortenerService;

    @GetMapping("/{shortUrl}")
    ResponseEntity<?> fetchURL(@PathVariable @NotNull String shortUrl){
        UrlShortenResponse response = urlShortenerService.getURL(shortUrl);
        if(response==null){
            return ResponseEntity.status(400).build();
        }else{
            return ResponseEntity.status(302).header(
                    "Location", response.getOutputUrl()
            ).build();
        }
    }
}
