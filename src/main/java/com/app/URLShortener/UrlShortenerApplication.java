package com.app.URLShortener;

import com.app.URLShortener.util.logging.LogLevel;
import com.app.URLShortener.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UrlShortenerApplication {

	public static void main(String[] args) throws Exception{
		SpringApplication.run(UrlShortenerApplication.class, args);
	}
}

