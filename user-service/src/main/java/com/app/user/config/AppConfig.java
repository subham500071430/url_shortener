package com.app.user.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import javax.ws.rs.HttpMethod;

@Configuration
@EnableWebSecurity
public class AppConfig {

       @Bean
       ModelMapper createModelMapper(){
               return new ModelMapper();
       }

       SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

               return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                   .authorizeHttpRequests(auth -> auth
                           .anyRequest().authenticated()
                   )
                   .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .build();
       }
}
