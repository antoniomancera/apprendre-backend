package com.antonio.apprendrebackend.model;

import jakarta.persistence.*;

@Entity
public class Mot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public Mot(String name) {
        this.name = name;
    }

    public Mot() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Mot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}