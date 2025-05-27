package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.ConjugationRegularTenseEnding;
import com.antonio.apprendrebackend.service.model.PersonGenderNumber;
import com.antonio.apprendrebackend.service.model.Tense;
import com.antonio.apprendrebackend.service.model.VerbGroup;
import com.antonio.apprendrebackend.service.repository.ConjugationRegularTenseEndingRepository;
import com.antonio.apprendrebackend.service.service.impl.ConjugationRegularTenseEndingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConjugationRegularTenseEndingServiceImplTest {
    @Mock
    private ConjugationRegularTenseEndingRepository conjugationRegularTenseEndingRepository;

    @InjectMocks
    private ConjugationRegularTenseEndingServiceImpl conjugationRegularTenseEndingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn() {
        // Given
        Tense.TenseEnum tenseEnum = Tense.TenseEnum.PRE_INF_FR;
        VerbGroup.VerbGroupEnum verbGroupEnum = VerbGroup.VerbGroupEnum.FIRST_GROUP_FR;
        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = Arrays.asList(
                PersonGenderNumber.PersonGenderNumberEnum.FIRST_SINGULAR_MASCULINE,
                PersonGenderNumber.PersonGenderNumberEnum.SECOND_SINGULAR_FEMININE,
                PersonGenderNumber.PersonGenderNumberEnum.THIRD_PLURAL_MASCULINE
        );

        // Create mock ConjugationRegularTenseEnding objects
        ConjugationRegularTenseEnding ending1 = new ConjugationRegularTenseEnding();
        ending1.setId(1);
        ending1.setEnding("-o");

        ConjugationRegularTenseEnding ending2 = new ConjugationRegularTenseEnding();
        ending2.setId(2);
        ending2.setEnding("-as");

        ConjugationRegularTenseEnding ending3 = new ConjugationRegularTenseEnding();
        ending3.setId(3);
        ending3.setEnding("-an");

        List<ConjugationRegularTenseEnding> expectedEndings = Arrays.asList(ending1, ending2, ending3);

        // When
        when(conjugationRegularTenseEndingRepository.findByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                tenseEnum, verbGroupEnum, personGenderNumberEnums)).thenReturn(expectedEndings);

        List<ConjugationRegularTenseEnding> result = conjugationRegularTenseEndingService
                .getByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                        tenseEnum, verbGroupEnum, personGenderNumberEnums);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("-o", result.get(0).getEnding());
        assertEquals(2, result.get(1).getId());
        assertEquals("-as", result.get(1).getEnding());
        assertEquals(3, result.get(2).getId());
        assertEquals("-an", result.get(2).getEnding());
        verify(conjugationRegularTenseEndingRepository, times(1))
                .findByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                        tenseEnum, verbGroupEnum, personGenderNumberEnums);
    }

    @Test
    void testGetByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumInEmpty() {
        // Given
        Tense.TenseEnum tenseEnum = Tense.TenseEnum.PRE_INF_FR;
        VerbGroup.VerbGroupEnum verbGroupEnum = VerbGroup.VerbGroupEnum.SECOND_GROUP_FR;
        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = Arrays.asList(
                PersonGenderNumber.PersonGenderNumberEnum.FIRST_SINGULAR_MASCULINE
        );

        // When
        when(conjugationRegularTenseEndingRepository.findByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                tenseEnum, verbGroupEnum, personGenderNumberEnums)).thenReturn(Arrays.asList());

        List<ConjugationRegularTenseEnding> result = conjugationRegularTenseEndingService
                .getByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                        tenseEnum, verbGroupEnum, personGenderNumberEnums);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(conjugationRegularTenseEndingRepository, times(1))
                .findByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                        tenseEnum, verbGroupEnum, personGenderNumberEnums);
    }

    @Test
    void testGetByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumInNullTenseEnum() {
        // Given
        Tense.TenseEnum tenseEnum = null;
        VerbGroup.VerbGroupEnum verbGroupEnum = VerbGroup.VerbGroupEnum.FIRST_GROUP_FR;
        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = Arrays.asList(
                PersonGenderNumber.PersonGenderNumberEnum.FIRST_SINGULAR_MASCULINE
        );

        // When
        when(conjugationRegularTenseEndingRepository.findByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                tenseEnum, verbGroupEnum, personGenderNumberEnums)).thenReturn(Arrays.asList());

        List<ConjugationRegularTenseEnding> result = conjugationRegularTenseEndingService
                .getByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                        tenseEnum, verbGroupEnum, personGenderNumberEnums);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(conjugationRegularTenseEndingRepository, times(1))
                .findByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                        tenseEnum, verbGroupEnum, personGenderNumberEnums);
    }

    @Test
    void testGetByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumInNullVerbGroupEnum() {
        // Given
        Tense.TenseEnum tenseEnum = Tense.TenseEnum.PRE_INF_FR;
        VerbGroup.VerbGroupEnum verbGroupEnum = null;
        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = Arrays.asList(
                PersonGenderNumber.PersonGenderNumberEnum.FIRST_SINGULAR_MASCULINE
        );

        // When
        when(conjugationRegularTenseEndingRepository.findByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                tenseEnum, verbGroupEnum, personGenderNumberEnums)).thenReturn(Arrays.asList());

        List<ConjugationRegularTenseEnding> result = conjugationRegularTenseEndingService
                .getByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                        tenseEnum, verbGroupEnum, personGenderNumberEnums);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(conjugationRegularTenseEndingRepository, times(1))
                .findByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                        tenseEnum, verbGroupEnum, personGenderNumberEnums);
    }

    @Test
    void testGetByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumInNullPersonGenderNumberList() {
        // Given
        Tense.TenseEnum tenseEnum = Tense.TenseEnum.PRE_INF_FR;
        VerbGroup.VerbGroupEnum verbGroupEnum = VerbGroup.VerbGroupEnum.FIRST_GROUP_FR;
        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = null;

        // When
        when(conjugationRegularTenseEndingRepository.findByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                tenseEnum, verbGroupEnum, personGenderNumberEnums)).thenReturn(Arrays.asList());

        List<ConjugationRegularTenseEnding> result = conjugationRegularTenseEndingService
                .getByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                        tenseEnum, verbGroupEnum, personGenderNumberEnums);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(conjugationRegularTenseEndingRepository, times(1))
                .findByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                        tenseEnum, verbGroupEnum, personGenderNumberEnums);
    }

    @Test
    void testGetByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumInEmptyPersonGenderNumberList() {
        // Given
        Tense.TenseEnum tenseEnum = Tense.TenseEnum.PRE_INF_FR;
        VerbGroup.VerbGroupEnum verbGroupEnum = VerbGroup.VerbGroupEnum.FIRST_GROUP_FR;
        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = Arrays.asList();

        // When
        when(conjugationRegularTenseEndingRepository.findByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                tenseEnum, verbGroupEnum, personGenderNumberEnums)).thenReturn(Arrays.asList());

        List<ConjugationRegularTenseEnding> result = conjugationRegularTenseEndingService
                .getByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                        tenseEnum, verbGroupEnum, personGenderNumberEnums);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(conjugationRegularTenseEndingRepository, times(1))
                .findByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                        tenseEnum, verbGroupEnum, personGenderNumberEnums);
    }

    @Test
    void testGetByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumInAllNull() {
        // Given
        Tense.TenseEnum tenseEnum = null;
        VerbGroup.VerbGroupEnum verbGroupEnum = null;
        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = null;

        // When
        when(conjugationRegularTenseEndingRepository.findByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                tenseEnum, verbGroupEnum, personGenderNumberEnums)).thenReturn(Arrays.asList());

        List<ConjugationRegularTenseEnding> result = conjugationRegularTenseEndingService
                .getByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                        tenseEnum, verbGroupEnum, personGenderNumberEnums);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(conjugationRegularTenseEndingRepository, times(1))
                .findByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                        tenseEnum, verbGroupEnum, personGenderNumberEnums);
    }

    @Test
    void testGetByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumInSingleResult() {
        // Given
        Tense.TenseEnum tenseEnum = Tense.TenseEnum.IMP_INF_FR;
        VerbGroup.VerbGroupEnum verbGroupEnum = VerbGroup.VerbGroupEnum.THIRD_GROUP_FR;
        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = Arrays.asList(
                PersonGenderNumber.PersonGenderNumberEnum.FIRST_SINGULAR_MASCULINE
        );

        ConjugationRegularTenseEnding ending = new ConjugationRegularTenseEnding();
        ending.setId(1);
        ending.setEnding("-ía");

        List<ConjugationRegularTenseEnding> expectedEndings = Arrays.asList(ending);

        // When
        when(conjugationRegularTenseEndingRepository.findByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                tenseEnum, verbGroupEnum, personGenderNumberEnums)).thenReturn(expectedEndings);

        List<ConjugationRegularTenseEnding> result = conjugationRegularTenseEndingService
                .getByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                        tenseEnum, verbGroupEnum, personGenderNumberEnums);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("-ía", result.get(0).getEnding());
        verify(conjugationRegularTenseEndingRepository, times(1))
                .findByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
                        tenseEnum, verbGroupEnum, personGenderNumberEnums);
    }
}
