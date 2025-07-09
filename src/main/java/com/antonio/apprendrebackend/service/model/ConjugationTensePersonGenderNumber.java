package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ConjugationTensePersonGenderNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tense_id")
    private Tense tense;

    @ManyToOne
    @JoinColumn(name = "person_gender_number_id")
    private PersonGenderNumber personGenderNumber;
}
