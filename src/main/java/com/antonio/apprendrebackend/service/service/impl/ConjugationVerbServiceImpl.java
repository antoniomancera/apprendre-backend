package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.ConjugationCompleteDTO;
import com.antonio.apprendrebackend.service.dto.ConjugationRegularIrregularDTO;
import com.antonio.apprendrebackend.service.exception.ConjugationVerbNotFoundException;
import com.antonio.apprendrebackend.service.exception.WordTypeNotVerbException;
import com.antonio.apprendrebackend.service.mapper.TenseMapper;
import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.repository.ConjugationVerbRepository;
import com.antonio.apprendrebackend.service.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.antonio.apprendrebackend.service.model.WordCollection.WordCollectionEnum.CONJ_PRO_NON_IMP_FR;

@Service
public class ConjugationVerbServiceImpl implements ConjugationVerbService {
    @Autowired
    private ConjugationVerbRepository conjugationVerbRepository;
    @Autowired
    private WordService wordService;
    @Autowired
    private WordSenseService wordSenseService;
    @Autowired
    private WordCollectionItemService wordCollectionItemService;
    @Autowired
    private ConjugationVariationService conjugationVariationService;
    @Autowired
    private TenseService tenseService;
    @Autowired
    private ConjugationNonExistService conjugationNonExistService;
    @Autowired
    private ConjugationVerbFormIrregularService conjugationVerbFormIrregularService;
    @Autowired
    private ConjugationRegularTenseEndingService conjugationRegularTenseEndingService;

    @Autowired
    private TenseMapper tenseMapper;

    //TODO: complete

    /**
     * Return the conjugationComplete of a verb
     *
     * @param wordISenseId
     * @return
     */
    @Override
    public List<ConjugationCompleteDTO> getConjugationComplete(int wordISenseId) {
        WordSense verb = wordSenseService.getById(wordISenseId);
        List<ConjugationCompleteDTO> conjugationCompleteDTOS = new ArrayList<>();

        ConjugationVerb conjugationVerb = getConjugationVerb(verb);
        Optional<ConjugationVariation> conjugationVariationOptional = conjugationVariationService.getByConjugationVerb(conjugationVerb);
        List<Tense> tenses = tenseService.getByLanguage(verb.getWord().getLanguage());
        List<Tense> tenseSimples = tenses.stream().filter((tense) -> tense.getTenseStructureType().equals(Tense.TenseStructureType.SIMPLE)).collect(Collectors.toList());
        List<Tense> tenseNonSimples = tenses.stream().filter((tense) -> !tense.getTenseStructureType().equals(Tense.TenseStructureType.SIMPLE)).collect(Collectors.toList());


        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = wordCollectionItemService.getWordCollectionItemsByWordCollectionEnum(CONJ_PRO_NON_IMP_FR).stream().map(word -> word.getPersonGenderNumber().getPersonGenderNumberEnum()).collect(Collectors.toList());

        List<ConjugationVerbForm> conjugationNonExistVerbForms = new ArrayList<>();
        List<ConjugationVerbFormIrregular> conjugationVerbFormIrregulars = new ArrayList<>();
        String verbEndingRemoved = removeEndingFromVerb(verb, conjugationVerb.getVerbGroupEnding().getVerbEnding().getName());

        List<ConjugationRegularTenseEnding> conjugationRegularTenseEndings = new ArrayList<>();
        for (Tense tense : tenseSimples) {
            conjugationRegularTenseEndings = conjugationRegularTenseEndingService.getByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(tense.getTenseEnum(), conjugationVerb.getVerbGroupEnding().getVerbGroup().getVerbGroupEnum(), personGenderNumberEnums);

            if (tense.getIsFinite() == null || !tense.getIsFinite()) {
                conjugationNonExistVerbForms = conjugationNonExistService.getByConjugationNonExistByTenseAndPersonGenderNumber(tense, personGenderNumberEnums).stream().map(conj -> conj.getConjugationVerbForm()).collect(Collectors.toList());
                conjugationVerbFormIrregulars = conjugationVerbFormIrregularService.findByConjugationVariation(conjugationVariationOptional.get());
                for (PersonGenderNumber.PersonGenderNumberEnum personGenderNumberEnum : personGenderNumberEnums) {
                    if (getConjugationVerbFormOtherwiseNull(conjugationNonExistVerbForms, tense, personGenderNumberEnum) != null) {
                        continue;
                    }
                    ConjugationRegularIrregularDTO conjugationRegularIrregular = new ConjugationRegularIrregularDTO();
                    ConjugationVerbFormIrregular conjugationVerbFormIrregular = getConjugationVerbFormIrregularOtherwiseNull(conjugationVerbFormIrregulars, tense, personGenderNumberEnum);
                    if (conjugationVerbFormIrregular != null) {
                        conjugationRegularIrregular.setConjugationIrregular(conjugationVerbFormIrregular.getName());
                    }

                    conjugationRegularIrregular.setConjugationRegular(verbEndingRemoved + getEndingByTenseEnumAndPersonGenderNumberEnum(conjugationRegularTenseEndings, tense.getTenseEnum(), personGenderNumberEnum));

                    HashMap<PersonGenderNumber.PersonGenderNumberEnum, ConjugationRegularIrregularDTO> conjugationComplete = new HashMap<>();
                    conjugationComplete.put(personGenderNumberEnum, conjugationRegularIrregular);


                    conjugationCompleteDTOS.add(new ConjugationCompleteDTO(tenseMapper.toDTO(tense), conjugationComplete));
                }
            }
        }
        return conjugationCompleteDTOS;
    }

    private ConjugationVerb getConjugationVerb(WordSense wordSense) {
        if (!wordSense.getWord().getType().getType().equals(Type.TypeEnum.VERB)) {
            throw new WordTypeNotVerbException(String.format("The word %s is not a verb", wordSense.getWord().getName()));
        }
        Optional<ConjugationVerb> conjugationVerbOptional = conjugationVerbRepository.findByWordId(wordSense.getWord().getId());
        if (conjugationVerbOptional.isEmpty()) {
            throw new ConjugationVerbNotFoundException(String.format("Not found conjugation for verb %s", wordSense.getWord().getName()));
        }
        return conjugationVerbOptional.get();
    }

    private ConjugationVerb getConjugationVerb(Word word) {
        if (!word.getType().getType().equals(Type.TypeEnum.VERB)) {
            throw new WordTypeNotVerbException(String.format("The word %s is not a verb", word.getName()));
        }
        Optional<ConjugationVerb> conjugationVerbOptional = conjugationVerbRepository.findByWordId(word.getId());
        if (conjugationVerbOptional.isEmpty()) {
            throw new ConjugationVerbNotFoundException(String.format("Not found conjugation for verb %s", word.getName()));
        }
        return conjugationVerbOptional.get();
    }

    private String removeEndingFromVerb(WordSense verb, String ending) {
        String verbName = verb.getWord().getName();
        int lastIndex = verbName.lastIndexOf(ending);
        if (lastIndex == -1) {
            return verbName;
        }
        return verbName.substring(0, lastIndex) + verbName.substring(lastIndex + ending.length());
    }


    private ConjugationVerbForm getConjugationVerbFormOtherwiseNull(List<ConjugationVerbForm> conjugationVerbForms, Tense tense, PersonGenderNumber.PersonGenderNumberEnum personGenderNumberEnum) {
        return conjugationVerbForms.stream()
                .filter(form -> form.getTense().getTenseEnum().equals(tense.getTenseEnum()) &&
                        form.getPersonGenderNumber().getPersonGenderNumberEnum().equals(personGenderNumberEnum))
                .findFirst()
                .orElse(null);
    }


    private ConjugationVerbFormIrregular getConjugationVerbFormIrregularOtherwiseNull(
            List<ConjugationVerbFormIrregular> conjugationVerbFormIrregulars,
            Tense tense,
            PersonGenderNumber.PersonGenderNumberEnum personGenderNumberEnum) {

        return conjugationVerbFormIrregulars.stream()
                .filter(irregular ->
                        irregular.getConjugationVerbForm().getTense().getTenseEnum().equals(tense.getTenseEnum()) &&
                                irregular.getConjugationVerbForm().getPersonGenderNumber().getPersonGenderNumberEnum().equals(personGenderNumberEnum))
                .findFirst()
                .orElse(null);
    }

    private ConjugationRegularTenseEnding getConjugationRegularTenseEndingByTenseEnumAndPersonGenderNumberEnum(List<ConjugationRegularTenseEnding> conjugationRegularTenseEndings, Tense.TenseEnum tenseEnum, PersonGenderNumber.PersonGenderNumberEnum personGenderNumberEnum) {
        return conjugationRegularTenseEndings.stream()
                .filter(ending -> ending.getTense().getTenseEnum().equals(tenseEnum) &&
                        ending.getPersonGenderNumber().getPersonGenderNumberEnum().equals(personGenderNumberEnum))
                .findFirst()
                .orElse(null);
    }

    private String getEndingByTenseEnumAndPersonGenderNumberEnum(List<ConjugationRegularTenseEnding> conjugationRegularTenseEndings, Tense.TenseEnum tenseEnum, PersonGenderNumber.PersonGenderNumberEnum personGenderNumberEnum) {
        ConjugationRegularTenseEnding conjugationRegularTenseEnding = getConjugationRegularTenseEndingByTenseEnumAndPersonGenderNumberEnum(conjugationRegularTenseEndings, tenseEnum, personGenderNumberEnum);
        if (conjugationRegularTenseEnding == null) {
            return "";
        }
        return conjugationRegularTenseEnding.getEnding();
    }

}
