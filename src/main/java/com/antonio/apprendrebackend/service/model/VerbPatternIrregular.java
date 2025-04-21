package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class VerbPatternIrregular {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    //poner wordSense
    private String wordSense;
}


