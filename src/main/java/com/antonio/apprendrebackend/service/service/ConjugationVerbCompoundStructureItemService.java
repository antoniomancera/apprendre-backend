package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.ConjugationVerbCompoundStructureItem;

import java.util.List;

public interface ConjugationVerbCompoundStructureItemService {
    /**
     * Returns all the ConjugationVerbCompoundStructureItem related to a tenseId, that will let to know if  a verb is nonCompound. On
     * the contrary with the ConjugationVerbCompoundStructureItem is possible to know every part of the verb
     *
     * @param tenseId
     * @return List<ConjugationVerbCompoundStructureItem>
     */
    List<ConjugationVerbCompoundStructureItem> getConjugationVerbCompoundStructureItemsByTenseId(Integer tenseId);
}
