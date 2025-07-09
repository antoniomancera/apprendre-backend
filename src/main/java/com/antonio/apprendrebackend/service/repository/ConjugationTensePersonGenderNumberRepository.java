package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.ConjugationTensePersonGenderNumber;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConjugationTensePersonGenderNumberRepository extends CrudRepository<ConjugationTensePersonGenderNumber, Integer> {
    List<ConjugationTensePersonGenderNumber> findByTenseId(Integer tenseId);
}
