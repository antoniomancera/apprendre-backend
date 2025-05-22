package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SubType {
    public enum SubTypeEnum {
        PERSONAL_PRONOUN,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;


    @Enumerated(EnumType.STRING)
    private SubTypeEnum name;
}
