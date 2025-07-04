package com.app.URLShortener.util.logging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogConfig {

    private String target;
    private String loglevel;
    private String timestampformat;

    //optional fields
    private int port;
    private String endpoint;
    private String filelocation;

}

