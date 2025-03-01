package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.WordTranslationHistorialNotFound;
import com.antonio.apprendrebackend.service.model.DailyStats;
import com.antonio.apprendrebackend.service.model.DeckWordTranslationHistorial;
import com.antonio.apprendrebackend.service.service.impl.StatsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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

    @InjectMocks
    private StatsServiceImpl statsService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDailyStatsLastWeek_ReturnsDailyStats() {
        // Given
        LocalDate today = LocalDate.now();


        List<DeckWordTranslationHistorial> mockHistorial = new ArrayList<>();
        DeckWordTranslationHistorial wordTranslationHistorial1 = new DeckWordTranslationHistorial();
        long dateHistorial1 = today.minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        wordTranslationHistorial1.setDate(dateHistorial1);
        wordTranslationHistorial1.setSuccess(3);
        mockHistorial.add(wordTranslationHistorial1);

        DeckWordTranslationHistorial wordTranslationHistorial2 = new DeckWordTranslationHistorial();
        long dateHistorial2 = today.minusDays(2).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        wordTranslationHistorial2.setDate(dateHistorial2);
        wordTranslationHistorial2.setSuccess(5);
        mockHistorial.add(wordTranslationHistorial2);

        when(deckWordTranslationHistorialService.getWordTranslationHistorialLastWeek()).thenReturn(Optional.of(mockHistorial));

        // When
        List<DailyStats> result = statsService.getDailyStatsLastWeek();
        DailyStats statsTMinus1 = result.stream()
                .filter(ds -> ds.getDate().equals(today.minusDays(1)))
                .findFirst()
                .orElse(null);
        DailyStats statsTMinus2 = result.stream()
                .filter(ds -> ds.getDate().equals(today.minusDays(2)))
                .findFirst()
                .orElse(null);
        // Then
        assertNotNull(result);
        assertEquals(7, result.size());
        assertNotNull(statsTMinus1);
        assertNotNull(statsTMinus2);
        assertEquals(3, statsTMinus1.getTotalSuccesses());
        assertEquals(5, statsTMinus2.getTotalSuccesses());
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
