package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.ConjugationIrregularPattern;
import com.antonio.apprendrebackend.service.model.ConjugationVariation;
import com.antonio.apprendrebackend.service.model.ConjugationVerb;
import com.antonio.apprendrebackend.service.model.ConjugationVerbFormIrregular;
import com.antonio.apprendrebackend.service.repository.ConjugationVerbFormIrregularRepository;
import com.antonio.apprendrebackend.service.service.impl.ConjugationVerbFormIrregularServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConjugationVerbFormIrregularServiceImplTest {

    @Mock
    private ConjugationVerbFormIrregularRepository conjugationVerbFormIrregularRepository;

    @InjectMocks
    private ConjugationVerbFormIrregularServiceImpl conjugationVerbFormIrregularService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByConjugationVariationWithMultipleResults() {
        // Given
        ConjugationVerb conjugationVerb = new ConjugationVerb();
        conjugationVerb.setId(1);
        conjugationVerb.setIsReflexive(0);

        ConjugationIrregularPattern irregularPattern = new ConjugationIrregularPattern();
        irregularPattern.setId(1);

        ConjugationVariation conjugationVariation = new ConjugationVariation();
        conjugationVariation.setId(1);
        conjugationVariation.setConjugationVerb(conjugationVerb);
        conjugationVariation.setConjugationIrregularPattern(irregularPattern);

        ConjugationVerbFormIrregular irregular1 = new ConjugationVerbFormIrregular();
        irregular1.setId(1);
        irregular1.setConjugationVariation(conjugationVariation);

        ConjugationVerbFormIrregular irregular2 = new ConjugationVerbFormIrregular();
        irregular2.setId(2);
        irregular2.setConjugationVariation(conjugationVariation);

        ConjugationVerbFormIrregular irregular3 = new ConjugationVerbFormIrregular();
        irregular3.setId(3);
        irregular3.setConjugationVariation(conjugationVariation);

        List<ConjugationVerbFormIrregular> expectedIrregulars = Arrays.asList(irregular1, irregular2, irregular3);

        // When
        when(conjugationVerbFormIrregularRepository.findByConjugationVariation(conjugationVariation))
                .thenReturn(expectedIrregulars);

        List<ConjugationVerbFormIrregular> result = conjugationVerbFormIrregularService
                .findByConjugationVariation(conjugationVariation);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals(3, result.get(2).getId());
        assertEquals(conjugationVariation, result.get(0).getConjugationVariation());
        assertEquals(conjugationVariation, result.get(1).getConjugationVariation());
        assertEquals(conjugationVariation, result.get(2).getConjugationVariation());
        verify(conjugationVerbFormIrregularRepository, times(1))
                .findByConjugationVariation(conjugationVariation);
    }

    @Test
    void testFindByConjugationVariationEmpty() {
        // Given
        ConjugationVerb conjugationVerb = new ConjugationVerb();
        conjugationVerb.setId(2);
        conjugationVerb.setIsReflexive(0);

        ConjugationVariation conjugationVariation = new ConjugationVariation();
        conjugationVariation.setId(2);
        conjugationVariation.setConjugationVerb(conjugationVerb);

        // When
        when(conjugationVerbFormIrregularRepository.findByConjugationVariation(conjugationVariation))
                .thenReturn(Arrays.asList());

        List<ConjugationVerbFormIrregular> result = conjugationVerbFormIrregularService
                .findByConjugationVariation(conjugationVariation);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(conjugationVerbFormIrregularRepository, times(1))
                .findByConjugationVariation(conjugationVariation);
    }

    @Test
    void testFindByConjugationVariationWithNullConjugationVariation() {
        // Given
        ConjugationVariation conjugationVariation = null;

        // When
        when(conjugationVerbFormIrregularRepository.findByConjugationVariation(conjugationVariation))
                .thenReturn(Arrays.asList());

        List<ConjugationVerbFormIrregular> result = conjugationVerbFormIrregularService
                .findByConjugationVariation(conjugationVariation);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(conjugationVerbFormIrregularRepository, times(1))
                .findByConjugationVariation(conjugationVariation);
    }

    @Test
    void testFindByConjugationVariationSingleResult() {
        // Given
        ConjugationVerb conjugationVerb = new ConjugationVerb();
        conjugationVerb.setId(3);
        conjugationVerb.setIsReflexive(1);

        ConjugationIrregularPattern irregularPattern = new ConjugationIrregularPattern();
        irregularPattern.setId(2);

        ConjugationVariation conjugationVariation = new ConjugationVariation();
        conjugationVariation.setId(3);
        conjugationVariation.setConjugationVerb(conjugationVerb);
        conjugationVariation.setConjugationIrregularPattern(irregularPattern);

        ConjugationVerbFormIrregular irregular = new ConjugationVerbFormIrregular();
        irregular.setId(1);
        irregular.setConjugationVariation(conjugationVariation);

        List<ConjugationVerbFormIrregular> expectedIrregulars = Arrays.asList(irregular);

        // When
        when(conjugationVerbFormIrregularRepository.findByConjugationVariation(conjugationVariation))
                .thenReturn(expectedIrregulars);

        List<ConjugationVerbFormIrregular> result = conjugationVerbFormIrregularService
                .findByConjugationVariation(conjugationVariation);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(conjugationVariation, result.get(0).getConjugationVariation());
        assertEquals(1, result.get(0).getConjugationVariation().getConjugationVerb().getIsReflexive());
        verify(conjugationVerbFormIrregularRepository, times(1))
                .findByConjugationVariation(conjugationVariation);
    }

    @Test
    void testFindByConjugationVariationWithDifferentConjugationVariations() {
        // Given
        ConjugationVariation conjugationVariation1 = new ConjugationVariation();
        conjugationVariation1.setId(1);

        ConjugationVariation conjugationVariation2 = new ConjugationVariation();
        conjugationVariation2.setId(2);

        ConjugationVerbFormIrregular irregular1 = new ConjugationVerbFormIrregular();
        irregular1.setId(1);
        irregular1.setConjugationVariation(conjugationVariation1);

        ConjugationVerbFormIrregular irregular2 = new ConjugationVerbFormIrregular();
        irregular2.setId(2);
        irregular2.setConjugationVariation(conjugationVariation1);

        List<ConjugationVerbFormIrregular> expectedIrregulars1 = Arrays.asList(irregular1, irregular2);
        List<ConjugationVerbFormIrregular> expectedIrregulars2 = Arrays.asList();

        // When
        when(conjugationVerbFormIrregularRepository.findByConjugationVariation(conjugationVariation1))
                .thenReturn(expectedIrregulars1);
        when(conjugationVerbFormIrregularRepository.findByConjugationVariation(conjugationVariation2))
                .thenReturn(expectedIrregulars2);

        List<ConjugationVerbFormIrregular> result1 = conjugationVerbFormIrregularService
                .findByConjugationVariation(conjugationVariation1);
        List<ConjugationVerbFormIrregular> result2 = conjugationVerbFormIrregularService
                .findByConjugationVariation(conjugationVariation2);

        // Then
        assertNotNull(result1);
        assertEquals(2, result1.size());
        assertEquals(1, result1.get(0).getId());
        assertEquals(2, result1.get(1).getId());

        assertNotNull(result2);
        assertTrue(result2.isEmpty());

        verify(conjugationVerbFormIrregularRepository, times(1))
                .findByConjugationVariation(conjugationVariation1);
        verify(conjugationVerbFormIrregularRepository, times(1))
                .findByConjugationVariation(conjugationVariation2);
    }

    @Test
    void testFindByConjugationVariationRepositoryReturnsNull() {
        // Given
        ConjugationVariation conjugationVariation = new ConjugationVariation();
        conjugationVariation.setId(4);

        // When
        when(conjugationVerbFormIrregularRepository.findByConjugationVariation(conjugationVariation))
                .thenReturn(null);

        List<ConjugationVerbFormIrregular> result = conjugationVerbFormIrregularService
                .findByConjugationVariation(conjugationVariation);

        // Then
        assertNull(result);
        verify(conjugationVerbFormIrregularRepository, times(1))
                .findByConjugationVariation(conjugationVariation);
    }

    @Test
    void testFindByConjugationVariationWithCompleteConjugationVariation() {
        // Given
        ConjugationVerb conjugationVerb = new ConjugationVerb();
        conjugationVerb.setId(5);
        conjugationVerb.setIsReflexive(0);

        ConjugationIrregularPattern irregularPattern = new ConjugationIrregularPattern();
        irregularPattern.setId(3);

        ConjugationVariation conjugationVariation = new ConjugationVariation();
        conjugationVariation.setId(5);
        conjugationVariation.setConjugationVerb(conjugationVerb);
        conjugationVariation.setConjugationIrregularPattern(irregularPattern);

        ConjugationVerbFormIrregular irregular = new ConjugationVerbFormIrregular();
        irregular.setId(5);
        irregular.setConjugationVariation(conjugationVariation);

        List<ConjugationVerbFormIrregular> expectedIrregulars = Arrays.asList(irregular);

        // When
        when(conjugationVerbFormIrregularRepository.findByConjugationVariation(conjugationVariation))
                .thenReturn(expectedIrregulars);

        List<ConjugationVerbFormIrregular> result = conjugationVerbFormIrregularService
                .findByConjugationVariation(conjugationVariation);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(5, result.get(0).getId());
        assertEquals(5, result.get(0).getConjugationVariation().getId());
        assertEquals(5, result.get(0).getConjugationVariation().getConjugationVerb().getId());
        assertEquals(3, result.get(0).getConjugationVariation().getConjugationIrregularPattern().getId());
        verify(conjugationVerbFormIrregularRepository, times(1))
                .findByConjugationVariation(conjugationVariation);
    }
}
