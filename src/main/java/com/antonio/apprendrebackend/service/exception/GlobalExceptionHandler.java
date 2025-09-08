package com.antonio.apprendrebackend.service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserInfoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserInfoNotFoundException(UserInfoNotFoundException ex, WebRequest request) {
        logger.warn("UserInfoNotFoundException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

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
    public ResponseEntity<ErrorResponse> handleUserRequestExistLastWeekNotAnswered(UserRequestExistLastWeekNotAnswered ex, WebRequest request) {
        logger.warn("UserRequestExistLastWeekNotAnswered: {} - Path: {}", ex.getMessage(), request.getDescription(false));

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
    public ResponseEntity<ErrorResponse> handleUserHistorialNotFoundException(UserHistorialNotFoundException ex, WebRequest request) {
        logger.warn("UserHistorialNotFoundException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

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
    public ResponseEntity<ErrorResponse> handleWordTranslationNotFoundException(WordTranslationNotFoundException ex, WebRequest request) {
        logger.warn("WordTranslationNotFoundException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

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
    public ResponseEntity<ErrorResponse> handlePhraseNotFoundException(PhraseNotFoundException ex, WebRequest request) {
        logger.warn("PhraseNotFoundException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

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
    public ResponseEntity<ErrorResponse> handleHomeNotFoundException(HomeNotFoundException ex, WebRequest request) {
        logger.warn("HomeNotFoundException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

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
    public ResponseEntity<ErrorResponse> handleGoalNotCreatedException(GoalNotCreatedException ex, WebRequest request) {
        logger.warn("GoalNotCreatedException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

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
    public ResponseEntity<ErrorResponse> handleWordTypeNotVerbException(WordTypeNotVerbException ex, WebRequest request) {
        logger.warn("WordTypeNotVerbException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

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
    public ResponseEntity<ErrorResponse> handleDeckWordPhraseTranslation(DeckWordPhraseTranslationNotFoundException ex, WebRequest request) {
        logger.warn("DeckWordPhraseTranslationNotFoundException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

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
    public ResponseEntity<ErrorResponse> handleConjugationVerbNotFoundException(ConjugationVerbNotFoundException ex, WebRequest request) {
        logger.warn("ConjugationVerbNotFoundException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

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
    public ResponseEntity<ErrorResponse> handleVerbGroupNotFoundException(VerbGroupNotFoundException ex, WebRequest request) {
        logger.warn("VerbGroupNotFoundException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

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

    @ExceptionHandler(PartSpeechFoundException.class)
    public ResponseEntity<ErrorResponse> handlePartSpeechNotFoundException(PartSpeechFoundException ex, WebRequest request) {
        logger.warn("PartSpeechNotFoundException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ErrorCode.PART_SPEECH_NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);

    }

    @ExceptionHandler(WordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWordNotFoundException(WordNotFoundException ex, WebRequest request) {
        logger.warn("WordNotFoundException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

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
    public ResponseEntity<ErrorResponse> handlePersonGenderNumberNotFoundExceptionException(PersonGenderNumberNotFoundException ex, WebRequest request) {
        logger.warn("PersonGenderNumberNotFoundException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

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
    public ResponseEntity<ErrorResponse> handleWordCollectionNotFoundException(WordCollectionNotFoundException ex, WebRequest request) {
        logger.warn("WordCollectionNotFoundException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

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
    public ResponseEntity<ErrorResponse> handleWordCollectionItemNotFoundException(WordCollectionItemNotFoundException ex, WebRequest request) {
        logger.warn("WordCollectionItemNotFoundException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

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
    public ResponseEntity<ErrorResponse> handleTenseNotFoundException(TenseNotFoundException ex, WebRequest request) {
        logger.warn("TenseNotFoundException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

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
    public ResponseEntity<ErrorResponse> handleWordSenseNotFoundException(WordSenseNotFoundException ex, WebRequest request) {
        logger.warn("WordSenseNotFoundException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

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

    @ExceptionHandler(ConjugationVariationFoundException.class)
    public ResponseEntity<ErrorResponse> handleConjugationVariationNotFoundException(ConjugationVariationFoundException ex, WebRequest request) {
        logger.warn("ConjugationVariationFoundException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ErrorCode.CONJUGATION_VARIATION_NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);

    }

    @ExceptionHandler(DeckNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDeckNotFoundException(DeckNotFoundException ex, WebRequest request) {
        logger.warn("DeckNotFoundException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ErrorCode.DECK_NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);

    }

    @ExceptionHandler(SuccessNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSuccessNotFoundException(SuccessNotFoundException ex, WebRequest request) {
        logger.warn("SuccessNotFoundException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ErrorCode.SUCCESS_NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);

    }

    @ExceptionHandler(WordPhraseTranslationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWordPhraseTranslationNotFoundException(WordPhraseTranslationNotFoundException ex, WebRequest request) {
        logger.warn("WordPhraseTranslationNotFoundException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ErrorCode.WORD_PHRASE_TRANSLATION_NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);

    }

    @ExceptionHandler(DeckAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleDeckAlreadyExistsException(DeckAlreadyExistsException ex, WebRequest request) {
        logger.warn("DeckAlreadyExistsException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ErrorCode.DECK_ALREADY_EXISTS,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);

    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCourseNotFoundException(CourseNotFoundException ex, WebRequest request) {
        logger.warn("CourseNotFoundException: {} - Path: {}", ex.getMessage(), request.getDescription(false));

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ErrorCode.COURSE_NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);

    }


}
