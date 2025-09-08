package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserHistorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "deck_word_phrase_translation_id")
    private DeckWordPhraseTranslation deckWordPhraseTranslation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @ManyToOne
    @JoinColumn(name = "deck_id")
    private Deck deck;

    @ManyToOne
    @JoinColumn(name = "success_id")
    private Success success;

    private Long date;

    public UserHistorial() {
        this.date = System.currentTimeMillis();
    }

    public UserHistorial(DeckWordPhraseTranslation deckWordPhraseTranslation, UserInfo userInfo, Success success, Deck deck) {
        this.deckWordPhraseTranslation = deckWordPhraseTranslation;
        this.userInfo = userInfo;
        this.date = System.currentTimeMillis();
        this.success = success;
        this.deck = deck;
    }

    public UserHistorial(DeckWordPhraseTranslation deckWordPhraseTranslation, Long date, Success success, Deck deck) {
        this.deckWordPhraseTranslation = deckWordPhraseTranslation;
        this.date = date;
        this.success = success;
        this.deck = deck;
    }
}
