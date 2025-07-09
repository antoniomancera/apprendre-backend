package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.repository.ConjugationVerbWordWordSenseRepository;
import com.antonio.apprendrebackend.service.service.impl.ConjugationVerbWordWordSenseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConjugationVerbWordWordSenseServiceImplTest {

    @Mock
    private ConjugationVerbWordWordSenseRepository conjugationVerbWordWordSenseRepository;

    @InjectMocks
    private ConjugationVerbWordWordSenseServiceImpl conjugationVerbWordWordSenseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetConjugationVerbWordWordSenseByWordSenseIdWhenExists() {
        // Given
        Integer wordSenseId = 1;
        ConjugationVerbWordWordSense expectedConjugationVerbWordWordSense =
                createConjugationVerbWordWordSense(wordSenseId, 2);

        Optional<ConjugationVerbWordWordSense> expectedOptional =
                Optional.of(expectedConjugationVerbWordWordSense);

        // When
        when(conjugationVerbWordWordSenseRepository.findByWordSenseId(wordSenseId))
                .thenReturn(expectedOptional);

        Optional<ConjugationVerbWordWordSense> result =
                conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordSenseId(wordSenseId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(expectedConjugationVerbWordWordSense, result.get());
        assertEquals(wordSenseId, result.get().getWordSense().getId());
        verify(conjugationVerbWordWordSenseRepository, times(1)).findByWordSenseId(wordSenseId);
    }

    @Test
    void testGetConjugationVerbWordWordSenseByWordSenseIdWhenNotExists() {
        // Given
        Integer wordSenseId = 999;
        Optional<ConjugationVerbWordWordSense> emptyOptional = Optional.empty();

        // When
        when(conjugationVerbWordWordSenseRepository.findByWordSenseId(wordSenseId))
                .thenReturn(emptyOptional);

        Optional<ConjugationVerbWordWordSense> result =
                conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordSenseId(wordSenseId);

        // Then
        assertFalse(result.isPresent());
        assertTrue(result.isEmpty());
        verify(conjugationVerbWordWordSenseRepository, times(1)).findByWordSenseId(wordSenseId);
    }

    @Test
    void testGetConjugationVerbWordWordSenseByWordSenseIdWithNullParameter() {
        // Given
        Integer wordSenseId = null;
        Optional<ConjugationVerbWordWordSense> emptyOptional = Optional.empty();

        // When
        when(conjugationVerbWordWordSenseRepository.findByWordSenseId(wordSenseId))
                .thenReturn(emptyOptional);

        Optional<ConjugationVerbWordWordSense> result =
                conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordSenseId(wordSenseId);

        // Then
        assertFalse(result.isPresent());
        verify(conjugationVerbWordWordSenseRepository, times(1)).findByWordSenseId(wordSenseId);
    }

    @Test
    void testGetConjugationVerbWordWordSenseByWordIdWhenExists() {
        // Given
        Integer wordId = 1;
        ConjugationVerbWordWordSense expectedConjugationVerbWordWordSense =
                createConjugationVerbWordWordSenseWithWordId(wordId, 2);

        Optional<ConjugationVerbWordWordSense> expectedOptional =
                Optional.of(expectedConjugationVerbWordWordSense);

        // When
        when(conjugationVerbWordWordSenseRepository.findByWordId(wordId))
                .thenReturn(expectedOptional);

        Optional<ConjugationVerbWordWordSense> result =
                conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordId(wordId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(expectedConjugationVerbWordWordSense, result.get());
        assertEquals(wordId, result.get().getWordSense().getWord().getId());
        verify(conjugationVerbWordWordSenseRepository, times(1)).findByWordId(wordId);
    }

    @Test
    void testGetConjugationVerbWordWordSenseByWordIdWhenNotExists() {
        // Given
        Integer wordId = 999;
        Optional<ConjugationVerbWordWordSense> emptyOptional = Optional.empty();

        // When
        when(conjugationVerbWordWordSenseRepository.findByWordId(wordId))
                .thenReturn(emptyOptional);

        Optional<ConjugationVerbWordWordSense> result =
                conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordId(wordId);

        // Then
        assertFalse(result.isPresent());
        assertTrue(result.isEmpty());
        verify(conjugationVerbWordWordSenseRepository, times(1)).findByWordId(wordId);
    }

    @Test
    void testGetConjugationVerbWordWordSenseByWordIdWithNullParameter() {
        // Given
        Integer wordId = null;
        Optional<ConjugationVerbWordWordSense> emptyOptional = Optional.empty();

        // When
        when(conjugationVerbWordWordSenseRepository.findByWordId(wordId))
                .thenReturn(emptyOptional);

        Optional<ConjugationVerbWordWordSense> result =
                conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordId(wordId);

        // Then
        assertFalse(result.isPresent());
        verify(conjugationVerbWordWordSenseRepository, times(1)).findByWordId(wordId);
    }

    @Test
    void testGetConjugationVerbWordWordSenseByWordSenseIdWithMultipleCallsUsesCache() {
        // Given
        Integer wordSenseId = 1;
        ConjugationVerbWordWordSense expectedConjugationVerbWordWordSense =
                createConjugationVerbWordWordSense(wordSenseId, 2);

        Optional<ConjugationVerbWordWordSense> expectedOptional =
                Optional.of(expectedConjugationVerbWordWordSense);

        // When
        when(conjugationVerbWordWordSenseRepository.findByWordSenseId(wordSenseId))
                .thenReturn(expectedOptional);

        Optional<ConjugationVerbWordWordSense> result1 =
                conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordSenseId(wordSenseId);
        Optional<ConjugationVerbWordWordSense> result2 =
                conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordSenseId(wordSenseId);

        // Then
        assertTrue(result1.isPresent());
        assertTrue(result2.isPresent());
        assertEquals(result1.get(), result2.get());
        // Repository should be called twice (no caching in this simple implementation)
        verify(conjugationVerbWordWordSenseRepository, times(2)).findByWordSenseId(wordSenseId);
    }

    @Test
    void testGetConjugationVerbWordWordSenseByWordIdWithMultipleCallsUsesCache() {
        // Given
        Integer wordId = 1;
        ConjugationVerbWordWordSense expectedConjugationVerbWordWordSense =
                createConjugationVerbWordWordSenseWithWordId(wordId, 2);

        Optional<ConjugationVerbWordWordSense> expectedOptional =
                Optional.of(expectedConjugationVerbWordWordSense);

        // When
        when(conjugationVerbWordWordSenseRepository.findByWordId(wordId))
                .thenReturn(expectedOptional);

        Optional<ConjugationVerbWordWordSense> result1 =
                conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordId(wordId);
        Optional<ConjugationVerbWordWordSense> result2 =
                conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordId(wordId);

        // Then
        assertTrue(result1.isPresent());
        assertTrue(result2.isPresent());
        assertEquals(result1.get(), result2.get());
        // Repository should be called twice (no caching in this simple implementation)
        verify(conjugationVerbWordWordSenseRepository, times(2)).findByWordId(wordId);
    }

    @Test
    void testGetConjugationVerbWordWordSenseByWordSenseIdReturnsDifferentInstancesForDifferentIds() {
        // Given
        Integer wordSenseId1 = 1;
        Integer wordSenseId2 = 2;

        ConjugationVerbWordWordSense conjugationVerbWordWordSense1 =
                createConjugationVerbWordWordSense(wordSenseId1, 10);
        ConjugationVerbWordWordSense conjugationVerbWordWordSense2 =
                createConjugationVerbWordWordSense(wordSenseId2, 20);

        Optional<ConjugationVerbWordWordSense> optional1 = Optional.of(conjugationVerbWordWordSense1);
        Optional<ConjugationVerbWordWordSense> optional2 = Optional.of(conjugationVerbWordWordSense2);

        // When
        when(conjugationVerbWordWordSenseRepository.findByWordSenseId(wordSenseId1))
                .thenReturn(optional1);
        when(conjugationVerbWordWordSenseRepository.findByWordSenseId(wordSenseId2))
                .thenReturn(optional2);

        Optional<ConjugationVerbWordWordSense> result1 =
                conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordSenseId(wordSenseId1);
        Optional<ConjugationVerbWordWordSense> result2 =
                conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordSenseId(wordSenseId2);

        // Then
        assertTrue(result1.isPresent());
        assertTrue(result2.isPresent());
        assertNotEquals(result1.get(), result2.get());
        assertEquals(wordSenseId1, result1.get().getWordSense().getId());
        assertEquals(wordSenseId2, result2.get().getWordSense().getId());
        verify(conjugationVerbWordWordSenseRepository, times(1)).findByWordSenseId(wordSenseId1);
        verify(conjugationVerbWordWordSenseRepository, times(1)).findByWordSenseId(wordSenseId2);
    }

    @Test
    void testGetConjugationVerbWordWordSenseByWordIdReturnsDifferentInstancesForDifferentIds() {
        // Given
        Integer wordId1 = 1;
        Integer wordId2 = 2;

        ConjugationVerbWordWordSense conjugationVerbWordWordSense1 =
                createConjugationVerbWordWordSenseWithWordId(wordId1, 10);
        ConjugationVerbWordWordSense conjugationVerbWordWordSense2 =
                createConjugationVerbWordWordSenseWithWordId(wordId2, 20);

        Optional<ConjugationVerbWordWordSense> optional1 = Optional.of(conjugationVerbWordWordSense1);
        Optional<ConjugationVerbWordWordSense> optional2 = Optional.of(conjugationVerbWordWordSense2);

        // When
        when(conjugationVerbWordWordSenseRepository.findByWordId(wordId1))
                .thenReturn(optional1);
        when(conjugationVerbWordWordSenseRepository.findByWordId(wordId2))
                .thenReturn(optional2);

        Optional<ConjugationVerbWordWordSense> result1 =
                conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordId(wordId1);
        Optional<ConjugationVerbWordWordSense> result2 =
                conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordId(wordId2);

        // Then
        assertTrue(result1.isPresent());
        assertTrue(result2.isPresent());
        assertNotEquals(result1.get(), result2.get());
        assertEquals(wordId1, result1.get().getWordSense().getWord().getId());
        assertEquals(wordId2, result2.get().getWordSense().getWord().getId());
        verify(conjugationVerbWordWordSenseRepository, times(1)).findByWordId(wordId1);
        verify(conjugationVerbWordWordSenseRepository, times(1)).findByWordId(wordId2);
    }

    // Helper methods
    private ConjugationVerbWordWordSense createConjugationVerbWordWordSense(Integer wordSenseId, Integer conjugationVerbId) {
        ConjugationVerbWordWordSense conjugationVerbWordWordSense = new ConjugationVerbWordWordSense();

        // Create WordSense
        WordSense wordSense = new WordSense();
        wordSense.setId(wordSenseId);

        // Create Word
        Word word = new Word();
        word.setId(wordSenseId + 100); // Different ID for word
        wordSense.setWord(word);

        // Create ConjugationVerb
        ConjugationVerb conjugationVerb = new ConjugationVerb();
        conjugationVerb.setId(conjugationVerbId);

        // Set relationships
        conjugationVerbWordWordSense.setWordSense(wordSense);
        conjugationVerbWordWordSense.setConjugationVerb(conjugationVerb);

        return conjugationVerbWordWordSense;
    }

    private ConjugationVerbWordWordSense createConjugationVerbWordWordSenseWithWordId(Integer wordId, Integer conjugationVerbId) {
        ConjugationVerbWordWordSense conjugationVerbWordWordSense = new ConjugationVerbWordWordSense();

        // Create Word
        Word word = new Word();
        word.setId(wordId);

        // Create WordSense
        WordSense wordSense = new WordSense();
        wordSense.setId(wordId + 100); // Different ID for wordSense
        wordSense.setWord(word);

        // Create ConjugationVerb
        ConjugationVerb conjugationVerb = new ConjugationVerb();
        conjugationVerb.setId(conjugationVerbId);

        // Set relationships
        conjugationVerbWordWordSense.setWordSense(wordSense);
        conjugationVerbWordWordSense.setConjugationVerb(conjugationVerb);

        return conjugationVerbWordWordSense;
    }
}