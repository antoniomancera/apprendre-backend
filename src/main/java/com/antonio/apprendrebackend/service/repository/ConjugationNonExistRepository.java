package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.ConjugationNonExist;
import com.antonio.apprendrebackend.service.model.PersonGenderNumber;
import com.antonio.apprendrebackend.service.model.Tense;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConjugationNonExistRepository extends CrudRepository<ConjugationNonExist, Integer> {
    /**
     * Get a list of ConjugationNonxist given a tense and a list of personGenderNumber
     *
     * @param tense
     * @param personGenderNumberEnums
     * @return List<ConjugationNonExist>
     */
    List<ConjugationNonExist> findByConjugationVerbForm_TenseAndConjugationVerbForm_PersonGenderNumber_PersonGenderNumberEnumIn(
            Tense tense,
            List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums
    );
}
