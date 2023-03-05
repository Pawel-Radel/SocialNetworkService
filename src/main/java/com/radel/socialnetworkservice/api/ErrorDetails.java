package com.radel.socialnetworkservice.api;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ErrorDetails {

    private int code;
    private String message;
    @Setter
    private HttpStatus status;
    private String timestamp;

    public ErrorDetails(int code, String message, HttpStatus status) {
        this(code, message);
        this.status = status;
    }

    public ErrorDetails(int code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = OffsetDateTime.now().toString();
    }
}
