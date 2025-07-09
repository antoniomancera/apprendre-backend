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

    @Column(name = "deck_id")
    private Integer deckId;

    private Long date;

    private Integer success;

    public UserHistorial() {
        this.date = System.currentTimeMillis();
    }

    public UserHistorial(DeckWordPhraseTranslation deckWordPhraseTranslation, Integer success, Integer deckId) {
        this.deckWordPhraseTranslation = deckWordPhraseTranslation;
        this.date = System.currentTimeMillis();
        this.success = success;
        this.deckId = deckId;
    }

    public UserHistorial(DeckWordPhraseTranslation deckWordPhraseTranslation, Long date, Integer success, Integer deckId) {
        this.deckWordPhraseTranslation = deckWordPhraseTranslation;
        this.date = date;
        this.success = success;
        this.deckId = deckId;
    }
}
