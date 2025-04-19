package com.antonio.apprendrebackend.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserInfoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserInfoNotFoundException(UserInfoNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ErrorCode.USER_INFO_NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(UserRequestExistLastWeekNotAnswered.class)
    public ResponseEntity<ErrorResponse> handleUserRequestExistLastWeekNotAnswered(UserRequestExistLastWeekNotAnswered ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ErrorCode.USER_REQUEST_LAST_WEEK_NOT_ANSWERED,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(WordTranslationHistorialNotFound.class)
    public ResponseEntity<ErrorResponse> handleWordTranslationHistorialNotFound(WordTranslationHistorialNotFound ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ErrorCode.WORD_TRANSLATION_HISTORIAL_NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(WordTranslationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWordTranslationNotFoundException(WordTranslationNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ErrorCode.WORD_TRANSLATION_NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(PhraseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePhraseNotFoundException(PhraseNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ErrorCode.PHRASE_NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(DeckWordTranslationHistorialNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDeckWordTranslationHistorialNotFoundException(DeckWordTranslationHistorialNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ErrorCode.DECK_WORD_TRANSLATION_HISTORIAL_NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(HomeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleHomeNotFoundException(HomeNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ErrorCode.HOME_NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(GoalNotCreatedException.class)
    public ResponseEntity<ErrorResponse> handleGoalNotCreatedException(GoalNotCreatedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ErrorCode.GOAL_NOT_CREATED,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

}
