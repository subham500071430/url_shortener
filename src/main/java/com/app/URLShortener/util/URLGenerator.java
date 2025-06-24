package com.app.URLShortener.util;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Component;

@Component
public class URLGenerator {

    public String generate(String inputUrl) {

        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('a', 'z')
                .build();

        String random = generator.generate(6);

        return random;
    }

}
