package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.repository.ConjugationTensePersonGenderNumberRepository;
import com.antonio.apprendrebackend.service.service.impl.ConjugationTensePersonGenderNumberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ConjugationTensePersonGenderNumberServiceImplTest {

    @Mock
    private ConjugationTensePersonGenderNumberRepository conjugationTensePersonGenderNumberRepository;

    @InjectMocks
    private ConjugationTensePersonGenderNumberServiceImpl conjugationTensePersonGenderNumberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetConjugationTensePersonGenderNumbersByTenseId_WhenResultsExist() {
        // Given
        Integer tenseId = 1;

        PersonGenderNumber personGenderNumber1 = new PersonGenderNumber();
        personGenderNumber1.setId(1);
        personGenderNumber1.setPersonGenderNumberEnum(PersonGenderNumber.PersonGenderNumberEnum.FIRST_PLURAL_NEUTRAL);

        PersonGenderNumber personGenderNumber2 = new PersonGenderNumber();
        personGenderNumber2.setId(2);
        personGenderNumber2.setPersonGenderNumberEnum(PersonGenderNumber.PersonGenderNumberEnum.SECOND_PLURAL_NEUTRAL);

        PersonGenderNumber personGenderNumber3 = new PersonGenderNumber();
        personGenderNumber3.setId(3);
        personGenderNumber3.setPersonGenderNumberEnum(PersonGenderNumber.PersonGenderNumberEnum.THIRD_PLURAL_NEUTRAL);

        Tense tense = new Tense();
        tense.setId(tenseId);

        ConjugationTensePersonGenderNumber conjugation1 = new ConjugationTensePersonGenderNumber();
        conjugation1.setId(1);
        conjugation1.setTense(tense);
        conjugation1.setPersonGenderNumber(personGenderNumber1);

        ConjugationTensePersonGenderNumber conjugation2 = new ConjugationTensePersonGenderNumber();
        conjugation2.setId(2);
        conjugation2.setTense(tense);
        conjugation2.setPersonGenderNumber(personGenderNumber2);

        ConjugationTensePersonGenderNumber conjugation3 = new ConjugationTensePersonGenderNumber();
        conjugation3.setId(3);
        conjugation3.setTense(tense);
        conjugation3.setPersonGenderNumber(personGenderNumber3);

        List<ConjugationTensePersonGenderNumber> expectedList = Arrays.asList(conjugation1, conjugation2, conjugation3);

        when(conjugationTensePersonGenderNumberRepository.findByTenseId(tenseId))
                .thenReturn(expectedList);

        // When
        List<ConjugationTensePersonGenderNumber> result = conjugationTensePersonGenderNumberService
                .getConjugationTensePersonGenderNumbersByTenseId(tenseId);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(expectedList, result);

        // Verify each element
        assertEquals(1, result.get(0).getId());
        assertEquals(PersonGenderNumber.PersonGenderNumberEnum.FIRST_PLURAL_NEUTRAL, result.get(0).getPersonGenderNumber().getPersonGenderNumberEnum());
        assertEquals(tenseId, result.get(0).getTense().getId());

        assertEquals(2, result.get(1).getId());
        assertEquals(PersonGenderNumber.PersonGenderNumberEnum.SECOND_PLURAL_NEUTRAL, result.get(1).getPersonGenderNumber().getPersonGenderNumberEnum());
        assertEquals(tenseId, result.get(1).getTense().getId());

        assertEquals(3, result.get(2).getId());
        assertEquals(PersonGenderNumber.PersonGenderNumberEnum.THIRD_PLURAL_NEUTRAL, result.get(2).getPersonGenderNumber().getPersonGenderNumberEnum());
        assertEquals(tenseId, result.get(2).getTense().getId());

        verify(conjugationTensePersonGenderNumberRepository, times(1)).findByTenseId(tenseId);
    }

    @Test
    void testGetConjugationTensePersonGenderNumbersByTenseId_WhenNoResultsExist() {
        // Given
        Integer tenseId = 999;
        List<ConjugationTensePersonGenderNumber> expectedList = new ArrayList<>();

        when(conjugationTensePersonGenderNumberRepository.findByTenseId(tenseId))
                .thenReturn(expectedList);

        // When
        List<ConjugationTensePersonGenderNumber> result = conjugationTensePersonGenderNumberService
                .getConjugationTensePersonGenderNumbersByTenseId(tenseId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());

        verify(conjugationTensePersonGenderNumberRepository, times(1)).findByTenseId(tenseId);
    }

    @Test
    void testGetConjugationTensePersonGenderNumbersByTenseId_WhenRepositoryReturnsNull() {
        // Given
        Integer tenseId = 1;

        when(conjugationTensePersonGenderNumberRepository.findByTenseId(tenseId))
                .thenReturn(null);

        // When
        List<ConjugationTensePersonGenderNumber> result = conjugationTensePersonGenderNumberService
                .getConjugationTensePersonGenderNumbersByTenseId(tenseId);

        // Then
        assertNull(result);

        verify(conjugationTensePersonGenderNumberRepository, times(1)).findByTenseId(tenseId);
    }

    @Test
    void testGetConjugationTensePersonGenderNumbersByTenseId_WithSingleResult() {
        // Given
        Integer tenseId = 2;

        PersonGenderNumber personGenderNumber = new PersonGenderNumber();
        personGenderNumber.setId(1);
        personGenderNumber.setPersonGenderNumberEnum(PersonGenderNumber.PersonGenderNumberEnum.FIRST_PLURAL_NEUTRAL);

        Tense tense = new Tense();
        tense.setId(tenseId);

        ConjugationTensePersonGenderNumber conjugation = new ConjugationTensePersonGenderNumber();
        conjugation.setId(1);
        conjugation.setTense(tense);
        conjugation.setPersonGenderNumber(personGenderNumber);

        List<ConjugationTensePersonGenderNumber> expectedList = Arrays.asList(conjugation);

        when(conjugationTensePersonGenderNumberRepository.findByTenseId(tenseId))
                .thenReturn(expectedList);

        // When
        List<ConjugationTensePersonGenderNumber> result = conjugationTensePersonGenderNumberService
                .getConjugationTensePersonGenderNumbersByTenseId(tenseId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(PersonGenderNumber.PersonGenderNumberEnum.FIRST_PLURAL_NEUTRAL, result.get(0).getPersonGenderNumber().getPersonGenderNumberEnum());
        assertEquals(tenseId, result.get(0).getTense().getId());

        verify(conjugationTensePersonGenderNumberRepository, times(1)).findByTenseId(tenseId);
    }

    @Test
    void testGetConjugationTensePersonGenderNumbersByTenseId_WithAllPersonGenderNumbers() {
        // Given
        Integer tenseId = 3;
        Tense tense = new Tense();
        tense.setId(tenseId);

        // Create all possible PersonGenderNumber combinations
        List<ConjugationTensePersonGenderNumber> expectedList = new ArrayList<>();
        PersonGenderNumber.PersonGenderNumberEnum[] allEnums = PersonGenderNumber.PersonGenderNumberEnum.values();

        for (int i = 0; i < allEnums.length; i++) {
            PersonGenderNumber personGenderNumber = new PersonGenderNumber();
            personGenderNumber.setId(i + 1);
            personGenderNumber.setPersonGenderNumberEnum(allEnums[i]);

            ConjugationTensePersonGenderNumber conjugation = new ConjugationTensePersonGenderNumber();
            conjugation.setId(i + 1);
            conjugation.setTense(tense);
            conjugation.setPersonGenderNumber(personGenderNumber);

            expectedList.add(conjugation);
        }

        when(conjugationTensePersonGenderNumberRepository.findByTenseId(tenseId))
                .thenReturn(expectedList);

        // When
        List<ConjugationTensePersonGenderNumber> result = conjugationTensePersonGenderNumberService
                .getConjugationTensePersonGenderNumbersByTenseId(tenseId);

        // Then
        assertNotNull(result);
        assertEquals(allEnums.length, result.size());

        for (int i = 0; i < allEnums.length; i++) {
            assertEquals(i + 1, result.get(i).getId());
            assertEquals(allEnums[i], result.get(i).getPersonGenderNumber().getPersonGenderNumberEnum());
            assertEquals(tenseId, result.get(i).getTense().getId());
        }

        verify(conjugationTensePersonGenderNumberRepository, times(1)).findByTenseId(tenseId);
    }

    @Test
    void testGetConjugationTensePersonGenderNumbersByTenseId_WithNullTenseId() {
        // Given
        Integer tenseId = null;
        List<ConjugationTensePersonGenderNumber> expectedList = new ArrayList<>();

        when(conjugationTensePersonGenderNumberRepository.findByTenseId(tenseId))
                .thenReturn(expectedList);

        // When
        List<ConjugationTensePersonGenderNumber> result = conjugationTensePersonGenderNumberService
                .getConjugationTensePersonGenderNumbersByTenseId(tenseId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(conjugationTensePersonGenderNumberRepository, times(1)).findByTenseId(tenseId);
    }

    @Test
    void testGetConjugationTensePersonGenderNumbersByTenseId_WithZeroTenseId() {
        // Given
        Integer tenseId = 0;
        List<ConjugationTensePersonGenderNumber> expectedList = new ArrayList<>();

        when(conjugationTensePersonGenderNumberRepository.findByTenseId(tenseId))
                .thenReturn(expectedList);

        // When
        List<ConjugationTensePersonGenderNumber> result = conjugationTensePersonGenderNumberService
                .getConjugationTensePersonGenderNumbersByTenseId(tenseId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(conjugationTensePersonGenderNumberRepository, times(1)).findByTenseId(tenseId);
    }

    @Test
    void testGetConjugationTensePersonGenderNumbersByTenseId_WithNegativeTenseId() {
        // Given
        Integer tenseId = -1;
        List<ConjugationTensePersonGenderNumber> expectedList = new ArrayList<>();

        when(conjugationTensePersonGenderNumberRepository.findByTenseId(tenseId))
                .thenReturn(expectedList);

        // When
        List<ConjugationTensePersonGenderNumber> result = conjugationTensePersonGenderNumberService
                .getConjugationTensePersonGenderNumbersByTenseId(tenseId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(conjugationTensePersonGenderNumberRepository, times(1)).findByTenseId(tenseId);
    }

    @Test
    void testGetConjugationTensePersonGenderNumbersByTenseId_MultipleCalls() {
        // Given
        Integer tenseId1 = 1;
        Integer tenseId2 = 2;

        PersonGenderNumber personGenderNumber1 = new PersonGenderNumber();
        personGenderNumber1.setId(1);
        personGenderNumber1.setPersonGenderNumberEnum(PersonGenderNumber.PersonGenderNumberEnum.FIRST_PLURAL_NEUTRAL);

        PersonGenderNumber personGenderNumber2 = new PersonGenderNumber();
        personGenderNumber2.setId(2);
        personGenderNumber2.setPersonGenderNumberEnum(PersonGenderNumber.PersonGenderNumberEnum.SECOND_PLURAL_NEUTRAL);

        Tense tense1 = new Tense();
        tense1.setId(tenseId1);

        Tense tense2 = new Tense();
        tense2.setId(tenseId2);

        ConjugationTensePersonGenderNumber conjugation1 = new ConjugationTensePersonGenderNumber();
        conjugation1.setId(1);
        conjugation1.setTense(tense1);
        conjugation1.setPersonGenderNumber(personGenderNumber1);

        ConjugationTensePersonGenderNumber conjugation2 = new ConjugationTensePersonGenderNumber();
        conjugation2.setId(2);
        conjugation2.setTense(tense2);
        conjugation2.setPersonGenderNumber(personGenderNumber2);

        List<ConjugationTensePersonGenderNumber> expectedList1 = Arrays.asList(conjugation1);
        List<ConjugationTensePersonGenderNumber> expectedList2 = Arrays.asList(conjugation2);

        when(conjugationTensePersonGenderNumberRepository.findByTenseId(tenseId1))
                .thenReturn(expectedList1);
        when(conjugationTensePersonGenderNumberRepository.findByTenseId(tenseId2))
                .thenReturn(expectedList2);

        // When
        List<ConjugationTensePersonGenderNumber> result1 = conjugationTensePersonGenderNumberService
                .getConjugationTensePersonGenderNumbersByTenseId(tenseId1);
        List<ConjugationTensePersonGenderNumber> result2 = conjugationTensePersonGenderNumberService
                .getConjugationTensePersonGenderNumbersByTenseId(tenseId2);

        // Then
        assertNotNull(result1);
        assertEquals(1, result1.size());
        assertEquals(1, result1.get(0).getId());
        assertEquals(PersonGenderNumber.PersonGenderNumberEnum.FIRST_PLURAL_NEUTRAL, result1.get(0).getPersonGenderNumber().getPersonGenderNumberEnum());

        assertNotNull(result2);
        assertEquals(1, result2.size());
        assertEquals(2, result2.get(0).getId());
        assertEquals(PersonGenderNumber.PersonGenderNumberEnum.SECOND_PLURAL_NEUTRAL, result2.get(0).getPersonGenderNumber().getPersonGenderNumberEnum());

        verify(conjugationTensePersonGenderNumberRepository, times(1)).findByTenseId(tenseId1);
        verify(conjugationTensePersonGenderNumberRepository, times(1)).findByTenseId(tenseId2);
    }
}
