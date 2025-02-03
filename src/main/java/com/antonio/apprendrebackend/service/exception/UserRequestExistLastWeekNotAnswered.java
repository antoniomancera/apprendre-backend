package com.antonio.apprendrebackend.service.exception;

public class UserRequestExistLastWeekNotAnswered extends RuntimeException {
    public UserRequestExistLastWeekNotAnswered(String message) {
        super(message);
    }
}
