package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Random;

@Entity
public class WordTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "word_fr_id")
    private WordFr wordFr;

    @ManyToOne
    @JoinColumn(name = "word_es_id")
    private WordSp wordSp;

    @OneToMany(mappedBy = "wordTranslation")
    private List<WordTranslationPhrase> phrases;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    private Integer attempts;
    private Integer successes;
    private Integer importanceIndex;


    public WordTranslation() {
        this.attempts = 0;
        this.successes = 0;
    }

    public WordTranslation(WordFr wordFr, WordSp wordSp) {
        this.wordFr = wordFr;
        this.wordSp = wordSp;
        this.attempts = 0;
        this.successes = 0;
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

    public Integer getImportanceIndex() {
        return importanceIndex;
    }

    public void setImportanceIndex(Integer importanceIndex) {
        this.importanceIndex = importanceIndex;
    }

    public List<WordTranslationPhrase> getPhrases() {
        return phrases;
    }

    public void setPhrases(List<WordTranslationPhrase> phrases) {
        this.phrases = phrases;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public WordTranslationPhrase getRandomPhrase() {
        if (phrases == null || phrases.isEmpty()) {
            return null;
        }
        Random rand = new Random();
        return phrases.get(rand.nextInt(phrases.size()));
    }
}
