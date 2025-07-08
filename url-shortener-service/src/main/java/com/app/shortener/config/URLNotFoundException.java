package com.app.shortener.config;

public class URLNotFoundException extends RuntimeException {

    public URLNotFoundException() {
        super("No Long URL mapped with the Short URL");
    }

    public URLNotFoundException(String message) {
        super(message);
    }
}
