package com.app.shortener.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest httpRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setHttpStatusCode(HttpStatusCode.valueOf(500));
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(httpRequest.getServletPath());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(URLNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(Exception ex, HttpServletRequest httpRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setHttpStatusCode(HttpStatusCode.valueOf(404));
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(httpRequest.getServletPath());
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404),ex.getMessage());
    }

}
