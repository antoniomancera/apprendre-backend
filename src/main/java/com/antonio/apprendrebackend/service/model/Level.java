package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Level {
    public enum LevelEnum {
        A1, A2, B1, B2, C1, C2
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private LevelEnum name;

    public Level(LevelEnum name) {
        this.name = name;
    }
}
