package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.DeckWordTranslation;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import com.antonio.apprendrebackend.service.repository.DeckWordTranslationRespository;
import com.antonio.apprendrebackend.service.service.impl.DeckWordTranslationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class DeckWordTranslationServiceImplTest {
    @Mock
    private DeckWordTranslationRespository deckWordTranslationRespository;

    @InjectMocks
    private DeckWordTranslationServiceImpl deckWordTranslationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWordTranslationsByPhraseIdAndDeckId_ReturnsTranslations() {
        // Given
        int phraseId = 1;
        int deckId = 1;

        DeckWordTranslation deckWordTranslation = new DeckWordTranslation();
        WordTranslation wordTranslation = new WordTranslation();
        wordTranslation.setId(1);
        deckWordTranslation.setWordTranslation(wordTranslation);

        when(deckWordTranslationRespository.findDeckWordTranslationsByPhraseIdAndDeckId(phraseId, deckId))
                .thenReturn(List.of(deckWordTranslation));

        // When
        List<WordTranslation> result = deckWordTranslationService.getWordTranslationsByPhraseIdAndDeckId(phraseId, deckId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(wordTranslation, result.get(0));

        verify(deckWordTranslationRespository, times(1))
                .findDeckWordTranslationsByPhraseIdAndDeckId(phraseId, deckId);
    }

    @Test
    void testGetWordTranslationsByPhraseIdAndDeckId_ReturnsEmptyListWhenNoTranslationsFound() {
        // Given
        int phraseId = 1;
        int deckId = 1;

        when(deckWordTranslationRespository.findDeckWordTranslationsByPhraseIdAndDeckId(phraseId, deckId))
                .thenReturn(Collections.emptyList());

        // When
        List<WordTranslation> result = deckWordTranslationService.getWordTranslationsByPhraseIdAndDeckId(phraseId, deckId);

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());

        verify(deckWordTranslationRespository, times(1))
                .findDeckWordTranslationsByPhraseIdAndDeckId(phraseId, deckId);
    }
}
