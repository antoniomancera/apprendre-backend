package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Success {
    public enum SuccessEnum {
        INCORRECT,
        CORRECT,
        ACCENT_MARK_INCORRECT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private SuccessEnum successEnum;

    private String name;
    private Double score;
}
