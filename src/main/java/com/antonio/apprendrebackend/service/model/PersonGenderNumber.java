package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class PersonGenderNumber {
    public enum PersonGenderNumberEnum {
        FIRST_SINGULAR_FEMININE,
        FIRST_SINGULAR_MASCULINE,
        FIRST_SINGULAR_NEUTRAL,
        SECOND_SINGULAR_FEMININE,
        SECOND_SINGULAR_MASCULINE,
        SECOND_SINGULAR_NEUTRAL,
        THIRD_SINGULAR_FEMININE,
        THIRD_SINGULAR_MASCULINE,
        THIRD_SINGULAR_NEUTRAL,
        FIRST_PLURAL_FEMININE,
        FIRST_PLURAL_MASCULINE,
        FIRST_PLURAL_NEUTRAL,
        SECOND_PLURAL_FEMININE,
        SECOND_PLURAL_MASCULINE,
        SECOND_PLURAL_NEUTRAL,
        THIRD_PLURAL_FEMININE,
        THIRD_PLURAL_MASCULINE,
        THIRD_PLURAL_NEUTRAL,
        NON_PERSONAL_SINGULAR_MASCULINE,
        NON_PERSONAL_SINGULAR_FEMININE,
        NON_PERSONAL_SINGULAR_NEUTRAL,
        NON_PERSONAL_PLURAL_MASCULINE,
        NON_PERSONAL_PLURAL_FEMININE,
        NON_PERSONAL_PLURAL_NEUTRAL,
        NON_PERSONAL_NON_NUMBER_NEUTRAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "number_id")
    private Number number;

    @Enumerated(EnumType.STRING)
    private PersonGenderNumber.PersonGenderNumberEnum personGenderNumberEnum;
}
