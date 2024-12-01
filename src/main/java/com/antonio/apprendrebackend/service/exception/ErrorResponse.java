package com.antonio.apprendrebackend.service.exception;

import com.antonio.apprendrebackend.service.util.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private int statusCode;
    private ErrorCode errorCode;
    private String message;
    private Long date;

    public ErrorResponse(int statusCode, ErrorCode errorCode, String message) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
        this.date = System.currentTimeMillis();
    }

}
