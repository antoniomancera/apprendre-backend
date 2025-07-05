package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.ConjugationVerbCompoundStructure;
import com.antonio.apprendrebackend.service.model.ConjugationVerbCompoundStructureItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConjugationVerbCompoundStructureItemRepository extends CrudRepository<ConjugationVerbCompoundStructureItem, Integer> {
    List<ConjugationVerbCompoundStructureItem> findByConjugationVerbCompoundStructureId(Integer conjugationVerbCompoundStructureId);
}
