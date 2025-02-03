package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.WordTranslationHistorialNotFound;
import com.antonio.apprendrebackend.service.model.DailyStats;
import com.antonio.apprendrebackend.service.model.DeckWordTranslationHistorial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StatsServiceImplTest {
    @Mock
    DeckWordTranslationHistorialService deckWordTranslationHistorialService;

    @Mock
    StatsService statsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDailyStatsLastWeek_ReturnsDailyStats() {
        // Given
        LocalDate today = LocalDate.now();
        long endMillis = System.currentTimeMillis();
        long startMillis = today.minusDays(6).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        List<DeckWordTranslationHistorial> mockHistorial = new ArrayList<>();
        DeckWordTranslationHistorial wordTranslationHistorial1 = new DeckWordTranslationHistorial();
        wordTranslationHistorial1.setDate(today.minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        mockHistorial.add(wordTranslationHistorial1);

        DeckWordTranslationHistorial wordTranslationHistorial2 = new DeckWordTranslationHistorial();
        wordTranslationHistorial2.setDate(today.minusDays(2).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        mockHistorial.add(wordTranslationHistorial2);

        when(deckWordTranslationHistorialService.getWordTranslationHistorialLastWeek()).thenReturn(Optional.of(mockHistorial));

        // When
        List<DailyStats> result = statsService.getDailyStatsLastWeek();

        // Then
        assertNotNull(result);
        assertEquals(7, result.size());
        assertEquals(3, result.get(0).getTotalSuccesses());
        assertEquals(5, result.get(1).getTotalSuccesses());
        verify(deckWordTranslationHistorialService, times(1)).getWordTranslationHistorialLastWeek();
    }

    @Test
    void testGetDailyStatsLastWeek_ThrowsWordTranslationHistorialNotFound() {
        // Given
        when(deckWordTranslationHistorialService.getWordTranslationHistorialLastWeek()).thenReturn(Optional.empty());

        // When / Then
        WordTranslationHistorialNotFound exception = assertThrows(WordTranslationHistorialNotFound.class,
                () -> statsService.getDailyStatsLastWeek());

        assertEquals("Not found any Word Translation Historial in last week", exception.getMessage());
        verify(deckWordTranslationHistorialService, times(1)).getWordTranslationHistorialLastWeek();
    }


}
