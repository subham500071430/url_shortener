package com.app.URLShortener.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class AppConfig implements AsyncConfigurer {

    private Executor executor;

    @Autowired
    private DefaultUncaughtExceptionHandler exceptionHandler;

    @Override
    public Executor getAsyncExecutor() {

        if(executor == null){
            executor = new ThreadPoolExecutor(8, 8, 1, TimeUnit.HOURS,
                    new ArrayBlockingQueue<>(100), new CustomThreadFactory());
        }

        return executor;
    }

    public AsyncUncaughtExceptionHandler getExceptionHandler() {

           return exceptionHandler;
    }
}
