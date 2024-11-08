package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.WordTranslationHistorial;
import com.antonio.apprendrebackend.service.repository.WordTranslationHistorialRepository;
import com.antonio.apprendrebackend.service.service.impl.WordTranslationHistorialServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class WordTranslationHistorialServiceImplTest {

    @InjectMocks
    private WordTranslationHistorialServiceImpl wordTranslationHistorialService;

    @Mock
    private WordTranslationHistorialRepository wordTranslationHistorialRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWordTranslationHistorialLastWeek_ReturnsList() {
        // Given
        LocalDate today = LocalDate.now();
        long endMillis = System.currentTimeMillis();
        long startMillis = today.minusDays(6).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        List<WordTranslationHistorial> mockList = new ArrayList<>();
        mockList.add(new WordTranslationHistorial(/* Initialize with mock data */));

        when(wordTranslationHistorialRepository.findByDateGreaterThanEqualAndDateLessThan(startMillis, endMillis))
                .thenReturn(mockList);

        // When
        List<WordTranslationHistorial> result = wordTranslationHistorialService.getWordTranslationHistorialLastWeek();

        // Then
        assertEquals(mockList.size(), result.size());
        verify(wordTranslationHistorialRepository, times(1)).findByDateGreaterThanEqualAndDateLessThan(startMillis, endMillis);
    }

    @Test
    void testGetWordTranslationHistorialLastWeek_ReturnsEmptyList() {
        // Given
        LocalDate today = LocalDate.now();
        long endMillis = System.currentTimeMillis();
        long startMillis = today.minusDays(6).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        when(wordTranslationHistorialRepository.findByDateGreaterThanEqualAndDateLessThan(startMillis, endMillis))
                .thenReturn(new ArrayList<>());

        // When
        List<WordTranslationHistorial> result = wordTranslationHistorialService.getWordTranslationHistorialLastWeek();

        // Then
        assertEquals(0, result.size());
        verify(wordTranslationHistorialRepository, times(1)).findByDateGreaterThanEqualAndDateLessThan(startMillis, endMillis);
    }

    @Test
    void testGetLastWordTranslationHistorial_ReturnsWordTranslationHistorial() {
        // Given
        WordTranslationHistorial mockHistorial = new WordTranslationHistorial(/* Initialize with mock data */);
        when(wordTranslationHistorialRepository.findFirstByOrderByDateDesc()).thenReturn(mockHistorial);

        // When
        WordTranslationHistorial result = wordTranslationHistorialService.getLastWordTranslationHistorial();

        // Then
        assertEquals(mockHistorial, result);
        verify(wordTranslationHistorialRepository, times(1)).findFirstByOrderByDateDesc();
    }

    @Test
    void testGetLastWordTranslationHistorial_ReturnsNull() {
        // Given
        when(wordTranslationHistorialRepository.findFirstByOrderByDateDesc()).thenReturn(null);

        // When
        WordTranslationHistorial result = wordTranslationHistorialService.getLastWordTranslationHistorial();

        // Then
        assertEquals(null, result);
        verify(wordTranslationHistorialRepository, times(1)).findFirstByOrderByDateDesc();
    }
}