package com.app.shortener.config;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadFactory implements ThreadFactory {

    private final AtomicInteger threadNum = new AtomicInteger(1);

    @Override
    public Thread newThread(Runnable r) {
           Thread th = new Thread(r);
           th.setName("Thread-" + threadNum.getAndIncrement());
           return th;
    }
}
