package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.WordTranslation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WordTranslationRepository extends CrudRepository<WordTranslation, Integer> {
    List<WordTranslation> findAll();


    @Query("""
                SELECT wt
                FROM WordTranslation wt
                JOIN DeckWordTranslation dwt ON wt.id = dwt.wordTranslation.id
                JOIN WordTranslationPhrase wtp ON wt.id = wtp.wordTranslation.id
                WHERE dwt.deck.id = :deckId
                  AND wtp.phrase.id = :phraseId
            """)
    List<WordTranslation> findWordTranslationsByPhraseIdAndDeckId(Integer phraseId, Integer deckId);


}
