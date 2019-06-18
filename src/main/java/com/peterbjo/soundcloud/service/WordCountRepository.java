package com.peterbjo.soundcloud.service;

import com.peterbjo.soundcloud.domain.Word;
import com.peterbjo.soundcloud.domain.WordCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.time.LocalDate;
import java.util.List;

public interface WordCountRepository extends Repository<WordCount, Long> {

    WordCount save(WordCount wordCount);

    @Query("select wc "
            + "from WordCount wc where wc.word = ?1 and wc.created = ?2")
    WordCount findByWordAndCreated(Word word, LocalDate created);

    @Query("select wc "
            + "from WordCount wc where wc.word = ?1 and wc.created between ?2 and ?3 order by wc.created DESC")
    List<WordCount> findByWordAndCreatedBetween(Word word, LocalDate from, LocalDate to);
}

