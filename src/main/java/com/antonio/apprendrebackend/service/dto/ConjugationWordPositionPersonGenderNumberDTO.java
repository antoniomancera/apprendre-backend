package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.PersonGenderNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ConjugationWordPositionPersonGenderNumberDTO {
    private PersonGenderNumber.PersonGenderNumberEnum personGenderNumberEnum;
    private ConjugationRegularIrregularDTO conjugationRegularIrregularDTO;
    private WordSenseDTO wordSenseDTO;
    private Integer position;

    public ConjugationWordPositionPersonGenderNumberDTO(PersonGenderNumber.PersonGenderNumberEnum personGenderNumberEnum, ConjugationRegularIrregularDTO conjugationRegularIrregularDTO) {
        this.personGenderNumberEnum = personGenderNumberEnum;
        this.conjugationRegularIrregularDTO = conjugationRegularIrregularDTO;
        this.position = -1;
    }

    public ConjugationWordPositionPersonGenderNumberDTO(PersonGenderNumber.PersonGenderNumberEnum personGenderNumberEnum, ConjugationRegularIrregularDTO conjugationRegularIrregularDTO, Integer position) {
        this.personGenderNumberEnum = personGenderNumberEnum;
        this.conjugationRegularIrregularDTO = conjugationRegularIrregularDTO;
        this.position = position;
    }

    public ConjugationWordPositionPersonGenderNumberDTO(PersonGenderNumber.PersonGenderNumberEnum personGenderNumberEnum, WordSenseDTO wordSenseDTO, Integer position) {
        this.personGenderNumberEnum = personGenderNumberEnum;
        this.wordSenseDTO = wordSenseDTO;
        this.position = position;
    }
}
