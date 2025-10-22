package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.ConjugationVerbCompoundStructureItem;
import com.antonio.apprendrebackend.service.model.PersonGenderNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ConjugationTenseInfoDTO {
    private TenseDTO tense;
    private String regularTenseBase;
    private List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums;
    private List<ConjugationVerbCompoundStructureItem> conjugationVerbCompoundStructureItems;
    private List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnumConjugationNonExists;
    private Map<PersonGenderNumber.PersonGenderNumberEnum, ConjugationPositionIrregularDTO> personGenderNumberConjugationPositionIrregular;

    public ConjugationTenseInfoDTO(TenseDTO tense) {
        this.tense = tense;
    }
}
