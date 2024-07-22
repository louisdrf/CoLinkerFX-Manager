package com.colinker.helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class FullLocalDate {
    LocalDate date;
    int hours;
    int minutes;

    public FullLocalDate(LocalDate date, int hours, int minutes) {
        this.date = date;
        this.hours = hours;
        this.minutes = minutes;
    }

    private LocalDateTime toLocalDateTime() {
        return LocalDateTime.of(this.date, java.time.LocalTime.of(this.hours, this.minutes));
    }

    public Date toDate() {
        LocalDateTime asLocalDateTime = this.toLocalDateTime();
        return Date.from(asLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
