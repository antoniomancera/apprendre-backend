package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long beginDate;
    private Long endDate;
    private Integer attempts;
    private Double successesAccuracy;


    public Goal() {
    }

    public Goal(Integer attempts, Double successesAccuracy) {
        this.attempts = attempts;
        this.successesAccuracy = successesAccuracy;
        this.beginDate = System.currentTimeMillis();
    }

}
