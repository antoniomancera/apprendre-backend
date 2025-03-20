package com.antonio.apprendrebackend.service.dto;


import com.antonio.apprendrebackend.service.model.WordSenseFr;
import com.antonio.apprendrebackend.service.model.WordSenseSp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordTranslationDTO {
    private Integer id;
    private WordSenseFrDTO wordSenseFr;
    private WordSenseSpDTO wordSenseSp;
    private Integer attempts;
    private Integer successes;
    private PhraseDTO phrase;

    public WordTranslationDTO() {
    }

    public WordTranslationDTO(Integer id, WordSenseFrDTO wordSenseFr, WordSenseSpDTO wordSenseSp, Integer attempts, Integer successes, PhraseDTO phrase) {
        this.id = id;
        this.wordSenseFr = wordSenseFr;
        this.wordSenseSp = wordSenseSp;
        this.attempts = attempts;
        this.successes = successes;
        this.phrase = phrase;
    }
}
