package com.example.module3.eventServiceRest.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public final class ErrorModel {

    private final HttpStatus httpStatus;
    private final LocalDateTime timestamp;
    private final String message;
    private final String details;

    public ErrorModel(HttpStatus httpStatus, String message, String details) {
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.details = details;
    }
}
