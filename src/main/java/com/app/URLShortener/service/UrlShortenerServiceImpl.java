package com.app.URLShortener.service;

import com.app.URLShortener.dto.UrlShortenRequest;
import com.app.URLShortener.dto.UrlShortenResponse;
import com.app.URLShortener.entity.UrlMapper;
import com.app.URLShortener.repository.UrlRepository;
import com.app.URLShortener.util.RedisLookup;
import com.app.URLShortener.util.RedisReverseLookup;
import com.app.URLShortener.util.URLGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private URLGenerator generator;
    @Autowired(required = false)
    private RedisLookup redisLookup;
    @Autowired(required = false)
    private RedisReverseLookup redisReverseLookup;
    @Autowired(required = false)
    private UrlRepository urlRepository;


    @Override
    public UrlShortenResponse shortenURL(UrlShortenRequest request) {

        String generatedShortUrl = null;
        String longUrl = request.getLongUrl();
        String prefixUrl = "http://localhost:8080/";

        if (urlRepository != null) {

            Optional<UrlMapper> urlMapper = urlRepository.findByLongUrl(longUrl);

            if(urlMapper.isPresent()){
                  return new UrlShortenResponse(urlMapper.get().getShortUrl());
            } else {
                do {
                    generatedShortUrl = prefixUrl + generator.generate(longUrl);
                } while (urlRepository.findById(generatedShortUrl).isPresent());

                urlRepository.save(new UrlMapper(generatedShortUrl,longUrl));
                return  new UrlShortenResponse(generatedShortUrl);
            }

        } else {

            if (redisReverseLookup.isLongUrlUsed(longUrl)) {
                return new UrlShortenResponse(redisReverseLookup.getShortCode(longUrl));
            }

            do {
                generatedShortUrl = prefixUrl + generator.generate(longUrl);
            } while (redisLookup.isCodeUsed(generatedShortUrl));


            redisLookup.cacheShortUrl(generatedShortUrl, longUrl);
            redisLookup.markCodeAsUsed(generatedShortUrl);
            redisReverseLookup.cacheLongUrl(longUrl, generatedShortUrl);
            redisReverseLookup.markLongUrlAsUsed(longUrl);

            return new UrlShortenResponse(generatedShortUrl);

        }

    }

    @Override
    public UrlShortenResponse getURL(String shortUrl) {

        String fullShortUrl = "http://localhost:8080/" + shortUrl;
        String longUrl = null;

        if(Objects.nonNull(redisLookup) && Objects.nonNull(redisLookup.getLongUrl(fullShortUrl))){
             longUrl = redisLookup.getLongUrl(fullShortUrl);
        } else if(Objects.nonNull(urlRepository) && urlRepository.findById(fullShortUrl).isPresent()){
             longUrl =  urlRepository.findById(fullShortUrl).get().getLongUrl();
        }

        return new UrlShortenResponse(longUrl);

    }
}
