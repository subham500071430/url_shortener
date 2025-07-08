package com.app.shortener.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpServerErrorException;

import java.net.URI;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

      LocalDateTime timestamp;
      HttpStatusCode httpStatusCode;
      HttpServerErrorException error;
      String message;
      URI path;
}
