package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.WordSense;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordSenseRepository extends CrudRepository<WordSense, Integer>, JpaSpecificationExecutor<WordSense> {
    @Query(value = """
            SELECT DISTINCT wsf
             FROM DeckWordPhraseTranslation dwpt 
             JOIN dwpt.wordPhraseTranslation wpt
             JOIN wpt.wordTranslation wt 
             JOIN wt.wordSenseFr wsf 
             where dwpt.deck.id = :deckId
             AND wsf.word.id = :wordId
            """)
    List<WordSense> findByWordIdAndDeckId(@Param("wordId") Integer wordId, @Param("deckId") Integer deckId);

    @Query(value = """
            SELECT DISTINCT wsf
             FROM DeckWordPhraseTranslation dwpt 
             JOIN dwpt.wordPhraseTranslation wpt
             JOIN wpt.wordTranslation wt 
             JOIN wt.wordSenseFr wsf 
             where dwpt.deck.id = :deckId
            """)
    List<WordSense> findByDeckId(@Param("deckId") Integer deckId);

    List<WordSense> findByWordId(Integer wordId);
}
