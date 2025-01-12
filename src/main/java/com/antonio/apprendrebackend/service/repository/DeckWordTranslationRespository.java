package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DeckWordTranslationRespository extends CrudRepository<DeckWordTranslation, Integer> {
    /**
     * Return a Random WordTranslation
     *
     * @return
     */
    @Query(value = "SELECT dwt.* FROM deck_word_translation dwt JOIN word_translation wt ON dwt.word_translation_id = wt.id ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<DeckWordTranslation> findRandomDeckWordTranslation();

    /**
     * Return a WordTranslation of a deck
     *
     * @param deckId
     * @return
     */
    @Query(value = "SELECT dwt.* FROM deck_word_translation dwt WHERE dwt.deck_id = :deckId ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<DeckWordTranslation> findRandomDeckWordTranslationWithByDeck(Integer deckId);
}

