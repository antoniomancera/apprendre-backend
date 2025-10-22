package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.ConjugationVerbForm;
import com.antonio.apprendrebackend.service.model.PersonGenderNumber;
import com.antonio.apprendrebackend.service.model.Tense;
import com.antonio.apprendrebackend.service.repository.ConjugationVerbFormRepository;
import com.antonio.apprendrebackend.service.service.impl.ConjugationVerbFormServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConjugationVerbFormServiceImplTest {
    @Mock
    private ConjugationVerbFormRepository conjugationVerbFormRepository;

    @InjectMocks
    private ConjugationVerbFormServiceImpl conjugationVerbFormService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetConjugationVerbFormsByTenseAndPersonGenderNumberEnumsWithMultipleResults() {
        // Given
        Tense tense = new Tense();
        tense.setId(1);

        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = Arrays.asList(
                PersonGenderNumber.PersonGenderNumberEnum.FIRST_SINGULAR_MASCULINE,
                PersonGenderNumber.PersonGenderNumberEnum.SECOND_SINGULAR_MASCULINE,
                PersonGenderNumber.PersonGenderNumberEnum.THIRD_SINGULAR_MASCULINE
        );

        PersonGenderNumber pgn1 = new PersonGenderNumber();
        pgn1.setId(1);
        pgn1.setPersonGenderNumberEnum(PersonGenderNumber.PersonGenderNumberEnum.FIRST_SINGULAR_MASCULINE);

        PersonGenderNumber pgn2 = new PersonGenderNumber();
        pgn2.setId(2);
        pgn2.setPersonGenderNumberEnum(PersonGenderNumber.PersonGenderNumberEnum.SECOND_SINGULAR_MASCULINE);

        PersonGenderNumber pgn3 = new PersonGenderNumber();
        pgn3.setId(3);
        pgn3.setPersonGenderNumberEnum(PersonGenderNumber.PersonGenderNumberEnum.THIRD_SINGULAR_MASCULINE);

        ConjugationVerbForm verbForm1 = new ConjugationVerbForm();
        verbForm1.setId(1);
        verbForm1.setTense(tense);
        verbForm1.setPersonGenderNumber(pgn1);

        ConjugationVerbForm verbForm2 = new ConjugationVerbForm();
        verbForm2.setId(2);
        verbForm2.setTense(tense);
        verbForm2.setPersonGenderNumber(pgn2);

        ConjugationVerbForm verbForm3 = new ConjugationVerbForm();
        verbForm3.setId(3);
        verbForm3.setTense(tense);
        verbForm3.setPersonGenderNumber(pgn3);

        List<ConjugationVerbForm> expectedVerbForms = Arrays.asList(verbForm1, verbForm2, verbForm3);

        // When
        when(conjugationVerbFormRepository.findByTenseAndPersonGenderNumberPersonGenderNumberEnumIn(
                tense, personGenderNumberEnums))
                .thenReturn(expectedVerbForms);

        List<ConjugationVerbForm> result = conjugationVerbFormService
                .getConjugationVerbFormsByTenseAndPersonGenderNumberEnums(tense, personGenderNumberEnums);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals(3, result.get(2).getId());
        assertEquals(tense, result.get(0).getTense());
        assertEquals(tense, result.get(1).getTense());
        assertEquals(tense, result.get(2).getTense());
        assertEquals(PersonGenderNumber.PersonGenderNumberEnum.FIRST_SINGULAR_MASCULINE,
                result.get(0).getPersonGenderNumber().getPersonGenderNumberEnum());
        assertEquals(PersonGenderNumber.PersonGenderNumberEnum.SECOND_SINGULAR_MASCULINE,
                result.get(1).getPersonGenderNumber().getPersonGenderNumberEnum());
        assertEquals(PersonGenderNumber.PersonGenderNumberEnum.THIRD_SINGULAR_MASCULINE,
                result.get(2).getPersonGenderNumber().getPersonGenderNumberEnum());
        verify(conjugationVerbFormRepository, times(1))
                .findByTenseAndPersonGenderNumberPersonGenderNumberEnumIn(tense, personGenderNumberEnums);
    }

    @Test
    void testGetConjugationVerbFormsByTenseAndPersonGenderNumberEnumsEmpty() {
        // Given
        Tense tense = new Tense();
        tense.setId(2);

        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = Arrays.asList(
                PersonGenderNumber.PersonGenderNumberEnum.FIRST_PLURAL_MASCULINE
        );

        // When
        when(conjugationVerbFormRepository.findByTenseAndPersonGenderNumberPersonGenderNumberEnumIn(
                tense, personGenderNumberEnums))
                .thenReturn(Arrays.asList());

        List<ConjugationVerbForm> result = conjugationVerbFormService
                .getConjugationVerbFormsByTenseAndPersonGenderNumberEnums(tense, personGenderNumberEnums);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(conjugationVerbFormRepository, times(1))
                .findByTenseAndPersonGenderNumberPersonGenderNumberEnumIn(tense, personGenderNumberEnums);
    }

    @Test
    void testGetConjugationVerbFormsByTenseAndPersonGenderNumberEnumsWithEmptyList() {
        // Given
        Tense tense = new Tense();
        tense.setId(3);

        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = Arrays.asList();

        // When
        when(conjugationVerbFormRepository.findByTenseAndPersonGenderNumberPersonGenderNumberEnumIn(
                tense, personGenderNumberEnums))
                .thenReturn(Arrays.asList());

        List<ConjugationVerbForm> result = conjugationVerbFormService
                .getConjugationVerbFormsByTenseAndPersonGenderNumberEnums(tense, personGenderNumberEnums);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(conjugationVerbFormRepository, times(1))
                .findByTenseAndPersonGenderNumberPersonGenderNumberEnumIn(tense, personGenderNumberEnums);
    }

    @Test
    void testGetConjugationVerbFormsByTenseAndPersonGenderNumberEnumsSingleResult() {
        // Given
        Tense tense = new Tense();
        tense.setId(4);

        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = Arrays.asList(
                PersonGenderNumber.PersonGenderNumberEnum.SECOND_PLURAL_MASCULINE
        );

        PersonGenderNumber pgn = new PersonGenderNumber();
        pgn.setId(4);
        pgn.setPersonGenderNumberEnum(PersonGenderNumber.PersonGenderNumberEnum.SECOND_PLURAL_MASCULINE);

        ConjugationVerbForm verbForm = new ConjugationVerbForm();
        verbForm.setId(10);
        verbForm.setTense(tense);
        verbForm.setPersonGenderNumber(pgn);

        List<ConjugationVerbForm> expectedVerbForms = Arrays.asList(verbForm);

        // When
        when(conjugationVerbFormRepository.findByTenseAndPersonGenderNumberPersonGenderNumberEnumIn(
                tense, personGenderNumberEnums))
                .thenReturn(expectedVerbForms);

        List<ConjugationVerbForm> result = conjugationVerbFormService
                .getConjugationVerbFormsByTenseAndPersonGenderNumberEnums(tense, personGenderNumberEnums);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(10, result.get(0).getId());
        assertEquals(tense, result.get(0).getTense());
        assertEquals(PersonGenderNumber.PersonGenderNumberEnum.SECOND_PLURAL_MASCULINE,
                result.get(0).getPersonGenderNumber().getPersonGenderNumberEnum());
        verify(conjugationVerbFormRepository, times(1))
                .findByTenseAndPersonGenderNumberPersonGenderNumberEnumIn(tense, personGenderNumberEnums);
    }

    @Test
    void testGetConjugationVerbFormsByTenseAndPersonGenderNumberEnumsWithAllPersonGenderNumbers() {
        // Given
        Tense tense = new Tense();
        tense.setId(5);

        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = Arrays.asList(
                PersonGenderNumber.PersonGenderNumberEnum.FIRST_SINGULAR_MASCULINE,
                PersonGenderNumber.PersonGenderNumberEnum.SECOND_SINGULAR_MASCULINE,
                PersonGenderNumber.PersonGenderNumberEnum.THIRD_SINGULAR_MASCULINE,
                PersonGenderNumber.PersonGenderNumberEnum.FIRST_PLURAL_MASCULINE,
                PersonGenderNumber.PersonGenderNumberEnum.SECOND_PLURAL_MASCULINE,
                PersonGenderNumber.PersonGenderNumberEnum.THIRD_PLURAL_MASCULINE
        );

        PersonGenderNumber pgn1 = new PersonGenderNumber();
        pgn1.setId(1);
        pgn1.setPersonGenderNumberEnum(PersonGenderNumber.PersonGenderNumberEnum.FIRST_SINGULAR_MASCULINE);

        PersonGenderNumber pgn2 = new PersonGenderNumber();
        pgn2.setId(2);
        pgn2.setPersonGenderNumberEnum(PersonGenderNumber.PersonGenderNumberEnum.SECOND_SINGULAR_MASCULINE);

        PersonGenderNumber pgn3 = new PersonGenderNumber();
        pgn3.setId(3);
        pgn3.setPersonGenderNumberEnum(PersonGenderNumber.PersonGenderNumberEnum.THIRD_SINGULAR_MASCULINE);

        PersonGenderNumber pgn4 = new PersonGenderNumber();
        pgn4.setId(4);
        pgn4.setPersonGenderNumberEnum(PersonGenderNumber.PersonGenderNumberEnum.FIRST_PLURAL_MASCULINE);

        PersonGenderNumber pgn5 = new PersonGenderNumber();
        pgn5.setId(5);
        pgn5.setPersonGenderNumberEnum(PersonGenderNumber.PersonGenderNumberEnum.SECOND_PLURAL_MASCULINE);

        PersonGenderNumber pgn6 = new PersonGenderNumber();
        pgn6.setId(6);
        pgn6.setPersonGenderNumberEnum(PersonGenderNumber.PersonGenderNumberEnum.THIRD_PLURAL_MASCULINE);

        ConjugationVerbForm verbForm1 = new ConjugationVerbForm();
        verbForm1.setId(1);
        verbForm1.setTense(tense);
        verbForm1.setPersonGenderNumber(pgn1);

        ConjugationVerbForm verbForm2 = new ConjugationVerbForm();
        verbForm2.setId(2);
        verbForm2.setTense(tense);
        verbForm2.setPersonGenderNumber(pgn2);

        ConjugationVerbForm verbForm3 = new ConjugationVerbForm();
        verbForm3.setId(3);
        verbForm3.setTense(tense);
        verbForm3.setPersonGenderNumber(pgn3);

        ConjugationVerbForm verbForm4 = new ConjugationVerbForm();
        verbForm4.setId(4);
        verbForm4.setTense(tense);
        verbForm4.setPersonGenderNumber(pgn4);

        ConjugationVerbForm verbForm5 = new ConjugationVerbForm();
        verbForm5.setId(5);
        verbForm5.setTense(tense);
        verbForm5.setPersonGenderNumber(pgn5);

        ConjugationVerbForm verbForm6 = new ConjugationVerbForm();
        verbForm6.setId(6);
        verbForm6.setTense(tense);
        verbForm6.setPersonGenderNumber(pgn6);

        List<ConjugationVerbForm> expectedVerbForms = Arrays.asList(
                verbForm1, verbForm2, verbForm3, verbForm4, verbForm5, verbForm6);

        // When
        when(conjugationVerbFormRepository.findByTenseAndPersonGenderNumberPersonGenderNumberEnumIn(
                tense, personGenderNumberEnums))
                .thenReturn(expectedVerbForms);

        List<ConjugationVerbForm> result = conjugationVerbFormService
                .getConjugationVerbFormsByTenseAndPersonGenderNumberEnums(tense, personGenderNumberEnums);

        // Then
        assertNotNull(result);
        assertEquals(6, result.size());
        for (int i = 0; i < 6; i++) {
            assertEquals(i + 1, result.get(i).getId());
            assertEquals(tense, result.get(i).getTense());
        }
        verify(conjugationVerbFormRepository, times(1))
                .findByTenseAndPersonGenderNumberPersonGenderNumberEnumIn(tense, personGenderNumberEnums);
    }

    @Test
    void testGetConjugationVerbFormsByTenseAndPersonGenderNumberEnumsWithDifferentTenses() {
        // Given
        Tense tense1 = new Tense();
        tense1.setId(6);

        Tense tense2 = new Tense();
        tense2.setId(7);

        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = Arrays.asList(
                PersonGenderNumber.PersonGenderNumberEnum.FIRST_SINGULAR_MASCULINE
        );

        PersonGenderNumber pgn = new PersonGenderNumber();
        pgn.setId(1);
        pgn.setPersonGenderNumberEnum(PersonGenderNumber.PersonGenderNumberEnum.FIRST_SINGULAR_MASCULINE);

        ConjugationVerbForm verbForm1 = new ConjugationVerbForm();
        verbForm1.setId(11);
        verbForm1.setTense(tense1);
        verbForm1.setPersonGenderNumber(pgn);

        ConjugationVerbForm verbForm2 = new ConjugationVerbForm();
        verbForm2.setId(12);
        verbForm2.setTense(tense2);
        verbForm2.setPersonGenderNumber(pgn);

        List<ConjugationVerbForm> expectedVerbForms1 = Arrays.asList(verbForm1);
        List<ConjugationVerbForm> expectedVerbForms2 = Arrays.asList(verbForm2);

        // When
        when(conjugationVerbFormRepository.findByTenseAndPersonGenderNumberPersonGenderNumberEnumIn(
                tense1, personGenderNumberEnums))
                .thenReturn(expectedVerbForms1);
        when(conjugationVerbFormRepository.findByTenseAndPersonGenderNumberPersonGenderNumberEnumIn(
                tense2, personGenderNumberEnums))
                .thenReturn(expectedVerbForms2);

        List<ConjugationVerbForm> result1 = conjugationVerbFormService
                .getConjugationVerbFormsByTenseAndPersonGenderNumberEnums(tense1, personGenderNumberEnums);
        List<ConjugationVerbForm> result2 = conjugationVerbFormService
                .getConjugationVerbFormsByTenseAndPersonGenderNumberEnums(tense2, personGenderNumberEnums);

        // Then
        assertNotNull(result1);
        assertEquals(1, result1.size());
        assertEquals(11, result1.get(0).getId());
        assertEquals(tense1, result1.get(0).getTense());

        assertNotNull(result2);
        assertEquals(1, result2.size());
        assertEquals(12, result2.get(0).getId());
        assertEquals(tense2, result2.get(0).getTense());

        verify(conjugationVerbFormRepository, times(1))
                .findByTenseAndPersonGenderNumberPersonGenderNumberEnumIn(tense1, personGenderNumberEnums);
        verify(conjugationVerbFormRepository, times(1))
                .findByTenseAndPersonGenderNumberPersonGenderNumberEnumIn(tense2, personGenderNumberEnums);
    }

    @Test
    void testGetConjugationVerbFormsByTenseAndPersonGenderNumberEnumsWithNullTense() {
        // Given
        Tense tense = new Tense();

        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = Arrays.asList(
                PersonGenderNumber.PersonGenderNumberEnum.THIRD_SINGULAR_MASCULINE
        );

        // When
        when(conjugationVerbFormRepository.findByTenseAndPersonGenderNumberPersonGenderNumberEnumIn(
                tense, personGenderNumberEnums))
                .thenReturn(Arrays.asList());

        List<ConjugationVerbForm> result = conjugationVerbFormService
                .getConjugationVerbFormsByTenseAndPersonGenderNumberEnums(tense, personGenderNumberEnums);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(conjugationVerbFormRepository, times(1))
                .findByTenseAndPersonGenderNumberPersonGenderNumberEnumIn(tense, personGenderNumberEnums);
    }

    @Test
    void testGetConjugationVerbFormsByTenseAndPersonGenderNumberEnumsRepositoryReturnsNull() {
        // Given
        Tense tense = new Tense();
        tense.setId(8);

        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = Arrays.asList(
                PersonGenderNumber.PersonGenderNumberEnum.FIRST_SINGULAR_MASCULINE
        );

        // When
        when(conjugationVerbFormRepository.findByTenseAndPersonGenderNumberPersonGenderNumberEnumIn(
                tense, personGenderNumberEnums))
                .thenReturn(null);

        List<ConjugationVerbForm> result = conjugationVerbFormService
                .getConjugationVerbFormsByTenseAndPersonGenderNumberEnums(tense, personGenderNumberEnums);

        // Then
        assertNull(result);
        verify(conjugationVerbFormRepository, times(1))
                .findByTenseAndPersonGenderNumberPersonGenderNumberEnumIn(tense, personGenderNumberEnums);
    }

    @Test
    void testGetConjugationVerbFormsByTenseAndPersonGenderNumberEnumsPartialMatch() {
        // Given
        Tense tense = new Tense();
        tense.setId(9);

        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = Arrays.asList(
                PersonGenderNumber.PersonGenderNumberEnum.FIRST_SINGULAR_MASCULINE,
                PersonGenderNumber.PersonGenderNumberEnum.SECOND_SINGULAR_MASCULINE,
                PersonGenderNumber.PersonGenderNumberEnum.THIRD_SINGULAR_MASCULINE
        );

        PersonGenderNumber pgn1 = new PersonGenderNumber();
        pgn1.setId(1);
        pgn1.setPersonGenderNumberEnum(PersonGenderNumber.PersonGenderNumberEnum.FIRST_SINGULAR_MASCULINE);

        PersonGenderNumber pgn3 = new PersonGenderNumber();
        pgn3.setId(3);
        pgn3.setPersonGenderNumberEnum(PersonGenderNumber.PersonGenderNumberEnum.THIRD_SINGULAR_MASCULINE);

        ConjugationVerbForm verbForm1 = new ConjugationVerbForm();
        verbForm1.setId(1);
        verbForm1.setTense(tense);
        verbForm1.setPersonGenderNumber(pgn1);

        ConjugationVerbForm verbForm3 = new ConjugationVerbForm();
        verbForm3.setId(3);
        verbForm3.setTense(tense);
        verbForm3.setPersonGenderNumber(pgn3);

        List<ConjugationVerbForm> expectedVerbForms = Arrays.asList(verbForm1, verbForm3);

        // When
        when(conjugationVerbFormRepository.findByTenseAndPersonGenderNumberPersonGenderNumberEnumIn(
                tense, personGenderNumberEnums))
                .thenReturn(expectedVerbForms);

        List<ConjugationVerbForm> result = conjugationVerbFormService
                .getConjugationVerbFormsByTenseAndPersonGenderNumberEnums(tense, personGenderNumberEnums);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(3, result.get(1).getId());
        assertEquals(PersonGenderNumber.PersonGenderNumberEnum.FIRST_SINGULAR_MASCULINE,
                result.get(0).getPersonGenderNumber().getPersonGenderNumberEnum());
        assertEquals(PersonGenderNumber.PersonGenderNumberEnum.THIRD_SINGULAR_MASCULINE,
                result.get(1).getPersonGenderNumber().getPersonGenderNumberEnum());
        verify(conjugationVerbFormRepository, times(1))
                .findByTenseAndPersonGenderNumberPersonGenderNumberEnumIn(tense, personGenderNumberEnums);
    }
}
