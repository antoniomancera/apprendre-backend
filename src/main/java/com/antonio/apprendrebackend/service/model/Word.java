package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @ManyToOne
    @JoinColumn(name = "part_speech_id")
    private PartSpeech partSpeech;

    @ManyToOne
    @JoinColumn(name = "part_speech_sub_type_id")
    private PartSpeechSubType partSpeechSubType;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    private String name;
}