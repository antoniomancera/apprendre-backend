package com.antonio.apprendrebackend.dto;

public class PhraseDTO {
    private Integer id;
    private String phraseEs;
    private String phraseFr;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhraseEs() {
        return phraseEs;
    }

    public void setPhraseEs(String phraseEs) {
        this.phraseEs = phraseEs;
    }

    public String getPhraseFr() {
        return phraseFr;
    }

    public void setPhraseFr(String phraseFr) {
        this.phraseFr = phraseFr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PhraseDTO{" +
                "id=" + id +
                ", phraseEs='" + phraseEs + '\'' +
                ", phraseFr='" + phraseFr + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
