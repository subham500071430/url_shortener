package com.app.URLShortener.util.logging;

public class LoggerWriterFactory {

       public static LoggerWriter getWrite(LogConfig config){

             switch(config.getTarget().toLowerCase()) {
                 case "db" :
                     return new DBLoggerWriter(config.getPort(), config.getEndpoint());
                 case "file" :
                     return new FileLoggerWriter(config.getFilelocation());
                 default:
                     return null;
             }

       }
}
