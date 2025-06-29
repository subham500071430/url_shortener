package com.app.URLShortener.util.logging;

public class DBLoggerWriter implements LoggerWriter{

    int port;
    String endPoint;

    DBLoggerWriter(int port,String endPoint){
        this.port = port;
        this.endPoint = endPoint;
    }

    @Override
    public void write(String msg) {
          System.out.println(msg);
          System.out.println("saving to db");
    }
}
