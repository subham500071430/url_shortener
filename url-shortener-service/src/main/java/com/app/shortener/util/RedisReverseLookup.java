package com.app.shortener.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisReverseLookup {

    private static final String USED_CODES_KEY = "used_codes";

    @Autowired
    private StringRedisTemplate redisTemplate;

    public boolean isLongUrlUsed(String longUrl) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(USED_CODES_KEY, longUrl));
    }

    public void markLongUrlAsUsed(String longUrl) {
        redisTemplate.opsForSet().add(USED_CODES_KEY, longUrl);
    }

    public void cacheLongUrl(String longUrl, String code) {
        redisTemplate.opsForValue().set("reverse url:" + longUrl, code, 1, TimeUnit.DAYS);
    }

    public String getShortCode(String longUrl) {
        return redisTemplate.opsForValue().get("reverse url:" + longUrl);
    }


}
