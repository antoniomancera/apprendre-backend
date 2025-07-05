package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.PersonGenderNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ConjugationTenseDTO {
    private TenseDTO tense;
    private List<ConjugationWordPositionPersonGenderNumberDTO> conjugationWordPositionPersonGenderNumbers;
}
