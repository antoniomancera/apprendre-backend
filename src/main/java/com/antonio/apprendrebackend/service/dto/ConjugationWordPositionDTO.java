package com.antonio.apprendrebackend.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ConjugationWordPositionDTO {
    private ConjugationRegularIrregularDTO conjugationRegularIrregular;
    private WordSenseDTO wordSenseDTO;
    private Integer position;

    public ConjugationWordPositionDTO(ConjugationRegularIrregularDTO conjugationRegularIrregular) {
        this.conjugationRegularIrregular = conjugationRegularIrregular;
        this.position = -1;
    }

    public ConjugationWordPositionDTO(ConjugationRegularIrregularDTO conjugationRegularIrregular, Integer position) {
        this.conjugationRegularIrregular = conjugationRegularIrregular;
        this.position = position;
    }

    public ConjugationWordPositionDTO(WordSenseDTO wordSenseDTO, Integer position) {
        this.wordSenseDTO = wordSenseDTO;
        this.position = position;
    }
}
