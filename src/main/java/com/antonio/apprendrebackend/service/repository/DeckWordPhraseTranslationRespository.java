package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeckWordPhraseTranslationRespository extends CrudRepository<DeckWordPhraseTranslation, Integer> {

    /**
     * Return a Random WordTranslation
     *
     * @return
     */
    @Query(value = "SELECT dwt.* FROM deck_word_phrase_translation dwt inner join deck ud on dwt.deck_id = ud.id WHERE ud.user_id=:userId ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<DeckWordPhraseTranslation> findRandomUserDeckWordPhraseTranslationWithByUser(Integer userId);


    /**
     * Return a WordTranslation of a deck
     *
     * @param deckId
     * @return
     */
    @Query(value = """
            SELECT dwpt.* FROM (
                SELECT *,
                       SUM(current_weight) OVER (ORDER BY id) as running_total,
                       (SELECT SUM(current_weight) FROM deck_word_phrase_translation WHERE deck_id = :deckId) as total_weight
                FROM deck_word_phrase_translation
                WHERE deck_id = :deckId AND current_weight > 0
                ORDER BY id
            ) dwpt
            WHERE dwpt.running_total >= (FLOOR(RANDOM() * dwpt.total_weight) + 1)
            LIMIT 1
            """, nativeQuery = true)
    Optional<DeckWordPhraseTranslation> findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(Integer deckId);

    Optional<DeckWordPhraseTranslation> findByDeckIdAndWordPhraseTranslationId(Integer deckId, Integer wordPhraseTranslationId);

    /**
     * Return all WordTranslation of a deck
     *
     * @param deckId
     * @return List<WordTranslation>
     */
    @Query("SELECT wpt.wordTranslation FROM DeckWordPhraseTranslation duwpt JOIN duwpt.wordPhraseTranslation wpt WHERE duwpt.deck.id = :deckId")
    List<WordTranslation> findWordTranslationsByDeckId(@Param("deckId") Integer deckId);

    /**
     * Return all PhraseTranslation of a deck
     *
     * @param deckId
     * @return List<PhraseTranslation>
     */
    @Query("SELECT wpt.phraseTranslation FROM DeckWordPhraseTranslation duwpt JOIN duwpt.wordPhraseTranslation wpt WHERE duwpt.deck.id = :deckId")
    List<PhraseTranslation> findPhraseTranslationsByDeckId(@Param("deckId") Integer deckId);
}

