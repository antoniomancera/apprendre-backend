package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Phrase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String phraseEs;
    private String phraseFr;
    private String description;
    private Integer attempts;
    private Integer successes;

    @Override
    public String toString() {
        return "Phrase{" +
                "id=" + id +
                ", phraseEs='" + phraseEs + '\'' +
                ", phraseFr='" + phraseFr + '\'' +
                ", description='" + description + '\'' +
                ", attempts=" + attempts +
                ", aciertos=" + successes +
                '}';
    }
}
