package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Level {
    public enum LevelEnum {
        A1, A2, B1, B2, C1, C2
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private LevelEnum level;

    public Level(LevelEnum level) {
        this.level = level;
    }
}
