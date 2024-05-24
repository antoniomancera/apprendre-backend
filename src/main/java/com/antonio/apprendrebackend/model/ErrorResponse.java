package com.antonio.apprendrebackend.model;

import com.antonio.apprendrebackend.util.ErrorCode;

public class ErrorResponse {
    private String message;
    private ErrorCode codeError;

    public ErrorResponse(String message, ErrorCode codeError) {
        this.message = message;
        this.codeError = codeError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorCode getCodeError() {
        return codeError;
    }

    public void setCodeError(ErrorCode codeError) {
        this.codeError = codeError;
    }
}
