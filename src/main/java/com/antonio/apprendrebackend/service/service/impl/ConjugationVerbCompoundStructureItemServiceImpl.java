package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.ConjugationVerbCompoundStructure;
import com.antonio.apprendrebackend.service.model.ConjugationVerbCompoundStructureItem;
import com.antonio.apprendrebackend.service.repository.ConjugationVerbCompoundStructureItemRepository;
import com.antonio.apprendrebackend.service.service.ConjugationVerbCompoundStructureItemService;
import com.antonio.apprendrebackend.service.service.ConjugationVerbCompoundStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConjugationVerbCompoundStructureItemServiceImpl implements ConjugationVerbCompoundStructureItemService {
    @Autowired
    private ConjugationVerbCompoundStructureItemRepository conjugationVerbCompoundStructureItemRepository;
    @Autowired
    private ConjugationVerbCompoundStructureService conjugationVerbCompoundStructureService;

    /**
     * Returns all the ConjugationVerbCompoundStructureItem related to a tenseId, that will let to know if  a verb is nonCompound. On
     * the contrary with the ConjugationVerbCompoundStructureItem is possible to know every part of the verb
     *
     * @param tenseId
     * @return List<ConjugationVerbCompoundStructureItem>
     */
    @Override
    public List<ConjugationVerbCompoundStructureItem> getConjugationVerbCompoundStructureItemsByTenseId(Integer tenseId) {
        ConjugationVerbCompoundStructure conjugationVerbCompoundStructure = conjugationVerbCompoundStructureService.getConjugationVerbCompoundStructureByTenseId(tenseId);
        if (conjugationVerbCompoundStructure != null) {
            return conjugationVerbCompoundStructureItemRepository.findByConjugationVerbCompoundStructureId(conjugationVerbCompoundStructure.getId());
        }
        return null;
    }
}
