package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.ConjugationVerbForm;
import com.antonio.apprendrebackend.service.model.PersonGenderNumber;
import com.antonio.apprendrebackend.service.model.Tense;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConjugationVerbFormRepository extends CrudRepository<ConjugationVerbForm, Integer> {
    List<ConjugationVerbForm> findByTenseAndPersonGenderNumberPersonGenderNumberEnumIn(Tense tense, List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums);
}
