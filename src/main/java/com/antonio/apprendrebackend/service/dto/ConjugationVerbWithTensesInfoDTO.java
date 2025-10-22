package com.antonio.apprendrebackend.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ConjugationVerbWithTensesInfoDTO {
    private ConjugationVerbDTO conjugationVerb;
    private List<ConjugationTenseInfoDTO> conjugationStructureAndIrregularsList;
}
