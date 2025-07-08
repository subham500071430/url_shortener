package com.app.shortener.util.logging;

public class FileLoggerWriter implements LoggerWriter{

    String filePath;

    FileLoggerWriter(String filePath){
        this.filePath = filePath;
    }
    @Override
    public void write(String msg) {
          System.out.println(msg);
          System.out.println("saving to file");
    }
}
