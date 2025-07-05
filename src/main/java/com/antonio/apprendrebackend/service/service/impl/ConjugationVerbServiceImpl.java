package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.ConjugationTenseDTO;
import com.antonio.apprendrebackend.service.dto.ConjugationRegularIrregularDTO;
import com.antonio.apprendrebackend.service.dto.ConjugationWordPositionPersonGenderNumberDTO;
import com.antonio.apprendrebackend.service.exception.ConjugationVerbNotFoundException;
import com.antonio.apprendrebackend.service.mapper.TenseMapper;
import com.antonio.apprendrebackend.service.mapper.WordSenseMapper;
import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConjugationVerbServiceImpl implements ConjugationVerbService {
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
    private ConjugationVerbWordWordSenseService conjugationVerbWordWordSenseService;

    @Autowired
    private TenseMapper tenseMapper;
    @Autowired
    private WordSenseMapper wordSenseMapper;


    /**
     * Return the conjugationComplete of a verb
     *
     * @param wordISenseId
     * @return
     */
    @Override
    public List<ConjugationTenseDTO> getConjugationComplete(int wordSenseId) {
        WordSense verb = wordSenseService.getById(wordSenseId);
        ConjugationVerb conjugationVerb = getConjugationVerbByWordSenseId(wordSenseId);
        ConjugationVariation conjugationVariation = conjugationVariationService.getConjugationVariationByConjugationVerb(conjugationVerb);
        List<Tense> tenses = tenseService.getByLanguage(verb.getWord().getLanguage());
        List<Tense> tenseNonCompounds = new ArrayList<>();
        List<HashMap<Tense, List<ConjugationVerbCompoundStructureItem>>> tenseCompounds = new ArrayList<>();
        tenses.forEach(tense -> {
            List<ConjugationVerbCompoundStructureItem> items = conjugationVerbCompoundStructureItemService.getConjugationVerbCompoundStructureItemsByTenseId(tense.getId());
            if (items != null && !items.isEmpty()) {
                HashMap<Tense, List<ConjugationVerbCompoundStructureItem>> map = new HashMap<>();
                map.put(tense, items);
                tenseCompounds.add(map);
            } else {
                tenseNonCompounds.add(tense);
            }
        });

        List<ConjugationTenseDTO> conjugationTenseDTOS = new ArrayList<>();
        for (Tense tense : tenseNonCompounds) {
            String conjugationBase = conjugationRegularTenseBaseVariationService.getRegularTenseBase(verb, conjugationVerb, tense.getId());
            List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = getPersonGenderNumberEnumsByTense(tense);
            List<ConjugationRegularTenseEnding> conjugationRegularTenseEndings = conjugationRegularTenseEndingService.getByTenseInPersonGenderNumberAndVerbGroup(tense.getId(), personGenderNumberEnums, conjugationVerb.getVerbGroupEnding().getVerbGroup().getId());
            List<ConjugationVerbForm> conjugationNonExistVerbForms = conjugationNonExistService.getByConjugationNonExistByTenseAndPersonGenderNumber(tense, personGenderNumberEnums).stream().map(conj -> conj.getConjugationVerbForm()).collect(Collectors.toList());
            List<ConjugationVerbFormIrregular> conjugationVerbFormIrregulars = conjugationVerbFormIrregularService.getConjugationVerbFormIrregularsByConjugationVariation(conjugationVariation);

            List<ConjugationWordPositionPersonGenderNumberDTO> conjugationWordPositionPersonGenderNumbers = new ArrayList<>();
            for (PersonGenderNumber.PersonGenderNumberEnum personGenderNumberEnum : personGenderNumberEnums) {
                if (conjugationNonExistVerbForms.stream().anyMatch(conjugationNonExist ->
                        conjugationNonExist.getPersonGenderNumber().getPersonGenderNumberEnum().equals(personGenderNumberEnum))) {
                    continue;
                }

                ConjugationVerbFormIrregular conjugationVerbFormIrregular =
                        getConjugationVerbFormIrregularOtherwiseNull(conjugationVerbFormIrregulars, tense, personGenderNumberEnum);

                String ending = getEndingForTenseAndPerson(conjugationRegularTenseEndings, tense.getTenseEnum(), personGenderNumberEnum);

                ConjugationRegularIrregularDTO conjugationRegularIrregular =
                        createConjugationRegularIrregular(conjugationBase, ending, conjugationVerbFormIrregular);

                conjugationWordPositionPersonGenderNumbers.add(
                        new ConjugationWordPositionPersonGenderNumberDTO(personGenderNumberEnum, conjugationRegularIrregular)
                );
            }
            conjugationTenseDTOS.add(new ConjugationTenseDTO(tenseMapper.toDTO(tense), conjugationWordPositionPersonGenderNumbers));
        }

        for (HashMap<Tense, List<ConjugationVerbCompoundStructureItem>> tenseConjugationMap : tenseCompounds) {
            Tense tense = tenseConjugationMap.keySet().iterator().next();
            List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = getPersonGenderNumberEnumsByTense(tense);
            List<ConjugationVerbForm> conjugationNonExistVerbForms = conjugationNonExistService.getByConjugationNonExistByTenseAndPersonGenderNumber(tense, personGenderNumberEnums).stream().map(conj -> conj.getConjugationVerbForm()).collect(Collectors.toList());

            List<ConjugationWordPositionPersonGenderNumberDTO> conjugationWordPositionPersonGenderNumbers = new ArrayList<>();
            for (PersonGenderNumber.PersonGenderNumberEnum personGenderNumberEnum : personGenderNumberEnums) {
                if (conjugationNonExistVerbForms.stream().anyMatch(conjugationNonExist -> conjugationNonExist.getPersonGenderNumber().getPersonGenderNumberEnum().equals(personGenderNumberEnum))) {
                    continue;
                }

                List<ConjugationVerbCompoundStructureItem> conjugationVerbCompoundStructureItems = conjugationVerbCompoundStructureItemService.getConjugationVerbCompoundStructureItemsByTenseId(tense.getId());
                List<ConjugationVerbCompoundStructureItem> sortedConjugationItems = conjugationVerbCompoundStructureItems.stream()
                        .sorted(Comparator.comparingInt(ConjugationVerbCompoundStructureItem::getPosition))
                        .toList();
                for (ConjugationVerbCompoundStructureItem item : sortedConjugationItems) {
                    if (item.getWordSense() != null) {
                        conjugationWordPositionPersonGenderNumbers.add(new ConjugationWordPositionPersonGenderNumberDTO(personGenderNumberEnum, wordSenseMapper.toDTO(item.getWordSense()), item.getPosition()));
                    }
                    if (item.getTense() != null) {
                        Integer verbGroupId = getVerbGroupId(conjugationVerb, item);

                        List<ConjugationRegularTenseEnding> conjugationRegularTenseEndings =
                                conjugationRegularTenseEndingService.getByTenseInPersonGenderNumberAndVerbGroup(
                                        item.getTense().getId(), personGenderNumberEnums, verbGroupId
                                );

                        String conjugationBase = conjugationRegularTenseBaseVariationService.getRegularTenseBase(
                                verb, conjugationVerb, item.getTense().getId()
                        );

                        List<ConjugationVerbFormIrregular> conjugationVerbFormIrregulars =
                                conjugationVerbFormIrregularService.getConjugationVerbFormIrregularsByConjugationVariation(conjugationVariation);

                        ConjugationVerbFormIrregular conjugationVerbFormIrregular =
                                getConjugationVerbFormIrregularOtherwiseNull(conjugationVerbFormIrregulars, item.getTense(), personGenderNumberEnum);

                        String ending = getEndingForTenseAndPerson(conjugationRegularTenseEndings, item.getTense().getTenseEnum(), personGenderNumberEnum);

                        ConjugationRegularIrregularDTO conjugationRegularIrregular =
                                createConjugationRegularIrregular(conjugationBase, ending, conjugationVerbFormIrregular);

                        conjugationWordPositionPersonGenderNumbers.add(
                                new ConjugationWordPositionPersonGenderNumberDTO(personGenderNumberEnum, conjugationRegularIrregular, item.getPosition())
                        );
                    }
                    if (item.getConjugationVerbForm() != null) {
                        Integer verbGroupId = getVerbGroupId(conjugationVerb, item);

                        ConjugationRegularTenseEnding conjugationRegularTenseEnding =
                                conjugationRegularTenseEndingService.getByTenseAndPersonGenderNumberAndVerbGroup(
                                        item.getConjugationVerbForm().getTense().getId(),
                                        item.getConjugationVerbForm().getPersonGenderNumber().getPersonGenderNumberEnum(),
                                        verbGroupId
                                );

                        String conjugationBase = conjugationRegularTenseBaseVariationService.getRegularTenseBase(
                                verb, conjugationVerb, item.getConjugationVerbForm().getTense().getId()
                        );

                        ConjugationVerbFormIrregular conjugationVerbFormIrregular =
                                conjugationVerbFormIrregularService.getConjugationVerbFormIrregularByConjugationVariationAndConjugationVerbForm(
                                        conjugationVariation, item.getConjugationVerbForm()
                                );

                        ConjugationRegularIrregularDTO conjugationRegularIrregular =
                                createConjugationRegularIrregular(conjugationBase, conjugationRegularTenseEnding.getEnding(), conjugationVerbFormIrregular);

                        conjugationWordPositionPersonGenderNumbers.add(
                                new ConjugationWordPositionPersonGenderNumberDTO(personGenderNumberEnum, conjugationRegularIrregular, item.getPosition())
                        );
                    }
                }
            }
            conjugationTenseDTOS.add(new ConjugationTenseDTO(tenseMapper.toDTO(tense), conjugationWordPositionPersonGenderNumbers));
        }

        return conjugationTenseDTOS;
    }


    @Override
    public ConjugationVerb getConjugationVerbByWordId(Integer wordId) {
        Optional<ConjugationVerbWordWordSense> conjugationVerbWordWordSenseOptional = conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordId(wordId);
        if (!conjugationVerbWordWordSenseOptional.isPresent()) {
            throw new ConjugationVerbNotFoundException(String.format("Not found conjugation for verb %s", wordId));
        }
        return conjugationVerbWordWordSenseOptional.get().getConjugationVerb();
    }

    @Override
    public ConjugationVerb getConjugationVerbByWordSenseId(Integer wordSenseId) {
        Optional<ConjugationVerbWordWordSense> conjugationVerbWordWordSenseOptional = conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordSenseId(wordSenseId);
        if (conjugationVerbWordWordSenseOptional.isPresent()) {
            return conjugationVerbWordWordSenseOptional.get().getConjugationVerb();
        }
        WordSense wordSense = wordSenseService.getById(wordSenseId);
        return getConjugationVerbByWordId(wordSense.getWord().getId());
    }

    private Integer getVerbGroupId(ConjugationVerb conjugationVerb, ConjugationVerbCompoundStructureItem item) {
        Integer verbGroupId = conjugationVerb.getVerbGroupEnding().getVerbGroup().getId();
        if (item.getAuxiliarPrincipalVerb().equals(ConjugationVerbCompoundStructureItem.AuxiliarPrincipalVerb.AUXILIAR)) {
            verbGroupId = conjugationVerb.getVerbAuxiliary().getId();
        }
        return verbGroupId;
    }

    private ConjugationRegularIrregularDTO createConjugationRegularIrregular(
            String conjugationBase,
            String ending,
            ConjugationVerbFormIrregular conjugationVerbFormIrregular) {

        ConjugationRegularIrregularDTO conjugationRegularIrregular = new ConjugationRegularIrregularDTO();

        if (conjugationVerbFormIrregular != null) {
            conjugationRegularIrregular.setConjugationIrregular(conjugationVerbFormIrregular.getName());
        }

        conjugationRegularIrregular.setConjugationRegular(conjugationBase + ending);
        return conjugationRegularIrregular;
    }

    private String getEndingForTenseAndPerson(
            List<ConjugationRegularTenseEnding> conjugationRegularTenseEndings,
            Tense.TenseEnum tenseEnum,
            PersonGenderNumber.PersonGenderNumberEnum personGenderNumberEnum) {

        return getEndingByTenseEnumAndPersonGenderNumberEnum(
                conjugationRegularTenseEndings,
                tenseEnum,
                personGenderNumberEnum
        );
    }


    private List<PersonGenderNumber.PersonGenderNumberEnum> getPersonGenderNumberEnumsByTense(Tense tense) {
        List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums;
        personGenderNumberEnums =
                conjugationTensePersonGenderNumberService.getConjugationTensePersonGenderNumbersByTenseId(tense.getId())
                        .stream()
                        .map(ctpgn -> ctpgn.getPersonGenderNumber().getPersonGenderNumberEnum())
                        .collect(Collectors.toList());
        return personGenderNumberEnums;
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
