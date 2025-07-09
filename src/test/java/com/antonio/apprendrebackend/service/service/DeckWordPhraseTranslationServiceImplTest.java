package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.DeckWordPhraseTranslationNotFoundException;
import com.antonio.apprendrebackend.service.model.DeckWordPhraseTranslation;
import com.antonio.apprendrebackend.service.model.PhraseTranslation;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import com.antonio.apprendrebackend.service.repository.DeckWordPhraseTranslationRespository;
import com.antonio.apprendrebackend.service.service.impl.DeckWordPhraseTranslationServiceImpl;
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

public class DeckWordPhraseTranslationServiceImplTest {
    @InjectMocks
    private DeckWordPhraseTranslationServiceImpl deckWordPhraseTranslationService;

    @Mock
    private DeckWordPhraseTranslationRespository deckWordPhraseTranslationRespository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRandomUserDeckWordPhraseTranslationWithByUser_ReturnsTranslation() {
        // Given
        Integer userId = 1;
        DeckWordPhraseTranslation mockTranslation = new DeckWordPhraseTranslation();
        when(deckWordPhraseTranslationRespository.findRandomUserDeckWordPhraseTranslationWithByUser(userId))
                .thenReturn(Optional.of(mockTranslation));

        // When
        DeckWordPhraseTranslation result = deckWordPhraseTranslationService
                .getRandomUserDeckWordPhraseTranslationWithByUser(userId);

        // Then
        assertNotNull(result);
        assertEquals(mockTranslation, result);
        verify(deckWordPhraseTranslationRespository, times(1))
                .findRandomUserDeckWordPhraseTranslationWithByUser(userId);
    }

    @Test
    void testGetRandomUserDeckWordPhraseTranslationWithByUser_ThrowsExceptionWhenNotFound() {
        // Given
        Integer userId = 1;
        when(deckWordPhraseTranslationRespository.findRandomUserDeckWordPhraseTranslationWithByUser(userId))
                .thenReturn(Optional.empty());

        // When / Then
        DeckWordPhraseTranslationNotFoundException exception = assertThrows(
                DeckWordPhraseTranslationNotFoundException.class,
                () -> deckWordPhraseTranslationService.getRandomUserDeckWordPhraseTranslationWithByUser(userId)
        );

        assertEquals("Word not found for deck", exception.getMessage());
        verify(deckWordPhraseTranslationRespository, times(1))
                .findRandomUserDeckWordPhraseTranslationWithByUser(userId);
    }

    @Test
    void testGetRandomUserDeckWordPhraseTranslationWithByDeckAndUser_ReturnsTranslation() {
        // Given
        Integer deckId = 1;
        Integer userId = 1;
        DeckWordPhraseTranslation mockTranslation = new DeckWordPhraseTranslation();
        when(deckWordPhraseTranslationRespository.findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId, userId))
                .thenReturn(Optional.of(mockTranslation));

        // When
        DeckWordPhraseTranslation result = deckWordPhraseTranslationService
                .getRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId, userId);

        // Then
        assertNotNull(result);
        assertEquals(mockTranslation, result);
        verify(deckWordPhraseTranslationRespository, times(1))
                .findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId, userId);
    }

    @Test
    void testGetRandomUserDeckWordPhraseTranslationWithByDeckAndUser_ThrowsExceptionWhenNotFound() {
        // Given
        Integer deckId = 1;
        Integer userId = 1;
        when(deckWordPhraseTranslationRespository.findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId, userId))
                .thenReturn(Optional.empty());

        // When / Then
        DeckWordPhraseTranslationNotFoundException exception = assertThrows(
                DeckWordPhraseTranslationNotFoundException.class,
                () -> deckWordPhraseTranslationService.getRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId, userId)
        );

        assertEquals("Word not found for deck", exception.getMessage());
        verify(deckWordPhraseTranslationRespository, times(1))
                .findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId, userId);
    }

    @Test
    void testGetByDeckIdAndWordPhraseTranslationId_ReturnsTranslation() {
        // Given
        Integer deckId = 1;
        Integer wordPhraseTranslationId = 2;
        DeckWordPhraseTranslation mockTranslation = new DeckWordPhraseTranslation();
        when(deckWordPhraseTranslationRespository.findByDeckIdAndWordPhraseTranslationId(deckId, wordPhraseTranslationId))
                .thenReturn(Optional.of(mockTranslation));

        // When
        DeckWordPhraseTranslation result = deckWordPhraseTranslationService
                .getByDeckIdAndWordPhraseTranslationId(deckId, wordPhraseTranslationId);

        // Then
        assertNotNull(result);
        assertEquals(mockTranslation, result);
        verify(deckWordPhraseTranslationRespository, times(1))
                .findByDeckIdAndWordPhraseTranslationId(deckId, wordPhraseTranslationId);
    }

    @Test
    void testGetByDeckIdAndWordPhraseTranslationId_ThrowsExceptionWhenNotFound() {
        // Given
        Integer deckId = 1;
        Integer wordPhraseTranslationId = 2;
        when(deckWordPhraseTranslationRespository.findByDeckIdAndWordPhraseTranslationId(deckId, wordPhraseTranslationId))
                .thenReturn(Optional.empty());

        // When / Then
        DeckWordPhraseTranslationNotFoundException exception = assertThrows(
                DeckWordPhraseTranslationNotFoundException.class,
                () -> deckWordPhraseTranslationService.getByDeckIdAndWordPhraseTranslationId(deckId, wordPhraseTranslationId)
        );

        assertEquals("DeckWordPhraseTranslation not found for deckId 1 and wordPhraseTranslationId 2", exception.getMessage());
        verify(deckWordPhraseTranslationRespository, times(1))
                .findByDeckIdAndWordPhraseTranslationId(deckId, wordPhraseTranslationId);
    }

    @Test
    void testGetWordTranslationsByDeckId_ReturnsTranslations() {
        // Given
        Integer deckId = 1;
        WordTranslation mockWordTranslation1 = new WordTranslation();
        WordTranslation mockWordTranslation2 = new WordTranslation();
        List<WordTranslation> mockWordTranslations = Arrays.asList(mockWordTranslation1, mockWordTranslation2);

        when(deckWordPhraseTranslationRespository.findWordTranslationsByDeckId(deckId))
                .thenReturn(mockWordTranslations);

        // When
        List<WordTranslation> result = deckWordPhraseTranslationService.getWordTranslationsByDeckId(deckId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(deckWordPhraseTranslationRespository, times(1)).findWordTranslationsByDeckId(deckId);
    }

    @Test
    void testGetWordTranslationsByDeckId_ReturnsEmptyList() {
        // Given
        Integer deckId = 1;
        when(deckWordPhraseTranslationRespository.findWordTranslationsByDeckId(deckId))
                .thenReturn(List.of());

        // When
        List<WordTranslation> result = deckWordPhraseTranslationService.getWordTranslationsByDeckId(deckId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(deckWordPhraseTranslationRespository, times(1)).findWordTranslationsByDeckId(deckId);
    }

    @Test
    void testGetPhraseTranslationsByDeckId_ReturnsTranslations() {
        // Given
        Integer deckId = 1;
        PhraseTranslation mockPhraseTranslation1 = new PhraseTranslation();
        PhraseTranslation mockPhraseTranslation2 = new PhraseTranslation();
        List<PhraseTranslation> mockPhraseTranslations = Arrays.asList(mockPhraseTranslation1, mockPhraseTranslation2);

        when(deckWordPhraseTranslationRespository.findPhraseTranslationsByDeckId(deckId))
                .thenReturn(mockPhraseTranslations);

        // When
        List<PhraseTranslation> result = deckWordPhraseTranslationService.getPhraseTranslationsByDeckId(deckId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(deckWordPhraseTranslationRespository, times(1)).findPhraseTranslationsByDeckId(deckId);
    }

    @Test
    void testGetPhraseTranslationsByDeckId_ReturnsEmptyList() {
        // Given
        Integer deckId = 1;
        when(deckWordPhraseTranslationRespository.findPhraseTranslationsByDeckId(deckId))
                .thenReturn(List.of());

        // When
        List<PhraseTranslation> result = deckWordPhraseTranslationService.getPhraseTranslationsByDeckId(deckId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(deckWordPhraseTranslationRespository, times(1)).findPhraseTranslationsByDeckId(deckId);
    }


}
