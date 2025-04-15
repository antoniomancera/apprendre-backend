package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class ConjugationVerbForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "person_genre_number_id")
    private PersonGenderNumber personGenderNumber;

    @ManyToOne
    @JoinColumn(name = "tense_id")
    private Tense tense;

}
