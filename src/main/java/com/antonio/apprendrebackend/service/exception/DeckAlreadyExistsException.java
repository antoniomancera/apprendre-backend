package com.antonio.apprendrebackend.service.exception;

public class DeckAlreadyExistsException extends RuntimeException {
    public DeckAlreadyExistsException(String message) {
        super(message);
    }
}