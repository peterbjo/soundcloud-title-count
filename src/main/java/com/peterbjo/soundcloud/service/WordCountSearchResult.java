package com.peterbjo.soundcloud.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WordCountSearchResult {

    private final String date;

    private final Long count;

    public WordCountSearchResult(@JsonProperty("date") String date, @JsonProperty("count") Long count) {
        this.date = date;
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public Long getCount() {
        return count;
    }
}
