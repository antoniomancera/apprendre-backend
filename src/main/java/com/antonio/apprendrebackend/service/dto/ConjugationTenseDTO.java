package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.PersonGenderNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ConjugationTenseDTO {
    private TenseDTO tense;
    private Map<PersonGenderNumber.PersonGenderNumberEnum, List<ConjugationWordPositionDTO>> personGenderNumberConjugation;
}
