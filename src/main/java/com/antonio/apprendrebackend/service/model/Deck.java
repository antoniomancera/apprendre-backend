package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private String name;
    private String description;
    private Long beginDate;
    private Long endDate;

    public Deck(UserInfo userInfo, String name, String description) {
        this.userInfo = userInfo;
        this.name = name;
        this.description = description;
        this.beginDate = System.currentTimeMillis();
    }
}
