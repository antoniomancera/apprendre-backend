package com.antonio.apprendrebackend.service.service;


import com.antonio.apprendrebackend.service.dto.PhraseDTO;
import com.antonio.apprendrebackend.service.dto.PhraseWithWordTranslationsDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.exception.PhraseNotFoundException;
import com.antonio.apprendrebackend.service.mapper.PhraseMapper;
import com.antonio.apprendrebackend.service.mapper.WordTranslationMapper;
import com.antonio.apprendrebackend.service.model.Phrase;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import com.antonio.apprendrebackend.service.repository.PhraseRepository;
import com.antonio.apprendrebackend.service.service.impl.PhraseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PhraseServiceImplTest {
    @Mock
    private PhraseRepository phraseRepository;

    @Mock
    private DeckWordTranslationService deckWordTranslationService;

    @Mock
    private PhraseMapper phraseMapper;

    @Mock
    private WordTranslationMapper wordTranslationMapper;

    @InjectMocks
    private PhraseServiceImpl phraseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPhrasesWithWordTranslationsByDeck_ReturnsPhrasesWithTranslations() {
        // Given
        int deckId = 1;
        Phrase phrase = new Phrase();
        phrase.setId(1);

        WordTranslation wordTranslation = new WordTranslation();
        wordTranslation.setId(1);

        WordTranslationDTO wordTranslationDTO = new WordTranslationDTO();
        wordTranslationDTO.setId(1);

        PhraseWithWordTranslationsDTO phraseWithWordTranslationsDTO = new PhraseWithWordTranslationsDTO();
        phraseWithWordTranslationsDTO.setPhrase(phraseMapper.toDTO(phrase));
        phraseWithWordTranslationsDTO.setWordTranslations(List.of(wordTranslationDTO));

        when(phraseRepository.findPhrasesByDeckId(deckId)).thenReturn(Optional.of(List.of(phrase)));
        when(deckWordTranslationService.getWordTranslationsByPhraseIdAndDeckId(phrase.getId(), deckId))
                .thenReturn(List.of(wordTranslation));
        when(wordTranslationMapper.toDTO(wordTranslation)).thenReturn(wordTranslationDTO);
        when(phraseMapper.toDTO(phrase)).thenReturn(phraseWithWordTranslationsDTO.getPhrase());

        // When
        List<PhraseWithWordTranslationsDTO> result = phraseService.getAllPhrasesWithWordTranslationsByDeck(deckId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        PhraseWithWordTranslationsDTO resultDTO = result.get(0);
        assertEquals(phraseWithWordTranslationsDTO.getPhrase(), resultDTO.getPhrase());
        assertEquals(phraseWithWordTranslationsDTO.getWordTranslations(), resultDTO.getWordTranslations());

        verify(phraseRepository, times(1)).findPhrasesByDeckId(deckId);
        verify(deckWordTranslationService, times(1)).getWordTranslationsByPhraseIdAndDeckId(phrase.getId(), deckId);
        verify(wordTranslationMapper, times(1)).toDTO(wordTranslation);
        verify(phraseMapper, times(2)).toDTO(phrase);
    }

    @Test
    void testGetAllPhrasesWithWordTranslationsByDeck_ThrowsExceptionWhenNoPhrasesFound() {
        // Given
        int deckId = 1;
        when(phraseRepository.findPhrasesByDeckId(deckId)).thenReturn(Optional.of(Collections.emptyList()));

        // When / Then
        PhraseNotFoundException exception = assertThrows(
                PhraseNotFoundException.class,
                () -> phraseService.getAllPhrasesWithWordTranslationsByDeck(deckId)
        );

        assertEquals(String.format("Not found any phrase of deck %s", deckId), exception.getMessage());
        verify(phraseRepository, times(1)).findPhrasesByDeckId(deckId);
    }

    @Test
    void testGetAllPhrasesWithWordTranslationsByDeck_ThrowsExceptionWhenPhrasesOptionalIsEmpty() {
        // Given
        int deckId = 1;
        when(phraseRepository.findPhrasesByDeckId(deckId)).thenReturn(Optional.empty());

        // When / Then
        PhraseNotFoundException exception = assertThrows(
                PhraseNotFoundException.class,
                () -> phraseService.getAllPhrasesWithWordTranslationsByDeck(deckId)
        );

        assertEquals(String.format("Not found any phrase of deck %s", deckId), exception.getMessage());
        verify(phraseRepository, times(1)).findPhrasesByDeckId(deckId);
    }

    @Test
    void testGetAllPhrases_ReturnsPhrases() {
        // Given
        int pageNumber = 0;
        int pageSize = 10;

        Phrase phrase = new Phrase();
        phrase.setId(1);

        PhraseDTO phraseDTO = new PhraseDTO();
        phraseDTO.setId(1);

        Page<Phrase> phrasePage = new PageImpl<>(List.of(phrase));

        when(phraseRepository.findAll(any(Pageable.class))).thenReturn(phrasePage);
        when(phraseMapper.toDTO(phrase)).thenReturn(phraseDTO);

        // When
        List<PhraseDTO> result = phraseService.getAllPhrases(pageNumber, pageSize);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(phraseDTO, result.get(0));

        verify(phraseRepository, times(1)).findAll(any(Pageable.class));
        verify(phraseMapper, times(1)).toDTO(phrase);
    }

    @Test
    void testGetAllPhrases_ThrowsExceptionWhenNoPhrasesFound() {
        // Given
        int pageNumber = 0;
        int pageSize = 10;

        Page<Phrase> phrasePage = new PageImpl<>(Collections.emptyList());

        when(phraseRepository.findAll(any(Pageable.class))).thenReturn(phrasePage);

        // When / Then
        PhraseNotFoundException exception = assertThrows(
                PhraseNotFoundException.class,
                () -> phraseService.getAllPhrases(pageNumber, pageSize)
        );

        assertEquals("Not found any phrase", exception.getMessage());
        verify(phraseRepository, times(1)).findAll(any(Pageable.class));
    }
}
