package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.Phrase;
import org.springframework.data.repository.CrudRepository;

public interface PhraseRepository extends CrudRepository<Phrase, Integer> {
  
}
