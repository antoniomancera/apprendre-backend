package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.DeckWordTranslationHistorialDTO;
import com.antonio.apprendrebackend.service.exception.DeckWordTranslationHistorialNotFoundException;
import com.antonio.apprendrebackend.service.mapper.DeckWordTranslationHistorialMapper;
import com.antonio.apprendrebackend.service.mapper.WordTranslationMapper;
import com.antonio.apprendrebackend.service.model.DeckWordTranslationHistorial;
import com.antonio.apprendrebackend.service.model.WordFr;
import com.antonio.apprendrebackend.service.model.WordSp;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import com.antonio.apprendrebackend.service.repository.DeckWordTranslationHistorialRespository;
import com.antonio.apprendrebackend.service.service.impl.DeckWordTranslationHistorialServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.antonio.apprendrebackend.service.util.GeneralConstants.ONE_DAY_MILLIS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeckWordTranslationHistorialServiceTest {
    @InjectMocks
    private DeckWordTranslationHistorialServiceImpl deckWordTranslationHistorialService;

    @Mock
    private DeckWordTranslationHistorialRespository deckWordTranslationHistorialRespository;

    @Mock
    private DeckWordTranslationHistorialMapper deckWordTranslationHistorialMapper;

    @Mock
    WordTranslationMapper wordTranslationMapper;

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

    @Test
    void testGetDeckWordTranslationHistorialByDayMillis_ThrowsExceptionWhenDayMillisIsNull() {
        // Given
        Long dayMillis = null;

        // When / Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> deckWordTranslationHistorialService.getDeckWordTranslationHistorialByDayMillis(dayMillis)
        );

        assertEquals("The argument is null", exception.getMessage());

        verify(deckWordTranslationHistorialRespository, never())
                .findByDateGreaterThanEqualAndDateLessThanOrderByDateDesc(anyLong(), anyLong());
    }

    @Test
    void testGetDeckWordTranslationHistorialByDayMillis_ThrowsExceptionWhenNoHistorialFound() {
        // Given
        Long dayMillis = 1698230400000L;
        LocalDate day = Instant.ofEpochMilli(dayMillis).atZone(ZoneId.systemDefault()).toLocalDate();

        when(deckWordTranslationHistorialRespository.findByDateGreaterThanEqualAndDateLessThanOrderByDateDesc(
                dayMillis, dayMillis + ONE_DAY_MILLIS
        )).thenReturn(Optional.empty());

        // When / Then
        DeckWordTranslationHistorialNotFoundException exception = assertThrows(
                DeckWordTranslationHistorialNotFoundException.class,
                () -> deckWordTranslationHistorialService.getDeckWordTranslationHistorialByDayMillis(dayMillis)
        );

        assertEquals(String.format("Not found any historial for the day %s", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(day)), exception.getMessage());

        verify(deckWordTranslationHistorialRespository, times(1))
                .findByDateGreaterThanEqualAndDateLessThanOrderByDateDesc(dayMillis, dayMillis + ONE_DAY_MILLIS);
    }

    @Test
    void testGetDeckWordTranslationHistorialByDayMillis_ReturnsGroupedHistorialWithoutDuplicates() {
        // Given
        Long dayMillis = 1698230400000L;
        DeckWordTranslationHistorial historial1 = new DeckWordTranslationHistorial();
        historial1.setId(1);
        historial1.setDeckId(1);
        WordFr wordFr = new WordFr("Bonjour");
        WordSp wordSp = new WordSp("Hola");
        WordTranslation wordTranslation = new WordTranslation(wordFr, wordSp);
        historial1.setWordTranslation(wordTranslation);
        historial1.setId(1);
        historial1.setSuccess(1);

        DeckWordTranslationHistorial historial2 = new DeckWordTranslationHistorial();
        historial2.setId(2);
        historial2.setDeckId(2);
        WordFr wordFr2 = new WordFr("Goodbye");
        WordSp wordSp2 = new WordSp("Adiós");
        historial2.setWordTranslation(new WordTranslation(wordFr2, wordSp2));
        historial2.setId(2);
        historial2.setSuccess(0);

        List<DeckWordTranslationHistorial> historialList = List.of(historial1, historial2);

        when(deckWordTranslationHistorialRespository.findByDateGreaterThanEqualAndDateLessThanOrderByDateDesc(
                dayMillis, dayMillis + ONE_DAY_MILLIS
        )).thenReturn(Optional.of(historialList));

        when(deckWordTranslationHistorialMapper.toDTO(historial1)).thenReturn(new DeckWordTranslationHistorialDTO(1, wordTranslationMapper.toDTO(wordTranslation), dayMillis, 1, 1, 1));
        when(deckWordTranslationHistorialMapper.toDTO(historial2)).thenReturn(new DeckWordTranslationHistorialDTO(2, wordTranslationMapper.toDTO(wordTranslation), dayMillis, 0, 1, 2));

        // When
        List<DeckWordTranslationHistorialDTO> result = deckWordTranslationHistorialService.getDeckWordTranslationHistorialByDayMillis(dayMillis);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        verify(deckWordTranslationHistorialRespository, times(1))
                .findByDateGreaterThanEqualAndDateLessThanOrderByDateDesc(dayMillis, dayMillis + ONE_DAY_MILLIS);
        verify(deckWordTranslationHistorialMapper, times(1)).toDTO(historial1);
        verify(deckWordTranslationHistorialMapper, times(1)).toDTO(historial2);
    }

    @Test
    void testGetDeckWordTranslationHistorialByDayMillis_ReturnsGroupedHistorialWithDuplicates() {
        // Given
        Long dayMillis = 1698230400000L;

        DeckWordTranslationHistorial historial1 = new DeckWordTranslationHistorial();
        historial1.setId(1);
        historial1.setDeckId(1);
        WordFr wordFr = new WordFr("Bonjour");
        WordSp wordSp = new WordSp("Hola");
        WordTranslation wordTranslation = new WordTranslation(wordFr, wordSp);
        historial1.setSuccess(1);


        DeckWordTranslationHistorial historial2 = new DeckWordTranslationHistorial();
        historial2.setId(2);
        historial2.setDeckId(1);
        WordFr wordFr2 = new WordFr("Goodbye");
        WordSp wordSp2 = new WordSp("Adiós");
        historial2.setWordTranslation(new WordTranslation(wordFr2, wordSp2));
        historial2.setSuccess(0);

        List<DeckWordTranslationHistorial> historialList = List.of(historial1, historial2);

        when(deckWordTranslationHistorialRespository.findByDateGreaterThanEqualAndDateLessThanOrderByDateDesc(
                dayMillis, dayMillis + ONE_DAY_MILLIS
        )).thenReturn(Optional.of(historialList));

        DeckWordTranslationHistorialDTO historialDTO = new DeckWordTranslationHistorialDTO(1, wordTranslationMapper.toDTO(wordTranslation), dayMillis, 1, 1, 1);
        when(deckWordTranslationHistorialMapper.toDTO(historial1)).thenReturn(historialDTO);

        // When
        List<DeckWordTranslationHistorialDTO> result = deckWordTranslationHistorialService.getDeckWordTranslationHistorialByDayMillis(dayMillis);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        DeckWordTranslationHistorialDTO resultDTO = result.get(0);
        assertEquals(1, resultDTO.getSuccess());
        assertEquals(2, resultDTO.getAttempts());

        verify(deckWordTranslationHistorialRespository, times(1))
                .findByDateGreaterThanEqualAndDateLessThanOrderByDateDesc(dayMillis, dayMillis + ONE_DAY_MILLIS);
        verify(deckWordTranslationHistorialMapper, times(1)).toDTO(historial1);
    }

}
