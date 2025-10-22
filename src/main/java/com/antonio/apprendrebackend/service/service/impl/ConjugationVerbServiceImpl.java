package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.*;
import com.antonio.apprendrebackend.service.exception.ConjugationVerbNotFoundException;
import com.antonio.apprendrebackend.service.mapper.ConjugationVerbMapper;
import com.antonio.apprendrebackend.service.mapper.TenseMapper;
import com.antonio.apprendrebackend.service.mapper.WordSenseMapper;
import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.service.*;
import com.antonio.apprendrebackend.service.util.AuxiliaryPrincipalVerbEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConjugationVerbServiceImpl implements ConjugationVerbService {
    private static final Logger logger = LoggerFactory.getLogger(ConjugationVerbServiceImpl.class);

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
    private ConjugationVerbFormService conjugationVerbFormService;
    @Autowired
    private TenseMapper tenseMapper;
    @Autowired
    private WordSenseMapper wordSenseMapper;
    @Autowired
    private ConjugationVerbMapper conjugationVerbMapper;


    /**
     * Return the conjugationComplete of a verb
     *
     * @param wordSenseId
     * @return
     */
    @Override
    public List<ConjugationTenseDTO> getConjugationComplete(int wordSenseId) {
        logger.debug("Getting all the conjugation for wordSenseId: %s", wordSenseId);

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

            Map<PersonGenderNumber.PersonGenderNumberEnum, List<ConjugationWordPositionDTO>> personGenderNumberConjugation = new HashMap<>();
            for (PersonGenderNumber.PersonGenderNumberEnum personGenderNumberEnum : personGenderNumberEnums) {
                if (conjugationNonExistVerbForms.stream().anyMatch(conjugationNonExist ->
                        conjugationNonExist.getPersonGenderNumber().getPersonGenderNumberEnum().equals(personGenderNumberEnum))) {
                    continue;
                }
                List<ConjugationWordPositionDTO> conjugationWordPositions = new ArrayList<>();

                ConjugationVerbFormIrregular conjugationVerbFormIrregular =
                        getConjugationVerbFormIrregularOtherwiseNull(conjugationVerbFormIrregulars, tense, personGenderNumberEnum);

                String ending = getEndingForTenseAndPerson(conjugationRegularTenseEndings, tense.getCode(), personGenderNumberEnum);

                ConjugationRegularIrregularDTO conjugationRegularIrregular =
                        createConjugationRegularIrregular(conjugationBase, ending, conjugationVerbFormIrregular);

                conjugationWordPositions.add(
                        new ConjugationWordPositionDTO(conjugationRegularIrregular)
                );
                personGenderNumberConjugation.put(personGenderNumberEnum, conjugationWordPositions);
            }
            conjugationTenseDTOS.add(new ConjugationTenseDTO(tenseMapper.toDTO(tense), personGenderNumberConjugation));
        }

        for (HashMap<Tense, List<ConjugationVerbCompoundStructureItem>> tenseConjugationMap : tenseCompounds) {
            Tense tense = tenseConjugationMap.keySet().iterator().next();
            List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = getPersonGenderNumberEnumsByTense(tense);
            List<ConjugationVerbForm> conjugationNonExistVerbForms = conjugationNonExistService.getByConjugationNonExistByTenseAndPersonGenderNumber(tense, personGenderNumberEnums).stream().map(conj -> conj.getConjugationVerbForm()).collect(Collectors.toList());

            Map<PersonGenderNumber.PersonGenderNumberEnum, List<ConjugationWordPositionDTO>> personGenderNumberConjugation = new HashMap<>();
            for (PersonGenderNumber.PersonGenderNumberEnum personGenderNumberEnum : personGenderNumberEnums) {
                if (conjugationNonExistVerbForms.stream().anyMatch(conjugationNonExist -> conjugationNonExist.getPersonGenderNumber().getPersonGenderNumberEnum().equals(personGenderNumberEnum))) {
                    continue;
                }
                List<ConjugationWordPositionDTO> conjugationWordPositions = new ArrayList<>();

                List<ConjugationVerbCompoundStructureItem> conjugationVerbCompoundStructureItems = conjugationVerbCompoundStructureItemService.getConjugationVerbCompoundStructureItemsByTenseId(tense.getId());
                List<ConjugationVerbCompoundStructureItem> sortedConjugationItems = conjugationVerbCompoundStructureItems.stream()
                        .sorted(Comparator.comparingInt(ConjugationVerbCompoundStructureItem::getPosition))
                        .toList();
                for (ConjugationVerbCompoundStructureItem item : sortedConjugationItems) {
                    if (item.getWordSense() != null) {
                        conjugationWordPositions.add(new ConjugationWordPositionDTO(wordSenseMapper.toDTO(item.getWordSense()), item.getPosition()));
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

                        String ending = getEndingForTenseAndPerson(conjugationRegularTenseEndings, item.getTense().getCode(), personGenderNumberEnum);

                        ConjugationRegularIrregularDTO conjugationRegularIrregular =
                                createConjugationRegularIrregular(conjugationBase, ending, conjugationVerbFormIrregular);

                        conjugationWordPositions.add(
                                new ConjugationWordPositionDTO(conjugationRegularIrregular, item.getPosition())
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

                        conjugationWordPositions.add(
                                new ConjugationWordPositionDTO(conjugationRegularIrregular, item.getPosition())
                        );
                    }
                    personGenderNumberConjugation.put(personGenderNumberEnum, conjugationWordPositions);
                }
            }
            conjugationTenseDTOS.add(new ConjugationTenseDTO(tenseMapper.toDTO(tense), personGenderNumberConjugation));
        }

        return conjugationTenseDTOS;
    }


    @Override
    public ConjugationVerb getConjugationVerbByWordId(Integer wordId) {
        logger.debug("Getting the conjugationVerb for wordId: %s", wordId);

        Optional<ConjugationVerbWordWordSense> conjugationVerbWordWordSenseOptional = conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordId(wordId);
        if (!conjugationVerbWordWordSenseOptional.isPresent()) {
            throw new ConjugationVerbNotFoundException(String.format("Not found conjugation for verb %s", wordId));
        }
        return conjugationVerbWordWordSenseOptional.get().getConjugationVerb();
    }

    @Override
    public ConjugationVerb getConjugationVerbByWordSenseId(Integer wordSenseId) {
        logger.debug("Getting the conjugationVerb for wordSenseId: %s", wordSenseId);

        Optional<ConjugationVerbWordWordSense> conjugationVerbWordWordSenseOptional = conjugationVerbWordWordSenseService.getConjugationVerbWordWordSenseByWordSenseId(wordSenseId);
        if (conjugationVerbWordWordSenseOptional.isPresent()) {
            return conjugationVerbWordWordSenseOptional.get().getConjugationVerb();
        }
        WordSense wordSense = wordSenseService.getById(wordSenseId);
        return getConjugationVerbByWordId(wordSense.getWord().getId());
    }

    /**
     * Given a wordSense return the structure general of every tense Conjugation and their irregulars
     *
     * @param wordSenseId
     * @return ConjugationVerbWithTensesInfoDTO
     */
    @Override
    public ConjugationVerbWithTensesInfoDTO getConjugationVerbWithTensesInfoDTOByWordSenseId(Integer wordSenseId) {
        logger.debug("Called getConjugationVerbWithTensesInfoDTOByWordSenseId in ConjugationVerbService for wordSense-{}", wordSenseId);

        ConjugationVerbWithTensesInfoDTO conjugationVerbWithTensesInfo = new ConjugationVerbWithTensesInfoDTO();

        List<ConjugationTenseInfoDTO> conjugationStructureAndIrregularsList = new ArrayList<>();

        WordSense verb = wordSenseService.getById(wordSenseId);
        ConjugationVerb conjugationVerb = getConjugationVerbByWordSenseId(wordSenseId);
        ConjugationVariation conjugationVariation = conjugationVariationService.getConjugationVariationByConjugationVerb(conjugationVerb);
        conjugationVerbWithTensesInfo.setConjugationVerb(conjugationVerbMapper.toDTO(conjugationVerb));
        List<Tense> tenses = tenseService.getByLanguage(verb.getWord().getLanguage());

        tenses.forEach(tense -> {
            ConjugationTenseInfoDTO conjugationStructureAndIrregulars = new ConjugationTenseInfoDTO(tenseMapper.toDTO(tense));

            String regularTenseBase = conjugationRegularTenseBaseVariationService.getRegularTenseBase(verb, conjugationVerb, tense.getId());
            conjugationStructureAndIrregulars.setRegularTenseBase(regularTenseBase);
            List<ConjugationVerbCompoundStructureItem> items = conjugationVerbCompoundStructureItemService.getConjugationVerbCompoundStructureItemsByTenseId(tense.getId());

            List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums = getPersonGenderNumberEnumsByTense(tense);
            conjugationStructureAndIrregulars.setPersonGenderNumberEnums(personGenderNumberEnums);
            if (items != null && !items.isEmpty()) {
                conjugationStructureAndIrregulars.setConjugationVerbCompoundStructureItems(items);
            }

            if (conjugationVariation != null) {
                List<ConjugationVerbForm> conjugationNonExistVerbForms = conjugationNonExistService.getByConjugationNonExistByTenseAndPersonGenderNumber(tense, personGenderNumberEnums).stream().map(conj -> conj.getConjugationVerbForm()).collect(Collectors.toList());
                List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnumConjugationNonExists = conjugationNonExistVerbForms.stream().map(conj -> conj.getPersonGenderNumber().getPersonGenderNumberEnum()).collect(Collectors.toList());
                conjugationStructureAndIrregulars.setPersonGenderNumberEnumConjugationNonExists(personGenderNumberEnumConjugationNonExists);

                Map<PersonGenderNumber.PersonGenderNumberEnum, ConjugationPositionIrregularDTO> personGenderNumberConjugationIrregular = new HashMap<>();
                if (items != null && !items.isEmpty()) {
                    for (ConjugationVerbCompoundStructureItem item : items) {
                        if (item.getConjugationVerbForm() != null && !tenses.contains(item.getConjugationVerbForm().getTense()) && item.getAuxiliarPrincipalVerb() == AuxiliaryPrincipalVerbEnum.PRINCIPAL) {
                            ConjugationVerbFormIrregular conjugationVerbFormIrregular = conjugationVerbFormIrregularService.getConjugationVerbFormIrregularByConjugationVariationAndConjugationVerbForm(conjugationVariation, item.getConjugationVerbForm());
                            if (conjugationVerbFormIrregular != null) {
                                personGenderNumberConjugationIrregular.put(item.getConjugationVerbForm().getPersonGenderNumber().getPersonGenderNumberEnum(), new ConjugationPositionIrregularDTO(conjugationVerbFormIrregular.getName(), item.getPosition()));
                            }
                        } else if (item.getTense() != null && !tenses.contains(item.getTense()) && item.getAuxiliarPrincipalVerb() == AuxiliaryPrincipalVerbEnum.PRINCIPAL) {

                            List<ConjugationVerbForm> conjugationVerbForms = conjugationVerbFormService.getConjugationVerbFormsByTenseAndPersonGenderNumberEnums(item.getTense(), personGenderNumberEnums);

                            List<ConjugationVerbFormIrregular> conjugationVerbFormIrregulars = conjugationVerbFormIrregularService.getConjugationVerbFormIrregularsByConjugationVariationAndConjugationVerbForms(conjugationVariation, conjugationVerbForms);
                            conjugationVerbFormIrregulars.forEach(conj ->
                                    personGenderNumberConjugationIrregular.put(
                                            conj.getConjugationVerbForm().getPersonGenderNumber().getPersonGenderNumberEnum(),
                                            new ConjugationPositionIrregularDTO(conj.getName(), item.getPosition())
                                    )
                            );
                        }
                    }

                } else {
                    List<ConjugationVerbForm> conjugationVerbForms = conjugationVerbFormService.getConjugationVerbFormsByTenseAndPersonGenderNumberEnums(tense, personGenderNumberEnums);

                    List<ConjugationVerbFormIrregular> conjugationVerbFormIrregulars = conjugationVerbFormIrregularService.getConjugationVerbFormIrregularsByConjugationVariationAndConjugationVerbForms(conjugationVariation, conjugationVerbForms);
                    conjugationVerbFormIrregulars.forEach(conj ->
                            personGenderNumberConjugationIrregular.put(
                                    conj.getConjugationVerbForm().getPersonGenderNumber().getPersonGenderNumberEnum(),
                                    new ConjugationPositionIrregularDTO(conj.getName(), -1)
                            )
                    );

                }
                conjugationStructureAndIrregulars.setPersonGenderNumberConjugationPositionIrregular(personGenderNumberConjugationIrregular);
                conjugationStructureAndIrregularsList.add(conjugationStructureAndIrregulars);
            }
        });
        conjugationVerbWithTensesInfo.setConjugationStructureAndIrregularsList(conjugationStructureAndIrregularsList);

        return conjugationVerbWithTensesInfo;
    }

    private Integer getVerbGroupId(ConjugationVerb conjugationVerb, ConjugationVerbCompoundStructureItem item) {
        Integer verbGroupId = conjugationVerb.getVerbGroupEnding().getVerbGroup().getId();
        if (item.getAuxiliarPrincipalVerb().equals(AuxiliaryPrincipalVerbEnum.AUXILIAR)) {
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
                        irregular.getConjugationVerbForm().getTense().getCode().equals(tense.getCode()) &&
                                irregular.getConjugationVerbForm().getPersonGenderNumber().getPersonGenderNumberEnum().equals(personGenderNumberEnum))
                .findFirst()
                .orElse(null);
    }

    private ConjugationRegularTenseEnding getConjugationRegularTenseEndingByTenseEnumAndPersonGenderNumberEnum(List<ConjugationRegularTenseEnding> conjugationRegularTenseEndings, Tense.TenseEnum tenseEnum, PersonGenderNumber.PersonGenderNumberEnum personGenderNumberEnum) {
        if (conjugationRegularTenseEndings == null) return null;
        return conjugationRegularTenseEndings.stream()
                .filter(ending -> ending.getTense().getCode().equals(tenseEnum) &&
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
