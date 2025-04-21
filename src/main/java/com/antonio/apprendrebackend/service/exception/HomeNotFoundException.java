package com.antonio.apprendrebackend.service.exception;

public class HomeNotFoundException extends RuntimeException {
    public HomeNotFoundException(String message) {
        super(message);
    }
}
