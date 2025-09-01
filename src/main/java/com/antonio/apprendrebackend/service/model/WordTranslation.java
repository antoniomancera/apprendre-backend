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
    @JoinColumn(name = "word_sense_fr_id")
    private WordSense wordSenseFr;

    @ManyToOne
    @JoinColumn(name = "word_sense_sp_id")
    private WordSense wordSenseSp;

    @ManyToOne
    @JoinColumn(name = "word_inflection_id")
    private WordInflection wordInflection;

    private Integer importanceIndex;

    private Integer baseWeight;

    public WordTranslation(WordSense wordSenseFr, WordSense wordSenseSp) {
        this.wordSenseFr = wordSenseFr;
        this.wordSenseSp = wordSenseSp;
    }
}
