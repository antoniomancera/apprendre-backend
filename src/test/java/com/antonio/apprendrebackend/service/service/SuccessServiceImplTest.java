package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.SuccessNotFoundException;
import com.antonio.apprendrebackend.service.model.Success;
import com.antonio.apprendrebackend.service.model.Word;
import com.antonio.apprendrebackend.service.model.WordSense;
import com.antonio.apprendrebackend.service.repository.SuccessRepository;
import com.antonio.apprendrebackend.service.service.impl.SuccessServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SuccessServiceImplTest {
    @Mock
    private SuccessRepository successRepository;

    @InjectMocks
    private SuccessServiceImpl successService;

    private Success correctSuccess;
    private Success incorrectSuccess;
    private WordSense wordSense;
    private Word word;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        correctSuccess = new Success();
        correctSuccess.setId(1);
        correctSuccess.setSuccessEnum(Success.SuccessEnum.CORRECT);

        incorrectSuccess = new Success();
        incorrectSuccess.setId(2);
        incorrectSuccess.setSuccessEnum(Success.SuccessEnum.INCORRECT);

        word = new Word();
        word.setId(101);
        word.setName("maison");

        wordSense = new WordSense();
        wordSense.setId(201);
        wordSense.setWord(word);
    }

    @Test
    void testGetSuccessBySuccessEnum_Correct() {
        // Given
        Success.SuccessEnum successEnum = Success.SuccessEnum.CORRECT;

        // When
        when(successRepository.findBySuccessEnum(successEnum))
                .thenReturn(Optional.of(correctSuccess));

        Success result = successService.getSuccessBySuccessEnum(successEnum);

        // Then
        assertNotNull(result);
        assertEquals(correctSuccess.getId(), result.getId());
        assertEquals(Success.SuccessEnum.CORRECT, result.getSuccessEnum());
        verify(successRepository, times(1)).findBySuccessEnum(successEnum);
    }

    @Test
    void testGetSuccessBySuccessEnum_Incorrect() {
        // Given
        Success.SuccessEnum successEnum = Success.SuccessEnum.INCORRECT;

        // When
        when(successRepository.findBySuccessEnum(successEnum))
                .thenReturn(Optional.of(incorrectSuccess));

        Success result = successService.getSuccessBySuccessEnum(successEnum);

        // Then
        assertNotNull(result);
        assertEquals(incorrectSuccess.getId(), result.getId());
        assertEquals(Success.SuccessEnum.INCORRECT, result.getSuccessEnum());
        verify(successRepository, times(1)).findBySuccessEnum(successEnum);
    }

    @Test
    void testGetSuccessBySuccessEnum_NotFound() {
        // Given
        Success.SuccessEnum successEnum = Success.SuccessEnum.CORRECT;

        // When
        when(successRepository.findBySuccessEnum(successEnum))
                .thenReturn(Optional.empty());

        // Then
        SuccessNotFoundException exception = assertThrows(
                SuccessNotFoundException.class,
                () -> successService.getSuccessBySuccessEnum(successEnum)
        );

        assertTrue(exception.getMessage().contains("Not dounf any success for enum: CORRECT"));
        verify(successRepository, times(1)).findBySuccessEnum(successEnum);
    }

    @Test
    void testGetSuccessByAttemptAndWordSense_CorrectAttempt() {
        // Given
        String attempt = "maison";

        // When
        when(successRepository.findBySuccessEnum(Success.SuccessEnum.CORRECT))
                .thenReturn(Optional.of(correctSuccess));

        Success result = successService.getSuccessByAttemptAndWordSense(attempt, wordSense);

        // Then
        assertNotNull(result);
        assertEquals(correctSuccess.getId(), result.getId());
        assertEquals(Success.SuccessEnum.CORRECT, result.getSuccessEnum());
        verify(successRepository, times(1)).findBySuccessEnum(Success.SuccessEnum.CORRECT);
    }

    @Test
    void testGetSuccessByAttemptAndWordSense_CorrectAttemptDifferentCase() {
        // Given
        String attempt = "MAISON";

        // When
        when(successRepository.findBySuccessEnum(Success.SuccessEnum.CORRECT))
                .thenReturn(Optional.of(correctSuccess));

        Success result = successService.getSuccessByAttemptAndWordSense(attempt, wordSense);

        // Then
        assertNotNull(result);
        assertEquals(correctSuccess.getId(), result.getId());
        assertEquals(Success.SuccessEnum.CORRECT, result.getSuccessEnum());
        verify(successRepository, times(1)).findBySuccessEnum(Success.SuccessEnum.CORRECT);
    }

    @Test
    void testGetSuccessByAttemptAndWordSense_IncorrectAttempt() {
        // Given
        String attempt = "house";

        // When
        when(successRepository.findBySuccessEnum(Success.SuccessEnum.INCORRECT))
                .thenReturn(Optional.of(incorrectSuccess));

        Success result = successService.getSuccessByAttemptAndWordSense(attempt, wordSense);

        // Then
        assertNotNull(result);
        assertEquals(incorrectSuccess.getId(), result.getId());
        assertEquals(Success.SuccessEnum.INCORRECT, result.getSuccessEnum());
        verify(successRepository, times(1)).findBySuccessEnum(Success.SuccessEnum.INCORRECT);
    }

    @Test
    void testGetSuccessByAttemptAndWordSense_EmptyAttempt() {
        // Given
        String attempt = "";

        // When
        when(successRepository.findBySuccessEnum(Success.SuccessEnum.INCORRECT))
                .thenReturn(Optional.of(incorrectSuccess));

        Success result = successService.getSuccessByAttemptAndWordSense(attempt, wordSense);

        // Then
        assertNotNull(result);
        assertEquals(incorrectSuccess.getId(), result.getId());
        assertEquals(Success.SuccessEnum.INCORRECT, result.getSuccessEnum());
        verify(successRepository, times(1)).findBySuccessEnum(Success.SuccessEnum.INCORRECT);
    }

    @Test
    void testGetSuccessByAttemptAndWordSense_NullAttempt() {
        // Given
        String attempt = null;

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            successService.getSuccessByAttemptAndWordSense(attempt, wordSense);
        });
    }

    @Test
    void testGetSuccessByAttemptAndWordSense_CorrectAttemptWithWhitespace() {
        // Given
        String attempt = " maison ";

        // When
        when(successRepository.findBySuccessEnum(Success.SuccessEnum.INCORRECT))
                .thenReturn(Optional.of(incorrectSuccess));

        Success result = successService.getSuccessByAttemptAndWordSense(attempt, wordSense);

        // Then
        assertNotNull(result);
        assertEquals(incorrectSuccess.getId(), result.getId());
        assertEquals(Success.SuccessEnum.INCORRECT, result.getSuccessEnum());
        verify(successRepository, times(1)).findBySuccessEnum(Success.SuccessEnum.INCORRECT);
    }

    @Test
    void testGetSuccessByAttemptAndWordSense_CorrectAttemptButRepositoryThrowsException() {
        // Given
        String attempt = "maison";

        // When
        when(successRepository.findBySuccessEnum(Success.SuccessEnum.CORRECT))
                .thenReturn(Optional.empty());

        // Then
        SuccessNotFoundException exception = assertThrows(
                SuccessNotFoundException.class,
                () -> successService.getSuccessByAttemptAndWordSense(attempt, wordSense)
        );

        assertTrue(exception.getMessage().contains("Not dounf any success for enum: CORRECT"));
        verify(successRepository, times(1)).findBySuccessEnum(Success.SuccessEnum.CORRECT);
    }

    @Test
    void testGetSuccessByAttemptAndWordSense_IncorrectAttemptButRepositoryThrowsException() {
        // Given
        String attempt = "house";

        // When
        when(successRepository.findBySuccessEnum(Success.SuccessEnum.INCORRECT))
                .thenReturn(Optional.empty());

        // Then
        SuccessNotFoundException exception = assertThrows(
                SuccessNotFoundException.class,
                () -> successService.getSuccessByAttemptAndWordSense(attempt, wordSense)
        );

        assertTrue(exception.getMessage().contains("Not dounf any success for enum: INCORRECT"));
        verify(successRepository, times(1)).findBySuccessEnum(Success.SuccessEnum.INCORRECT);
    }
}
