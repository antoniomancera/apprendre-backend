package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.PersonGenderNumber;
import com.antonio.apprendrebackend.service.model.Tense;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ConjugationCompleteDTO {
    private Tense tense;
    private HashMap<PersonGenderNumber.PersonGenderNumberEnum, ConjugationRegularIrregularDTO> conjugationRegularity;
}
