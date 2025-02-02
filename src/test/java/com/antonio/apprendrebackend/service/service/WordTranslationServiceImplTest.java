package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.PhraseDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationWithPhrasesDTO;
import com.antonio.apprendrebackend.service.exception.WordTranslationNotFoundException;
import com.antonio.apprendrebackend.service.mapper.PhraseMapper;
import com.antonio.apprendrebackend.service.mapper.WordTranslationMapper;
import com.antonio.apprendrebackend.service.model.DeckWordTranslation;
import com.antonio.apprendrebackend.service.model.DeckWordTranslationHistorial;
import com.antonio.apprendrebackend.service.model.Phrase;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import com.antonio.apprendrebackend.service.repository.DeckWordTranslationHistorialRespository;
import com.antonio.apprendrebackend.service.repository.DeckWordTranslationRespository;
import com.antonio.apprendrebackend.service.repository.PhraseRepository;
import com.antonio.apprendrebackend.service.repository.WordTranslationRepository;
import com.antonio.apprendrebackend.service.service.impl.WordTranslationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WordTranslationServiceImplTest {

    @Mock
    private DeckWordTranslationRespository deckWordTranslationRespository;

    @Mock
    private WordTranslationRepository wordTranslationRepository;

    @Mock
    private WordTranslationMapper wordTranslationMapper;

    @Mock
    private PhraseRepository phraseRepository;

    @Mock
    private DeckWordTranslationHistorialRespository deckWordTranslationHistorialRespository;

    @Mock
    private PhraseService phraseService;

    @Mock
    private PhraseMapper phraseMapper;

    @InjectMocks
    private WordTranslationServiceImpl wordTranslationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRandomWordTranslation_WithDeckId_ReturnsTranslation() {
        // Given
        int deckId = 1;
        DeckWordTranslation mockDeckWordTranslation = mock(DeckWordTranslation.class);
        WordTranslation mockWordTranslation = mock(WordTranslation.class);
        WordTranslationDTO mockDto = mock(WordTranslationDTO.class);

        when(deckWordTranslationRespository.findRandomDeckWordTranslationWithByDeck(deckId))
                .thenReturn(Optional.of(mockDeckWordTranslation));
        when(mockDeckWordTranslation.getWordTranslation()).thenReturn(mockWordTranslation);
        when(wordTranslationMapper.toDTO(mockWordTranslation)).thenReturn(mockDto);

        // When
        WordTranslationDTO result = wordTranslationService.getRandomWordTranslation(deckId);

        // Then
        assertNotNull(result);
        assertEquals(mockDto, result);
        verify(deckWordTranslationRespository, times(1)).findRandomDeckWordTranslationWithByDeck(deckId);
        verify(wordTranslationMapper, times(1)).toDTO(mockWordTranslation);
    }

    @Test
    void testGetRandomWordTranslation_NoDeckId_ReturnsTranslation() {
        // Given
        DeckWordTranslation mockDeckWordTranslation = mock(DeckWordTranslation.class);
        WordTranslation mockWordTranslation = mock(WordTranslation.class);
        WordTranslationDTO mockDto = mock(WordTranslationDTO.class);

        when(deckWordTranslationRespository.findRandomDeckWordTranslation())
                .thenReturn(Optional.of(mockDeckWordTranslation));
        when(mockDeckWordTranslation.getWordTranslation()).thenReturn(mockWordTranslation);
        when(wordTranslationMapper.toDTO(mockWordTranslation)).thenReturn(mockDto);

        // When
        WordTranslationDTO result = wordTranslationService.getRandomWordTranslation(null);

        // Then
        assertNotNull(result);
        assertEquals(mockDto, result);
        verify(deckWordTranslationRespository, times(1)).findRandomDeckWordTranslation();
        verify(wordTranslationMapper, times(1)).toDTO(mockWordTranslation);
    }

    @Test
    void testGetRandomWordTranslation_ThrowsExceptionWhenNotFound() {
        // Given
        when(deckWordTranslationRespository.findRandomDeckWordTranslation())
                .thenReturn(Optional.empty());

        // When / Then
        WordTranslationNotFoundException exception = assertThrows(
                WordTranslationNotFoundException.class,
                () -> wordTranslationService.getRandomWordTranslation(null)
        );

        assertEquals("Not found any WordTranslation", exception.getMessage());
        verify(deckWordTranslationRespository, times(1)).findRandomDeckWordTranslation();
    }

    @Test
    void testAttemptsWordTranslation_UpdatesStatsAndReturnsNewTranslation() {
        // Given
        int wordId = 1;
        int phraseId = 2;
        boolean success = true;
        Integer deckId = 1;

        WordTranslation mockWordTranslation = new WordTranslation();
        mockWordTranslation.setAttempts(0);
        mockWordTranslation.setSuccesses(0);

        Phrase mockPhrase = new Phrase();
        mockPhrase.setAttempts(0);
        mockPhrase.setSuccesses(0);

        WordTranslationDTO mockDto = mock(WordTranslationDTO.class);

        DeckWordTranslation mockDeckWordTranslation = new DeckWordTranslation();
        mockDeckWordTranslation.setWordTranslation(mockWordTranslation);

        when(wordTranslationRepository.findById(wordId)).thenReturn(Optional.of(mockWordTranslation));
        when(phraseRepository.findById(phraseId)).thenReturn(Optional.of(mockPhrase));
        when(deckWordTranslationRespository.findRandomDeckWordTranslationWithByDeck(deckId))
                .thenReturn(Optional.of(mockDeckWordTranslation));
        when(wordTranslationMapper.toDTO(mockWordTranslation)).thenReturn(mockDto);

        // When
        WordTranslationDTO result = wordTranslationService.attemptsWordTranslation(wordId, phraseId, success, deckId);

        // Then
        assertEquals(mockDto, result);
        assertEquals(1, mockWordTranslation.getAttempts());
        assertEquals(1, mockPhrase.getAttempts());
        assertEquals(1, mockWordTranslation.getSuccesses());
        assertEquals(1, mockPhrase.getSuccesses());

        verify(deckWordTranslationHistorialRespository, times(1)).save(any(DeckWordTranslationHistorial.class));
    }


    @Test
    void testAttemptsWordTranslation_ThrowsExceptionWhenWordNotFound() {
        // Given
        int wordId = 1;
        int phraseId = 2;
        when(wordTranslationRepository.findById(wordId)).thenReturn(Optional.empty());

        // When / Then
        WordTranslationNotFoundException exception = assertThrows(
                WordTranslationNotFoundException.class,
                () -> wordTranslationService.attemptsWordTranslation(wordId, phraseId, true, null)
        );

        assertEquals("WordTranslation not found", exception.getMessage());
        verify(wordTranslationRepository, times(1)).findById(wordId);
    }

    @Test
    void testAttemptsWordTranslation_ThrowsExceptionWhenPhraseNotFound() {
        // Given
        int wordId = 1;
        int phraseId = 2;
        WordTranslation mockWordTranslation = mock(WordTranslation.class);

        when(wordTranslationRepository.findById(wordId)).thenReturn(Optional.of(mockWordTranslation));
        when(phraseRepository.findById(phraseId)).thenReturn(Optional.empty());

        // When / Then
        WordTranslationNotFoundException exception = assertThrows(
                WordTranslationNotFoundException.class,
                () -> wordTranslationService.attemptsWordTranslation(wordId, phraseId, true, null)
        );

        assertEquals("Phrase not found", exception.getMessage());
        verify(phraseRepository, times(1)).findById(phraseId);
    }

    @Test
    void testGetAllWordTranslationsWithPhrasesByDeck_ReturnsWordTranslationsWithPhrases() {
        // Given
        int deckId = 1;

        // Mock WordTranslation
        WordTranslation wordTranslation = new WordTranslation();
        wordTranslation.setId(1);

        // Mock Phrase
        Phrase phrase = new Phrase();
        phrase.setId(1);

        // Mock DTOs
        WordTranslationDTO wordTranslationDTO = new WordTranslationDTO();
        wordTranslationDTO.setId(1);

        PhraseDTO phraseDTO = new PhraseDTO();
        phraseDTO.setId(1);

        WordTranslationWithPhrasesDTO wordTranslationWithPhrasesDTO = new WordTranslationWithPhrasesDTO(
                wordTranslationDTO,
                List.of(phraseDTO)
        );

        when(wordTranslationRepository.findWordTranslationsByDeckId(deckId))
                .thenReturn(Optional.of(List.of(wordTranslation)));
        when(phraseService.findPhrasesByDeckIdAndWordTranslationId(deckId, wordTranslation.getId()))
                .thenReturn(List.of(phrase));
        when(wordTranslationMapper.toDTO(wordTranslation)).thenReturn(wordTranslationDTO);
        when(phraseMapper.toDTO(phrase)).thenReturn(phraseDTO);

        // When
        List<WordTranslationWithPhrasesDTO> result = wordTranslationService.getAllWordTranslationsWithPhrasesByDeck(deckId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        WordTranslationWithPhrasesDTO resultDTO = result.get(0);
        assertEquals(wordTranslationWithPhrasesDTO.getWordTranslation(), resultDTO.getWordTranslation());
        assertEquals(wordTranslationWithPhrasesDTO.getPhrases(), resultDTO.getPhrases());

        verify(wordTranslationRepository, times(1)).findWordTranslationsByDeckId(deckId);
        verify(phraseService, times(1)).findPhrasesByDeckIdAndWordTranslationId(deckId, wordTranslation.getId());
        verify(wordTranslationMapper, times(1)).toDTO(wordTranslation);
        verify(phraseMapper, times(1)).toDTO(phrase);
    }

    @Test
    void testGetAllWordTranslationsWithPhrasesByDeck_ThrowsExceptionWhenNoWordTranslationsFound() {
        // Given
        int deckId = 1;
        when(wordTranslationRepository.findWordTranslationsByDeckId(deckId))
                .thenReturn(Optional.of(Collections.emptyList()));

        // When / Then
        WordTranslationNotFoundException exception = assertThrows(
                WordTranslationNotFoundException.class,
                () -> wordTranslationService.getAllWordTranslationsWithPhrasesByDeck(deckId)
        );

        assertEquals("Not found any wordTranslation", exception.getMessage());
        verify(wordTranslationRepository, times(1)).findWordTranslationsByDeckId(deckId);
    }

    @Test
    void testGetAllWordTranslationsWithPhrasesByDeck_ReturnsEmptyPhrasesListWhenNoPhrasesFound() {
        // Given
        int deckId = 1;

        WordTranslation wordTranslation = new WordTranslation();
        wordTranslation.setId(1);
        WordTranslationDTO wordTranslationDTO = new WordTranslationDTO();
        wordTranslationDTO.setId(1);

        when(wordTranslationRepository.findWordTranslationsByDeckId(deckId))
                .thenReturn(Optional.of(List.of(wordTranslation)));
        when(phraseService.findPhrasesByDeckIdAndWordTranslationId(deckId, wordTranslation.getId()))
                .thenReturn(Collections.emptyList());
        when(wordTranslationMapper.toDTO(wordTranslation)).thenReturn(wordTranslationDTO);

        // When
        List<WordTranslationWithPhrasesDTO> result = wordTranslationService.getAllWordTranslationsWithPhrasesByDeck(deckId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        WordTranslationWithPhrasesDTO resultDTO = result.get(0);
        assertEquals(wordTranslationDTO, resultDTO.getWordTranslation());
        assertTrue(resultDTO.getPhrases().isEmpty());

        verify(wordTranslationRepository, times(1)).findWordTranslationsByDeckId(deckId);
        verify(phraseService, times(1)).findPhrasesByDeckIdAndWordTranslationId(deckId, wordTranslation.getId());
        verify(wordTranslationMapper, times(1)).toDTO(wordTranslation);
    }
}
