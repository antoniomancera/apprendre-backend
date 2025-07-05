package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.repository.ConjugationRegularTenseBaseVariationRepository;
import com.antonio.apprendrebackend.service.service.impl.ConjugationRegularTenseBaseVariationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class ConjugationRegularTenseBaseVariationServiceImplTest {

    @Mock
    private ConjugationRegularTenseBaseVariationRepository conjugationRegularTenseBaseVariationRepository;

    @Mock
    private ConjugationVerbFormIrregularService conjugationVerbFormIrregularService;

    @Mock
    private ConjugationRegularTenseEndingService conjugationRegularTenseEndingService;

    @Mock
    private ConjugationVariationService conjugationVariationService;

    @InjectMocks
    private ConjugationRegularTenseBaseVariationServiceImpl conjugationRegularTenseBaseVariationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Tests for getConjugationRegularTenseBaseVariationByTenseId method

    @Test
    void testGetConjugationRegularTenseBaseVariationByTenseId_WhenExists() {
        // Given
        Integer tenseId = 1;

        Tense tense = new Tense();
        tense.setId(1);
        ConjugationRegularTenseBaseVariation variation = new ConjugationRegularTenseBaseVariation();
        variation.setId(1);
        variation.setTense(tense);

        when(conjugationRegularTenseBaseVariationRepository.findByTenseId(tenseId))
                .thenReturn(Optional.of(variation));

        // When
        ConjugationRegularTenseBaseVariation result = conjugationRegularTenseBaseVariationService
                .getConjugationRegularTenseBaseVariationByTenseId(tenseId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(tenseId, result.getTense().getId());
        verify(conjugationRegularTenseBaseVariationRepository, times(1)).findByTenseId(tenseId);
    }

    @Test
    void testGetConjugationRegularTenseBaseVariationByTenseId_WhenNotExists() {
        // Given
        Integer tenseId = 999;

        when(conjugationRegularTenseBaseVariationRepository.findByTenseId(tenseId))
                .thenReturn(Optional.empty());

        // When
        ConjugationRegularTenseBaseVariation result = conjugationRegularTenseBaseVariationService
                .getConjugationRegularTenseBaseVariationByTenseId(tenseId);

        // Then
        assertNull(result);
        verify(conjugationRegularTenseBaseVariationRepository, times(1)).findByTenseId(tenseId);
    }

    // Tests for getRegularTenseBase method

    @Test
    void testGetRegularTenseBase_WhenNoVariationExists() {
        // Given
        Integer tenseId = 1;
        WordSense verb = createWordSense("hablar");
        ConjugationVerb conjugationVerb = createConjugationVerb();

        when(conjugationRegularTenseBaseVariationRepository.findByTenseId(tenseId))
                .thenReturn(Optional.empty());

        // When
        String result = conjugationRegularTenseBaseVariationService.getRegularTenseBase(verb, conjugationVerb, tenseId);

        // Then
        assertEquals("habl", result); // "hablar" sin "ar"
        verify(conjugationRegularTenseBaseVariationRepository, times(1)).findByTenseId(tenseId);
    }

    @Test
    void testGetRegularTenseBase_WhenVariationExistsWithInfinitiveBaseAndEndingInBase() {
        // Given
        Integer tenseId = 1;
        WordSense verb = createWordSense("hablar");
        ConjugationVerb conjugationVerb = createConjugationVerb();

        Tense tense = new Tense();
        tense.setId(1);

        ConjugationRegularTenseBaseVariation variation = new ConjugationRegularTenseBaseVariation();
        variation.setId(1);
        variation.setTense(tense);
        variation.setIsInfinitiveBase(true);
        variation.setIsEndingInBase(true);

        when(conjugationRegularTenseBaseVariationRepository.findByTenseId(tenseId))
                .thenReturn(Optional.of(variation));

        // When
        String result = conjugationRegularTenseBaseVariationService.getRegularTenseBase(verb, conjugationVerb, tenseId);

        // Then
        assertEquals("hablar", result); // Retorna el verbo completo
        verify(conjugationRegularTenseBaseVariationRepository, times(1)).findByTenseId(tenseId);
    }

    @Test
    void testGetRegularTenseBase_WhenVariationExistsWithConjugationVerbFormBase() {
        // Given
        Integer tenseId = 1;
        Tense tense = new Tense();
        tense.setId(1);

        WordSense verb = createWordSense("decir");
        ConjugationVerb conjugationVerb = createConjugationVerb();

        ConjugationVerbForm verbForm = new ConjugationVerbForm();
        verbForm.setId(1);

        ConjugationRegularTenseBaseVariation variation = new ConjugationRegularTenseBaseVariation();
        variation.setId(1);
        variation.setTense(tense);
        variation.setIsInfinitiveBase(false);
        variation.setIsEndingInBase(true);
        variation.setConjugationVerbFormBase(verbForm);

        ConjugationVariation conjugationVariation = new ConjugationVariation();
        conjugationVariation.setId(1);

        ConjugationVerbFormIrregular irregular = new ConjugationVerbFormIrregular();
        irregular.setId(1);
        irregular.setName("digo");

        when(conjugationRegularTenseBaseVariationRepository.findByTenseId(tenseId))
                .thenReturn(Optional.of(variation));
        when(conjugationVariationService.getConjugationVariationByConjugationVerb(conjugationVerb))
                .thenReturn(conjugationVariation);
        when(conjugationVerbFormIrregularService.getConjugationVerbFormIrregularByConjugationVariationAndConjugationVerbForm(
                conjugationVariation, verbForm))
                .thenReturn(irregular);

        // When
        String result = conjugationRegularTenseBaseVariationService.getRegularTenseBase(verb, conjugationVerb, tenseId);

        // Then
        assertEquals("digo", result); // Retorna la forma irregular completa
        verify(conjugationRegularTenseBaseVariationRepository, times(1)).findByTenseId(tenseId);
        verify(conjugationVariationService, times(1)).getConjugationVariationByConjugationVerb(conjugationVerb);
        verify(conjugationVerbFormIrregularService, times(1))
                .getConjugationVerbFormIrregularByConjugationVariationAndConjugationVerbForm(conjugationVariation, verbForm);
    }

    @Test
    void testGetRegularTenseBase_WhenVariationExistsWithConjugationVerbFormBaseButNotEndingInBase() {
        // Given
        Integer tenseId = 1;
        WordSense verb = createWordSense("decir");
        ConjugationVerb conjugationVerb = createConjugationVerb();

        Tense tense = new Tense();
        tense.setId(1);

        PersonGenderNumber personGenderNumber = new PersonGenderNumber();
        personGenderNumber.setPersonGenderNumberEnum(PersonGenderNumber.PersonGenderNumberEnum.FIRST_PLURAL_NEUTRAL);

        ConjugationVerbForm verbForm = new ConjugationVerbForm();
        verbForm.setId(1);
        verbForm.setTense(tense);
        verbForm.setPersonGenderNumber(personGenderNumber);

        ConjugationRegularTenseBaseVariation variation = new ConjugationRegularTenseBaseVariation();
        variation.setId(1);
        variation.setTense(tense);
        variation.setIsInfinitiveBase(false);
        variation.setIsEndingInBase(false);
        variation.setConjugationVerbFormBase(verbForm);

        ConjugationVariation conjugationVariation = new ConjugationVariation();
        conjugationVariation.setId(1);

        ConjugationVerbFormIrregular irregular = new ConjugationVerbFormIrregular();
        irregular.setId(1);
        irregular.setName("digo");
        irregular.setConjugationVerbForm(verbForm);

        ConjugationRegularTenseEnding ending = new ConjugationRegularTenseEnding();
        ending.setId(1);
        ending.setEnding("o");

        when(conjugationRegularTenseBaseVariationRepository.findByTenseId(tenseId))
                .thenReturn(Optional.of(variation));
        when(conjugationVariationService.getConjugationVariationByConjugationVerb(conjugationVerb))
                .thenReturn(conjugationVariation);
        when(conjugationVerbFormIrregularService.getConjugationVerbFormIrregularByConjugationVariationAndConjugationVerbForm(
                conjugationVariation, verbForm))
                .thenReturn(irregular);
        when(conjugationRegularTenseEndingService.getByTenseAndPersonGenderNumberAndVerbGroup(
                anyInt(), any(PersonGenderNumber.PersonGenderNumberEnum.class), anyInt()))
                .thenReturn(ending);

        // When
        String result = conjugationRegularTenseBaseVariationService.getRegularTenseBase(verb, conjugationVerb, tenseId);

        // Then
        assertEquals("dig", result); // "digo" sin "o"
        verify(conjugationRegularTenseBaseVariationRepository, times(1)).findByTenseId(tenseId);
        verify(conjugationVariationService, times(1)).getConjugationVariationByConjugationVerb(conjugationVerb);
        verify(conjugationVerbFormIrregularService, times(1))
                .getConjugationVerbFormIrregularByConjugationVariationAndConjugationVerbForm(conjugationVariation, verbForm);
        verify(conjugationRegularTenseEndingService, times(1))
                .getByTenseAndPersonGenderNumberAndVerbGroup(anyInt(), any(PersonGenderNumber.PersonGenderNumberEnum.class), anyInt());
    }

    @Test
    void testGetRegularTenseBase_WhenVariationExistsButNoIrregularFormFound() {
        // Given
        Integer tenseId = 1;

        Tense tense = new Tense();
        tense.setId(1);

        WordSense verb = createWordSense("hablar");
        ConjugationVerb conjugationVerb = createConjugationVerb();

        ConjugationVerbForm verbForm = new ConjugationVerbForm();
        verbForm.setId(1);

        ConjugationRegularTenseBaseVariation variation = new ConjugationRegularTenseBaseVariation();
        variation.setId(1);
        variation.setTense(tense);
        variation.setIsInfinitiveBase(false);
        variation.setIsEndingInBase(false);
        variation.setConjugationVerbFormBase(verbForm);

        ConjugationVariation conjugationVariation = new ConjugationVariation();
        conjugationVariation.setId(1);

        when(conjugationRegularTenseBaseVariationRepository.findByTenseId(tenseId))
                .thenReturn(Optional.of(variation));
        when(conjugationVariationService.getConjugationVariationByConjugationVerb(conjugationVerb))
                .thenReturn(conjugationVariation);
        when(conjugationVerbFormIrregularService.getConjugationVerbFormIrregularByConjugationVariationAndConjugationVerbForm(
                conjugationVariation, verbForm))
                .thenReturn(null);

        // When
        String result = conjugationRegularTenseBaseVariationService.getRegularTenseBase(verb, conjugationVerb, tenseId);

        // Then
        assertEquals("habl", result); // Fallback al comportamiento regular
        verify(conjugationRegularTenseBaseVariationRepository, times(1)).findByTenseId(tenseId);
        verify(conjugationVariationService, times(1)).getConjugationVariationByConjugationVerb(conjugationVerb);
        verify(conjugationVerbFormIrregularService, times(1))
                .getConjugationVerbFormIrregularByConjugationVariationAndConjugationVerbForm(conjugationVariation, verbForm);
    }

    // Tests for edge cases

    @Test
    void testGetRegularTenseBase_WhenVerbDoesNotContainEnding() {
        // Given
        Integer tenseId = 1;
        WordSense verb = createWordSense("ir"); // Verbo que no contiene la terminación "ar"
        ConjugationVerb conjugationVerb = createConjugationVerb();

        when(conjugationRegularTenseBaseVariationRepository.findByTenseId(tenseId))
                .thenReturn(Optional.empty());

        // When
        String result = conjugationRegularTenseBaseVariationService.getRegularTenseBase(verb, conjugationVerb, tenseId);

        // Then
        assertEquals("ir", result); // Retorna el verbo completo cuando no encuentra la terminación
        verify(conjugationRegularTenseBaseVariationRepository, times(1)).findByTenseId(tenseId);
    }

    @Test
    void testGetRegularTenseBase_WhenVerbHasEndingInMiddle() {
        // Given
        Integer tenseId = 1;
        WordSense verb = createWordSense("arar"); // Verbo que contiene "ar" en el medio
        ConjugationVerb conjugationVerb = createConjugationVerb();

        when(conjugationRegularTenseBaseVariationRepository.findByTenseId(tenseId))
                .thenReturn(Optional.empty());

        // When
        String result = conjugationRegularTenseBaseVariationService.getRegularTenseBase(verb, conjugationVerb, tenseId);

        // Then
        assertEquals("ar", result); // Remueve la última ocurrencia de "ar"
        verify(conjugationRegularTenseBaseVariationRepository, times(1)).findByTenseId(tenseId);
    }

    // Helper methods

    private WordSense createWordSense(String verbName) {
        Word word = new Word();
        word.setId(1);
        word.setName(verbName);

        WordSense wordSense = new WordSense();
        wordSense.setId(1);
        wordSense.setWord(word);

        return wordSense;
    }

    private ConjugationVerb createConjugationVerb() {
        VerbEnding verbEnding = new VerbEnding();
        verbEnding.setId(1);
        verbEnding.setName("ar");

        VerbGroup verbGroup = new VerbGroup();
        verbGroup.setId(1);

        VerbGroupEnding verbGroupEnding = new VerbGroupEnding();
        verbGroupEnding.setId(1);
        verbGroupEnding.setVerbEnding(verbEnding);
        verbGroupEnding.setVerbGroup(verbGroup);

        ConjugationVerb conjugationVerb = new ConjugationVerb();
        conjugationVerb.setId(1);
        conjugationVerb.setVerbGroupEnding(verbGroupEnding);

        return conjugationVerb;
    }
}
