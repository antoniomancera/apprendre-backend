package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class VerbGroup {
    public enum VerbGroupEnum {
        FIRST_GROUP_FR, SECOND_GROUP_FR, THIRD_GROUP_FR
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @Enumerated(EnumType.STRING)
    private VerbGroupEnum verbGroupEnum;

    private String name;
}
