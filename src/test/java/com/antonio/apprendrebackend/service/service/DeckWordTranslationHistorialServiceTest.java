package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.DeckWordTranslationHistorial;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import com.antonio.apprendrebackend.service.repository.DeckWordTranslationHistorialRespository;
import com.antonio.apprendrebackend.service.service.impl.DeckWordTranslationHistorialServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class DeckWordTranslationHistorialServiceTest {
    @InjectMocks
    private DeckWordTranslationHistorialServiceImpl deckWordTranslationHistorialService;

    @Mock
    private DeckWordTranslationHistorialRespository deckWordTranslationHistorialRespository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWordTranslationHistorialLastWeek_ReturnsHistorial() {
        // Given
        LocalDate today = LocalDate.now();
        long startMillis = today.minusDays(6).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long endMillis = System.currentTimeMillis();

        DeckWordTranslationHistorial historial1 = new DeckWordTranslationHistorial();
        DeckWordTranslationHistorial historial2 = new DeckWordTranslationHistorial();
        List<DeckWordTranslationHistorial> mockHistorialList = List.of(historial1, historial2);

        when(deckWordTranslationHistorialRespository.findByDateGreaterThanEqualAndDateLessThanOrderByDateDesc(
                eq(startMillis),
                argThat(end -> Math.abs(end - endMillis) < 100)))
                .thenReturn(Optional.of(mockHistorialList));

        // When
        Optional<List<DeckWordTranslationHistorial>> result = deckWordTranslationHistorialService.getWordTranslationHistorialLastWeek();

        // Then
        assertTrue(result.isPresent(), "El resultado debería estar presente.");
        assertEquals(2, result.get().size(), "El tamaño de la lista debería ser 2.");
        verify(deckWordTranslationHistorialRespository, times(1))
                .findByDateGreaterThanEqualAndDateLessThanOrderByDateDesc(eq(startMillis), anyLong());
    }

    @Test
    void testGetLastWordTranslationHistorial_ReturnsLastHistorial() {
        // Given
        DeckWordTranslationHistorial mockHistorial = new DeckWordTranslationHistorial();
        mockHistorial.setDate(System.currentTimeMillis());

        when(deckWordTranslationHistorialRespository.findFirstByOrderByDateDesc())
                .thenReturn(Optional.of(mockHistorial));

        // When
        Optional<DeckWordTranslationHistorial> result = deckWordTranslationHistorialService.getLastWordTranslationHistorial();

        // Then
        assertTrue(result.isPresent());
        assertEquals(mockHistorial, result.get());
        verify(deckWordTranslationHistorialRespository, times(1)).findFirstByOrderByDateDesc();
    }

}
