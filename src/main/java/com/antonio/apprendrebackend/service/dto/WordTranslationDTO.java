package com.antonio.apprendrebackend.service.dto;


import com.antonio.apprendrebackend.service.model.Language;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordTranslationDTO {
    private Integer id;
    private WordSenseDTO wordSenseFr;
    private WordSenseDTO wordSenseSp;
    private Integer attempts;
    private Integer successes;

    public WordTranslationDTO() {
    }

    public WordTranslationDTO(Integer id, WordSenseDTO wordSenseFr, WordSenseDTO wordSenseSp, Integer attempts, Integer successes) {
        this.id = id;
        this.wordSenseFr = wordSenseFr;
        this.wordSenseSp = wordSenseSp;
        this.attempts = attempts;
        this.successes = successes;
    }
}
