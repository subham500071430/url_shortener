package com.app.shortener.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND )
public class URLNotFoundException extends RuntimeException {

    public URLNotFoundException(){
        super("No URL mapped with the Short URL");
    }

}
