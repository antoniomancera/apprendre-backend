package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DeckUserWordPhraseTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "deck_user_id", nullable = false)
    private DeckUser deckUser;

    @ManyToOne
    @JoinColumn(name = "word_phrase_translation_id", nullable = false)
    private WordPhraseTranslation wordPhraseTranslation;

    private Integer baseWeight;
    private Integer currentWeight;
}
