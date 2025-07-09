package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.ConjugationVariationFoundException;
import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.repository.ConjugationVariationRepository;
import com.antonio.apprendrebackend.service.service.impl.ConjugationVariationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConjugationVariationServiceImplTest {
    @Mock
    private ConjugationVariationRepository conjugationVariationRepository;

    @InjectMocks
    private ConjugationVariationServiceImpl conjugationVariationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetByConjugationVerbWhenVariationExists() {
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

        Optional<ConjugationVariation> expectedVariation = Optional.of(conjugationVariation);

        // When
        when(conjugationVariationRepository.findByConjugationVerb(conjugationVerb))
                .thenReturn(expectedVariation);

        ConjugationVariation result = conjugationVariationService.getConjugationVariationByConjugationVerb(conjugationVerb);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(conjugationVerb, result.getConjugationVerb());
        assertEquals(irregularPattern, result.getConjugationIrregularPattern());
        verify(conjugationVariationRepository, times(1)).findByConjugationVerb(conjugationVerb);
    }

    @Test
    void testGetByConjugationVerbWhenVariationDoesNotExist_ShouldThrowException() {
        // Given
        ConjugationVerb conjugationVerb = new ConjugationVerb();
        conjugationVerb.setId(2);
        conjugationVerb.setIsReflexive(0);

        Optional<ConjugationVariation> expectedVariation = Optional.empty();

        // When
        when(conjugationVariationRepository.findByConjugationVerb(conjugationVerb))
                .thenReturn(expectedVariation);

        // Then
        ConjugationVariationFoundException exception = assertThrows(
                ConjugationVariationFoundException.class,
                () -> conjugationVariationService.getConjugationVariationByConjugationVerb(conjugationVerb)
        );

        assertTrue(exception.getMessage().contains("Not found any COnjugation variation for Conjugationverb 2"));
        verify(conjugationVariationRepository, times(1)).findByConjugationVerb(conjugationVerb);
    }

    @Test
    void testGetByConjugationVerbWithReflexiveVerb() {
        // Given
        ConjugationVerb conjugationVerb = new ConjugationVerb();
        conjugationVerb.setId(3);
        conjugationVerb.setIsReflexive(1);

        ConjugationIrregularPattern irregularPattern = new ConjugationIrregularPattern();
        irregularPattern.setId(2);

        ConjugationVariation conjugationVariation = new ConjugationVariation();
        conjugationVariation.setId(2);
        conjugationVariation.setConjugationVerb(conjugationVerb);
        conjugationVariation.setConjugationIrregularPattern(irregularPattern);

        Optional<ConjugationVariation> expectedVariation = Optional.of(conjugationVariation);

        // When
        when(conjugationVariationRepository.findByConjugationVerb(conjugationVerb))
                .thenReturn(expectedVariation);

        ConjugationVariation result = conjugationVariationService.getConjugationVariationByConjugationVerb(conjugationVerb);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals(1, result.getConjugationVerb().getIsReflexive());
        assertEquals(2, result.getConjugationIrregularPattern().getId());
        verify(conjugationVariationRepository, times(1)).findByConjugationVerb(conjugationVerb);
    }

    @Test
    void testGetByConjugationVerbMultipleCalls() {
        // Given
        ConjugationVerb conjugationVerb1 = new ConjugationVerb();
        conjugationVerb1.setId(1);
        conjugationVerb1.setIsReflexive(0);

        ConjugationVerb conjugationVerb2 = new ConjugationVerb();
        conjugationVerb2.setId(2);
        conjugationVerb2.setIsReflexive(0);

        ConjugationVariation variation1 = new ConjugationVariation();
        variation1.setId(1);
        variation1.setConjugationVerb(conjugationVerb1);

        Optional<ConjugationVariation> expectedVariation1 = Optional.of(variation1);
        Optional<ConjugationVariation> expectedVariation2 = Optional.empty();

        // When
        when(conjugationVariationRepository.findByConjugationVerb(conjugationVerb1))
                .thenReturn(expectedVariation1);
        when(conjugationVariationRepository.findByConjugationVerb(conjugationVerb2))
                .thenReturn(expectedVariation2);

        ConjugationVariation result1 = conjugationVariationService.getConjugationVariationByConjugationVerb(conjugationVerb1);

        // Then
        assertNotNull(result1);
        assertEquals(1, result1.getId());

        assertThrows(ConjugationVariationFoundException.class,
                () -> conjugationVariationService.getConjugationVariationByConjugationVerb(conjugationVerb2));

        verify(conjugationVariationRepository, times(1)).findByConjugationVerb(conjugationVerb1);
        verify(conjugationVariationRepository, times(1)).findByConjugationVerb(conjugationVerb2);
    }

    @Test
    void testGetByConjugationVerbRepositoryReturnsNull_ShouldThrowException() {
        // Given
        ConjugationVerb conjugationVerb = new ConjugationVerb();
        conjugationVerb.setId(4);
        conjugationVerb.setIsReflexive(0);

        // When
        when(conjugationVariationRepository.findByConjugationVerb(conjugationVerb))
                .thenReturn(null);

        // Then
        assertThrows(ConjugationVariationFoundException.class,
                () -> conjugationVariationService.getConjugationVariationByConjugationVerb(conjugationVerb));

        verify(conjugationVariationRepository, times(1)).findByConjugationVerb(conjugationVerb);
    }

    @Test
    void testGetByConjugationVerbWithCompleteRelationships() {
        // Given
        VerbAuxiliary verbAuxiliary = new VerbAuxiliary();
        verbAuxiliary.setId(1);

        VerbGroupEnding verbGroupEnding = new VerbGroupEnding();
        verbGroupEnding.setId(1);

        WordSense wordSense = new WordSense();
        wordSense.setId(1);

        ConjugationVerb conjugationVerb = new ConjugationVerb();
        conjugationVerb.setId(5);
        conjugationVerb.setIsReflexive(0);
        conjugationVerb.setVerbAuxiliary(verbAuxiliary);
        conjugationVerb.setVerbGroupEnding(verbGroupEnding);
        conjugationVerb.setWordSense(wordSense);

        ConjugationIrregularPattern irregularPattern = new ConjugationIrregularPattern();
        irregularPattern.setId(3);

        ConjugationVariation conjugationVariation = new ConjugationVariation();
        conjugationVariation.setId(3);
        conjugationVariation.setConjugationVerb(conjugationVerb);
        conjugationVariation.setConjugationIrregularPattern(irregularPattern);

        Optional<ConjugationVariation> expectedVariation = Optional.of(conjugationVariation);

        // When
        when(conjugationVariationRepository.findByConjugationVerb(conjugationVerb))
                .thenReturn(expectedVariation);

        ConjugationVariation result = conjugationVariationService.getConjugationVariationByConjugationVerb(conjugationVerb);

        // Then
        assertNotNull(result);
        assertEquals(3, result.getId());
        assertEquals(5, result.getConjugationVerb().getId());
        assertEquals(0, result.getConjugationVerb().getIsReflexive());
        assertEquals(verbAuxiliary, result.getConjugationVerb().getVerbAuxiliary());
        assertEquals(verbGroupEnding, result.getConjugationVerb().getVerbGroupEnding());
        assertEquals(wordSense, result.getConjugationVerb().getWordSense());
        verify(conjugationVariationRepository, times(1)).findByConjugationVerb(conjugationVerb);
    }
}