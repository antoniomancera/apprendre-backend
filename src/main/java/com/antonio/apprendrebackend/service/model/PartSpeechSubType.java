package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PartSpeechSubType {
    public enum SubTypeEnum {
        PERSONAL_PRONOUN,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "part_speech_id")
    private PartSpeech partSpeech;


    @Enumerated(EnumType.STRING)
    private SubTypeEnum name;
}
