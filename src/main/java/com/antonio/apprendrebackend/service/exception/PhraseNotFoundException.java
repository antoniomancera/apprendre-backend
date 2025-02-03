package com.antonio.apprendrebackend.service.exception;

public class PhraseNotFoundException extends RuntimeException {
    public PhraseNotFoundException(String message) {
        super(message);
    }
}
