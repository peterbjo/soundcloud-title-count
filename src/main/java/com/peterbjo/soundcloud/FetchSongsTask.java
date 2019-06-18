package com.peterbjo.soundcloud;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.peterbjo.soundcloud.domain.Word;
import com.peterbjo.soundcloud.domain.WordCount;
import com.peterbjo.soundcloud.service.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component("fetchSongsTask")
@Transactional
public class FetchSongsTask {
    private static final Logger log = LoggerFactory.getLogger(FetchSongsTask.class);

    private static final DateTimeFormatter fromDateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss Z");

    private final WordService wordService;

    private final RestTemplate restTemplate;

    public FetchSongsTask(WordService wordService, RestTemplate restTemplate) {
        this.wordService = wordService;
        this.restTemplate = restTemplate;
    }

    @Scheduled(cron = "0 59 23 ? * * ", zone = "UTC")
    public void reportCurrentTime() {
        fetchAndPopulate();
    }

    void fetchAndPopulate() {
        log.info("Fetching songs");
        Map<String, Map<LocalDate, Long>> wordMap = new HashMap<>();
        Song[] songs = restTemplate.getForObject("https://api.soundcloud.com/tracks?client_id=3a792e628dbaf8054e55f6e134ebe5fa", Song[].class);
        if (songs == null) {
            log.info("No new songs where found");
            return;
        }
        for (Song song : songs) {
            log.info("Parsing song {}", song.title);
            String title = song.title;
            LocalDate created = LocalDate.from(fromDateFormat.parse(song.created));
            String[] words = title.split(" ");
            for (String word : words) {
                String key = word.trim().toLowerCase();
                if (key.isEmpty()) {
                    continue;
                }
                Map<LocalDate, Long> wordEntry = wordMap.get(key);
                if (wordEntry == null) {
                    Map<LocalDate, Long> countEntry = new HashMap<>();
                    countEntry.put(created, 1L);
                    wordMap.put(key, countEntry);
                    continue;
                }
                Long count = wordEntry.get(created);
                if (count == null) {
                    wordEntry.put(created, 1L);
                    continue;
                }
                count++;
                wordEntry.put(created, count);
            }

        }
        for (String letters : wordMap.keySet()) {
            Word word = wordService.getWord(letters);
            if (word == null) {
                word = wordService.addWord(new Word(letters));
                log.info("Added {}", word.toString());
            }
            log.info("Handling {}", word.toString());
            Map<LocalDate, Long> wordCountEntry = wordMap.get(letters);
            for (LocalDate created : wordCountEntry.keySet()) {
                Long count = wordCountEntry.get(created);
                WordCount wordCount = wordService.getWordCount(word, created);
                if (wordCount == null) {
                    wordCount = wordService.addWordCount(new WordCount(created, count, word));
                    log.info("Added {}", wordCount.toString());
                    continue;
                }
                wordCount.setCount(count);
                wordService.addWordCount(wordCount);
                log.info("Updated {}", wordCount.toString());
            }
        }

    }

    @PostConstruct
    public void init() {
        fetchAndPopulate();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static final class Song {
        private final String created;
        private final String title;

        private Song(@JsonProperty("created_at") String created, @JsonProperty("title") String title) {
            this.created = created;
            this.title = title;
        }
    }

}
