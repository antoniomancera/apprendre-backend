package com.antonio.apprendrebackend.repository;

import com.antonio.apprendrebackend.model.Phrase;
import org.springframework.data.repository.CrudRepository;

public interface PhraseRepository extends CrudRepository<Phrase, Integer> {
}
