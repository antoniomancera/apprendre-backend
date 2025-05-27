package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class VerbGroupEnding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "verb_group_id")
    private VerbGroup verbGroup;
    @ManyToOne
    @JoinColumn(name = "verb_ending_id")
    private VerbEnding verbEnding;
}
