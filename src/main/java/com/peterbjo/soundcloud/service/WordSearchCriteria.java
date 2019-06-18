package com.peterbjo.soundcloud.service;

import java.time.LocalDate;
import java.util.Objects;

public class WordSearchCriteria {

    private String letters;

    private LocalDate from;

    private LocalDate to;

    public WordSearchCriteria(String letters, LocalDate from, LocalDate to) {
        this.letters = Objects.requireNonNull(letters, "letters cannot be null");
        this.from = Objects.requireNonNull(from, "from cannot be null");
        this.to = Objects.requireNonNull(to, "to cannot be null");
    }

    public String getLetters() {
        return letters;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }
}
