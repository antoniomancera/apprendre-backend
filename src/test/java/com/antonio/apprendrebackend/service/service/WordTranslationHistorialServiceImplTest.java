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

public class WordTranslationHistorialServiceImplTest {

    @InjectMocks
    private DeckWordTranslationHistorialServiceImpl deckWordTranslationHistorialService;

    @Mock
    private DeckWordTranslationHistorialRespository deckWordTranslationHistorialRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetWordTranslationHistorialLastWeek_ReturnsList() {
        // Given
        LocalDate today = LocalDate.now();
        LocalDate lastWeekStart = today.minusDays(6);
        LocalDate nextDay = today.plusDays(1);

        long startMillis = lastWeekStart.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long endMillis = nextDay.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1;

        List<DeckWordTranslationHistorial> mockList = List.of(new DeckWordTranslationHistorial(new WordTranslation(), startMillis, 1, 1));//Usando List.of para crear listas inmutables
        when(deckWordTranslationHistorialRepository.findByDateGreaterThanEqualAndDateLessThanOrderByDateDesc(startMillis, endMillis))
                .thenReturn(Optional.of(mockList));

        // When
        Optional<List<DeckWordTranslationHistorial>> result = deckWordTranslationHistorialService.getWordTranslationHistorialLastWeek();

        // Then
        assertTrue(result.isPresent());
        assertEquals(mockList.size(), result.get().size());
        assertEquals(mockList, result.get()); //Comprobamos que las listas son iguales
        verify(deckWordTranslationHistorialRepository, times(1))
                .findByDateGreaterThanEqualAndDateLessThanOrderByDateDesc(startMillis, endMillis);
    }

    @Test
    void testGetWordTranslationHistorialLastWeek_ReturnsEmptyOptional() {
        // Given
        LocalDate today = LocalDate.now();
        LocalDate nextDay = today.plusDays(1);
        LocalDate lastWeekStart = today.minusDays(6);
        long startMillis = lastWeekStart.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long endMillis = nextDay.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1;

        when(deckWordTranslationHistorialRepository.findByDateGreaterThanEqualAndDateLessThanOrderByDateDesc(startMillis, endMillis))
                .thenReturn(Optional.empty());

        // When
        Optional<List<DeckWordTranslationHistorial>> result = deckWordTranslationHistorialService.getWordTranslationHistorialLastWeek();

        // Then
        assertTrue(result.isEmpty());
        verify(deckWordTranslationHistorialRepository, times(1))
                .findByDateGreaterThanEqualAndDateLessThanOrderByDateDesc(startMillis, endMillis);
    }

    @Test
    void testGetLastWordTranslationHistorial_ReturnsWordTranslationHistorial() {
        // Given
        DeckWordTranslationHistorial mockHistorial = new DeckWordTranslationHistorial(new WordTranslation(), 2L, 100, 100);
        when(deckWordTranslationHistorialRepository.findFirstByOrderByDateDesc()).thenReturn(Optional.of(mockHistorial));//Devolvemos un optional

        // When
        Optional<DeckWordTranslationHistorial> result = deckWordTranslationHistorialService.getLastWordTranslationHistorial();//El servicio ahora devuelve un optional

        // Then
        assertTrue(result.isPresent());
        assertEquals(mockHistorial, result.get());
        verify(deckWordTranslationHistorialRepository, times(1)).findFirstByOrderByDateDesc();
    }

    @Test
    void testGetLastWordTranslationHistorial_ReturnsEmptyOptional() {
        // Given
        when(deckWordTranslationHistorialRepository.findFirstByOrderByDateDesc()).thenReturn(Optional.empty());

        // When
        Optional<DeckWordTranslationHistorial> result = deckWordTranslationHistorialService.getLastWordTranslationHistorial();

        // Then
        assertTrue(result.isEmpty());
        verify(deckWordTranslationHistorialRepository, times(1)).findFirstByOrderByDateDesc();
    }
}