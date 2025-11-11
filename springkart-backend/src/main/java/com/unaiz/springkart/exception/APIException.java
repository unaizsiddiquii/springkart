package com.unaiz.springkart.exception;

public class APIException extends RuntimeException {
    public APIException(String message) {
        super(message);
    }
}
