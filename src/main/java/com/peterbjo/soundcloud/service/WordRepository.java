package com.peterbjo.soundcloud.service;

import com.peterbjo.soundcloud.domain.Word;
import org.springframework.data.repository.Repository;

public interface WordRepository extends Repository<Word, Long> {

    Word save(Word word);

    Word findByLetters(String letters);
}
