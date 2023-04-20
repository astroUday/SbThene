package com.chicmic.sbthene.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorDetails {

    private final HttpStatus httpStatus;
    private final LocalDateTime timeStamp;
    private final String message;

    public ErrorDetails(LocalDateTime timeStamp, String message,HttpStatus httpStatus) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.httpStatus=httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }


}
