package com.app.user.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

       @Bean
       ModelMapper createModelMapper(){
               return new ModelMapper();
       }
}
