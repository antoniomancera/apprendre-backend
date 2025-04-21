package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.UserHistorialDTO;
import com.antonio.apprendrebackend.service.exception.UserHistorialNotFoundException;
import com.antonio.apprendrebackend.service.mapper.UserHistorialMapper;
import com.antonio.apprendrebackend.service.model.DeckUserWordPhraseTranslation;
import com.antonio.apprendrebackend.service.model.UserHistorial;
import com.antonio.apprendrebackend.service.model.UserInfo;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Common test data
        userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUserName("testUser");

        DeckUserWordPhraseTranslation translation1 = new DeckUserWordPhraseTranslation();
        translation1.setId(101);

        DeckUserWordPhraseTranslation translation2 = new DeckUserWordPhraseTranslation();
        translation2.setId(102);

        userHistorial1 = new UserHistorial();
        userHistorial1.setId(1);
        userHistorial1.setUserInfo(userInfo);
        userHistorial1.setDate(System.currentTimeMillis() - 24 * 60 * 60 * 1000); // yesterday
        userHistorial1.setSuccess(1);
        userHistorial1.setDeckId(1);
        userHistorial1.setDeckUserWordPhraseTranslation(translation1);

        userHistorial2 = new UserHistorial();
        userHistorial2.setId(2);
        userHistorial2.setUserInfo(userInfo);
        userHistorial2.setDate(System.currentTimeMillis()); // today
        userHistorial2.setSuccess(0);
        userHistorial2.setDeckId(1);
        userHistorial2.setDeckUserWordPhraseTranslation(translation2);
    }

    @Test
    void testGetUserHistorialLastWeek() {
        // Given
        List<UserHistorial> historialList = Arrays.asList(userHistorial1, userHistorial2);

        LocalDate today = LocalDate.now();
        long endMillis = System.currentTimeMillis();
        long startMillis = today.minusDays(6).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

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
        dto1.setDeckId(1);
        dto1.setSuccess(1);

        UserHistorialDTO dto2 = new UserHistorialDTO();
        dto2.setId(2);
        dto2.setDeckId(1);
        dto2.setSuccess(0);

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

        // Create second historial with same deck and translation ID to test grouping
        UserHistorial userHistorial2SameTranslation = new UserHistorial();
        userHistorial2SameTranslation.setId(3);
        userHistorial2SameTranslation.setUserInfo(userInfo);
        userHistorial2SameTranslation.setDate(System.currentTimeMillis());
        userHistorial2SameTranslation.setSuccess(1);
        userHistorial2SameTranslation.setDeckId(1);
        userHistorial2SameTranslation.setDeckUserWordPhraseTranslation(userHistorial1.getDeckUserWordPhraseTranslation());

        List<UserHistorial> historialList = Arrays.asList(userHistorial1, userHistorial2SameTranslation);

        UserHistorialDTO dto1 = new UserHistorialDTO();
        dto1.setId(1);
        dto1.setDeckId(1);
        dto1.setSuccess(1);

        // When
        when(userHistorialRespository.findByUserInfoAndDateBetweenOrderByDateDesc(
                eq(userInfo), eq(dayMillis), eq(dayMillis + ONE_DAY_MILLIS - 1))).thenReturn(historialList);
        when(userHistorialMapper.toDTO(userHistorial1)).thenReturn(dto1);

        List<UserHistorialDTO> result = userHistorialService.getDeckWordTranslationHistorialByDayMillis(userInfo, dayMillis);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size()); // Should be grouped to 1 result
        assertEquals(2, result.get(0).getAttempts()); // 2 attempts
        assertEquals(2, result.get(0).getSuccess()); // Total success: 1+1
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
        newHistorial.setSuccess(1);

        UserHistorial savedHistorial = new UserHistorial();
        savedHistorial.setId(3);
        savedHistorial.setUserInfo(userInfo);
        savedHistorial.setDate(System.currentTimeMillis());
        savedHistorial.setSuccess(1);

        // When
        when(userHistorialRespository.save(newHistorial)).thenReturn(savedHistorial);

        UserHistorial result = userHistorialService.postUserHistorial(newHistorial);

        // Then
        assertNotNull(result);
        assertEquals(3, result.getId());
        verify(userHistorialRespository, times(1)).save(newHistorial);
    }
}
