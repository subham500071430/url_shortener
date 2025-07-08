package com.app.shortener.service;

import com.app.shortener.dto.UrlShortenRequest;
import com.app.shortener.dto.UrlShortenResponse;
import org.springframework.stereotype.Service;

@Service
public interface UrlShortenerService {

       public UrlShortenResponse shortenURL(UrlShortenRequest request);

       public UrlShortenResponse getURL(String shortUrl);
}
