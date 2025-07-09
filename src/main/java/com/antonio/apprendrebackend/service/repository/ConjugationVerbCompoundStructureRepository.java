package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.ConjugationVerbCompoundStructure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ConjugationVerbCompoundStructureRepository extends CrudRepository<ConjugationVerbCompoundStructure, Integer> {
    Optional<ConjugationVerbCompoundStructure> findByTenseId(Integer tenseId);
}
