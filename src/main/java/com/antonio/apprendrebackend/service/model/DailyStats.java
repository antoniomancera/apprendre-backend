package com.antonio.apprendrebackend.service.model;


import java.time.LocalDate;

public class DailyStats {
    private LocalDate date;
    private int totalAttempts;
    private int totalSuccesses;

    public DailyStats(LocalDate date) {
        this.date = date;
    }

    public DailyStats(LocalDate date, int totalAttempts, int totalSuccesses) {
        this.date = date;
        this.totalAttempts = totalAttempts;
        this.totalSuccesses = totalSuccesses;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    public void setTotalAttempts(int totalAttempts) {
        this.totalAttempts = totalAttempts;
    }

    public int getTotalSuccesses() {
        return totalSuccesses;
    }

    public void setTotalSuccesses(int totalSuccesses) {
        this.totalSuccesses = totalSuccesses;
    }
}
