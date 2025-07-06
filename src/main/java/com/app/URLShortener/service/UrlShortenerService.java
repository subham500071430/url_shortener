package com.app.URLShortener.service;

import com.app.URLShortener.dto.UrlShortenRequest;
import com.app.URLShortener.dto.UrlShortenResponse;
import org.springframework.stereotype.Service;

import java.net.http.HttpHeaders;
import java.util.concurrent.CompletableFuture;

@Service
public interface UrlShortenerService {

       public UrlShortenResponse shortenURL(UrlShortenRequest request);

       public UrlShortenResponse getURL(String shortUrl);
}
