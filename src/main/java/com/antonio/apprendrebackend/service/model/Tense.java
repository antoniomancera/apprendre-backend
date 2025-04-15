package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Tense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "mode_id")
    private Mode mode;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    private String name;

    private Boolean isCompound;


}
