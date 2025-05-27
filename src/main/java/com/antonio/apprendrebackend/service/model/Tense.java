package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Tense {
    public enum TenseEnum {
        INF_FR, PART_PRE_FR, PART_PAST_FR, GER_FR, PRE_INF_FR,
        PAS_COM_INF_FR, IMP_INF_FR, FUT_SIMP_INF_FR, PLUS_PARF_INF_FR,
        PRES_SUB_FR, PRE_CON_FR, PRE_IMP_FR
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "mood_id")
    private Mood mood;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @Enumerated(EnumType.STRING)
    private TenseEnum tenseEnum;

    private String name;

    private Boolean isCompound;

    private Boolean isFinite;
}