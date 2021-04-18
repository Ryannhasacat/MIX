package com.mix.exception;

public class HttpClientNullException extends RuntimeException{

    public HttpClientNullException() {
    }

    public HttpClientNullException(String message) {
        super(message);
    }
}
