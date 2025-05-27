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

    @ExceptionHandler(UserHistorialNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserHistorialNotFoundException(UserHistorialNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ErrorCode.USER_HISTORIAL,
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
                HttpStatus.NO_CONTENT.value(),
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
                HttpStatus.NO_CONTENT.value(),
                ErrorCode.PHRASE_NOT_FOUND,
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

    @ExceptionHandler(WordTypeNotVerbException.class)
    public ResponseEntity<ErrorResponse> handleWordTypeNotVerbException(WordTypeNotVerbException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ErrorCode.WORD_TYPE_NOT_VERB,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(DeckWordPhraseTranslationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDeckWordPhraseTranslation(DeckWordPhraseTranslationNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ErrorCode.DECK_USER_WORD_PHRASE_TRANSLATION,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(ConjugationVerbNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleConjugationVerbNotFoundException(ConjugationVerbNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ErrorCode.CONJUGATION_VERB_NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);

    }

    @ExceptionHandler(VerbGroupNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleVerbGroupNotFoundExceptionn(VerbGroupNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ErrorCode.VERB_GROUP_NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);

    }

    @ExceptionHandler(TypeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTypeNotFoundException(TypeNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ErrorCode.TYPE_NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);

    }

    @ExceptionHandler(WordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWordNotFoundException(WordNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ErrorCode.WORD_NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);

    }

    @ExceptionHandler(PersonGenderNumberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePersonGenderNumberNotFoundExceptionException(PersonGenderNumberNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ErrorCode.PERSON_GENDER_NUMBER_NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);

    }

    @ExceptionHandler(WordCollectionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWordCollectionNotFoundException(WordCollectionNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ErrorCode.WORD_COLLECTION_NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);

    }

    @ExceptionHandler(WordCollectionItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWordCollectionItemNotFoundException(WordCollectionItemNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ErrorCode.WORD_COLLECTION_ITEM_NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);

    }


    @ExceptionHandler(TenseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTenseNotFoundException(TenseNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ErrorCode.TENSE_NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);

    }

    @ExceptionHandler(WordSenseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWordSenseNotFoundException(WordSenseNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ErrorCode.WORD_SENSE_NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);

    }


}
