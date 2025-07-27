package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.WordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.exception.WordPhraseTranslationNotFoundException;
import com.antonio.apprendrebackend.service.mapper.WordPhraseTranslationMapper;
import com.antonio.apprendrebackend.service.model.PhraseTranslation;
import com.antonio.apprendrebackend.service.model.WordPhraseTranslation;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import com.antonio.apprendrebackend.service.repository.WordPhraseTranslationRepository;
import com.antonio.apprendrebackend.service.service.impl.WordPhraseTranslationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WordPhraseTranslationServiceImplTest {
    @InjectMocks
    private WordPhraseTranslationServiceImpl wordPhraseTranslationService;

    @Mock
    private WordPhraseTranslationMapper wordPhraseTranslationMapper;

    @Mock
    private WordPhraseTranslationRepository wordPhraseTranslationRepository;

    private PhraseTranslation phraseTranslation1;
    private PhraseTranslation phraseTranslation2;
    private WordTranslation wordTranslation1;
    private WordTranslation wordTranslation2;
    private WordPhraseTranslation wordPhraseTranslation1;
    private WordPhraseTranslation wordPhraseTranslation2;
    private WordPhraseTranslationDTO wordPhraseTranslationDTO1;
    private WordPhraseTranslationDTO wordPhraseTranslationDTO2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup PhraseTranslation objects
        phraseTranslation1 = new PhraseTranslation();
        phraseTranslation1.setId(1);

        phraseTranslation2 = new PhraseTranslation();
        phraseTranslation2.setId(2);

        // Setup WordTranslation objects
        wordTranslation1 = new WordTranslation();
        wordTranslation1.setId(101);

        wordTranslation2 = new WordTranslation();
        wordTranslation2.setId(102);

        // Setup WordPhraseTranslation objects
        wordPhraseTranslation1 = new WordPhraseTranslation();
        wordPhraseTranslation1.setId(1001);
        wordPhraseTranslation1.setWordTranslation(wordTranslation1);

        wordPhraseTranslation2 = new WordPhraseTranslation();
        wordPhraseTranslation2.setId(1002);
        wordPhraseTranslation2.setWordTranslation(wordTranslation2);

        // Setup WordPhraseTranslationDTO objects
        wordPhraseTranslationDTO1 = new WordPhraseTranslationDTO();
        wordPhraseTranslationDTO1.setId(1001);

        wordPhraseTranslationDTO2 = new WordPhraseTranslationDTO();
        wordPhraseTranslationDTO2.setId(1002);
    }

    @Test
    void testGetPhrasesByDeckIdAndWordTranslationId_ReturnsListOfPhrases() {
        // Given
        Integer deckId = 1;
        Integer wordTranslationId = 101;
        List<PhraseTranslation> expectedPhrases = Arrays.asList(phraseTranslation1, phraseTranslation2);

        when(wordPhraseTranslationRepository.findPhrasesByDeckIdAndWordTranslationId(deckId, wordTranslationId))
                .thenReturn(expectedPhrases);

        // When
        List<PhraseTranslation> result = wordPhraseTranslationService
                .getPhrasesByDeckIdAndWordTranslationId(deckId, wordTranslationId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(phraseTranslation1.getId(), result.get(0).getId());
        assertEquals(phraseTranslation2.getId(), result.get(1).getId());
        verify(wordPhraseTranslationRepository, times(1))
                .findPhrasesByDeckIdAndWordTranslationId(deckId, wordTranslationId);
    }

    @Test
    void testGetPhrasesByDeckIdAndWordTranslationId_ReturnsEmptyList() {
        // Given
        Integer deckId = 1;
        Integer wordTranslationId = 999;

        when(wordPhraseTranslationRepository.findPhrasesByDeckIdAndWordTranslationId(deckId, wordTranslationId))
                .thenReturn(Collections.emptyList());

        // When
        List<PhraseTranslation> result = wordPhraseTranslationService
                .getPhrasesByDeckIdAndWordTranslationId(deckId, wordTranslationId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(wordPhraseTranslationRepository, times(1))
                .findPhrasesByDeckIdAndWordTranslationId(deckId, wordTranslationId);
    }

    @Test
    void testGetWordTranslationsByDeckIdPhraseTranslationId_ReturnsListOfWordTranslations() {
        // Given
        Integer deckId = 1;
        Integer phraseTranslationId = 1;
        List<WordTranslation> expectedWordTranslations = Arrays.asList(wordTranslation1, wordTranslation2);

        when(wordPhraseTranslationRepository.findWordTranslationsByDeckIdPhraseTranslationId(deckId, phraseTranslationId))
                .thenReturn(expectedWordTranslations);

        // When
        List<WordTranslation> result = wordPhraseTranslationService
                .getWordTranslationsByDeckIdPhraseTranslationId(deckId, phraseTranslationId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(wordTranslation1.getId(), result.get(0).getId());
        assertEquals(wordTranslation2.getId(), result.get(1).getId());
        verify(wordPhraseTranslationRepository, times(1))
                .findWordTranslationsByDeckIdPhraseTranslationId(deckId, phraseTranslationId);
    }

    @Test
    void testGetWordTranslationsByDeckIdPhraseTranslationId_ReturnsEmptyList() {
        // Given
        Integer deckId = 1;
        Integer phraseTranslationId = 999;

        when(wordPhraseTranslationRepository.findWordTranslationsByDeckIdPhraseTranslationId(deckId, phraseTranslationId))
                .thenReturn(Collections.emptyList());

        // When
        List<WordTranslation> result = wordPhraseTranslationService
                .getWordTranslationsByDeckIdPhraseTranslationId(deckId, phraseTranslationId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(wordPhraseTranslationRepository, times(1))
                .findWordTranslationsByDeckIdPhraseTranslationId(deckId, phraseTranslationId);
    }

    @Test
    void testGetAllWordPhraseTranslationByWordSense_ReturnsListOfDTOs() {
        // Given
        List<Integer> senseIds = Arrays.asList(201, 202, 203);
        List<WordPhraseTranslation> wordPhraseTranslations = Arrays.asList(wordPhraseTranslation1, wordPhraseTranslation2);

        when(wordPhraseTranslationRepository.findByWordSenseIds(senseIds))
                .thenReturn(wordPhraseTranslations);
        when(wordPhraseTranslationMapper.toDTO(wordPhraseTranslation1))
                .thenReturn(wordPhraseTranslationDTO1);
        when(wordPhraseTranslationMapper.toDTO(wordPhraseTranslation2))
                .thenReturn(wordPhraseTranslationDTO2);

        // When
        List<WordPhraseTranslationDTO> result = wordPhraseTranslationService
                .getAllWordPhraseTranslationByWordSense(senseIds);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(wordPhraseTranslationDTO1.getId(), result.get(0).getId());
        assertEquals(wordPhraseTranslationDTO2.getId(), result.get(1).getId());
        verify(wordPhraseTranslationRepository, times(1)).findByWordSenseIds(senseIds);
        verify(wordPhraseTranslationMapper, times(2)).toDTO(any(WordPhraseTranslation.class));
    }

    @Test
    void testGetAllWordPhraseTranslationByWordSense_ReturnsEmptyList() {
        // Given
        List<Integer> senseIds = Arrays.asList(999, 998);

        when(wordPhraseTranslationRepository.findByWordSenseIds(senseIds))
                .thenReturn(Collections.emptyList());

        // When
        List<WordPhraseTranslationDTO> result = wordPhraseTranslationService
                .getAllWordPhraseTranslationByWordSense(senseIds);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(wordPhraseTranslationRepository, times(1)).findByWordSenseIds(senseIds);
        verify(wordPhraseTranslationMapper, never()).toDTO(any(WordPhraseTranslation.class));
    }

    @Test
    void testGetAllWordPhraseTranslationByWordSense_WithEmptyInputList() {
        // Given
        List<Integer> senseIds = Collections.emptyList();

        when(wordPhraseTranslationRepository.findByWordSenseIds(senseIds))
                .thenReturn(Collections.emptyList());

        // When
        List<WordPhraseTranslationDTO> result = wordPhraseTranslationService
                .getAllWordPhraseTranslationByWordSense(senseIds);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(wordPhraseTranslationRepository, times(1)).findByWordSenseIds(senseIds);
        verify(wordPhraseTranslationMapper, never()).toDTO(any(WordPhraseTranslation.class));
    }

    @Test
    void testGetWordPhraseTranslationById_ReturnsWordPhraseTranslation() {
        // Given
        Integer wordPhraseTranslationId = 1001;

        when(wordPhraseTranslationRepository.findById(wordPhraseTranslationId))
                .thenReturn(Optional.of(wordPhraseTranslation1));

        // When
        WordPhraseTranslation result = wordPhraseTranslationService
                .getWordPhraseTranslationById(wordPhraseTranslationId);

        // Then
        assertNotNull(result);
        assertEquals(wordPhraseTranslation1.getId(), result.getId());
        assertEquals(wordPhraseTranslation1.getWordTranslation().getId(), result.getWordTranslation().getId());
        verify(wordPhraseTranslationRepository, times(1)).findById(wordPhraseTranslationId);
    }

    @Test
    void testGetWordPhraseTranslationById_ThrowsExceptionWhenNotFound() {
        // Given
        Integer wordPhraseTranslationId = 999;

        when(wordPhraseTranslationRepository.findById(wordPhraseTranslationId))
                .thenReturn(Optional.empty());

        // When / Then
        WordPhraseTranslationNotFoundException exception = assertThrows(
                WordPhraseTranslationNotFoundException.class,
                () -> wordPhraseTranslationService.getWordPhraseTranslationById(wordPhraseTranslationId)
        );

        assertEquals("Not found any wordPhraseTranslation with id: 999", exception.getMessage());
        verify(wordPhraseTranslationRepository, times(1)).findById(wordPhraseTranslationId);
    }

    @Test
    void testGetWordPhraseTranslationById_WithNullId() {
        // Given
        Integer wordPhraseTranslationId = null;

        when(wordPhraseTranslationRepository.findById(null))
                .thenReturn(Optional.empty());

        // When / Then
        WordPhraseTranslationNotFoundException exception = assertThrows(
                WordPhraseTranslationNotFoundException.class,
                () -> wordPhraseTranslationService.getWordPhraseTranslationById(wordPhraseTranslationId)
        );

        assertEquals("Not found any wordPhraseTranslation with id: null", exception.getMessage());
        verify(wordPhraseTranslationRepository, times(1)).findById(null);
    }

    @Test
    void testGetAllWordPhraseTranslationByWordSense_WithSingleElement() {
        // Given
        List<Integer> senseIds = Arrays.asList(201);
        List<WordPhraseTranslation> wordPhraseTranslations = Arrays.asList(wordPhraseTranslation1);

        when(wordPhraseTranslationRepository.findByWordSenseIds(senseIds))
                .thenReturn(wordPhraseTranslations);
        when(wordPhraseTranslationMapper.toDTO(wordPhraseTranslation1))
                .thenReturn(wordPhraseTranslationDTO1);

        // When
        List<WordPhraseTranslationDTO> result = wordPhraseTranslationService
                .getAllWordPhraseTranslationByWordSense(senseIds);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(wordPhraseTranslationDTO1.getId(), result.get(0).getId());
        verify(wordPhraseTranslationRepository, times(1)).findByWordSenseIds(senseIds);
        verify(wordPhraseTranslationMapper, times(1)).toDTO(wordPhraseTranslation1);
    }
}
