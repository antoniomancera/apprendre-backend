package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//TODO refactor Wordtranslation to WordSenseTransation
@Entity
@Getter
@Setter
@NoArgsConstructor
public class WordTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "word_sense_a_id")
    private WordSense wordSenseA;

    @ManyToOne
    @JoinColumn(name = "word_sense_b_id")
    private WordSense wordSenseB;

    @ManyToOne
    @JoinColumn(name = "word_inflection_id")
    private WordInflection wordInflection;

    private Integer importanceIndex;

    private Integer baseWeight;

    public WordTranslation(WordSense wordSenseA, WordSense wordSenseB) {
        this.wordSenseA = wordSenseA;
        this.wordSenseB = wordSenseB;
    }
}
