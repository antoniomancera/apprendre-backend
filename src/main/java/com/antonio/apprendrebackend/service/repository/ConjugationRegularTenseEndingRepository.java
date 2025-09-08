package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConjugationRegularTenseEndingRepository extends CrudRepository<ConjugationRegularTenseEnding, Integer> {
    /**
     * Given a tense a verbGroup and a list of PersonGenderNumber return the regular ending related to them
     *
     * @param tenseEnum
     * @param verbGroupEnum
     * @param personGenderNumberEnums
     * @return List<ConjugationRegularTenseEnding>
     */
    List<ConjugationRegularTenseEnding> findByTenseCodeAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
            Tense.TenseEnum tenseEnum,
            VerbGroup.VerbGroupEnum verbGroupEnum,
            List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums
    );

    /**
     * Returns the regular endings of a tense and a list of PersonGenderNumber
     *
     * @param tenseId
     * @param personGenderNumberEnums
     * @return List<ConjugationRegularTenseEnding>
     */
    @Query("""
                    SELECT e 
                    FROM ConjugationRegularTenseEnding e
                    WHERE e.tense.id = :tenseId
                    AND e.personGenderNumber.personGenderNumberEnum IN (:personGenderNumberEnums)
                    AND e.verbGroup.id IS NULL
            """)
    List<ConjugationRegularTenseEnding> getByTenseInPersonGenderNumberEnum(
            @Param("tenseId") Integer tenseId,
            @Param("personGenderNumberEnums") List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums
    );

    /**
     * Return the regular ending of a tense and a PersonGenderNumber
     *
     * @param tenseId
     * @param personGenderNumberEnum
     * @return ConjugationRegularTenseEnding
     */
    @Query("""
                    SELECT e 
                    FROM ConjugationRegularTenseEnding e
                    WHERE e.tense.id = :tenseId
                    AND e.personGenderNumber.personGenderNumberEnum = :personGenderNumberEnum
                    AND e.verbGroup.id IS NULL
            """)
    Optional<ConjugationRegularTenseEnding> getByTenseAndPersonGenderNumberEnum(
            @Param("tenseId") Integer tenseId,
            @Param("personGenderNumberEnum") PersonGenderNumber.PersonGenderNumberEnum personGenderNumberEnum
    );

    /**
     * Method to get the regular endings of a tense given a list of personGenderNumbers and a verbGroup
     *
     * @param tenseId
     * @param personGenderNumberEnums
     * @param verbGroupId
     * @return List<ConjugationRegularTenseEnding>
     */
    @Query("""
                    SELECT e 
                    FROM ConjugationRegularTenseEnding e
                    WHERE e.tense.id = :tenseId
                    AND e.personGenderNumber.personGenderNumberEnum IN (:personGenderNumberEnums)
                    AND e.verbGroup.id = :verbGroupId
            """)
    List<ConjugationRegularTenseEnding> getByTenseInPersonGenderNumberEnumAndVerbGroup(
            @Param("tenseId") Integer tenseId,
            @Param("personGenderNumberEnums") List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums,
            @Param("verbGroupId") Integer verbGroupId
    );

    /**
     * Method to get the regular endings of a tense given a personGenderNumbers and a verbGroup
     *
     * @param tenseId
     * @param personGenderNumberEnum
     * @param verbGroupId
     * @return ConjugationRegularTenseEnding
     */
    @Query("""
                    SELECT e 
                    FROM ConjugationRegularTenseEnding e
                    WHERE e.tense.id = :tenseId
                    AND e.personGenderNumber.personGenderNumberEnum = :personGenderNumberEnum
                    AND e.verbGroup.id = :verbGroupId
            """)
    Optional<ConjugationRegularTenseEnding> getByTenseAndPersonGenderNumberEnumAndVerbGroup(
            @Param("tenseId") Integer tenseId,
            @Param("personGenderNumberEnum") PersonGenderNumber.PersonGenderNumberEnum personGenderNumberEnum,
            @Param("verbGroupId") Integer verbGroupId
    );
}
