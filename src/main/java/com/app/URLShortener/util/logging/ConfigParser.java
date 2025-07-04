package com.app.URLShortener.util.logging;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class ConfigParser {

       static LogConfig parse(String filePath) throws Exception{
           ObjectMapper mapper = new ObjectMapper();
           return mapper.readValue(new File(filePath) , LogConfig.class);
       }
}
