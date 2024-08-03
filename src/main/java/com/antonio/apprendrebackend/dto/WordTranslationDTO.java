package com.antonio.apprendrebackend.dto;

import com.antonio.apprendrebackend.model.WordFr;
import com.antonio.apprendrebackend.model.WordSp;


public class WordTranslationDTO {
    private Integer id;
    private WordFr wordFr;
    private WordSp wordSp;
    private Integer attempts;
    private Integer successes;
    private PhraseDTO phrase;

    public WordTranslationDTO() {
    }

    public WordTranslationDTO(Integer id, WordFr wordFr, WordSp wordSp, Integer attempts, Integer successes, PhraseDTO phrase) {
        this.id = id;
        this.wordFr = wordFr;
        this.wordSp = wordSp;
        this.attempts = attempts;
        this.successes = successes;
        this.phrase = phrase;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public WordFr getWordFr() {
        return wordFr;
    }

    public void setWordFr(WordFr wordFr) {
        this.wordFr = wordFr;
    }

    public WordSp getWordSp() {
        return wordSp;
    }

    public void setWordSp(WordSp wordSp) {
        this.wordSp = wordSp;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public Integer getSuccesses() {
        return successes;
    }

    public void setSuccesses(Integer successes) {
        this.successes = successes;
    }

    public PhraseDTO getPhrase() {
        return phrase;
    }

    public void setPhrase(PhraseDTO phrase) {
        this.phrase = phrase;
    }
}
