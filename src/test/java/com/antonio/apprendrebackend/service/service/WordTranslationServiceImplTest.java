package com.antonio.apprendrebackend.service.service;


import com.antonio.apprendrebackend.service.dto.PhraseTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationWithPhraseTranslationsDTO;
import com.antonio.apprendrebackend.service.exception.WordTranslationNotFoundException;
import com.antonio.apprendrebackend.service.mapper.PhraseTranslationMapper;
import com.antonio.apprendrebackend.service.mapper.WordTranslationMapper;
import com.antonio.apprendrebackend.service.model.PhraseTranslation;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import com.antonio.apprendrebackend.service.repository.WordTranslationRepository;
import com.antonio.apprendrebackend.service.service.impl.WordTranslationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class WordTranslationServiceImplTest {
    @Mock
    private WordTranslationRepository wordTranslationRepository;

    @Mock
    private WordPhraseTranslationService wordPhraseTranslationService;

    @Mock
    private WordTranslationMapper wordTranslationMapper;

    @Mock
    private PhraseTranslationMapper phraseTranslationMapper;

    @Mock
    private DeckUserWordPhraseTranslationService deckUserWordPhraseTranslationService;

    @InjectMocks
    private WordTranslationServiceImpl wordTranslationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllWordTranslationsWithPhrasesByDeck() {
        // Given
        Integer deckId = 10;

        WordTranslation wordTranslation1 = new WordTranslation();
        wordTranslation1.setId(101);

        WordTranslation wordTranslation2 = new WordTranslation();
        wordTranslation2.setId(102);

        List<WordTranslation> wordTranslations = Arrays.asList(wordTranslation1, wordTranslation2);

        WordTranslationDTO wordTranslationDTO1 = new WordTranslationDTO();
        wordTranslationDTO1.setId(101);

        WordTranslationDTO wordTranslationDTO2 = new WordTranslationDTO();
        wordTranslationDTO2.setId(102);

        PhraseTranslation phraseTranslation1 = new PhraseTranslation();
        phraseTranslation1.setId(201);

        PhraseTranslation phraseTranslation2 = new PhraseTranslation();
        phraseTranslation2.setId(202);

        List<PhraseTranslation> phrasesForWord1 = Arrays.asList(phraseTranslation1);
        List<PhraseTranslation> phrasesForWord2 = Arrays.asList(phraseTranslation2);

        PhraseTranslationDTO phraseTranslationDTO1 = new PhraseTranslationDTO();
        phraseTranslationDTO1.setId(201);

        PhraseTranslationDTO phraseTranslationDTO2 = new PhraseTranslationDTO();
        phraseTranslationDTO2.setId(202);

        // When
        when(deckUserWordPhraseTranslationService.getWordTranslationsByDeckId(deckId))
                .thenReturn(wordTranslations);

        when(wordPhraseTranslationService.getPhrasesByDeckIdAndWordTranslationId(eq(deckId), eq(101)))
                .thenReturn(phrasesForWord1);
        when(wordPhraseTranslationService.getPhrasesByDeckIdAndWordTranslationId(eq(deckId), eq(102)))
                .thenReturn(phrasesForWord2);

        when(wordTranslationMapper.toDTO(wordTranslation1)).thenReturn(wordTranslationDTO1);
        when(wordTranslationMapper.toDTO(wordTranslation2)).thenReturn(wordTranslationDTO2);

        when(phraseTranslationMapper.toDTO(phraseTranslation1)).thenReturn(phraseTranslationDTO1);
        when(phraseTranslationMapper.toDTO(phraseTranslation2)).thenReturn(phraseTranslationDTO2);

        List<WordTranslationWithPhraseTranslationsDTO> result = wordTranslationService.getAllWordTranslationsWithPhrasesByDeck(deckId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(101, result.get(0).getWordTranslation().getId());
        assertEquals(1, result.get(0).getPhraseTranslations().size());
        assertEquals(201, result.get(0).getPhraseTranslations().get(0).getId());

        assertEquals(102, result.get(1).getWordTranslation().getId());
        assertEquals(1, result.get(1).getPhraseTranslations().size());
        assertEquals(202, result.get(1).getPhraseTranslations().get(0).getId());

        verify(deckUserWordPhraseTranslationService, times(1)).getWordTranslationsByDeckId(deckId);
        verify(wordPhraseTranslationService, times(1)).getPhrasesByDeckIdAndWordTranslationId(deckId, 101);
        verify(wordPhraseTranslationService, times(1)).getPhrasesByDeckIdAndWordTranslationId(deckId, 102);
    }

    @Test
    void testGetAllWordTranslationsWithPhrasesByDeckEmpty() {
        // Given
        Integer deckId = 10;
        List<WordTranslation> emptyList = new ArrayList<>();

        // When
        when(deckUserWordPhraseTranslationService.getWordTranslationsByDeckId(deckId))
                .thenReturn(emptyList);

        // Then
        WordTranslationNotFoundException exception = assertThrows(WordTranslationNotFoundException.class, () -> {
            wordTranslationService.getAllWordTranslationsWithPhrasesByDeck(deckId);
        });

        assertEquals("Not found any wordTranslation", exception.getMessage());
        verify(deckUserWordPhraseTranslationService, times(1)).getWordTranslationsByDeckId(deckId);
        verify(wordPhraseTranslationService, never()).getPhrasesByDeckIdAndWordTranslationId(anyInt(), anyInt());
    }

    @Test
    void testGetAllWordTranslations() {
        // Given
        Integer pageNumber = 0;
        Integer pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        WordTranslation wordTranslation1 = new WordTranslation();
        wordTranslation1.setId(101);

        WordTranslation wordTranslation2 = new WordTranslation();
        wordTranslation2.setId(102);

        List<WordTranslation> wordTranslations = Arrays.asList(wordTranslation1, wordTranslation2);
        Page<WordTranslation> wordTranslationPage = new PageImpl<>(wordTranslations);

        WordTranslationDTO wordTranslationDTO1 = new WordTranslationDTO();
        wordTranslationDTO1.setId(101);

        WordTranslationDTO wordTranslationDTO2 = new WordTranslationDTO();
        wordTranslationDTO2.setId(102);

        // When
        when(wordTranslationRepository.findAll(pageable)).thenReturn(wordTranslationPage);
        when(wordTranslationMapper.toDTO(wordTranslation1)).thenReturn(wordTranslationDTO1);
        when(wordTranslationMapper.toDTO(wordTranslation2)).thenReturn(wordTranslationDTO2);

        List<WordTranslationDTO> result = wordTranslationService.getAllWordTranslations(pageNumber, pageSize);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(101, result.get(0).getId());
        assertEquals(102, result.get(1).getId());

        verify(wordTranslationRepository, times(1)).findAll(pageable);
        verify(wordTranslationMapper, times(1)).toDTO(wordTranslation1);
        verify(wordTranslationMapper, times(1)).toDTO(wordTranslation2);
    }

    @Test
    void testGetAllWordTranslationsEmpty() {
        // Given
        Integer pageNumber = 0;
        Integer pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<WordTranslation> emptyList = new ArrayList<>();
        Page<WordTranslation> emptyPage = new PageImpl<>(emptyList);

        // When
        when(wordTranslationRepository.findAll(pageable)).thenReturn(emptyPage);

        // Then
        WordTranslationNotFoundException exception = assertThrows(WordTranslationNotFoundException.class, () -> {
            wordTranslationService.getAllWordTranslations(pageNumber, pageSize);
        });

        assertEquals("Not found any phrase", exception.getMessage());
        verify(wordTranslationRepository, times(1)).findAll(pageable);
        verify(wordTranslationMapper, never()).toDTO(any());
    }
}