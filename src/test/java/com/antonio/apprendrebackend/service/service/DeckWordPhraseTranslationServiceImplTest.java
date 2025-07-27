package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.AttemptResultDTO;
import com.antonio.apprendrebackend.service.dto.DeckDTO;
import com.antonio.apprendrebackend.service.dto.WordPhraseTranslationDTO;
import com.antonio.apprendrebackend.service.exception.DeckWordPhraseTranslationNotFoundException;
import com.antonio.apprendrebackend.service.mapper.DeckMapper;
import com.antonio.apprendrebackend.service.mapper.WordPhraseTranslationMapper;
import com.antonio.apprendrebackend.service.model.*;
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
    @Mock
    private WordPhraseTranslationMapper wordPhraseTranslationMapper;
    @Mock
    private DeckService deckService;
    @Mock
    private WordPhraseTranslationService wordPhraseTranslationService;
    @Mock
    private UserHistorialService userHistorialService;
    @Mock
    private SuccessService successService;
    @Mock
    private DeckMapper deckMapper;


    private UserInfo userInfo;
    private DeckWordPhraseTranslation deckWordPhraseTranslation;
    private Word word;
    private WordSense wordSenseFr;
    private WordTranslation wordTranslation;
    private WordPhraseTranslation wordPhraseTranslation;
    private Deck deck;
    private Success success;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
        wordTranslation.setBaseWeight(1); // AGREGAR ESTA LÍNEA

        wordPhraseTranslation = new WordPhraseTranslation();
        wordPhraseTranslation.setId(401);
        wordPhraseTranslation.setWordTranslation(wordTranslation);

        deckWordPhraseTranslation = new DeckWordPhraseTranslation();
        deckWordPhraseTranslation.setId(501);
        deckWordPhraseTranslation.setWordPhraseTranslation(wordPhraseTranslation);
        deckWordPhraseTranslation.setAttempts(5);
        deckWordPhraseTranslation.setSuccesses(3);

        deck = new Deck();
        deck.setId(1);
        deck.setName("Test Deck");
        deck.setUserInfo(userInfo);

        success = new Success();
        success.setId(1);
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
        DeckWordPhraseTranslation mockTranslation = new DeckWordPhraseTranslation();
        when(deckWordPhraseTranslationRespository.findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId))
                .thenReturn(Optional.of(mockTranslation));

        // When
        DeckWordPhraseTranslation result = deckWordPhraseTranslationService
                .getRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId);

        // Then
        assertNotNull(result);
        assertEquals(mockTranslation, result);
        verify(deckWordPhraseTranslationRespository, times(1))
                .findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId);
    }

    @Test
    void testGetRandomUserDeckWordPhraseTranslationWithByDeckAndUser_ThrowsExceptionWhenNotFound() {
        // Given
        Integer deckId = 1;
        when(deckWordPhraseTranslationRespository.findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId))
                .thenReturn(Optional.empty());

        // When / Then
        DeckWordPhraseTranslationNotFoundException exception = assertThrows(
                DeckWordPhraseTranslationNotFoundException.class,
                () -> deckWordPhraseTranslationService.getRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId)
        );

        assertEquals("Word not found for deck", exception.getMessage());
        verify(deckWordPhraseTranslationRespository, times(1))
                .findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId);
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

    @Test
    void testGetRandomWordPhraseTranslationWithDeckId() {
        // Given
        Integer deckId = 10;
        WordPhraseTranslationDTO expectedDto = new WordPhraseTranslationDTO();
        expectedDto.setId(401);

        when(deckWordPhraseTranslationRespository.findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId))
                .thenReturn(Optional.of(deckWordPhraseTranslation));
        when(wordPhraseTranslationMapper.toDTO(wordPhraseTranslation)).thenReturn(expectedDto);

        // When
        WordPhraseTranslationDTO result = deckWordPhraseTranslationService.getRandomWordPhraseTranslation(userInfo, deckId);

        // Then
        assertNotNull(result);
        assertEquals(401, result.getId());
        verify(deckWordPhraseTranslationRespository, times(1))
                .findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId);
        verify(wordPhraseTranslationMapper, times(1)).toDTO(wordPhraseTranslation);
    }

    @Test
    void testGetRandomWordPhraseTranslationWithoutDeckId() {
        // Given
        Integer deckId = null;
        WordPhraseTranslationDTO expectedDto = new WordPhraseTranslationDTO();
        expectedDto.setId(401);

        when(deckWordPhraseTranslationRespository.findRandomUserDeckWordPhraseTranslationWithByUser(userInfo.getId()))
                .thenReturn(Optional.of(deckWordPhraseTranslation));
        when(wordPhraseTranslationMapper.toDTO(wordPhraseTranslation)).thenReturn(expectedDto);

        // When
        WordPhraseTranslationDTO result = deckWordPhraseTranslationService.getRandomWordPhraseTranslation(userInfo, deckId);

        // Then
        assertNotNull(result);
        assertEquals(401, result.getId());
        verify(deckWordPhraseTranslationRespository, times(1))
                .findRandomUserDeckWordPhraseTranslationWithByUser(userInfo.getId());
        verify(wordPhraseTranslationMapper, times(1)).toDTO(wordPhraseTranslation);
    }


    @Test
    void testAttemptsWordPhraseTranslation_SuccessfulAttempt() {
        // Given
        Integer wordPhraseId = 401;
        Integer deckId = 1;
        String attempt = "maison";

        WordPhraseTranslationDTO nextWordDto = new WordPhraseTranslationDTO();
        nextWordDto.setId(402);

        when(deckWordPhraseTranslationRespository.findByDeckIdAndWordPhraseTranslationId(deckId, wordPhraseId))
                .thenReturn(Optional.of(deckWordPhraseTranslation));
        when(deckService.getDeckbyId(deckId)).thenReturn(deck);
        when(successService.getSuccessByAttemptAndWordSense(attempt, wordSenseFr)).thenReturn(success);
        when(deckWordPhraseTranslationRespository.findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId))
                .thenReturn(Optional.of(deckWordPhraseTranslation));
        when(wordPhraseTranslationMapper.toDTO(wordPhraseTranslation)).thenReturn(nextWordDto);

        // When
        AttemptResultDTO result = deckWordPhraseTranslationService
                .attemptsWordPhraseTranslation(userInfo, wordPhraseId, deckId, attempt);

        // Then
        assertNotNull(result);
        assertTrue(result.isHasSuccess());
        assertNotNull(result.getWordPhraseTranslation());

        assertEquals(6, deckWordPhraseTranslation.getAttempts());
        assertEquals(4, deckWordPhraseTranslation.getSuccesses());

        verify(userHistorialService, times(1)).postUserHistorial(any(UserHistorial.class));
        verify(deckWordPhraseTranslationRespository, times(1))
                .findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId);
    }

    @Test
    void testAttemptsWordPhraseTranslation_FailedAttempt() {
        // Given
        Integer wordPhraseId = 401;
        Integer deckId = 1;
        String attempt = "wrong_answer";

        when(deckWordPhraseTranslationRespository.findByDeckIdAndWordPhraseTranslationId(deckId, wordPhraseId))
                .thenReturn(Optional.of(deckWordPhraseTranslation));
        when(deckService.getDeckbyId(deckId)).thenReturn(deck);
        when(successService.getSuccessByAttemptAndWordSense(attempt, wordSenseFr)).thenReturn(success);

        // When
        AttemptResultDTO result = deckWordPhraseTranslationService
                .attemptsWordPhraseTranslation(userInfo, wordPhraseId, deckId, attempt);

        // Then
        assertNotNull(result);
        assertFalse(result.isHasSuccess());
        assertNull(result.getWordPhraseTranslation());

        assertEquals(6, deckWordPhraseTranslation.getAttempts()); // 5 + 1
        assertEquals(3, deckWordPhraseTranslation.getSuccesses()); // No change

        verify(userHistorialService, times(1)).postUserHistorial(any(UserHistorial.class));
        verify(deckWordPhraseTranslationRespository, never())
                .findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(any());
    }

    @Test
    void testCreateDeckWithWordPhraseTranslation_Success() {
        // Given
        String name = "New Test Deck";
        String description = "Test Description";
        List<Integer> wordPhraseTranslationIds = Arrays.asList(401, 402, 403);

        Deck createdDeck = new Deck(userInfo, name, description);
        createdDeck.setId(1);

        DeckDTO expectedDeckDTO = new DeckDTO();
        expectedDeckDTO.setId(1);
        expectedDeckDTO.setName(name);

        WordPhraseTranslation wordPhraseTranslation1 = new WordPhraseTranslation();
        wordPhraseTranslation1.setId(401);
        wordPhraseTranslation1.setWordTranslation(wordTranslation);

        WordPhraseTranslation wordPhraseTranslation2 = new WordPhraseTranslation();
        wordPhraseTranslation2.setId(402);
        wordPhraseTranslation2.setWordTranslation(wordTranslation);

        WordPhraseTranslation wordPhraseTranslation3 = new WordPhraseTranslation();
        wordPhraseTranslation3.setId(403);
        wordPhraseTranslation3.setWordTranslation(wordTranslation);

        when(deckService.createDeck(any(Deck.class))).thenReturn(createdDeck);
        when(wordPhraseTranslationService.getWordPhraseTranslationById(401)).thenReturn(wordPhraseTranslation1);
        when(wordPhraseTranslationService.getWordPhraseTranslationById(402)).thenReturn(wordPhraseTranslation2);
        when(wordPhraseTranslationService.getWordPhraseTranslationById(403)).thenReturn(wordPhraseTranslation3);
        when(deckMapper.toDTO(createdDeck)).thenReturn(expectedDeckDTO);

        // When
        DeckDTO result = deckWordPhraseTranslationService
                .createDeckWithWordPhraseTranslation(userInfo, name, description, wordPhraseTranslationIds);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(name, result.getName());

        verify(deckService, times(1)).createDeck(any(Deck.class));
        verify(wordPhraseTranslationService, times(3)).getWordPhraseTranslationById(any());
        verify(deckWordPhraseTranslationRespository, times(3)).save(any(DeckWordPhraseTranslation.class));
        verify(deckMapper, times(1)).toDTO(createdDeck);
    }


    @Test
    void testCreateDeckWithWordPhraseTranslation_EmptyList() {
        // Given
        String name = "Empty Deck";
        String description = "Empty Description";
        List<Integer> wordPhraseTranslationIds = Arrays.asList();

        Deck createdDeck = new Deck(userInfo, name, description);
        createdDeck.setId(1);

        DeckDTO expectedDeckDTO = new DeckDTO();
        expectedDeckDTO.setId(1);
        expectedDeckDTO.setName(name);

        when(deckService.createDeck(any(Deck.class))).thenReturn(createdDeck);
        when(deckMapper.toDTO(createdDeck)).thenReturn(expectedDeckDTO);

        // When
        DeckDTO result = deckWordPhraseTranslationService
                .createDeckWithWordPhraseTranslation(userInfo, name, description, wordPhraseTranslationIds);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(name, result.getName());

        verify(deckService, times(1)).createDeck(any(Deck.class));
        verify(wordPhraseTranslationService, never()).getWordPhraseTranslationById(any());
        verify(deckWordPhraseTranslationRespository, never()).save(any(DeckWordPhraseTranslation.class));
        verify(deckMapper, times(1)).toDTO(createdDeck);
    }

    @Test
    void testAttemptsWordPhraseTranslation_DeckWordPhraseTranslationNotFound() {
        // Given
        Integer wordPhraseId = 999;
        Integer deckId = 1;
        String attempt = "test";

        when(deckWordPhraseTranslationRespository.findByDeckIdAndWordPhraseTranslationId(deckId, wordPhraseId))
                .thenReturn(Optional.empty());

        // When / Then
        DeckWordPhraseTranslationNotFoundException exception = assertThrows(
                DeckWordPhraseTranslationNotFoundException.class,
                () -> deckWordPhraseTranslationService.attemptsWordPhraseTranslation(userInfo, wordPhraseId, deckId, attempt)
        );

        assertEquals("DeckWordPhraseTranslation not found for deckId 1 and wordPhraseTranslationId 999",
                exception.getMessage());

        verify(deckService, never()).getDeckbyId(any());
        verify(userHistorialService, never()).postUserHistorial(any());
    }
}
