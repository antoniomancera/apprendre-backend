package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WordPhraseTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "word_translation_id")
    private WordTranslation wordTranslation;

    @ManyToOne
    @JoinColumn(name = "phrase_translation_id")
    private PhraseTranslation phraseTranslation;
}
