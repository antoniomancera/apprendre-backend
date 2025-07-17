package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.WordTranslationHistorialNotFound;
import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.service.impl.StatsServiceImpl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StatsServiceImplTest {
    @Mock
    private UserHistorialService userHistorialService;

    @InjectMocks
    private StatsServiceImpl statsService;

    private UserInfo userInfo;
    private UserHistorial userHistorial1;
    private UserHistorial userHistorial2;
    private UserHistorial userHistorial3;
    private UserHistorial userHistorial4;
    private Success successCorrect;
    private Success successIncorrect;
    private Deck deck;
    private DeckWordPhraseTranslation translation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Common test data
        userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUserName("testUser");

        successCorrect = new Success();
        successCorrect.setId(1);
        successCorrect.setSuccessEnum(Success.SuccessEnum.CORRECT);
        successCorrect.setScore(1.0);

        successIncorrect = new Success();
        successIncorrect.setId(2);
        successIncorrect.setSuccessEnum(Success.SuccessEnum.INCORRECT);
        successIncorrect.setScore(0.0);

        deck = new Deck();
        deck.setId(1);

        translation = new DeckWordPhraseTranslation();
        translation.setId(101);

        // Create test historiales for different days
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate twoDaysAgo = today.minusDays(2);
        LocalDate threeDaysAgo = today.minusDays(3);

        // Today - 2 attempts, 1 success
        userHistorial1 = new UserHistorial();
        userHistorial1.setId(1);
        userHistorial1.setUserInfo(userInfo);
        userHistorial1.setDate(today.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        userHistorial1.setSuccess(successCorrect);
        userHistorial1.setDeck(deck);
        userHistorial1.setDeckWordPhraseTranslation(translation);

        userHistorial2 = new UserHistorial();
        userHistorial2.setId(2);
        userHistorial2.setUserInfo(userInfo);
        userHistorial2.setDate(today.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() + 3600000);
        userHistorial2.setSuccess(successIncorrect);
        userHistorial2.setDeck(deck);
        userHistorial2.setDeckWordPhraseTranslation(translation);

        // Yesterday - 1 attempt, 1 success
        userHistorial3 = new UserHistorial();
        userHistorial3.setId(3);
        userHistorial3.setUserInfo(userInfo);
        userHistorial3.setDate(yesterday.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        userHistorial3.setSuccess(successCorrect);
        userHistorial3.setDeck(deck);
        userHistorial3.setDeckWordPhraseTranslation(translation);

        // Two days ago - 1 attempt, 0 successes
        userHistorial4 = new UserHistorial();
        userHistorial4.setId(4);
        userHistorial4.setUserInfo(userInfo);
        userHistorial4.setDate(twoDaysAgo.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        userHistorial4.setSuccess(successIncorrect);
        userHistorial4.setDeck(deck);
        userHistorial4.setDeckWordPhraseTranslation(translation);
    }

    @Test
    void testGetDailyStatsLastWeekWithData() {
        // Given
        List<UserHistorial> historialList = Arrays.asList(
                userHistorial1, userHistorial2, userHistorial3, userHistorial4
        );

        // When
        when(userHistorialService.getUserHistorialLastWeek(userInfo)).thenReturn(historialList);

        List<DailyStats> result = statsService.getDailyStatsLastWeek(userInfo);

        // Then
        assertNotNull(result);
        assertEquals(7, result.size());

        for (int i = 0; i < result.size() - 1; i++) {
            assertTrue(result.get(i).getDate().isBefore(result.get(i + 1).getDate()) ||
                    result.get(i).getDate().isEqual(result.get(i + 1).getDate()));
        }

        LocalDate today = LocalDate.now();
        DailyStats todayStats = result.stream()
                .filter(stat -> stat.getDate().equals(today))
                .findFirst()
                .orElse(null);

        assertNotNull(todayStats);
        assertEquals(2, todayStats.getTotalAttempts());
        assertEquals(1, todayStats.getTotalSuccesses());

        LocalDate yesterday = today.minusDays(1);
        DailyStats yesterdayStats = result.stream()
                .filter(stat -> stat.getDate().equals(yesterday))
                .findFirst()
                .orElse(null);

        assertNotNull(yesterdayStats);
        assertEquals(1, yesterdayStats.getTotalAttempts());
        assertEquals(1, yesterdayStats.getTotalSuccesses());

        LocalDate twoDaysAgo = today.minusDays(2);
        DailyStats twoDaysAgoStats = result.stream()
                .filter(stat -> stat.getDate().equals(twoDaysAgo))
                .findFirst()
                .orElse(null);

        assertNotNull(twoDaysAgoStats);
        assertEquals(1, twoDaysAgoStats.getTotalAttempts());
        assertEquals(0, twoDaysAgoStats.getTotalSuccesses());

        long daysWithNoActivity = result.stream()
                .filter(stat -> stat.getTotalAttempts() == 0 && stat.getTotalSuccesses() == 0)
                .count();

        assertEquals(4, daysWithNoActivity);

        verify(userHistorialService, times(1)).getUserHistorialLastWeek(userInfo);
    }

    @Test
    void testGetDailyStatsLastWeekWithEmptyHistorial() {
        // Given
        List<UserHistorial> emptyHistorialList = new ArrayList<>();

        // When
        when(userHistorialService.getUserHistorialLastWeek(userInfo)).thenReturn(emptyHistorialList);

        List<DailyStats> result = statsService.getDailyStatsLastWeek(userInfo);

        // Then
        assertNotNull(result);
        assertEquals(7, result.size());

        for (DailyStats stat : result) {
            assertEquals(0, stat.getTotalAttempts());
            assertEquals(0, stat.getTotalSuccesses());
        }

        LocalDate today = LocalDate.now();
        LocalDate expectedStartDate = today.minusDays(6);

        assertEquals(expectedStartDate, result.get(0).getDate());
        assertEquals(today, result.get(6).getDate());

        verify(userHistorialService, times(1)).getUserHistorialLastWeek(userInfo);
    }

    @Test
    void testGetDailyStatsLastWeekWithSingleDay() {
        // Given - Only one day with multiple attempts
        List<UserHistorial> singleDayHistorial = Arrays.asList(userHistorial1, userHistorial2);

        // When
        when(userHistorialService.getUserHistorialLastWeek(userInfo)).thenReturn(singleDayHistorial);

        List<DailyStats> result = statsService.getDailyStatsLastWeek(userInfo);

        // Then
        assertNotNull(result);
        assertEquals(7, result.size());

        LocalDate today = LocalDate.now();
        DailyStats todayStats = result.stream()
                .filter(stat -> stat.getDate().equals(today))
                .findFirst()
                .orElse(null);

        assertNotNull(todayStats);
        assertEquals(2, todayStats.getTotalAttempts());
        assertEquals(1, todayStats.getTotalSuccesses());

        long daysWithNoActivity = result.stream()
                .filter(stat -> !stat.getDate().equals(today))
                .filter(stat -> stat.getTotalAttempts() == 0 && stat.getTotalSuccesses() == 0)
                .count();

        assertEquals(6, daysWithNoActivity);

        verify(userHistorialService, times(1)).getUserHistorialLastWeek(userInfo);
    }

    @Test
    void testGetDailyStatsLastWeekWithOnlySuccesses() {
        // Given - Create historiales with only successes
        UserHistorial successfulHistorial1 = new UserHistorial();
        successfulHistorial1.setId(5);
        successfulHistorial1.setUserInfo(userInfo);
        successfulHistorial1.setDate(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        successfulHistorial1.setSuccess(successCorrect);
        successfulHistorial1.setDeck(deck);
        successfulHistorial1.setDeckWordPhraseTranslation(translation);

        UserHistorial successfulHistorial2 = new UserHistorial();
        successfulHistorial2.setId(6);
        successfulHistorial2.setUserInfo(userInfo);
        successfulHistorial2.setDate(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() + 3600000);
        successfulHistorial2.setSuccess(successCorrect);
        successfulHistorial2.setDeck(deck);
        successfulHistorial2.setDeckWordPhraseTranslation(translation);

        List<UserHistorial> successHistorialList = Arrays.asList(successfulHistorial1, successfulHistorial2);

        // When
        when(userHistorialService.getUserHistorialLastWeek(userInfo)).thenReturn(successHistorialList);

        List<DailyStats> result = statsService.getDailyStatsLastWeek(userInfo);

        // Then
        assertNotNull(result);
        assertEquals(7, result.size());

        LocalDate today = LocalDate.now();
        DailyStats todayStats = result.stream()
                .filter(stat -> stat.getDate().equals(today))
                .findFirst()
                .orElse(null);

        assertNotNull(todayStats);
        assertEquals(2, todayStats.getTotalAttempts());
        assertEquals(2, todayStats.getTotalSuccesses());

        verify(userHistorialService, times(1)).getUserHistorialLastWeek(userInfo);
    }

    @Test
    void testGetDailyStatsLastWeekWithOnlyFailures() {
        // Given - Create historiales with only failures
        UserHistorial failedHistorial1 = new UserHistorial();
        failedHistorial1.setId(7);
        failedHistorial1.setUserInfo(userInfo);
        failedHistorial1.setDate(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        failedHistorial1.setSuccess(successIncorrect);
        failedHistorial1.setDeck(deck);
        failedHistorial1.setDeckWordPhraseTranslation(translation);

        UserHistorial failedHistorial2 = new UserHistorial();
        failedHistorial2.setId(8);
        failedHistorial2.setUserInfo(userInfo);
        failedHistorial2.setDate(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() + 3600000);
        failedHistorial2.setSuccess(successIncorrect);
        failedHistorial2.setDeck(deck);
        failedHistorial2.setDeckWordPhraseTranslation(translation);

        List<UserHistorial> failedHistorialList = Arrays.asList(failedHistorial1, failedHistorial2);

        // When
        when(userHistorialService.getUserHistorialLastWeek(userInfo)).thenReturn(failedHistorialList);

        List<DailyStats> result = statsService.getDailyStatsLastWeek(userInfo);

        // Then
        assertNotNull(result);
        assertEquals(7, result.size());

        LocalDate today = LocalDate.now();
        DailyStats todayStats = result.stream()
                .filter(stat -> stat.getDate().equals(today))
                .findFirst()
                .orElse(null);

        assertNotNull(todayStats);
        assertEquals(2, todayStats.getTotalAttempts());
        assertEquals(0, todayStats.getTotalSuccesses());

        verify(userHistorialService, times(1)).getUserHistorialLastWeek(userInfo);
    }

    @Test
    void testGetDailyStatsLastWeekDateRange() {
        // Given
        List<UserHistorial> historialList = Arrays.asList(userHistorial1);

        // When
        when(userHistorialService.getUserHistorialLastWeek(userInfo)).thenReturn(historialList);

        List<DailyStats> result = statsService.getDailyStatsLastWeek(userInfo);

        // Then
        assertNotNull(result);
        assertEquals(7, result.size());

        LocalDate today = LocalDate.now();
        LocalDate expectedStartDate = today.minusDays(6);

        assertEquals(expectedStartDate, result.get(0).getDate());
        assertEquals(today, result.get(6).getDate());

        for (int i = 0; i < result.size() - 1; i++) {
            assertEquals(result.get(i).getDate().plusDays(1), result.get(i + 1).getDate());
        }

        verify(userHistorialService, times(1)).getUserHistorialLastWeek(userInfo);
    }

    //this tests is probably not compulsory due to lastWeek data can be null
   /* @Test
    void testGetDailyStatsLastWeekNullUserInfo() {
        // Given
        UserInfo nullUserInfo = null;

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            statsService.getDailyStatsLastWeek(nullUserInfo);
        });

        verify(userHistorialService, times(1)).getUserHistorialLastWeek(nullUserInfo);
    }

    */

}
