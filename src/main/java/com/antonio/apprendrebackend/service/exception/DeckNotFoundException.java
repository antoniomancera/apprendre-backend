package com.antonio.apprendrebackend.service.exception;

public class DeckNotFoundException extends RuntimeException {
    public DeckNotFoundException(String message) {
        super(message);
    }
}