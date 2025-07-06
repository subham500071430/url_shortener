package com.app.URLShortener.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class AppConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {

        return new ThreadPoolExecutor(8, 8, 1, TimeUnit.HOURS,
                new ArrayBlockingQueue<>(100), new CustomThreadFactory());
    }
}
