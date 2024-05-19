package com.antonio.apprendrebackend.model;

import jakarta.persistence.*;

@Entity
public class Type {
    public enum TypeEnum {
        SUSTANTIVE, VERB, ADJECTIVE, ADVERB, PRONOUN, PREPOSITION, CONJUNCTION, INTERJECTION
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String typeName;

    @Enumerated(EnumType.STRING)
    private TypeEnum typeEnum;

    public Type() {
    }

    public Type(String typeName, TypeEnum typeEnum) {
        this.typeName = typeName;
        this.typeEnum = typeEnum;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public TypeEnum getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(TypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }


}
