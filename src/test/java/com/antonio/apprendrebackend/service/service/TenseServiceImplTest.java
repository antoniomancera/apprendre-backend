package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.TenseNotFoundException;
import com.antonio.apprendrebackend.service.model.Language;
import com.antonio.apprendrebackend.service.model.Tense;
import com.antonio.apprendrebackend.service.repository.TenseRepository;
import com.antonio.apprendrebackend.service.service.impl.TenseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class TenseServiceImplTest {
    @Mock
    private TenseRepository tenseRepository;

    @InjectMocks
    private TenseServiceImpl tenseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetByLanguageWithMultipleResults() {
        // Given
        Language language = new Language();
        language.setId(1);
        language.setName("Spanish");

        Tense tense1 = new Tense();
        tense1.setId(1);
        tense1.setLanguage(language);

        Tense tense2 = new Tense();
        tense2.setId(2);
        tense2.setLanguage(language);

        Tense tense3 = new Tense();
        tense3.setId(3);
        tense3.setLanguage(language);

        List<Tense> expectedTenses = Arrays.asList(tense1, tense2, tense3);

        // When
        when(tenseRepository.findByLanguage(language)).thenReturn(expectedTenses);

        List<Tense> result = tenseService.getByLanguage(language);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals(3, result.get(2).getId());
        assertEquals(language, result.get(0).getLanguage());
        assertEquals(language, result.get(1).getLanguage());
        assertEquals(language, result.get(2).getLanguage());
        verify(tenseRepository, times(1)).findByLanguage(language);
    }

    @Test
    void testGetByLanguageEmpty() {
        // Given
        Language language = new Language();
        language.setId(2);
        language.setName("French");

        // When
        when(tenseRepository.findByLanguage(language)).thenReturn(Arrays.asList());

        // Then
        TenseNotFoundException exception = assertThrows(TenseNotFoundException.class, () -> {
            tenseService.getByLanguage(language);
        });

        assertEquals("Not found any tense for the language French", exception.getMessage());
        verify(tenseRepository, times(1)).findByLanguage(language);
    }

    @Test
    void testGetByLanguageWithNullLanguage() {
        // Given
        Language language = new Language();
        language.setId(3);
        language.setName("nonExist");

        // When
        when(tenseRepository.findByLanguage(language)).thenReturn(Arrays.asList());

        // Then
        TenseNotFoundException exception = assertThrows(TenseNotFoundException.class, () -> {
            tenseService.getByLanguage(language);
        });

        assertTrue(exception.getMessage().contains("Not found any tense for the language"));
        verify(tenseRepository, times(1)).findByLanguage(language);
    }

    @Test
    void testGetByLanguageSingleResult() {
        // Given
        Language language = new Language();
        language.setId(3);
        language.setName("English");

        Tense tense = new Tense();
        tense.setId(1);
        tense.setLanguage(language);

        List<Tense> expectedTenses = Arrays.asList(tense);

        // When
        when(tenseRepository.findByLanguage(language)).thenReturn(expectedTenses);

        List<Tense> result = tenseService.getByLanguage(language);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(language, result.get(0).getLanguage());
        assertEquals("English", result.get(0).getLanguage().getName());
        verify(tenseRepository, times(1)).findByLanguage(language);
    }

    @Test
    void testGetByLanguageWithDifferentLanguages() {
        // Given
        Language language1 = new Language();
        language1.setId(1);
        language1.setName("Spanish");

        Language language2 = new Language();
        language2.setId(2);
        language2.setName("Italian");

        Tense tense1 = new Tense();
        tense1.setId(1);
        tense1.setLanguage(language1);

        Tense tense2 = new Tense();
        tense2.setId(2);
        tense2.setLanguage(language1);

        List<Tense> expectedTenses1 = Arrays.asList(tense1, tense2);
        List<Tense> expectedTenses2 = Arrays.asList();

        // When
        when(tenseRepository.findByLanguage(language1)).thenReturn(expectedTenses1);
        when(tenseRepository.findByLanguage(language2)).thenReturn(expectedTenses2);

        List<Tense> result1 = tenseService.getByLanguage(language1);

        // Then
        assertNotNull(result1);
        assertEquals(2, result1.size());
        assertEquals(1, result1.get(0).getId());
        assertEquals(2, result1.get(1).getId());

        TenseNotFoundException exception = assertThrows(TenseNotFoundException.class, () -> {
            tenseService.getByLanguage(language2);
        });

        assertEquals("Not found any tense for the language Italian", exception.getMessage());

        verify(tenseRepository, times(1)).findByLanguage(language1);
        verify(tenseRepository, times(1)).findByLanguage(language2);
    }

    @Test
    void testGetByLanguageWithLanguageNameNull() {
        // Given
        Language language = new Language();
        language.setId(4);
        language.setName(null);

        // When
        when(tenseRepository.findByLanguage(language)).thenReturn(Arrays.asList());

        // Then
        TenseNotFoundException exception = assertThrows(TenseNotFoundException.class, () -> {
            tenseService.getByLanguage(language);
        });

        assertEquals("Not found any tense for the language null", exception.getMessage());
        verify(tenseRepository, times(1)).findByLanguage(language);
    }

    @Test
    void testGetByLanguageWithCompleteLanguage() {
        // Given
        Language language = new Language();
        language.setId(5);
        language.setName("Portuguese");

        Tense tense = new Tense();
        tense.setId(5);
        tense.setLanguage(language);

        List<Tense> expectedTenses = Arrays.asList(tense);

        // When
        when(tenseRepository.findByLanguage(language)).thenReturn(expectedTenses);

        List<Tense> result = tenseService.getByLanguage(language);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(5, result.get(0).getId());
        assertEquals(5, result.get(0).getLanguage().getId());
        assertEquals("Portuguese", result.get(0).getLanguage().getName());
        verify(tenseRepository, times(1)).findByLanguage(language);
    }

    @Test
    void testGetByLanguageExceptionMessage() {
        // Given
        Language language = new Language();
        language.setId(6);
        language.setName("German");

        // When
        when(tenseRepository.findByLanguage(language)).thenReturn(Arrays.asList());

        // Then
        TenseNotFoundException exception = assertThrows(TenseNotFoundException.class, () -> {
            tenseService.getByLanguage(language);
        });

        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("German"));
        assertTrue(exception.getMessage().startsWith("Not found any tense for the language"));
        verify(tenseRepository, times(1)).findByLanguage(language);
    }
}
