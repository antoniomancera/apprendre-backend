package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    private Long beginDate;
    private Long endDate;
    private Integer attempts;
    private Double successesAccuracy;


    public Goal() {
    }

    public Goal(UserInfo userInfo, Integer attempts, Double successesAccuracy) {
        this.userInfo = userInfo;
        this.attempts = attempts;
        this.successesAccuracy = successesAccuracy;
        this.beginDate = System.currentTimeMillis();
    }

}
