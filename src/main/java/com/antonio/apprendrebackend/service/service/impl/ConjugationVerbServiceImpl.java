package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.ConjugationTenseDTO;
import com.antonio.apprendrebackend.service.dto.ConjugationRegularIrregularDTO;
import com.antonio.apprendrebackend.service.dto.ConjugationWordPositionPersonGenderNumberDTO;
import com.antonio.apprendrebackend.service.exception.ConjugationVerbNotFoundException;
import com.antonio.apprendrebackend.service.exception.WordTypeNotVerbException;
import com.antonio.apprendrebackend.service.mapper.TenseMapper;
import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.repository.ConjugationVerbRepository;
import com.antonio.apprendrebackend.service.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConjugationVerbServiceImpl implements ConjugationVerbService {
    @Autowired
    private ConjugationVerbRepository conjugationVerbRepository;

    @Autowired
    private WordSenseService wordSenseService;

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
    private ConjugationTensePersonGenderNumberService conjugationTensePersonGenderNumberService;
    @Autowired
    private ConjugationRegularTenseBaseVariationService conjugationRegularTenseBaseVariationService;
    @Autowired
    private ConjugationVerbCompoundStructureItemService conjugationVerbCompoundStructureItemService;


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
    public List<ConjugationTenseDTO> getConjugationComplete(int wordISenseId) {
        WordSense verb = wordSenseService.getById(wordISenseId);
        List<ConjugationTenseDTO> conjugationTenseDTOS = new ArrayList<>();

        ConjugationVerb conjugationVerb = getConjugationVerb(verb);
        Optional<ConjugationVariation> conjugationVariationOptional = conjugationVariationService.getByConjugationVerb(conjugationVerb);
        List<Tense> tenses = tenseService.getByLanguage(verb.getWord().getLanguage());
        List<Tense> tenseNonCompounds = tenses.stream().filter((tense) -> !tense.getIsCompound() && !tense.getIsInfinitive()).collect(Collectors.toList());
        List<Tense> tenseCompounds = tenses.stream().filter((tense) -> tense.getIsCompound()).collect(Collectors.toList());

        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = new ArrayList<>();
        List<ConjugationVerbForm> conjugationNonExistVerbForms = new ArrayList<>();
        List<ConjugationVerbFormIrregular> conjugationVerbFormIrregulars = new ArrayList<>();
        for (Tense tense : tenseNonCompounds) {
            String conjugationBase = conjugationRegularTenseBaseVariationService.getRegularTenseBase(verb, conjugationVerb, tense.getId());
            personGenderNumberEnums =
                    conjugationTensePersonGenderNumberService.getByTenseId(tense.getId())
                            .stream()
                            .map(ctpgn -> ctpgn.getPersonGenderNumber().getPersonGenderNumberEnum())
                            .collect(Collectors.toList());


            List<ConjugationRegularTenseEnding> conjugationRegularTenseEndings = conjugationRegularTenseEndingService.getByTenseInPersonGenderNumberAndVerbGroup(tense.getId(), personGenderNumberEnums, conjugationVerb.getVerbGroupEnding().getVerbGroup().getId());
            List<ConjugationWordPositionPersonGenderNumberDTO> conjugationWordPositionPersonGenderNumbers = new ArrayList<>();

            conjugationNonExistVerbForms = conjugationNonExistService.getByConjugationNonExistByTenseAndPersonGenderNumber(tense, personGenderNumberEnums).stream().map(conj -> conj.getConjugationVerbForm()).collect(Collectors.toList());
            conjugationVerbFormIrregulars = conjugationVerbFormIrregularService.findByConjugationVariation(conjugationVariationOptional.get());

            for (PersonGenderNumber.PersonGenderNumberEnum personGenderNumberEnum : personGenderNumberEnums) {
                if (conjugationNonExistVerbForms.stream().anyMatch(conjugationNonExist -> conjugationNonExist.getPersonGenderNumber().getPersonGenderNumberEnum().equals(personGenderNumberEnum))) {
                    continue;
                }

                ConjugationRegularIrregularDTO conjugationRegularIrregular = new ConjugationRegularIrregularDTO();
                ConjugationVerbFormIrregular conjugationVerbFormIrregular = getConjugationVerbFormIrregularOtherwiseNull(conjugationVerbFormIrregulars, tense, personGenderNumberEnum);
                if (conjugationVerbFormIrregular != null) {
                    conjugationRegularIrregular.setConjugationIrregular(conjugationVerbFormIrregular.getName());
                }

                conjugationRegularIrregular.setConjugationRegular(conjugationBase + getEndingByTenseEnumAndPersonGenderNumberEnum(conjugationRegularTenseEndings, tense.getTenseEnum(), personGenderNumberEnum));


                conjugationWordPositionPersonGenderNumbers.add(new ConjugationWordPositionPersonGenderNumberDTO(personGenderNumberEnum, conjugationRegularIrregular, null, 0));


            }
            conjugationTenseDTOS.add(new ConjugationTenseDTO(tenseMapper.toDTO(tense), conjugationWordPositionPersonGenderNumbers));
        }


        for (Tense tense : tenseCompounds) {
            personGenderNumberEnums =
                    conjugationTensePersonGenderNumberService.getByTenseId(tense.getId())
                            .stream()
                            .map(ctpgn -> ctpgn.getPersonGenderNumber().getPersonGenderNumberEnum())
                            .collect(Collectors.toList());
            for (PersonGenderNumber.PersonGenderNumberEnum personGenderNumberEnum : personGenderNumberEnums) {
                if (conjugationNonExistVerbForms.stream().anyMatch(conjugationNonExist -> conjugationNonExist.getPersonGenderNumber().getPersonGenderNumberEnum().equals(personGenderNumberEnum))) {
                    continue;
                }
                List<ConjugationVerbCompoundStructureItem> conjugationVerbCompoundStructureItems = conjugationVerbCompoundStructureItemService.getByTenseId(tense.getId());

            }
        }
        
        return conjugationTenseDTOS;
    }

    @Override
    public ConjugationVerb getConjugationVerb(WordSense wordSense) {
        if (!wordSense.getWord().getType().getType().equals(Type.TypeEnum.VERB)) {
            throw new WordTypeNotVerbException(String.format("The word %s is not a verb", wordSense.getWord().getName()));
        }
        Optional<ConjugationVerb> conjugationVerbOptional = conjugationVerbRepository.findByWordId(wordSense.getWord().getId());
        if (conjugationVerbOptional.isEmpty()) {
            throw new ConjugationVerbNotFoundException(String.format("Not found conjugation for verb %s", wordSense.getWord().getName()));
        }
        return conjugationVerbOptional.get();
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
        if (conjugationRegularTenseEndings == null) return null;
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
