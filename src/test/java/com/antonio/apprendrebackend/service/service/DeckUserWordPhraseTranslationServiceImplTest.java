package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.DeckUserWordPhraseTranslationNotFoundException;
import com.antonio.apprendrebackend.service.model.DeckUserWordPhraseTranslation;
import com.antonio.apprendrebackend.service.model.PhraseTranslation;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import com.antonio.apprendrebackend.service.repository.DeckUserWordPhraseTranslationRespository;
import com.antonio.apprendrebackend.service.service.impl.DeckUserWordPhraseTranslationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeckUserWordPhraseTranslationServiceImplTest {
    @InjectMocks
    private DeckUserWordPhraseTranslationServiceImpl deckUserWordPhraseTranslationService;

    @Mock
    private DeckUserWordPhraseTranslationRespository deckUserWordPhraseTranslationRespository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRandomUserDeckWordPhraseTranslationWithByUser_ReturnsTranslation() {
        // Given
        Integer userId = 1;
        DeckUserWordPhraseTranslation mockTranslation = new DeckUserWordPhraseTranslation();
        when(deckUserWordPhraseTranslationRespository.findRandomUserDeckWordPhraseTranslationWithByUser(userId))
                .thenReturn(Optional.of(mockTranslation));

        // When
        DeckUserWordPhraseTranslation result = deckUserWordPhraseTranslationService
                .getRandomUserDeckWordPhraseTranslationWithByUser(userId);

        // Then
        assertNotNull(result);
        assertEquals(mockTranslation, result);
        verify(deckUserWordPhraseTranslationRespository, times(1))
                .findRandomUserDeckWordPhraseTranslationWithByUser(userId);
    }

    @Test
    void testGetRandomUserDeckWordPhraseTranslationWithByUser_ThrowsExceptionWhenNotFound() {
        // Given
        Integer userId = 1;
        when(deckUserWordPhraseTranslationRespository.findRandomUserDeckWordPhraseTranslationWithByUser(userId))
                .thenReturn(Optional.empty());

        // When / Then
        DeckUserWordPhraseTranslationNotFoundException exception = assertThrows(
                DeckUserWordPhraseTranslationNotFoundException.class,
                () -> deckUserWordPhraseTranslationService.getRandomUserDeckWordPhraseTranslationWithByUser(userId)
        );

        assertEquals("Word not found for deck", exception.getMessage());
        verify(deckUserWordPhraseTranslationRespository, times(1))
                .findRandomUserDeckWordPhraseTranslationWithByUser(userId);
    }

    @Test
    void testGetRandomUserDeckWordPhraseTranslationWithByDeckAndUser_ReturnsTranslation() {
        // Given
        Integer deckId = 1;
        Integer userId = 1;
        DeckUserWordPhraseTranslation mockTranslation = new DeckUserWordPhraseTranslation();
        when(deckUserWordPhraseTranslationRespository.findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId, userId))
                .thenReturn(Optional.of(mockTranslation));

        // When
        DeckUserWordPhraseTranslation result = deckUserWordPhraseTranslationService
                .getRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId, userId);

        // Then
        assertNotNull(result);
        assertEquals(mockTranslation, result);
        verify(deckUserWordPhraseTranslationRespository, times(1))
                .findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId, userId);
    }

    @Test
    void testGetRandomUserDeckWordPhraseTranslationWithByDeckAndUser_ThrowsExceptionWhenNotFound() {
        // Given
        Integer deckId = 1;
        Integer userId = 1;
        when(deckUserWordPhraseTranslationRespository.findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId, userId))
                .thenReturn(Optional.empty());

        // When / Then
        DeckUserWordPhraseTranslationNotFoundException exception = assertThrows(
                DeckUserWordPhraseTranslationNotFoundException.class,
                () -> deckUserWordPhraseTranslationService.getRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId, userId)
        );

        assertEquals("Word not found for deck", exception.getMessage());
        verify(deckUserWordPhraseTranslationRespository, times(1))
                .findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId, userId);
    }

    @Test
    void testGetByDeckUserIdAndWordPhraseTranslationId_ReturnsTranslation() {
        // Given
        Integer deckId = 1;
        Integer wordPhraseTranslationId = 2;
        DeckUserWordPhraseTranslation mockTranslation = new DeckUserWordPhraseTranslation();
        when(deckUserWordPhraseTranslationRespository.findByDeckUserIdAndWordPhraseTranslationId(deckId, wordPhraseTranslationId))
                .thenReturn(Optional.of(mockTranslation));

        // When
        DeckUserWordPhraseTranslation result = deckUserWordPhraseTranslationService
                .getByDeckUserIdAndWordPhraseTranslationId(deckId, wordPhraseTranslationId);

        // Then
        assertNotNull(result);
        assertEquals(mockTranslation, result);
        verify(deckUserWordPhraseTranslationRespository, times(1))
                .findByDeckUserIdAndWordPhraseTranslationId(deckId, wordPhraseTranslationId);
    }

    @Test
    void testGetByDeckUserIdAndWordPhraseTranslationId_ThrowsExceptionWhenNotFound() {
        // Given
        Integer deckId = 1;
        Integer wordPhraseTranslationId = 2;
        when(deckUserWordPhraseTranslationRespository.findByDeckUserIdAndWordPhraseTranslationId(deckId, wordPhraseTranslationId))
                .thenReturn(Optional.empty());

        // When / Then
        DeckUserWordPhraseTranslationNotFoundException exception = assertThrows(
                DeckUserWordPhraseTranslationNotFoundException.class,
                () -> deckUserWordPhraseTranslationService.getByDeckUserIdAndWordPhraseTranslationId(deckId, wordPhraseTranslationId)
        );

        assertEquals("DeckUserWordPhraseTranslation not found for deckId 1 and wordPhraseTranslationId 2", exception.getMessage());
        verify(deckUserWordPhraseTranslationRespository, times(1))
                .findByDeckUserIdAndWordPhraseTranslationId(deckId, wordPhraseTranslationId);
    }

    @Test
    void testGetWordTranslationsByDeckId_ReturnsTranslations() {
        // Given
        Integer deckId = 1;
        WordTranslation mockWordTranslation1 = new WordTranslation();
        WordTranslation mockWordTranslation2 = new WordTranslation();
        List<WordTranslation> mockWordTranslations = Arrays.asList(mockWordTranslation1, mockWordTranslation2);

        when(deckUserWordPhraseTranslationRespository.findWordTranslationsByDeckId(deckId))
                .thenReturn(mockWordTranslations);

        // When
        List<WordTranslation> result = deckUserWordPhraseTranslationService.getWordTranslationsByDeckId(deckId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(deckUserWordPhraseTranslationRespository, times(1)).findWordTranslationsByDeckId(deckId);
    }

    @Test
    void testGetWordTranslationsByDeckId_ReturnsEmptyList() {
        // Given
        Integer deckId = 1;
        when(deckUserWordPhraseTranslationRespository.findWordTranslationsByDeckId(deckId))
                .thenReturn(List.of());

        // When
        List<WordTranslation> result = deckUserWordPhraseTranslationService.getWordTranslationsByDeckId(deckId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(deckUserWordPhraseTranslationRespository, times(1)).findWordTranslationsByDeckId(deckId);
    }

    @Test
    void testGetPhraseTranslationsByDeckId_ReturnsTranslations() {
        // Given
        Integer deckId = 1;
        PhraseTranslation mockPhraseTranslation1 = new PhraseTranslation();
        PhraseTranslation mockPhraseTranslation2 = new PhraseTranslation();
        List<PhraseTranslation> mockPhraseTranslations = Arrays.asList(mockPhraseTranslation1, mockPhraseTranslation2);

        when(deckUserWordPhraseTranslationRespository.findPhraseTranslationsByDeckId(deckId))
                .thenReturn(mockPhraseTranslations);

        // When
        List<PhraseTranslation> result = deckUserWordPhraseTranslationService.getPhraseTranslationsByDeckId(deckId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(deckUserWordPhraseTranslationRespository, times(1)).findPhraseTranslationsByDeckId(deckId);
    }

    @Test
    void testGetPhraseTranslationsByDeckId_ReturnsEmptyList() {
        // Given
        Integer deckId = 1;
        when(deckUserWordPhraseTranslationRespository.findPhraseTranslationsByDeckId(deckId))
                .thenReturn(List.of());

        // When
        List<PhraseTranslation> result = deckUserWordPhraseTranslationService.getPhraseTranslationsByDeckId(deckId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(deckUserWordPhraseTranslationRespository, times(1)).findPhraseTranslationsByDeckId(deckId);
    }


}
