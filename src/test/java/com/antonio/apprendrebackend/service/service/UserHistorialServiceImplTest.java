package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.DeckDTO;
import com.antonio.apprendrebackend.service.dto.SuccessDTO;
import com.antonio.apprendrebackend.service.dto.UserHistorialDTO;
import com.antonio.apprendrebackend.service.exception.UserHistorialNotFoundException;
import com.antonio.apprendrebackend.service.mapper.UserHistorialMapper;
import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.repository.UserHistorialRespository;
import com.antonio.apprendrebackend.service.service.impl.UserHistorialServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.antonio.apprendrebackend.service.util.GeneralConstants.ONE_DAY_MILLIS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserHistorialServiceImplTest {
    @Mock
    private UserHistorialRespository userHistorialRespository;

    @Mock
    private UserHistorialMapper userHistorialMapper;

    @InjectMocks
    private UserHistorialServiceImpl userHistorialService;

    private UserInfo userInfo;
    private UserHistorial userHistorial1;
    private UserHistorial userHistorial2;
    private Success success1;
    private Success success2;
    private Deck deck;
    private SuccessDTO success1DTO;
    private SuccessDTO success2DTO;
    private DeckDTO deckDTO;
    private DeckWordPhraseTranslation translation1;
    private DeckWordPhraseTranslation translation2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUserName("testUser");

        success1 = new Success();
        success1.setId(1);
        success1.setSuccessEnum(Success.SuccessEnum.INCORRECT);
        success1.setScore(0.0);

        success2 = new Success();
        success2.setId(2);
        success2.setSuccessEnum(Success.SuccessEnum.CORRECT);
        success2.setScore(1.0);

        deck = new Deck();
        deck.setId(1);

        success1DTO = new SuccessDTO();
        success1DTO.setSuccessEnum(Success.SuccessEnum.INCORRECT);
        success1DTO.setScore(0.0);

        success2DTO = new SuccessDTO();
        success2DTO.setSuccessEnum(Success.SuccessEnum.INCORRECT);
        success2DTO.setScore(0.0);

        deckDTO = new DeckDTO();
        deckDTO.setId(1);

        translation1 = new DeckWordPhraseTranslation();
        translation1.setId(101);

        translation2 = new DeckWordPhraseTranslation();
        translation2.setId(102);

        userHistorial1 = new UserHistorial();
        userHistorial1.setId(1);
        userHistorial1.setUserInfo(userInfo);
        userHistorial1.setDate(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
        userHistorial1.setSuccess(success1);
        userHistorial1.setDeck(deck);
        userHistorial1.setDeckWordPhraseTranslation(translation1);

        userHistorial2 = new UserHistorial();
        userHistorial2.setId(2);
        userHistorial2.setUserInfo(userInfo);
        userHistorial2.setDate(System.currentTimeMillis());
        userHistorial2.setSuccess(success2);
        userHistorial2.setDeck(deck);
        userHistorial2.setDeckWordPhraseTranslation(translation2);
    }

    @Test
    void testGetUserHistorialLastWeek() {
        // Given
        List<UserHistorial> historialList = Arrays.asList(userHistorial1, userHistorial2);

        // When
        when(userHistorialRespository.findByUserInfoAndDateBetweenOrderByDateDesc(
                eq(userInfo), anyLong(), anyLong())).thenReturn(historialList);

        List<UserHistorial> result = userHistorialService.getUserHistorialLastWeek(userInfo);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        verify(userHistorialRespository, times(1))
                .findByUserInfoAndDateBetweenOrderByDateDesc(eq(userInfo), anyLong(), anyLong());
    }

    @Test
    void testGetUserHistorialLastWeekEmpty() {
        // Given
        List<UserHistorial> emptyList = new ArrayList<>();

        // When
        when(userHistorialRespository.findByUserInfoAndDateBetweenOrderByDateDesc(
                eq(userInfo), anyLong(), anyLong())).thenReturn(emptyList);

        List<UserHistorial> result = userHistorialService.getUserHistorialLastWeek(userInfo);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userHistorialRespository, times(1))
                .findByUserInfoAndDateBetweenOrderByDateDesc(eq(userInfo), anyLong(), anyLong());
    }

    @Test
    void testGetLastUserHistorial() {
        // Given
        Optional<UserHistorial> lastHistorial = Optional.of(userHistorial2);

        // When
        when(userHistorialRespository.findFirstByUserInfoOrderByDateDesc(userInfo)).thenReturn(lastHistorial);

        Optional<UserHistorial> result = userHistorialService.getLastUserHistorial(userInfo);

        // Then
        assertTrue(result.isPresent());
        assertEquals(2, result.get().getId());
        verify(userHistorialRespository, times(1)).findFirstByUserInfoOrderByDateDesc(userInfo);
    }

    @Test
    void testGetLastUserHistorialEmpty() {
        // Given
        Optional<UserHistorial> emptyHistorial = Optional.empty();

        // When
        when(userHistorialRespository.findFirstByUserInfoOrderByDateDesc(userInfo)).thenReturn(emptyHistorial);

        Optional<UserHistorial> result = userHistorialService.getLastUserHistorial(userInfo);

        // Then
        assertFalse(result.isPresent());
        verify(userHistorialRespository, times(1)).findFirstByUserInfoOrderByDateDesc(userInfo);
    }

    @Test
    void testGetDeckWordTranslationHistorialByDayMillis() {
        // Given
        Long dayMillis = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        List<UserHistorial> historialList = Arrays.asList(userHistorial1, userHistorial2);

        UserHistorialDTO dto1 = new UserHistorialDTO();
        dto1.setId(1);
        dto1.setDeck(deckDTO);
        dto1.setSuccess(success1DTO);
        dto1.setAttempts(1);

        UserHistorialDTO dto2 = new UserHistorialDTO();
        dto2.setId(2);
        dto2.setDeck(deckDTO);
        dto2.setSuccess(success2DTO);
        dto2.setAttempts(1);

        // When
        when(userHistorialRespository.findByUserInfoAndDateBetweenOrderByDateDesc(
                eq(userInfo), eq(dayMillis), eq(dayMillis + ONE_DAY_MILLIS - 1))).thenReturn(historialList);
        when(userHistorialMapper.toDTO(userHistorial1)).thenReturn(dto1);
        when(userHistorialMapper.toDTO(userHistorial2)).thenReturn(dto2);

        List<UserHistorialDTO> result = userHistorialService.getDeckWordTranslationHistorialByDayMillis(userInfo, dayMillis);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userHistorialRespository, times(1))
                .findByUserInfoAndDateBetweenOrderByDateDesc(eq(userInfo), eq(dayMillis), eq(dayMillis + ONE_DAY_MILLIS - 1));
        verify(userHistorialMapper, times(2)).toDTO(any(UserHistorial.class));
    }

    @Test
    void testGetDeckWordTranslationHistorialByDayMillisWithSameWordTranslation() {
        // Given
        Long dayMillis = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        Success success3 = new Success();
        success3.setId(3);
        success3.setSuccessEnum(Success.SuccessEnum.CORRECT);
        success3.setScore(1.0);

        UserHistorial userHistorial2SameTranslation = new UserHistorial();
        userHistorial2SameTranslation.setId(3);
        userHistorial2SameTranslation.setUserInfo(userInfo);
        userHistorial2SameTranslation.setDate(System.currentTimeMillis());
        userHistorial2SameTranslation.setSuccess(success3);
        userHistorial2SameTranslation.setDeck(deck);
        userHistorial2SameTranslation.setDeckWordPhraseTranslation(translation1);

        List<UserHistorial> historialList = Arrays.asList(userHistorial1, userHistorial2SameTranslation);

        UserHistorialDTO dto1 = new UserHistorialDTO();
        dto1.setId(1);
        dto1.setDeck(deckDTO);
        dto1.setSuccess(success1DTO);
        dto1.setAttempts(1);

        // When
        when(userHistorialRespository.findByUserInfoAndDateBetweenOrderByDateDesc(
                eq(userInfo), eq(dayMillis), eq(dayMillis + ONE_DAY_MILLIS - 1))).thenReturn(historialList);
        when(userHistorialMapper.toDTO(userHistorial1)).thenReturn(dto1);

        List<UserHistorialDTO> result = userHistorialService.getDeckWordTranslationHistorialByDayMillis(userInfo, dayMillis);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getAttempts());
        assertEquals(1, result.get(0).getSuccess().getScore());
        verify(userHistorialRespository, times(1))
                .findByUserInfoAndDateBetweenOrderByDateDesc(eq(userInfo), eq(dayMillis), eq(dayMillis + ONE_DAY_MILLIS - 1));
        verify(userHistorialMapper, times(1)).toDTO(any(UserHistorial.class));
    }

    @Test
    void testGetDeckWordTranslationHistorialByDayMillisEmpty() {
        // Given
        Long dayMillis = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        List<UserHistorial> emptyList = new ArrayList<>();

        // When
        when(userHistorialRespository.findByUserInfoAndDateBetweenOrderByDateDesc(
                eq(userInfo), eq(dayMillis), eq(dayMillis + ONE_DAY_MILLIS - 1))).thenReturn(emptyList);

        // Then
        UserHistorialNotFoundException exception = assertThrows(UserHistorialNotFoundException.class, () -> {
            userHistorialService.getDeckWordTranslationHistorialByDayMillis(userInfo, dayMillis);
        });

        assertTrue(exception.getMessage().contains("Not found any historial for the day"));
        verify(userHistorialRespository, times(1))
                .findByUserInfoAndDateBetweenOrderByDateDesc(eq(userInfo), eq(dayMillis), eq(dayMillis + ONE_DAY_MILLIS - 1));
    }

    @Test
    void testGetDeckWordTranslationHistorialByDayMillisNullArgument() {
        // Given
        Long dayMillis = null;

        // Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userHistorialService.getDeckWordTranslationHistorialByDayMillis(userInfo, dayMillis);
        });

        assertEquals("The argument is null", exception.getMessage());
        verify(userHistorialRespository, never())
                .findByUserInfoAndDateBetweenOrderByDateDesc(any(), anyLong(), anyLong());
    }

    @Test
    void testPostUserHistorial() {
        // Given
        UserHistorial newHistorial = new UserHistorial();
        newHistorial.setUserInfo(userInfo);
        newHistorial.setDate(System.currentTimeMillis());
        newHistorial.setSuccess(success1);
        newHistorial.setDeck(deck);
        newHistorial.setDeckWordPhraseTranslation(translation1);

        UserHistorial savedHistorial = new UserHistorial();
        savedHistorial.setId(3);
        savedHistorial.setUserInfo(userInfo);
        savedHistorial.setDate(System.currentTimeMillis());
        savedHistorial.setSuccess(success2);
        savedHistorial.setDeck(deck);
        savedHistorial.setDeckWordPhraseTranslation(translation1);

        // When
        when(userHistorialRespository.save(newHistorial)).thenReturn(savedHistorial);

        UserHistorial result = userHistorialService.postUserHistorial(newHistorial);

        // Then
        assertNotNull(result);
        assertEquals(3, result.getId());
        verify(userHistorialRespository, times(1)).save(newHistorial);
    }

    @Test
    void testGetUserHistorialsByUserIdAndWordId() {
        // Given
        Integer userId = 1;
        Integer wordId = 100;
        List<UserHistorial> historialList = Arrays.asList(userHistorial1, userHistorial2);

        // When
        when(userHistorialRespository.findByUserInfoIdAndDeckWordPhraseTranslationWordPhraseTranslationWordTranslationWordSenseFrWordId(
                userId, wordId)).thenReturn(historialList);

        List<UserHistorial> result = userHistorialService.getUserHistorialsByUserIdAndWordId(userId, wordId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        verify(userHistorialRespository, times(1))
                .findByUserInfoIdAndDeckWordPhraseTranslationWordPhraseTranslationWordTranslationWordSenseFrWordId(userId, wordId);
    }

    @Test
    void testGetUserHistorialsByUserIdAndWordIdEmpty() {
        // Given
        Integer userId = 1;
        Integer wordId = 100;
        List<UserHistorial> emptyList = new ArrayList<>();

        // When
        when(userHistorialRespository.findByUserInfoIdAndDeckWordPhraseTranslationWordPhraseTranslationWordTranslationWordSenseFrWordId(
                userId, wordId)).thenReturn(emptyList);

        List<UserHistorial> result = userHistorialService.getUserHistorialsByUserIdAndWordId(userId, wordId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userHistorialRespository, times(1))
                .findByUserInfoIdAndDeckWordPhraseTranslationWordPhraseTranslationWordTranslationWordSenseFrWordId(userId, wordId);
    }

    @Test
    void testGetUserHistorialsByDeckIdAndWordId() {
        // Given
        Integer deckId = 1;
        Integer wordId = 100;
        List<UserHistorial> historialList = Arrays.asList(userHistorial1, userHistorial2);

        // When
        when(userHistorialRespository.findByDeckWordPhraseTranslationDeckIdAndDeckWordPhraseTranslationWordPhraseTranslationWordTranslationWordSenseFrWordId(
                deckId, wordId)).thenReturn(historialList);

        List<UserHistorial> result = userHistorialService.getUserHistorialsByDeckIdAndWordId(deckId, wordId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        verify(userHistorialRespository, times(1))
                .findByDeckWordPhraseTranslationDeckIdAndDeckWordPhraseTranslationWordPhraseTranslationWordTranslationWordSenseFrWordId(deckId, wordId);
    }

    @Test
    void testGetUserHistorialsByDeckIdAndWordIdEmpty() {
        // Given
        Integer deckId = 1;
        Integer wordId = 100;
        List<UserHistorial> emptyList = new ArrayList<>();

        // When
        when(userHistorialRespository.findByDeckWordPhraseTranslationDeckIdAndDeckWordPhraseTranslationWordPhraseTranslationWordTranslationWordSenseFrWordId(
                deckId, wordId)).thenReturn(emptyList);

        List<UserHistorial> result = userHistorialService.getUserHistorialsByDeckIdAndWordId(deckId, wordId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userHistorialRespository, times(1))
                .findByDeckWordPhraseTranslationDeckIdAndDeckWordPhraseTranslationWordPhraseTranslationWordTranslationWordSenseFrWordId(deckId, wordId);
    }

    @Test
    void testGetUserHistorialsByDeckIdAndWordSenseId() {
        // Given
        Integer deckId = 1;
        Integer wordSenseId = 200;
        List<UserHistorial> historialList = Arrays.asList(userHistorial1, userHistorial2);

        // When
        when(userHistorialRespository.findByDeckWordPhraseTranslationDeckIdAndDeckWordPhraseTranslationWordPhraseTranslationWordTranslationWordSenseFrId(
                deckId, wordSenseId)).thenReturn(historialList);

        List<UserHistorial> result = userHistorialService.getUserHistorialsByDeckIdAndWordSenseId(deckId, wordSenseId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        verify(userHistorialRespository, times(1))
                .findByDeckWordPhraseTranslationDeckIdAndDeckWordPhraseTranslationWordPhraseTranslationWordTranslationWordSenseFrId(deckId, wordSenseId);
    }

    @Test
    void testGetUserHistorialsByDeckIdAndWordSenseIdEmpty() {
        // Given
        Integer deckId = 1;
        Integer wordSenseId = 200;
        List<UserHistorial> emptyList = new ArrayList<>();

        // When
        when(userHistorialRespository.findByDeckWordPhraseTranslationDeckIdAndDeckWordPhraseTranslationWordPhraseTranslationWordTranslationWordSenseFrId(
                deckId, wordSenseId)).thenReturn(emptyList);

        List<UserHistorial> result = userHistorialService.getUserHistorialsByDeckIdAndWordSenseId(deckId, wordSenseId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userHistorialRespository, times(1))
                .findByDeckWordPhraseTranslationDeckIdAndDeckWordPhraseTranslationWordPhraseTranslationWordTranslationWordSenseFrId(deckId, wordSenseId);
    }

    @Test
    void testGetUserHistorialsByUserIdAndWordIdNullUserId() {
        // Given
        Integer userId = null;
        Integer wordId = 100;

        // When / Then
        assertDoesNotThrow(() -> {
            userHistorialService.getUserHistorialsByUserIdAndWordId(userId, wordId);
        });

        verify(userHistorialRespository, times(1))
                .findByUserInfoIdAndDeckWordPhraseTranslationWordPhraseTranslationWordTranslationWordSenseFrWordId(userId, wordId);
    }

    @Test
    void testGetUserHistorialsByDeckIdAndWordIdNullDeckId() {
        // Given
        Integer deckId = null;
        Integer wordId = 100;

        // When / Then
        assertDoesNotThrow(() -> {
            userHistorialService.getUserHistorialsByDeckIdAndWordId(deckId, wordId);
        });

        verify(userHistorialRespository, times(1))
                .findByDeckWordPhraseTranslationDeckIdAndDeckWordPhraseTranslationWordPhraseTranslationWordTranslationWordSenseFrWordId(deckId, wordId);
    }

    @Test
    void testGetUserHistorialsByDeckIdAndWordSenseIdNullDeckId() {
        // Given
        Integer deckId = null;
        Integer wordSenseId = 200;

        // When / Then
        assertDoesNotThrow(() -> {
            userHistorialService.getUserHistorialsByDeckIdAndWordSenseId(deckId, wordSenseId);
        });

        verify(userHistorialRespository, times(1))
                .findByDeckWordPhraseTranslationDeckIdAndDeckWordPhraseTranslationWordPhraseTranslationWordTranslationWordSenseFrId(deckId, wordSenseId);
    }
}
