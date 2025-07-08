package com.app.shortener.util.logging;

public class Logger {

    LoggerWriter writer;
    LogLevel configLevel;

    public Logger(String filePath) throws Exception {
        LogConfig config = ConfigParser.parse(filePath);
        this.configLevel = LogLevel.fromString(config.getLoglevel());
        this.writer = LoggerWriterFactory.getWrite(config);
    }

    public void log(LogLevel level, String msg) {
        if(level.getLevel()>= configLevel.getLevel())
           writer.write(level + " : " + msg);
    }
}
