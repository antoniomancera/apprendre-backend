package com.antonio.apprendrebackend.service.exception;

public class UserInfoNotFoundException extends RuntimeException {
    public UserInfoNotFoundException(String message) {
        super(message);
    }
}