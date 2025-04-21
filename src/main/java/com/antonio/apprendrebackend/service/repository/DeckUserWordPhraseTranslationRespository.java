package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeckUserWordPhraseTranslationRespository extends CrudRepository<DeckUserWordPhraseTranslation, Integer> {

    /**
     * Return a Random WordTranslation
     *
     * @return
     */
    @Query(value = "SELECT dwt.* FROM deck_user_word_phrase_translation dwt inner join deck_user ud on dwt.deck_user_id = ud.id WHERE ud.user_id=:userId ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<DeckUserWordPhraseTranslation> findRandomUserDeckWordPhraseTranslationWithByUser(Integer userId);


    /**
     * Return a WordTranslation of a deck
     *
     * @param deckId
     * @return
     */
    @Query(value = "SELECT dwt.* FROM deck_user_word_phrase_translation dwt inner join deck_user ud on dwt.deck_user_id = ud.id WHERE dwt.deck_user_id = :deckId and ud.user_id=:userId ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<DeckUserWordPhraseTranslation> findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(Integer deckId, Integer userId);

    Optional<DeckUserWordPhraseTranslation> findByDeckUserIdAndWordPhraseTranslationId(Integer deckUserId, Integer wordPhraseTranslationId);

    /**
     * Return all WordTranslation of a deckUser
     *
     * @param deckId
     * @return List<WordTranslation>
     */
    @Query("SELECT wpt.wordTranslation FROM DeckUserWordPhraseTranslation duwpt JOIN duwpt.wordPhraseTranslation wpt WHERE duwpt.deckUser.id = :deckId")
    List<WordTranslation> findWordTranslationsByDeckId(@Param("deckId") Integer deckId);

    /**
     * Return all PhraseTranslation of a deckUser
     *
     * @param deckId
     * @return List<PhraseTranslation>
     */
    @Query("SELECT wpt.phraseTranslation FROM DeckUserWordPhraseTranslation duwpt JOIN duwpt.wordPhraseTranslation wpt WHERE duwpt.deckUser.id = :deckId")
    List<PhraseTranslation> findPhraseTranslationsByDeckId(@Param("deckId") Integer deckId);
}

