package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.ConjugationNonExist;
import com.antonio.apprendrebackend.service.model.PersonGenderNumber;
import com.antonio.apprendrebackend.service.model.Tense;
import com.antonio.apprendrebackend.service.repository.ConjugationNonExistRepository;
import com.antonio.apprendrebackend.service.service.impl.ConjugationNonExistServiceImpl;
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

public class ConjugationNonExistServiceImplTest {
    @Mock
    private ConjugationNonExistRepository conjugationNonExistRepository;

    @InjectMocks
    private ConjugationNonExistServiceImpl conjugationNonExistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetByConjugationNonExistByTenseAndPersonGenderNumber() {
        // Given
        Tense tense = new Tense();
        tense.setId(1);
        tense.setName("Present");

        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = Arrays.asList(
                PersonGenderNumber.PersonGenderNumberEnum.FIRST_SINGULAR_MASCULINE,
                PersonGenderNumber.PersonGenderNumberEnum.SECOND_SINGULAR_FEMININE
        );

        // Create mock ConjugationNonExist objects
        ConjugationNonExist conjugation1 = new ConjugationNonExist();
        conjugation1.setId(1);

        ConjugationNonExist conjugation2 = new ConjugationNonExist();
        conjugation2.setId(2);

        List<ConjugationNonExist> expectedConjugations = Arrays.asList(conjugation1, conjugation2);

        // When
        when(conjugationNonExistRepository.findByConjugationVerbForm_TenseAndConjugationVerbForm_PersonGenderNumber_PersonGenderNumberEnumIn(
                tense, personGenderNumberEnums)).thenReturn(expectedConjugations);

        List<ConjugationNonExist> result = conjugationNonExistService.getByConjugationNonExistByTenseAndPersonGenderNumber(
                tense, personGenderNumberEnums);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        verify(conjugationNonExistRepository, times(1))
                .findByConjugationVerbForm_TenseAndConjugationVerbForm_PersonGenderNumber_PersonGenderNumberEnumIn(
                        tense, personGenderNumberEnums);
    }

    @Test
    void testGetByConjugationNonExistByTenseAndPersonGenderNumberEmpty() {
        // Given
        Tense tense = new Tense();
        tense.setId(1);
        tense.setName("Present");

        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = Arrays.asList(
                PersonGenderNumber.PersonGenderNumberEnum.FIRST_SINGULAR_MASCULINE
        );

        // When
        when(conjugationNonExistRepository.findByConjugationVerbForm_TenseAndConjugationVerbForm_PersonGenderNumber_PersonGenderNumberEnumIn(
                tense, personGenderNumberEnums)).thenReturn(Arrays.asList());

        List<ConjugationNonExist> result = conjugationNonExistService.getByConjugationNonExistByTenseAndPersonGenderNumber(
                tense, personGenderNumberEnums);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(conjugationNonExistRepository, times(1))
                .findByConjugationVerbForm_TenseAndConjugationVerbForm_PersonGenderNumber_PersonGenderNumberEnumIn(
                        tense, personGenderNumberEnums);
    }

    @Test
    void testGetByConjugationNonExistByTenseAndPersonGenderNumberNullTense() {
        // Given
        Tense tense = null;
        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = Arrays.asList(
                PersonGenderNumber.PersonGenderNumberEnum.FIRST_SINGULAR_MASCULINE
        );

        // When
        when(conjugationNonExistRepository.findByConjugationVerbForm_TenseAndConjugationVerbForm_PersonGenderNumber_PersonGenderNumberEnumIn(
                tense, personGenderNumberEnums)).thenReturn(Arrays.asList());

        List<ConjugationNonExist> result = conjugationNonExistService.getByConjugationNonExistByTenseAndPersonGenderNumber(
                tense, personGenderNumberEnums);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(conjugationNonExistRepository, times(1))
                .findByConjugationVerbForm_TenseAndConjugationVerbForm_PersonGenderNumber_PersonGenderNumberEnumIn(
                        tense, personGenderNumberEnums);
    }

    @Test
    void testGetByConjugationNonExistByTenseAndPersonGenderNumberNullPersonGenderNumberList() {
        // Given
        Tense tense = new Tense();
        tense.setId(1);
        tense.setName("Present");

        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = null;

        // When
        when(conjugationNonExistRepository.findByConjugationVerbForm_TenseAndConjugationVerbForm_PersonGenderNumber_PersonGenderNumberEnumIn(
                tense, personGenderNumberEnums)).thenReturn(Arrays.asList());

        List<ConjugationNonExist> result = conjugationNonExistService.getByConjugationNonExistByTenseAndPersonGenderNumber(
                tense, personGenderNumberEnums);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(conjugationNonExistRepository, times(1))
                .findByConjugationVerbForm_TenseAndConjugationVerbForm_PersonGenderNumber_PersonGenderNumberEnumIn(
                        tense, personGenderNumberEnums);
    }

    @Test
    void testGetByConjugationNonExistByTenseAndPersonGenderNumberEmptyPersonGenderNumberList() {
        // Given
        Tense tense = new Tense();
        tense.setId(1);
        tense.setName("Present");

        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = Arrays.asList();

        // When
        when(conjugationNonExistRepository.findByConjugationVerbForm_TenseAndConjugationVerbForm_PersonGenderNumber_PersonGenderNumberEnumIn(
                tense, personGenderNumberEnums)).thenReturn(Arrays.asList());

        List<ConjugationNonExist> result = conjugationNonExistService.getByConjugationNonExistByTenseAndPersonGenderNumber(
                tense, personGenderNumberEnums);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(conjugationNonExistRepository, times(1))
                .findByConjugationVerbForm_TenseAndConjugationVerbForm_PersonGenderNumber_PersonGenderNumberEnumIn(
                        tense, personGenderNumberEnums);
    }

    @Test
    void testGetByConjugationNonExistByTenseAndPersonGenderNumberBothNull() {
        // Given
        Tense tense = null;
        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = null;

        // When
        when(conjugationNonExistRepository.findByConjugationVerbForm_TenseAndConjugationVerbForm_PersonGenderNumber_PersonGenderNumberEnumIn(
                tense, personGenderNumberEnums)).thenReturn(Arrays.asList());

        List<ConjugationNonExist> result = conjugationNonExistService.getByConjugationNonExistByTenseAndPersonGenderNumber(
                tense, personGenderNumberEnums);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(conjugationNonExistRepository, times(1))
                .findByConjugationVerbForm_TenseAndConjugationVerbForm_PersonGenderNumber_PersonGenderNumberEnumIn(
                        tense, personGenderNumberEnums);
    }
}
