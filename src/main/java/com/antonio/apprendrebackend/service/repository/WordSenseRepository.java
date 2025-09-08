package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.WordSense;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordSenseRepository extends CrudRepository<WordSense, Integer>, JpaSpecificationExecutor<WordSense> {
    /**
     * Returns all the wordSenses of the targetLanguage of word in a deck
     *
     * @param wordId
     * @param deckId
     * @return
     */
    @Query(value = """
            SELECT DISTINCT ws
                        FROM DeckWordPhraseTranslation dwpt
                        JOIN dwpt.wordPhraseTranslation wpt
                        JOIN wpt.wordTranslation wt
                        JOIN wt.wordSenseA ws
                        WHERE dwpt.deck.id = :deckId
                        AND ws.word.id = :wordId
                        UNION
                        SELECT DISTINCT ws
                        FROM DeckWordPhraseTranslation dwpt
                        JOIN dwpt.wordPhraseTranslation wpt
                        JOIN wpt.wordTranslation wt
                        JOIN wt.wordSenseB ws
                        WHERE dwpt.deck.id = :deckId
                        AND ws.word.id = :wordId
            """)
    List<WordSense> findByWordIdAndDeckId(@Param("wordId") Integer wordId, @Param("deckId") Integer deckId);

    /**
     * Returns all the wordSenses of the targetLanguage of a deck
     *
     * @param deckId
     * @return List<WordSense>
     */
    @Query(value = """
              SELECT DISTINCT wsf FROM DeckWordPhraseTranslation dwpt
                JOIN dwpt.deck deck
                JOIN deck.course course
                JOIN dwpt.wordPhraseTranslation wpt
                JOIN wpt.wordTranslation wt
                JOIN wt.wordSenseA wsf
                JOIN wsf.word word
                WHERE dwpt.deck.id = :deckId
                AND word.language = course.targetLanguage
                UNION
                SELECT DISTINCT wsf FROM DeckWordPhraseTranslation dwpt
                JOIN dwpt.deck deck
                JOIN deck.course course
                JOIN dwpt.wordPhraseTranslation wpt
                JOIN wpt.wordTranslation wt
                JOIN wt.wordSenseB wsf
                JOIN wsf.word word
                WHERE dwpt.deck.id = :deckId
                AND word.language = course.targetLanguage
            """)
    List<WordSense> findTargetLanguageWordSensesByDeckId(@Param("deckId") Integer deckId);

    List<WordSense> findByWordId(Integer wordId);
}
