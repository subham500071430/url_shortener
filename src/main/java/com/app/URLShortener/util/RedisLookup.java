package com.app.URLShortener.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisLookup {

    private static final String USED_CODES_KEY = "used_codes";

    @Autowired
    private StringRedisTemplate redisTemplate;

    public boolean isCodeUsed(String code) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(USED_CODES_KEY, code));
    }

    public void markCodeAsUsed(String code) {
        redisTemplate.opsForSet().add(USED_CODES_KEY, code);
    }

    public void cacheShortUrl(String code, String longUrl) {
        redisTemplate.opsForValue().set("url:" + code, longUrl, 1, TimeUnit.DAYS);
    }

    public String getLongUrl(String code) {
        return redisTemplate.opsForValue().get("url:" + code);
    }
}
