package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.ConjugationVerb;
import com.antonio.apprendrebackend.service.model.Deck;
import com.antonio.apprendrebackend.service.model.UserInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ConjugationVerbRepository extends CrudRepository<ConjugationVerb, Integer> {
    Optional<ConjugationVerb> findByWordSenseId(int wordSenseId);
}
