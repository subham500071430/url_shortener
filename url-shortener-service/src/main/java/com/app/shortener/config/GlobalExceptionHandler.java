package com.app.shortener.config;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest httpRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(httpRequest.getServletPath());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(URLNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(Exception ex, HttpServletRequest httpRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setHttpStatus(HttpStatus.NOT_FOUND);
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(httpRequest.getServletPath());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

}
