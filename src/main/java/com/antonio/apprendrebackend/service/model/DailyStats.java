package com.antonio.apprendrebackend.service.model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DailyStats {
    private LocalDate date;
    private List<WordTranslationHistorial> historialDay;

    public DailyStats(LocalDate date) {
        this.date = date;
        this.historialDay = new ArrayList<>();
    }

    public DailyStats(LocalDate date, List<WordTranslationHistorial> historialDay) {
        this.date = date;
        this.historialDay = historialDay;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<WordTranslationHistorial> getHistorialDay() {
        return historialDay;
    }

    public void setHistorialDay(List<WordTranslationHistorial> historialDay) {
        this.historialDay = historialDay;
    }
}
