package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class WordCollection {
    public enum WordCollectionEnum {
        FRENCH_PERSONAL_PRONOUNS,
        CONJ_PRO_NON_IMP_FR,
        CONJ_PRO_IMP_FR
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @Enumerated(EnumType.STRING)
    private WordCollectionEnum wordCollectionEnum;

    private String description;
}