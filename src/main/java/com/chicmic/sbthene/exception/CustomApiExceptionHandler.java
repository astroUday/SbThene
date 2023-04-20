package com.chicmic.sbthene.exception;

import org.springframework.http.HttpStatus;

public class CustomApiExceptionHandler extends RuntimeException{

    private final HttpStatus status;
    public CustomApiExceptionHandler(HttpStatus status,String message) {

        super(message);
        this.status=status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
