package com.peterbjo.soundcloud.service;


import com.peterbjo.soundcloud.domain.Word;
import com.peterbjo.soundcloud.domain.WordCount;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component("wordService")
@Transactional
public class WordServiceImpl implements WordService {
    private static final DateTimeFormatter toDateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");


    private final WordRepository wordRepository;
    private final WordCountRepository wordCountRepository;

    public WordServiceImpl(WordRepository wordRepository, WordCountRepository wordCountRepository) {
        this.wordRepository = wordRepository;
        this.wordCountRepository = wordCountRepository;
    }

    @Override
    public Word addWord(Word word) {
        Assert.notNull(word, "word cannot be null");
        return wordRepository.save(word);
    }

    @Override
    public Word getWord(String letters) {
        Assert.hasText(letters, "letters cannot be null");
        return wordRepository.findByLetters(letters);
    }

    @Override
    public WordSearchResult findWord(WordSearchCriteria searchCriteria) {
        Word word = wordRepository.findByLetters(searchCriteria.getLetters());
        if (word == null) {
            return null;
        }
        List<WordCount> wordCounts = wordCountRepository.findByWordAndCreatedBetween(word, searchCriteria.getFrom(), searchCriteria.getTo());
        return new WordSearchResult(searchCriteria.getLetters(),
                wordCounts
                        .stream()
                        .map(wordCount -> new WordCountSearchResult(toDateFormat.format(wordCount.getCreated()), wordCount.getCount()))
                        .collect(Collectors.toList()));
    }

    @Override
    public WordCount getWordCount(Word word, LocalDate created) {
        Assert.notNull(word, "word cannot be null");
        Assert.notNull(created, "created cannot be null");
        return wordCountRepository.findByWordAndCreated(word, created);
    }

    @Override
    public WordCount addWordCount(WordCount wordCount) {
        Assert.notNull(wordCount, "wordCount cannot be null");
        return wordCountRepository.save(wordCount);
    }
}
