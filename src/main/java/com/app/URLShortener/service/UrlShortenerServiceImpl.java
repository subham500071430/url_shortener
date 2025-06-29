package com.app.URLShortener.service;

import com.app.URLShortener.dto.UrlShortenRequest;
import com.app.URLShortener.dto.UrlShortenResponse;
import com.app.URLShortener.util.RedisLookup;
import com.app.URLShortener.util.RedisReverseLookup;
import com.app.URLShortener.util.URLGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    @Override
    public UrlShortenResponse shortenURL(UrlShortenRequest request) {

        String shortCode = null;
        String inputUrl = request.getInputUrl();
        String prefixUrl = "http://localhost:8080/";

        if (redisReverseLookup.isLongUrlUsed(inputUrl)) {
            return new UrlShortenResponse(redisReverseLookup.getShortCode(inputUrl));
        }

        do {
            shortCode = prefixUrl+generator.generate(inputUrl);
        } while (redisLookup.isCodeUsed(shortCode));


        redisLookup.cacheShortUrl(shortCode, inputUrl);
        redisLookup.markCodeAsUsed(shortCode);
        redisReverseLookup.cacheLongUrl(inputUrl, shortCode);
        redisReverseLookup.markLongUrlAsUsed(inputUrl);


        return new UrlShortenResponse(shortCode);

    }

    @Override
    public UrlShortenResponse getURL(String shortUrl) {

           String fullShortUrl = "http://localhost:8080/" + shortUrl;
           String longUrl = redisLookup.getLongUrl(fullShortUrl);

           if(longUrl == null || longUrl.isEmpty()){
               return null;
           }

           return new UrlShortenResponse(longUrl);

    }
}
