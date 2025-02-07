package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Random;

@Entity
@Getter
@Setter
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

    public WordTranslationPhrase getRandomPhrase() {
        if (phrases == null || phrases.isEmpty()) {
            return null;
        }
        Random rand = new Random();
        return phrases.get(rand.nextInt(phrases.size()));
    }
}
