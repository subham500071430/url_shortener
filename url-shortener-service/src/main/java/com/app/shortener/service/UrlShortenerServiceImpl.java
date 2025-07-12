package com.app.shortener.service;

import com.app.shortener.dto.UrlShortenRequest;
import com.app.shortener.dto.UrlShortenResponse;
import com.app.shortener.entity.UrlMapper;
import com.app.shortener.repository.UrlRepository;
import com.app.shortener.util.RedisLookup;
import com.app.shortener.util.RedisReverseLookup;
import com.app.shortener.util.URLGenerator;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private URLGenerator generator;
    @Autowired
    private RedisLookup redisLookup;
    @Autowired
    private RedisReverseLookup redisReverseLookup;
    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private RestTemplate restTemplate;


    @Override
    @Transactional
    public UrlShortenResponse shortenURL(UrlShortenRequest request) {

        String generatedShortUrl = null;
        String longUrl = request.getLongUrl();
        String prefixUrl = "http://localhost:8080/";

        // check cache
        if (redisReverseLookup.isLongUrlUsed(longUrl))
            return new UrlShortenResponse(redisReverseLookup.getShortCode(longUrl));

        // check db
        Optional<UrlMapper> urlMapper = urlRepository.findByLongUrl(longUrl);

        if (urlMapper.isPresent())
            return new UrlShortenResponse(urlMapper.get().getShortUrl());

        // generate short url
        do {
            generatedShortUrl = prefixUrl + generator.generate(longUrl);
        } while (urlRepository.findById(generatedShortUrl).isPresent());

        urlRepository.save(new UrlMapper(generatedShortUrl, longUrl));
        redisLookup.cacheShortUrl(generatedShortUrl, longUrl);
        redisLookup.markCodeAsUsed(generatedShortUrl);
        redisReverseLookup.cacheLongUrl(longUrl, generatedShortUrl);
        redisReverseLookup.markLongUrlAsUsed(longUrl);

        log.info("short url :" + generatedShortUrl);

        return new UrlShortenResponse(generatedShortUrl);
    }


    @Override
    public UrlShortenResponse getURL(String shortUrl) {

        String res = restTemplate.getForObject("http://localhost:8081/user/getAllUsers",String.class);

        String fullShortUrl = "http://localhost:8080/" + shortUrl;
        String longUrl = null;

        if (Objects.nonNull(redisLookup) && Objects.nonNull(redisLookup.getLongUrl(fullShortUrl))) {
            longUrl = redisLookup.getLongUrl(fullShortUrl);
        } else if (Objects.nonNull(urlRepository) && urlRepository.findById(fullShortUrl).isPresent()) {
            longUrl = urlRepository.findById(fullShortUrl).get().getLongUrl();
        }

        return new UrlShortenResponse(longUrl);

    }
}
