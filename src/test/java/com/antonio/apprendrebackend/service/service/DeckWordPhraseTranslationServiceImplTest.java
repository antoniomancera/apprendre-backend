package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.*;
import com.antonio.apprendrebackend.service.exception.DeckWordPhraseTranslationNotFoundException;
import com.antonio.apprendrebackend.service.mapper.*;
import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.repository.DeckWordPhraseTranslationRespository;
import com.antonio.apprendrebackend.service.service.impl.DeckWordPhraseTranslationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

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
    private WordSenseCategoryService wordSenseCategoryService;
    @Mock
    private WordService wordService;
    @Mock
    private WordSenseService wordSenseService;
    @Mock
    private DeckMapper deckMapper;
    @Mock
    private WordMapper wordMapper;
    @Mock
    private WordSenseWithoutWordMapper wordSenseWithoutWordMapper;
    @Mock
    private CategoryMapper categoryMapper;

    private Language languageFr;
    private Language languageSp;
    private Course course;
    private UserInfo userInfo;
    private DeckWordPhraseTranslation deckWordPhraseTranslation;
    private Word word;
    private Word word1;
    private Word word2;
    private WordSense wordSenseFr;
    private WordSense wordSense1;
    private WordSense wordSense2;
    private WordTranslation wordTranslation;
    private WordPhraseTranslation wordPhraseTranslation;
    private Deck deck;
    private Success success;
    private Success success1;
    private Success success2;
    private UserHistorial userHistorial1;
    private UserHistorial userHistorial2;
    private Category category1;
    private Category category2;
    private WordTranslation wordTranslation1;
    private WordTranslation wordTranslation2;
    private WordPhraseTranslation wordPhraseTranslation1;
    private WordPhraseTranslation wordPhraseTranslation2;
    private DeckWordPhraseTranslation deckWordPhraseTranslation1;
    private DeckWordPhraseTranslation deckWordPhraseTranslation2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        languageFr = new Language();
        languageFr.setCode(Language.LanguageEnum.FR);

        languageSp = new Language();
        languageSp.setCode(Language.LanguageEnum.ES);

        course = new Course();
        course.setBaseLanguage(languageSp);
        course.setTargetLanguage(languageFr);

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
        wordTranslation.setWordSenseA(wordSenseFr);
        wordTranslation.setBaseWeight(1);

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
        deck.setCourse(course);

        success = new Success();
        success.setId(1);

        word1 = new Word();
        word1.setId(1);
        word1.setName("hello");

        word2 = new Word();
        word2.setId(2);
        word2.setName("world");

        wordSense1 = new WordSense();
        wordSense1.setId(1);
        wordSense1.setWord(word1);

        wordSense2 = new WordSense();
        wordSense2.setId(2);
        wordSense2.setWord(word2);

        success1 = new Success();
        success1.setScore(1.0);

        success2 = new Success();
        success2.setScore(0.5);

        userHistorial1 = new UserHistorial();
        userHistorial1.setSuccess(success1);

        userHistorial2 = new UserHistorial();
        userHistorial2.setSuccess(success2);

        category1 = new Category();
        category1.setId(1);
        category1.setName("Technology");

        category2 = new Category();
        category2.setId(2);
        category2.setName("Science");

        wordTranslation1 = new WordTranslation();
        wordTranslation1.setWordSenseA(wordSense1);

        wordTranslation2 = new WordTranslation();
        wordTranslation2.setWordSenseA(wordSense2);

        wordPhraseTranslation1 = new WordPhraseTranslation();
        wordPhraseTranslation1.setWordTranslation(wordTranslation1);

        wordPhraseTranslation2 = new WordPhraseTranslation();
        wordPhraseTranslation2.setWordTranslation(wordTranslation2);

        deckWordPhraseTranslation1 = new DeckWordPhraseTranslation();
        deckWordPhraseTranslation1.setWordPhraseTranslation(wordPhraseTranslation1);

        deckWordPhraseTranslation2 = new DeckWordPhraseTranslation();
        deckWordPhraseTranslation2.setWordPhraseTranslation(wordPhraseTranslation2);

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
        when(wordPhraseTranslationMapper.toDTO(wordPhraseTranslation, languageFr)).thenReturn(expectedDto);
        when(deckService.getDeckbyId(deckId)).thenReturn(deck);
        // When
        WordPhraseTranslationDTO result = deckWordPhraseTranslationService.getRandomWordPhraseTranslation(userInfo, deckId);

        // Then
        assertNotNull(result);
        assertEquals(401, result.getId());
        verify(deckWordPhraseTranslationRespository, times(1))
                .findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId);
        verify(wordPhraseTranslationMapper, times(1)).toDTO(wordPhraseTranslation, languageFr);
    }

    @Test
    void testGetRandomWordPhraseTranslationWithoutDeckId() {
        // Given
        Integer deckId = null;
        WordPhraseTranslationDTO expectedDto = new WordPhraseTranslationDTO();
        expectedDto.setId(401);

        when(deckWordPhraseTranslationRespository.findRandomUserDeckWordPhraseTranslationWithByUser(userInfo.getId()))
                .thenReturn(Optional.of(deckWordPhraseTranslation));
        when(wordPhraseTranslationMapper.toDTO(wordPhraseTranslation, languageFr)).thenReturn(expectedDto);
        when(deckService.getDeckbyId(deckId)).thenReturn(deck);
        // When
        WordPhraseTranslationDTO result = deckWordPhraseTranslationService.getRandomWordPhraseTranslation(userInfo, deckId);

        // Then
        assertNotNull(result);
        assertEquals(401, result.getId());
        verify(deckWordPhraseTranslationRespository, times(1))
                .findRandomUserDeckWordPhraseTranslationWithByUser(userInfo.getId());
        verify(wordPhraseTranslationMapper, times(1)).toDTO(wordPhraseTranslation, languageFr);
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
        when(wordPhraseTranslationMapper.toDTO(wordPhraseTranslation, languageFr)).thenReturn(nextWordDto);

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

    @Test
    void testGetWordWithAttemptsAndSuccessPaginatedByDeckIdSuccess() {
        // Given
        Integer deckId = 1;
        Integer pageNumber = 0;
        Integer pageSize = 10;
        Integer userId = 1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<DeckWordPhraseTranslation> deckWordPhraseTranslations = Arrays.asList(
                deckWordPhraseTranslation1, deckWordPhraseTranslation2
        );

        List<UserHistorial> userHistorials1 = Arrays.asList(userHistorial1, userHistorial2);
        List<UserHistorial> userHistorials2 = Arrays.asList(userHistorial1);

        WordDTO wordDTO1 = new WordDTO();
        wordDTO1.setId(1);
        wordDTO1.setName("hello");

        WordDTO wordDTO2 = new WordDTO();
        wordDTO2.setId(2);
        wordDTO2.setName("world");

        // When
        when(deckWordPhraseTranslationRespository.findByDeckId(pageable, deckId))
                .thenReturn(deckWordPhraseTranslations);
        when(userHistorialService.getUserHistorialsByDeckIdAndWordId(deckId, word1.getId()))
                .thenReturn(userHistorials1);
        when(userHistorialService.getUserHistorialsByDeckIdAndWordId(deckId, word2.getId()))
                .thenReturn(userHistorials2);
        when(wordMapper.toDTO(word1)).thenReturn(wordDTO1);
        when(wordMapper.toDTO(word2)).thenReturn(wordDTO2);

        List<WordWithAttemptsAndSuccessDTO> result = deckWordPhraseTranslationService
                .getWordWithAttemptsAndSuccessPaginatedByDeckId(deckId, pageNumber, pageSize, userId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        WordWithAttemptsAndSuccessDTO firstResult = result.get(0);
        assertEquals(wordDTO1, firstResult.getWord());
        assertEquals(2, firstResult.getAttempts());
        assertEquals(1.5, firstResult.getSuccess());

        WordWithAttemptsAndSuccessDTO secondResult = result.get(1);
        assertEquals(wordDTO2, secondResult.getWord());
        assertEquals(1, secondResult.getAttempts());
        assertEquals(1.0, secondResult.getSuccess());

        verify(deckWordPhraseTranslationRespository, times(1)).findByDeckId(pageable, deckId);
        verify(userHistorialService, times(1)).getUserHistorialsByDeckIdAndWordId(deckId, word1.getId());
        verify(userHistorialService, times(1)).getUserHistorialsByDeckIdAndWordId(deckId, word2.getId());
        verify(wordMapper, times(1)).toDTO(word1);
        verify(wordMapper, times(1)).toDTO(word2);
    }

    @Test
    void testGetWordWithAttemptsAndSuccessPaginatedByDeckIdEmpty() {
        // Given
        Integer deckId = 1;
        Integer pageNumber = 0;
        Integer pageSize = 10;
        Integer userId = 1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // When
        when(deckWordPhraseTranslationRespository.findByDeckId(pageable, deckId))
                .thenReturn(Collections.emptyList());

        List<WordWithAttemptsAndSuccessDTO> result = deckWordPhraseTranslationService
                .getWordWithAttemptsAndSuccessPaginatedByDeckId(deckId, pageNumber, pageSize, userId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(deckWordPhraseTranslationRespository, times(1)).findByDeckId(pageable, deckId);
    }

    @Test
    void testGetWordSenseInfosWithoutWordByWordIdAndDeckIdSuccess() {
        // Given
        Integer deckId = 1;
        Integer wordId = 1;

        List<WordSense> wordSenses = Arrays.asList(wordSense1, wordSense2);
        List<UserHistorial> userHistorials1 = Arrays.asList(userHistorial1, userHistorial2);
        List<UserHistorial> userHistorials2 = Arrays.asList(userHistorial1);
        List<Category> categories1 = Arrays.asList(category1);
        List<Category> categories2 = Arrays.asList(category2);

        WordSenseWithoutWordDTO wordSenseDTO1 = new WordSenseWithoutWordDTO();
        WordSenseWithoutWordDTO wordSenseDTO2 = new WordSenseWithoutWordDTO();
        CategoryDTO categoryDTO1 = new CategoryDTO();
        CategoryDTO categoryDTO2 = new CategoryDTO();

        // When
        when(wordSenseService.getWordSensesByWordIdAndDeckId(wordId, deckId)).thenReturn(wordSenses);
        when(userHistorialService.getUserHistorialsByDeckIdAndWordSenseId(deckId, wordSense1.getId()))
                .thenReturn(userHistorials1);
        when(userHistorialService.getUserHistorialsByDeckIdAndWordSenseId(deckId, wordSense2.getId()))
                .thenReturn(userHistorials2);
        when(wordSenseCategoryService.getCategoryByWordSenseId(wordSense1.getId())).thenReturn(categories1);
        when(wordSenseCategoryService.getCategoryByWordSenseId(wordSense2.getId())).thenReturn(categories2);
        when(wordSenseWithoutWordMapper.toDTO(wordSense1)).thenReturn(wordSenseDTO1);
        when(wordSenseWithoutWordMapper.toDTO(wordSense2)).thenReturn(wordSenseDTO2);
        when(categoryMapper.toDTO(category1)).thenReturn(categoryDTO1);
        when(categoryMapper.toDTO(category2)).thenReturn(categoryDTO2);

        List<WordSenseInfoWithoutWordDTO> result = deckWordPhraseTranslationService
                .getWordSenseInfosWithoutWordByWordIdAndDeckId(deckId, wordId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        WordSenseInfoWithoutWordDTO firstResult = result.get(0);
        assertEquals(wordSenseDTO1, firstResult.getWordSense());
        assertEquals(2, firstResult.getAttempts());
        assertEquals(1.5, firstResult.getSuccess());
        assertEquals(1, firstResult.getCategories().size());

        WordSenseInfoWithoutWordDTO secondResult = result.get(1);
        assertEquals(wordSenseDTO2, secondResult.getWordSense());
        assertEquals(1, secondResult.getAttempts());
        assertEquals(1.0, secondResult.getSuccess());
        assertEquals(1, secondResult.getCategories().size());

        verify(wordSenseService, times(1)).getWordSensesByWordIdAndDeckId(wordId, deckId);
        verify(userHistorialService, times(1)).getUserHistorialsByDeckIdAndWordSenseId(deckId, wordSense1.getId());
        verify(userHistorialService, times(1)).getUserHistorialsByDeckIdAndWordSenseId(deckId, wordSense2.getId());
    }

    @Test
    void testGetWordSenseInfosWithoutWordByWordIdAndDeckIdEmpty() {
        // Given
        Integer deckId = 1;
        Integer wordId = 1;

        // When
        when(wordSenseService.getWordSensesByWordIdAndDeckId(wordId, deckId))
                .thenReturn(Collections.emptyList());

        List<WordSenseInfoWithoutWordDTO> result = deckWordPhraseTranslationService
                .getWordSenseInfosWithoutWordByWordIdAndDeckId(deckId, wordId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(wordSenseService, times(1)).getWordSensesByWordIdAndDeckId(wordId, deckId);
    }

    @Test
    void testGetDeckEditInitInfoSuccess() {
        // Given
        Integer deckId = 1;
        Integer pageSize = 10;
        Integer userId = 1;
        Language.LanguageEnum languageCode = Language.LanguageEnum.FR;

        List<WordSense> wordSenses = Arrays.asList(wordSense1, wordSense2);
        List<WordWithAttemptsAndSuccessDTO> wordWithAttemptsAndSuccesses = Arrays.asList(
                new WordWithAttemptsAndSuccessDTO(new WordDTO(), 5, 3.0),
                new WordWithAttemptsAndSuccessDTO(new WordDTO(), 3, 2.5)
        );

        // When
        when(wordSenseService.getTargetLanguageWordSensesByDeckId(deckId)).thenReturn(wordSenses);
        when(wordService.getWordWithAttemptsAndSuccessPaginatedByLanguageCode(0, pageSize, userId, languageCode))
                .thenReturn(wordWithAttemptsAndSuccesses);
        when(deckService.getDeckbyId(deckId)).thenReturn(deck);

        DeckEditInitInfoDTO result = deckWordPhraseTranslationService.getDeckEditInitInfo(deckId, pageSize, userId);

        // Then
        assertNotNull(result);
        assertNotNull(result.getWordToWordSensesIdMap());
        assertNotNull(result.getWordWithAttemptsAndSuccesses());

        Map<Integer, List<Integer>> wordToWordSensesMap = result.getWordToWordSensesIdMap();
        assertTrue(wordToWordSensesMap.containsKey(word1.getId()));
        assertTrue(wordToWordSensesMap.containsKey(word2.getId()));
        assertTrue(wordToWordSensesMap.get(word1.getId()).contains(wordSense1.getId()));
        assertTrue(wordToWordSensesMap.get(word2.getId()).contains(wordSense2.getId()));

        assertEquals(2, result.getWordWithAttemptsAndSuccesses().size());

        verify(wordSenseService, times(1)).getTargetLanguageWordSensesByDeckId(deckId);
        verify(wordService, times(1)).getWordWithAttemptsAndSuccessPaginatedByLanguageCode(0, pageSize, userId, languageCode);
    }

    @Test
    void testGetDeckEditInitInfoWithEmptyWordSenses() {
        // Given
        Integer deckId = 1;
        Integer pageSize = 10;
        Integer userId = 1;
        Language.LanguageEnum languageCode = Language.LanguageEnum.FR;

        List<WordWithAttemptsAndSuccessDTO> wordWithAttemptsAndSuccesses = Arrays.asList(
                new WordWithAttemptsAndSuccessDTO(new WordDTO(), 5, 3.0)
        );

        // When
        when(wordSenseService.getTargetLanguageWordSensesByDeckId(deckId)).thenReturn(Collections.emptyList());
        when(wordService.getWordWithAttemptsAndSuccessPaginatedByLanguageCode(0, pageSize, userId, languageCode))
                .thenReturn(wordWithAttemptsAndSuccesses);
        when(deckService.getDeckbyId(deckId)).thenReturn(deck);

        DeckEditInitInfoDTO result = deckWordPhraseTranslationService.getDeckEditInitInfo(deckId, pageSize, userId);

        // Then
        assertNotNull(result);
        assertNotNull(result.getWordToWordSensesIdMap());
        assertTrue(result.getWordToWordSensesIdMap().isEmpty());
        assertNotNull(result.getWordWithAttemptsAndSuccesses());
        assertEquals(1, result.getWordWithAttemptsAndSuccesses().size());

        verify(wordSenseService, times(1)).getTargetLanguageWordSensesByDeckId(deckId);
        verify(wordService, times(1)).getWordWithAttemptsAndSuccessPaginatedByLanguageCode(0, pageSize, userId, languageCode);
    }

    @Test
    void testGetWordWithAttemptsAndSuccessPaginatedByDeckIdWithNoHistorials() {
        // Given
        Integer deckId = 1;
        Integer pageNumber = 0;
        Integer pageSize = 10;
        Integer userId = 1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<DeckWordPhraseTranslation> deckWordPhraseTranslations = Arrays.asList(deckWordPhraseTranslation1);

        WordDTO wordDTO1 = new WordDTO();
        wordDTO1.setId(1);
        wordDTO1.setName("hello");

        // When
        when(deckWordPhraseTranslationRespository.findByDeckId(pageable, deckId))
                .thenReturn(deckWordPhraseTranslations);
        when(userHistorialService.getUserHistorialsByDeckIdAndWordId(deckId, word1.getId()))
                .thenReturn(Collections.emptyList());
        when(wordMapper.toDTO(word1)).thenReturn(wordDTO1);

        List<WordWithAttemptsAndSuccessDTO> result = deckWordPhraseTranslationService
                .getWordWithAttemptsAndSuccessPaginatedByDeckId(deckId, pageNumber, pageSize, userId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        WordWithAttemptsAndSuccessDTO firstResult = result.get(0);
        assertEquals(wordDTO1, firstResult.getWord());
        assertEquals(0, firstResult.getAttempts());
        assertEquals(0.0, firstResult.getSuccess());

        verify(deckWordPhraseTranslationRespository, times(1)).findByDeckId(pageable, deckId);
        verify(userHistorialService, times(1)).getUserHistorialsByDeckIdAndWordId(deckId, word1.getId());
        verify(wordMapper, times(1)).toDTO(word1);
    }
}
