package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.PhraseTranslation;
import com.antonio.apprendrebackend.service.model.WordPhraseTranslation;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WordPhraseTranslationRepository extends CrudRepository<WordPhraseTranslation, Integer> {
    /**
     * Get all PhraseTranslation associated to a WordTranslation of a Deck
     *
     * @param deckId
     * @param wordTranslationId
     * @return List<PhraseTranslation>
     */
    @Query("""
                SELECT wpt.phraseTranslation
                FROM DeckWordPhraseTranslation dwt
                JOIN dwt.wordPhraseTranslation wpt 
                WHERE dwt.deck.id = :deckId
                  AND wpt.wordTranslation.id = :wordTranslationId
            """)
    List<PhraseTranslation> findPhrasesByDeckIdAndWordTranslationId(@Param("deckId") Integer deckId, @Param("wordTranslationId") Integer wordTranslationId);

    /**
     * Get all WordTranslation associated to a phraseTranslation of a Deck
     *
     * @param deckId
     * @param phraseTranslationId
     * @return List<WordTranslation>
     */
    @Query("""
                SELECT wpt.wordTranslation
                FROM DeckWordPhraseTranslation dwt
                JOIN dwt.wordPhraseTranslation wpt 
                WHERE dwt.deck.id = :deckId
                  AND wpt.phraseTranslation.id = :phraseTranslationId
            """)
    List<WordTranslation> findWordTranslationsByDeckIdPhraseTranslationId(@Param("deckId") Integer deckId, @Param("phraseTranslationId") Integer phraseTranslationId);
}
