package com.peterbjo.soundcloud.service;

import com.peterbjo.soundcloud.domain.Word;
import com.peterbjo.soundcloud.domain.WordCount;

import java.time.LocalDate;

public interface WordService {
    Word addWord(Word word);

    Word getWord(String letters);

    WordSearchResult findWord(WordSearchCriteria searchCriteria);

    WordCount getWordCount(Word word, LocalDate created);

    WordCount addWordCount(WordCount wordCount);
}
