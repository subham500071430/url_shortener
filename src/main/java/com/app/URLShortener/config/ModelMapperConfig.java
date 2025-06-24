package com.app.URLShortener.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

       @Bean
       public ModelMapper modeMapper(){
              return new ModelMapper();
       }
}
