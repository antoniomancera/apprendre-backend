package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.exception.WordTranslationNotFoundException;
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


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WordTranslationServiceTest {

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
}
