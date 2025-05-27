package com.antonio.apprendrebackend.service.exception;

public class WordSenseNotFoundException extends RuntimeException {
    public WordSenseNotFoundException(String message) {
        super(message);
    }
}
