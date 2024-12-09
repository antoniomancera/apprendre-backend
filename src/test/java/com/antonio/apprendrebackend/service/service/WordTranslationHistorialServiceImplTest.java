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
import java.util.Optional;

import static com.antonio.apprendrebackend.service.util.GeneralConstants.ONE_DAY_MILLIS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        long startMillis = today.minusDays(6).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long endMillis = today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1;


        List<WordTranslationHistorial> mockList = new ArrayList<>();
        WordTranslationHistorial wordTranslationHistorial = new WordTranslationHistorial();
        wordTranslationHistorial.setDate(startMillis + 100000);
        mockList.add(wordTranslationHistorial);


        when(wordTranslationHistorialRepository.findByDateGreaterThanEqualAndDateLessThanOrderByDateDesc(
                eq(startMillis), eq(endMillis)))
                .thenReturn(Optional.of(mockList));

        // When
        Optional<List<WordTranslationHistorial>> result = wordTranslationHistorialService.getWordTranslationHistorialLastWeek();

        // Then
        assertTrue(result.isPresent());
        assertEquals(mockList.size(), result.get().size());
        verify(wordTranslationHistorialRepository, times(1))
                .findByDateGreaterThanEqualAndDateLessThanOrderByDateDesc(eq(startMillis), eq(endMillis));
    }


    @Test
    void testGetWordTranslationHistorialLastWeek_ReturnsEmptyOptional() {
        // Given
        LocalDate today = LocalDate.now();
        long endMillis = System.currentTimeMillis();
        long startMillis = today.minusDays(6).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        when(wordTranslationHistorialRepository.findByDateGreaterThanEqualAndDateLessThanOrderByDateDesc(
                eq(startMillis),
                anyLong()
        )).thenReturn(Optional.empty());

        // When
        Optional<List<WordTranslationHistorial>> result = wordTranslationHistorialService.getWordTranslationHistorialLastWeek();

        // Then
        assertTrue(result.isEmpty());
        verify(wordTranslationHistorialRepository, times(1)).findByDateGreaterThanEqualAndDateLessThanOrderByDateDesc(
                eq(startMillis),
                anyLong()
        );
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