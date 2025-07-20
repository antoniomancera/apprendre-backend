package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.AttemptResultDTO;
import com.antonio.apprendrebackend.service.dto.WordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.mapper.WordPhraseTranslationMapper;
import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.repository.WordPhraseTranslationRepository;
import com.antonio.apprendrebackend.service.service.impl.WordPhraseTranslationServiceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WordTranslationHistorialServiceImplTest {

    @Mock
    private DeckWordPhraseTranslationService deckWordPhraseTranslationService;

    @Mock
    private WordPhraseTranslationMapper wordPhraseTranslationMapper;

    @Mock
    private UserHistorialService userHistorialService;

    @Mock
    private WordPhraseTranslationRepository wordPhraseTranslationRepository;

    @Mock
    private DeckService deckService;

    @Mock
    private SuccessService successService;

    @InjectMocks
    private WordPhraseTranslationServiceServiceImpl wordPhraseTranslationService;

    private UserInfo userInfo;
    private DeckWordPhraseTranslation deckWordPhraseTranslation;
    private WordPhraseTranslation wordPhraseTranslation;
    private WordTranslation wordTranslation;
    private WordSense wordSenseFr;
    private Word word;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Common test data setup
        userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUserName("testUser");

        word = new Word();
        word.setId(101);
        word.setName("maison");

        wordSenseFr = new WordSense();
        wordSenseFr.setId(201);
        wordSenseFr.setWord(word);

        wordTranslation = new WordTranslation();
        wordTranslation.setId(301);
        wordTranslation.setWordSenseFr(wordSenseFr);

        wordPhraseTranslation = new WordPhraseTranslation();
        wordPhraseTranslation.setId(401);
        wordPhraseTranslation.setWordTranslation(wordTranslation);

        deckWordPhraseTranslation = new DeckWordPhraseTranslation();
        deckWordPhraseTranslation.setId(501);
        deckWordPhraseTranslation.setWordPhraseTranslation(wordPhraseTranslation);
        deckWordPhraseTranslation.setAttempts(5);
        deckWordPhraseTranslation.setSuccesses(3);
    }

    @Test
    void testGetRandomWordPhraseTranslationWithDeckId() {
        // Given
        Integer deckId = 10;
        WordPhraseTranslationDTO expectedDto = new WordPhraseTranslationDTO();
        expectedDto.setId(401);

        // When
        when(deckWordPhraseTranslationService.getRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId))
                .thenReturn(deckWordPhraseTranslation);
        when(wordPhraseTranslationMapper.toDTO(wordPhraseTranslation)).thenReturn(expectedDto);

        WordPhraseTranslationDTO result = wordPhraseTranslationService.getRandomWordPhraseTranslation(userInfo, deckId);

        // Then
        assertNotNull(result);
        assertEquals(401, result.getId());
        verify(deckWordPhraseTranslationService, times(1))
                .getRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId);
        verify(wordPhraseTranslationMapper, times(1)).toDTO(wordPhraseTranslation);
    }

    @Test
    void testGetRandomWordPhraseTranslationWithoutDeckId() {
        // Given
        Integer deckId = null;
        WordPhraseTranslationDTO expectedDto = new WordPhraseTranslationDTO();
        expectedDto.setId(401);

        // When
        when(deckWordPhraseTranslationService.getRandomUserDeckWordPhraseTranslationWithByUser(userInfo.getId()))
                .thenReturn(deckWordPhraseTranslation);
        when(wordPhraseTranslationMapper.toDTO(wordPhraseTranslation)).thenReturn(expectedDto);

        WordPhraseTranslationDTO result = wordPhraseTranslationService.getRandomWordPhraseTranslation(userInfo, deckId);

        // Then
        assertNotNull(result);
        assertEquals(401, result.getId());
        verify(deckWordPhraseTranslationService, times(1))
                .getRandomUserDeckWordPhraseTranslationWithByUser(userInfo.getId());
        verify(wordPhraseTranslationMapper, times(1)).toDTO(wordPhraseTranslation);
    }

   /* @Test
    void testAttemptsWordPhraseTranslationSuccess() {
        // Given
        Integer wordPhraseId = 401;
        Integer deckId = 10;
        String attempt = "maison"; // Correct attempt matching word name

        WordPhraseTranslationDTO nextWordPhraseDto = new WordPhraseTranslationDTO();
        nextWordPhraseDto.setId(402);

        // When
        when(deckUserWordPhraseTranslationService.getByDeckUserIdAndWordPhraseTranslationId(deckId, wordPhraseId))
                .thenReturn(deckUserWordPhraseTranslation);
        when(userHistorialService.postUserHistorial(any(UserHistorial.class)))
                .thenReturn(new UserHistorial());
        when(wordPhraseTranslationService.getRandomWordPhraseTranslation(userInfo, deckId))
                .thenReturn(nextWordPhraseDto);

        AttemptResultDTO result = wordPhraseTranslationService.attemptsWordPhraseTranslation(
                userInfo, wordPhraseId, deckId, attempt);

        // Then
        assertNotNull(result);
        assertTrue(result.isHasSuccess());
        //assertNotNull(result.getWordPhraseTranslation());
        assertEquals(402, result.getWordPhraseTranslation().getId());

        // Verify stats update
        assertEquals(6, deckUserWordPhraseTranslation.getAttempts());
        assertEquals(4, deckUserWordPhraseTranslation.getSuccesses());

        // Verify historial was created with success=1
        ArgumentCaptor<UserHistorial> historialCaptor = ArgumentCaptor.forClass(UserHistorial.class);
        verify(userHistorialService, times(1)).postUserHistorial(historialCaptor.capture());
        UserHistorial capturedHistorial = historialCaptor.getValue();
        assertEquals(1, capturedHistorial.getSuccess());
        assertEquals(deckUserWordPhraseTranslation, capturedHistorial.getDeckUserWordPhraseTranslation());
        assertEquals(deckId, capturedHistorial.getDeckId());
    }

    */


    @Test
    void testGetPhrasesByDeckIdAndWordTranslationId() {
        // Given
        Integer deckId = 10;
        Integer wordTranslationId = 301;

        PhraseTranslation phrase1 = new PhraseTranslation();
        phrase1.setId(601);

        PhraseTranslation phrase2 = new PhraseTranslation();
        phrase2.setId(602);

        List<PhraseTranslation> expectedPhrases = Arrays.asList(phrase1, phrase2);

        // When
        when(wordPhraseTranslationRepository.findPhrasesByDeckIdAndWordTranslationId(deckId, wordTranslationId))
                .thenReturn(expectedPhrases);

        List<PhraseTranslation> result = wordPhraseTranslationService.getPhrasesByDeckIdAndWordTranslationId(
                deckId, wordTranslationId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(601, result.get(0).getId());
        assertEquals(602, result.get(1).getId());
        verify(wordPhraseTranslationRepository, times(1))
                .findPhrasesByDeckIdAndWordTranslationId(deckId, wordTranslationId);
    }

    @Test
    void testGetWordTranslationsByDeckIdPhraseTranslationId() {
        // Given
        Integer deckId = 10;
        Integer phraseTranslationId = 601;

        WordTranslation wordTranslation1 = new WordTranslation();
        wordTranslation1.setId(301);

        WordTranslation wordTranslation2 = new WordTranslation();
        wordTranslation2.setId(302);

        List<WordTranslation> expectedWordTranslations = Arrays.asList(wordTranslation1, wordTranslation2);

        // When
        when(wordPhraseTranslationRepository.findWordTranslationsByDeckIdPhraseTranslationId(deckId, phraseTranslationId))
                .thenReturn(expectedWordTranslations);

        List<WordTranslation> result = wordPhraseTranslationService.getWordTranslationsByDeckIdPhraseTranslationId(
                deckId, phraseTranslationId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(301, result.get(0).getId());
        assertEquals(302, result.get(1).getId());
        verify(wordPhraseTranslationRepository, times(1))
                .findWordTranslationsByDeckIdPhraseTranslationId(deckId, phraseTranslationId);
    }

}