package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class WordCollectionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "word_collection_id")
    private WordCollection wordCollection;

    @ManyToOne
    @JoinColumn(name = "word_sense_id")
    private WordSense wordSense;

    @ManyToOne
    @JoinColumn(name = "part_speech_sub_type_id")
    private PartSpeechSubType partSpeechSubType;

    @ManyToOne
    @JoinColumn(name = "person_gender_number_id")
    private PersonGenderNumber personGenderNumber;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;
}