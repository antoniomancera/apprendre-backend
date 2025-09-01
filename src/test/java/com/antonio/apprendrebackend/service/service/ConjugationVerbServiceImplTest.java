package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.ConjugationTenseDTO;
import com.antonio.apprendrebackend.service.dto.ConjugationWordPositionDTO;
import com.antonio.apprendrebackend.service.dto.TenseDTO;
import com.antonio.apprendrebackend.service.exception.ConjugationVerbNotFoundException;
import com.antonio.apprendrebackend.service.mapper.TenseMapper;
import com.antonio.apprendrebackend.service.mapper.WordSenseMapper;
import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.service.impl.ConjugationVerbServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConjugationVerbServiceImplTest {
    @Mock
    private WordSenseService wordSenseService;
    @Mock
    private ConjugationVariationService conjugationVariationService;
    @Mock
    private TenseService tenseService;
    @Mock
    private ConjugationNonExistService conjugationNonExistService;
    @Mock
    private ConjugationVerbFormIrregularService conjugationVerbFormIrregularService;
    @Mock
    private ConjugationRegularTenseEndingService conjugationRegularTenseEndingService;
    @Mock
    private ConjugationTensePersonGenderNumberService conjugationTensePersonGenderNumberService;
    @Mock
    private ConjugationRegularTenseBaseVariationService conjugationRegularTenseBaseVariationService;
    @Mock
    private ConjugationVerbCompoundStructureItemService conjugationVerbCompoundStructureItemService;
    @Mock
    private ConjugationVerbWordWordSenseService conjugationVerbWordWordSenseService;
    @Mock
    private TenseMapper tenseMapper;
    @Mock
    private WordSenseMapper wordSenseMapper;

    @InjectMocks
    private ConjugationVerbServiceImpl conjugationVerbService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetConjugationVerbByWordIdWhenVerbExists() {
        // Given
        Integer wordId = 1;
        ConjugationVerb expectedConjugationVerb = new ConjugationVerb();
        expectedConjugationVerb.setId(1);

        ConjugationVerbWordWordSense conjugationVerbWordWordSense = new ConjugationVerbWordWordSense();
        conjugationVerbWordWordSense.setConjugationVerb(expectedConjugationVerb);

        Optional<ConjugationVerbWordWordSense> optionalConjugationVerbWordWordSense =
                Optional.of(conjugationVerbWordWordSense);

        // When
        when(conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordId(wordId))
                .thenReturn(optionalConjugationVerbWordWordSense);

        ConjugationVerb result = conjugationVerbService.getConjugationVerbByWordId(wordId);

        // Then
        assertNotNull(result);
        assertEquals(expectedConjugationVerb.getId(), result.getId());
        verify(conjugationVerbWordWordSenseService, times(1))
                .getConjugationVerbWordWordSenseByWordId(wordId);
    }

    @Test
    void testGetConjugationVerbByWordIdWhenVerbNotExists() {
        // Given
        Integer wordId = 1;
        Optional<ConjugationVerbWordWordSense> emptyOptional = Optional.empty();

        // When
        when(conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordId(wordId))
                .thenReturn(emptyOptional);

        // Then
        assertThrows(ConjugationVerbNotFoundException.class, () -> {
            conjugationVerbService.getConjugationVerbByWordId(wordId);
        });

        verify(conjugationVerbWordWordSenseService, times(1))
                .getConjugationVerbWordWordSenseByWordId(wordId);
    }

    @Test
    void testGetConjugationVerbByWordSenseIdWhenDirectlyExists() {
        // Given
        Integer wordSenseId = 1;
        ConjugationVerb expectedConjugationVerb = new ConjugationVerb();
        expectedConjugationVerb.setId(1);

        ConjugationVerbWordWordSense conjugationVerbWordWordSense = new ConjugationVerbWordWordSense();
        conjugationVerbWordWordSense.setConjugationVerb(expectedConjugationVerb);

        Optional<ConjugationVerbWordWordSense> optionalConjugationVerbWordWordSense =
                Optional.of(conjugationVerbWordWordSense);

        // When
        when(conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordSenseId(wordSenseId))
                .thenReturn(optionalConjugationVerbWordWordSense);

        ConjugationVerb result = conjugationVerbService.getConjugationVerbByWordSenseId(wordSenseId);

        // Then
        assertNotNull(result);
        assertEquals(expectedConjugationVerb.getId(), result.getId());
        verify(conjugationVerbWordWordSenseService, times(1))
                .getConjugationVerbWordWordSenseByWordSenseId(wordSenseId);
    }

    @Test
    void testGetConjugationVerbByWordSenseIdWhenNotDirectlyExistsFallsBackToWordId() {
        // Given
        Integer wordSenseId = 1;
        Integer wordId = 2;

        WordSense wordSense = new WordSense();
        Word word = new Word();
        word.setId(wordId);
        wordSense.setWord(word);

        ConjugationVerb expectedConjugationVerb = new ConjugationVerb();
        expectedConjugationVerb.setId(1);

        ConjugationVerbWordWordSense conjugationVerbWordWordSense = new ConjugationVerbWordWordSense();
        conjugationVerbWordWordSense.setConjugationVerb(expectedConjugationVerb);

        Optional<ConjugationVerbWordWordSense> emptyOptional = Optional.empty();
        Optional<ConjugationVerbWordWordSense> optionalWithConjugationVerb =
                Optional.of(conjugationVerbWordWordSense);

        // When
        when(conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordSenseId(wordSenseId))
                .thenReturn(emptyOptional);
        when(wordSenseService.getById(wordSenseId)).thenReturn(wordSense);
        when(conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordId(wordId))
                .thenReturn(optionalWithConjugationVerb);

        ConjugationVerb result = conjugationVerbService.getConjugationVerbByWordSenseId(wordSenseId);

        // Then
        assertNotNull(result);
        assertEquals(expectedConjugationVerb.getId(), result.getId());
        verify(conjugationVerbWordWordSenseService, times(1))
                .getConjugationVerbWordWordSenseByWordSenseId(wordSenseId);
        verify(wordSenseService, times(1)).getById(wordSenseId);
        verify(conjugationVerbWordWordSenseService, times(1))
                .getConjugationVerbWordWordSenseByWordId(wordId);
    }

    @Test
    void testGetConjugationCompleteWithNonCompoundTenses() {
        // Given
        Integer wordSenseId = 1;

        // Setup WordSense
        WordSense verb = new WordSense();
        Word word = new Word();
        Language language = new Language();
        language.setId(1);
        word.setLanguage(language);
        verb.setWord(word);

        // Setup ConjugationVerb
        ConjugationVerb conjugationVerb = new ConjugationVerb();
        conjugationVerb.setId(1);
        VerbGroupEnding verbGroupEnding = new VerbGroupEnding();
        VerbGroup verbGroup = new VerbGroup();
        verbGroup.setId(1);
        verbGroupEnding.setVerbGroup(verbGroup);
        conjugationVerb.setVerbGroupEnding(verbGroupEnding);

        // Setup ConjugationVariation
        ConjugationVariation conjugationVariation = new ConjugationVariation();
        conjugationVariation.setId(1);

        // Setup Tense
        Tense tense = new Tense();
        tense.setId(1);
        tense.setCode(Tense.TenseEnum.PRE_INF_FR);
        List<Tense> tenses = Arrays.asList(tense);

        // Setup PersonGenderNumber
        PersonGenderNumber personGenderNumber = new PersonGenderNumber();
        personGenderNumber.setPersonGenderNumberEnum(PersonGenderNumber.PersonGenderNumberEnum.FIRST_PLURAL_NEUTRAL);

        ConjugationTensePersonGenderNumber conjugationTensePersonGenderNumber = new ConjugationTensePersonGenderNumber();
        conjugationTensePersonGenderNumber.setPersonGenderNumber(personGenderNumber);
        List<ConjugationTensePersonGenderNumber> conjugationTensePersonGenderNumbers =
                Arrays.asList(conjugationTensePersonGenderNumber);

        // Setup ConjugationRegularTenseEnding
        ConjugationRegularTenseEnding conjugationRegularTenseEnding = new ConjugationRegularTenseEnding();
        conjugationRegularTenseEnding.setEnding("o");
        conjugationRegularTenseEnding.setTense(tense);
        conjugationRegularTenseEnding.setPersonGenderNumber(personGenderNumber);
        List<ConjugationRegularTenseEnding> conjugationRegularTenseEndings =
                Arrays.asList(conjugationRegularTenseEnding);

        // When
        when(wordSenseService.getById(wordSenseId)).thenReturn(verb);
        when(conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordSenseId(wordSenseId))
                .thenReturn(Optional.of(createConjugationVerbWordWordSense(conjugationVerb)));
        when(conjugationVariationService.getConjugationVariationByConjugationVerb(conjugationVerb))
                .thenReturn(conjugationVariation);
        when(tenseService.getByLanguage(language)).thenReturn(tenses);
        when(conjugationVerbCompoundStructureItemService.getConjugationVerbCompoundStructureItemsByTenseId(tense.getId()))
                .thenReturn(Collections.emptyList());
        when(conjugationRegularTenseBaseVariationService.getRegularTenseBase(verb, conjugationVerb, tense.getId()))
                .thenReturn("habl");
        when(conjugationTensePersonGenderNumberService.getConjugationTensePersonGenderNumbersByTenseId(tense.getId()))
                .thenReturn(conjugationTensePersonGenderNumbers);
        when(conjugationRegularTenseEndingService.getByTenseInPersonGenderNumberAndVerbGroup(
                eq(tense.getId()), any(), eq(verbGroup.getId())))
                .thenReturn(conjugationRegularTenseEndings);
        when(conjugationNonExistService.getByConjugationNonExistByTenseAndPersonGenderNumber(eq(tense), any()))
                .thenReturn(Collections.emptyList());
        when(conjugationVerbFormIrregularService.getConjugationVerbFormIrregularsByConjugationVariation(conjugationVariation))
                .thenReturn(Collections.emptyList());
        when(tenseMapper.toDTO(tense)).thenReturn(createTenseDTO(tense));

        List<ConjugationTenseDTO> result = conjugationVerbService.getConjugationComplete(wordSenseId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        ConjugationTenseDTO conjugationTenseDTO = result.get(0);
        assertNotNull(conjugationTenseDTO.getTense());

        // Verificar que el mapa contiene la entrada esperada
        Map<PersonGenderNumber.PersonGenderNumberEnum, List<ConjugationWordPositionDTO>> personGenderNumberConjugation =
                conjugationTenseDTO.getPersonGenderNumberConjugation();
        assertNotNull(personGenderNumberConjugation);
        assertEquals(1, personGenderNumberConjugation.size());

        assertTrue(personGenderNumberConjugation.containsKey(PersonGenderNumber.PersonGenderNumberEnum.FIRST_PLURAL_NEUTRAL));

        List<ConjugationWordPositionDTO> conjugationWordPositions =
                personGenderNumberConjugation.get(PersonGenderNumber.PersonGenderNumberEnum.FIRST_PLURAL_NEUTRAL);
        assertNotNull(conjugationWordPositions);
        assertEquals(1, conjugationWordPositions.size());

        ConjugationWordPositionDTO conjugationWordPosition = conjugationWordPositions.get(0);
        assertNotNull(conjugationWordPosition.getConjugationRegularIrregular());
        assertEquals("hablo", conjugationWordPosition.getConjugationRegularIrregular().getConjugationRegular());

        // Verify interactions
        verify(wordSenseService, times(1)).getById(wordSenseId);
        verify(conjugationVariationService, times(1)).getConjugationVariationByConjugationVerb(conjugationVerb);
        verify(tenseService, times(1)).getByLanguage(language);
    }

    @Test
    void testGetConjugationCompleteWithIrregularVerb() {
        // Given
        Integer wordSenseId = 1;

        // Setup basic objects
        WordSense verb = createWordSense();
        ConjugationVerb conjugationVerb = createConjugationVerb();
        ConjugationVariation conjugationVariation = new ConjugationVariation();
        conjugationVariation.setId(1);

        Tense tense = new Tense();
        tense.setId(1);
        tense.setCode(Tense.TenseEnum.PRE_INF_FR);
        List<Tense> tenses = Arrays.asList(tense);

        // Setup irregular conjugation
        ConjugationVerbFormIrregular irregular = new ConjugationVerbFormIrregular();
        irregular.setName("soy");
        ConjugationVerbForm conjugationVerbForm = new ConjugationVerbForm();
        conjugationVerbForm.setTense(tense);
        PersonGenderNumber personGenderNumber = new PersonGenderNumber();
        personGenderNumber.setPersonGenderNumberEnum(PersonGenderNumber.PersonGenderNumberEnum.FIRST_PLURAL_NEUTRAL);
        conjugationVerbForm.setPersonGenderNumber(personGenderNumber);
        irregular.setConjugationVerbForm(conjugationVerbForm);

        List<ConjugationVerbFormIrregular> irregulars = Arrays.asList(irregular);

        // Setup other required objects
        ConjugationTensePersonGenderNumber conjugationTensePersonGenderNumber = new ConjugationTensePersonGenderNumber();
        conjugationTensePersonGenderNumber.setPersonGenderNumber(personGenderNumber);
        List<ConjugationTensePersonGenderNumber> conjugationTensePersonGenderNumbers =
                Arrays.asList(conjugationTensePersonGenderNumber);

        ConjugationRegularTenseEnding conjugationRegularTenseEnding = new ConjugationRegularTenseEnding();
        conjugationRegularTenseEnding.setEnding("o");
        conjugationRegularTenseEnding.setTense(tense);
        conjugationRegularTenseEnding.setPersonGenderNumber(personGenderNumber);
        List<ConjugationRegularTenseEnding> conjugationRegularTenseEndings =
                Arrays.asList(conjugationRegularTenseEnding);

        // When
        when(wordSenseService.getById(wordSenseId)).thenReturn(verb);
        when(conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordSenseId(wordSenseId))
                .thenReturn(Optional.of(createConjugationVerbWordWordSense(conjugationVerb)));
        when(conjugationVariationService.getConjugationVariationByConjugationVerb(conjugationVerb))
                .thenReturn(conjugationVariation);
        when(tenseService.getByLanguage(verb.getWord().getLanguage())).thenReturn(tenses);
        when(conjugationVerbCompoundStructureItemService.getConjugationVerbCompoundStructureItemsByTenseId(tense.getId()))
                .thenReturn(Collections.emptyList());
        when(conjugationRegularTenseBaseVariationService.getRegularTenseBase(verb, conjugationVerb, tense.getId()))
                .thenReturn("s");
        when(conjugationTensePersonGenderNumberService.getConjugationTensePersonGenderNumbersByTenseId(tense.getId()))
                .thenReturn(conjugationTensePersonGenderNumbers);
        when(conjugationRegularTenseEndingService.getByTenseInPersonGenderNumberAndVerbGroup(
                eq(tense.getId()), any(), any()))
                .thenReturn(conjugationRegularTenseEndings);
        when(conjugationNonExistService.getByConjugationNonExistByTenseAndPersonGenderNumber(eq(tense), any()))
                .thenReturn(Collections.emptyList());
        when(conjugationVerbFormIrregularService.getConjugationVerbFormIrregularsByConjugationVariation(conjugationVariation))
                .thenReturn(irregulars);
        when(tenseMapper.toDTO(tense)).thenReturn(createTenseDTO(tense));

        List<ConjugationTenseDTO> result = conjugationVerbService.getConjugationComplete(wordSenseId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        ConjugationTenseDTO conjugationTenseDTO = result.get(0);

        // Verificar que el mapa contiene la entrada esperada para verbo irregular
        Map<PersonGenderNumber.PersonGenderNumberEnum, List<ConjugationWordPositionDTO>> personGenderNumberConjugation =
                conjugationTenseDTO.getPersonGenderNumberConjugation();
        assertNotNull(personGenderNumberConjugation);
        assertTrue(personGenderNumberConjugation.containsKey(PersonGenderNumber.PersonGenderNumberEnum.FIRST_PLURAL_NEUTRAL));

        List<ConjugationWordPositionDTO> conjugationWordPositions =
                personGenderNumberConjugation.get(PersonGenderNumber.PersonGenderNumberEnum.FIRST_PLURAL_NEUTRAL);
        assertNotNull(conjugationWordPositions);
        assertEquals(1, conjugationWordPositions.size());

        ConjugationWordPositionDTO conjugationWordPosition = conjugationWordPositions.get(0);
        assertNotNull(conjugationWordPosition.getConjugationRegularIrregular());
        assertEquals("soy", conjugationWordPosition.getConjugationRegularIrregular().getConjugationIrregular());
        assertEquals("so", conjugationWordPosition.getConjugationRegularIrregular().getConjugationRegular());
    }

    // Helper methods
    private WordSense createWordSense() {
        WordSense verb = new WordSense();
        Word word = new Word();
        Language language = new Language();
        language.setId(1);
        word.setLanguage(language);
        verb.setWord(word);
        return verb;
    }

    private ConjugationVerb createConjugationVerb() {
        ConjugationVerb conjugationVerb = new ConjugationVerb();
        conjugationVerb.setId(1);
        VerbGroupEnding verbGroupEnding = new VerbGroupEnding();
        VerbGroup verbGroup = new VerbGroup();
        verbGroup.setId(1);
        verbGroupEnding.setVerbGroup(verbGroup);
        conjugationVerb.setVerbGroupEnding(verbGroupEnding);
        return conjugationVerb;
    }

    private ConjugationVerbWordWordSense createConjugationVerbWordWordSense(ConjugationVerb conjugationVerb) {
        ConjugationVerbWordWordSense conjugationVerbWordWordSense = new ConjugationVerbWordWordSense();
        conjugationVerbWordWordSense.setConjugationVerb(conjugationVerb);
        return conjugationVerbWordWordSense;
    }

    private TenseDTO createTenseDTO(Tense tense) {
        return new TenseDTO();
    }
}
