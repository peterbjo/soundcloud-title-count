package com.peterbjo.soundcloud.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WordSearchResult {

    private final String word;

    private final List<WordCountSearchResult> wordCounts;

    public WordSearchResult(@JsonProperty("word") String word, @JsonProperty("timeSeries") List<WordCountSearchResult> wordCounts) {
        this.word = word;
        this.wordCounts = wordCounts;
    }

    public String getWord() {
        return word;
    }

    public List<WordCountSearchResult> getWordCounts() {
        return wordCounts;
    }
}
