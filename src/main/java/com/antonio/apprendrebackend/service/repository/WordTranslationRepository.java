package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.Phrase;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WordTranslationRepository extends CrudRepository<WordTranslation, Integer> {
    List<WordTranslation> findAll();

    /**
     * Get all WordTranslation of a deck
     *
     * @param deckId
     * @return Optional<List<WordTranslation>>
     */
    @Query("""
                SELECT wt
                FROM WordTranslation wt
                JOIN DeckWordTranslation dwt ON wt.id = dwt.wordTranslation.id
                WHERE dwt.deck.id = :deckId
            """)
    Optional<List<WordTranslation>> findWordTranslationsByDeckId(@Param("deckId") Integer deckId);


}
