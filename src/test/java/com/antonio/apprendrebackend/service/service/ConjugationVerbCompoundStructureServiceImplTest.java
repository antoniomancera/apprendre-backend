package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.ConjugationVerbCompoundStructure;
import com.antonio.apprendrebackend.service.model.Tense;
import com.antonio.apprendrebackend.service.repository.ConjugationVerbCompoundStructureRepository;
import com.antonio.apprendrebackend.service.service.impl.ConjugationVerbCompoundStructureServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class ConjugationVerbCompoundStructureServiceImplTest {
    @Mock
    private ConjugationVerbCompoundStructureRepository conjugationVerbCompoundStructureRepository;

    @InjectMocks
    private ConjugationVerbCompoundStructureServiceImpl conjugationVerbCompoundStructureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Tests for getConjugationVerbCompoundStructureByTenseId method

    @Test
    void testGetConjugationVerbCompoundStructureByTenseId_WhenExists() {
        // Given
        Integer tenseId = 1;

        Tense tense = new Tense();
        tense.setId(tenseId);

        ConjugationVerbCompoundStructure compoundStructure = new ConjugationVerbCompoundStructure();
        compoundStructure.setId(10);
        compoundStructure.setTense(tense);

        when(conjugationVerbCompoundStructureRepository.findByTenseId(tenseId))
                .thenReturn(Optional.of(compoundStructure));

        // When
        ConjugationVerbCompoundStructure result = conjugationVerbCompoundStructureService
                .getConjugationVerbCompoundStructureByTenseId(tenseId);

        // Then
        assertNotNull(result);
        assertEquals(10, result.getId());
        assertEquals(tenseId, result.getTense().getId());
        verify(conjugationVerbCompoundStructureRepository, times(2)).findByTenseId(tenseId);
    }

    @Test
    void testGetConjugationVerbCompoundStructureByTenseId_WhenNotExists() {
        // Given
        Integer tenseId = 999;

        when(conjugationVerbCompoundStructureRepository.findByTenseId(tenseId))
                .thenReturn(Optional.empty());

        // When
        ConjugationVerbCompoundStructure result = conjugationVerbCompoundStructureService
                .getConjugationVerbCompoundStructureByTenseId(tenseId);

        // Then
        assertNull(result);
        verify(conjugationVerbCompoundStructureRepository, times(1)).findByTenseId(tenseId);
    }

    @Test
    void testGetConjugationVerbCompoundStructureByTenseId_WhenExistsWithCompleteData() {
        // Given
        Integer tenseId = 5;

        Tense tense = new Tense();
        tense.setId(tenseId);
        tense.setName("Present Perfect");

        ConjugationVerbCompoundStructure compoundStructure = new ConjugationVerbCompoundStructure();
        compoundStructure.setId(25);
        compoundStructure.setTense(tense);

        when(conjugationVerbCompoundStructureRepository.findByTenseId(tenseId))
                .thenReturn(Optional.of(compoundStructure));

        // When
        ConjugationVerbCompoundStructure result = conjugationVerbCompoundStructureService
                .getConjugationVerbCompoundStructureByTenseId(tenseId);

        // Then
        assertNotNull(result);
        assertEquals(25, result.getId());
        assertNotNull(result.getTense());
        assertEquals(tenseId, result.getTense().getId());
        assertEquals("Present Perfect", result.getTense().getName());
        verify(conjugationVerbCompoundStructureRepository, times(2)).findByTenseId(tenseId);
    }

    @Test
    void testGetConjugationVerbCompoundStructureByTenseId_WhenRepositoryThrowsException() {
        // Given
        Integer tenseId = 1;

        when(conjugationVerbCompoundStructureRepository.findByTenseId(tenseId))
                .thenThrow(new RuntimeException("Database connection error"));

        // When // Then
        assertThrows(RuntimeException.class, () -> {
            conjugationVerbCompoundStructureService
                    .getConjugationVerbCompoundStructureByTenseId(tenseId);
        });

        verify(conjugationVerbCompoundStructureRepository, times(1)).findByTenseId(tenseId);
    }

    @Test
    void testGetConjugationVerbCompoundStructureByTenseId_WhenSecondCallThrowsException() {
        // Given
        Integer tenseId = 1;

        ConjugationVerbCompoundStructure compoundStructure = new ConjugationVerbCompoundStructure();
        compoundStructure.setId(10);

        when(conjugationVerbCompoundStructureRepository.findByTenseId(tenseId))
                .thenReturn(Optional.of(compoundStructure))
                .thenThrow(new RuntimeException("Second call error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            conjugationVerbCompoundStructureService
                    .getConjugationVerbCompoundStructureByTenseId(tenseId);
        });

        verify(conjugationVerbCompoundStructureRepository, times(2)).findByTenseId(tenseId);
    }

    // Edge cases

    @Test
    void testGetConjugationVerbCompoundStructureByTenseId_WithNullTenseId() {
        // Given
        Integer tenseId = null;

        when(conjugationVerbCompoundStructureRepository.findByTenseId(tenseId))
                .thenReturn(Optional.empty());

        // When
        ConjugationVerbCompoundStructure result = conjugationVerbCompoundStructureService
                .getConjugationVerbCompoundStructureByTenseId(tenseId);

        // Then
        assertNull(result);
        verify(conjugationVerbCompoundStructureRepository, times(1)).findByTenseId(tenseId);
    }

    @Test
    void testGetConjugationVerbCompoundStructureByTenseId_WithZeroTenseId() {
        // Given
        Integer tenseId = 0;

        when(conjugationVerbCompoundStructureRepository.findByTenseId(tenseId))
                .thenReturn(Optional.empty());

        // When
        ConjugationVerbCompoundStructure result = conjugationVerbCompoundStructureService
                .getConjugationVerbCompoundStructureByTenseId(tenseId);

        // Then
        assertNull(result);
        verify(conjugationVerbCompoundStructureRepository, times(1)).findByTenseId(tenseId);
    }

    @Test
    void testGetConjugationVerbCompoundStructureByTenseId_WithNegativeTenseId() {
        // Given
        Integer tenseId = -1;

        when(conjugationVerbCompoundStructureRepository.findByTenseId(tenseId))
                .thenReturn(Optional.empty());

        // When
        ConjugationVerbCompoundStructure result = conjugationVerbCompoundStructureService
                .getConjugationVerbCompoundStructureByTenseId(tenseId);

        // Then
        assertNull(result);
        verify(conjugationVerbCompoundStructureRepository, times(1)).findByTenseId(tenseId);
    }

    @Test
    void testGetConjugationVerbCompoundStructureByTenseId_WithLargeTenseId() {
        // Given
        Integer tenseId = Integer.MAX_VALUE;

        when(conjugationVerbCompoundStructureRepository.findByTenseId(tenseId))
                .thenReturn(Optional.empty());

        // When
        ConjugationVerbCompoundStructure result = conjugationVerbCompoundStructureService
                .getConjugationVerbCompoundStructureByTenseId(tenseId);

        // Then
        assertNull(result);
        verify(conjugationVerbCompoundStructureRepository, times(1)).findByTenseId(tenseId);
    }

    @Test
    void testGetConjugationVerbCompoundStructureByTenseId_WhenExistsWithNullTense() {
        // Given
        Integer tenseId = 1;

        ConjugationVerbCompoundStructure compoundStructure = new ConjugationVerbCompoundStructure();
        compoundStructure.setId(10);
        compoundStructure.setTense(null);

        when(conjugationVerbCompoundStructureRepository.findByTenseId(tenseId))
                .thenReturn(Optional.of(compoundStructure));

        // When
        ConjugationVerbCompoundStructure result = conjugationVerbCompoundStructureService
                .getConjugationVerbCompoundStructureByTenseId(tenseId);

        // Then
        assertNotNull(result);
        assertEquals(10, result.getId());
        assertNull(result.getTense());
        verify(conjugationVerbCompoundStructureRepository, times(2)).findByTenseId(tenseId);
    }

    // Performance test to verify the double call issue

    @Test
    void testGetConjugationVerbCompoundStructureByTenseId_VerifyDoubleRepositoryCall() {
        // Given
        Integer tenseId = 1;

        ConjugationVerbCompoundStructure compoundStructure = new ConjugationVerbCompoundStructure();
        compoundStructure.setId(10);

        when(conjugationVerbCompoundStructureRepository.findByTenseId(tenseId))
                .thenReturn(Optional.of(compoundStructure));

        // When
        ConjugationVerbCompoundStructure result = conjugationVerbCompoundStructureService
                .getConjugationVerbCompoundStructureByTenseId(tenseId);

        // Then
        assertNotNull(result);
        assertEquals(10, result.getId());
        // Verify that the repository method is called twice due to the inefficient implementation
        verify(conjugationVerbCompoundStructureRepository, times(2)).findByTenseId(tenseId);
    }

    @Test
    void testGetConjugationVerbCompoundStructureByTenseId_VerifyNoDoubleCallWhenNotExists() {
        // Given
        Integer tenseId = 999;

        when(conjugationVerbCompoundStructureRepository.findByTenseId(tenseId))
                .thenReturn(Optional.empty());

        // When
        ConjugationVerbCompoundStructure result = conjugationVerbCompoundStructureService
                .getConjugationVerbCompoundStructureByTenseId(tenseId);

        // Then
        assertNull(result);
        // Verify that the repository method is called only once when Optional is empty
        verify(conjugationVerbCompoundStructureRepository, times(1)).findByTenseId(tenseId);
    }
}
