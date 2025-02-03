package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.DeckWordTranslation;
import com.antonio.apprendrebackend.service.model.Phrase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PhraseRepository extends CrudRepository<Phrase, Integer> {
    @Query("SELECT p FROM Phrase p JOIN WordTranslationPhrase wtp ON p.id = wtp.phrase.id WHERE wtp.wordTranslation.id = :wordTranslationId")
    List<Phrase> findPhrasesByWordTranslationId(@Param("wordTranslationId") Integer wordTranslationId);

    @Query("""
                SELECT p
                FROM Phrase p
                JOIN WordTranslationPhrase wtp ON p.id = wtp.phrase.id
                JOIN DeckWordTranslation dwt ON wtp.wordTranslation.id = dwt.wordTranslation.id
                WHERE dwt.deck.id = :deckId
            """)
    Optional<List<Phrase>> findPhrasesByDeckId(@Param("deckId") Integer deckId);

    /**
     * Get all phrases associated to a WordTranslation of a Deck
     *
     * @param deckId
     * @param wordTranslationId
     * @return List<Phrase>
     */
    @Query("""
                SELECT p
                FROM Phrase p
                JOIN WordTranslationPhrase wtp ON p.id = wtp.phrase.id
                JOIN DeckWordTranslation dwt ON wtp.wordTranslation.id = dwt.wordTranslation.id
                WHERE dwt.deck.id = :deckId
                  AND wtp.wordTranslation.id = :wordTranslationId
            """)
    List<Phrase> findPhrasesByDeckIdAndWordTranslationId(@Param("deckId") Integer deckId, @Param("wordTranslationId") Integer wordTranslationId);

    /**
     * Get a page with phrases
     *
     * @param pageable
     * @return
     */
    Page<Phrase> findAll(Pageable pageable);
}
