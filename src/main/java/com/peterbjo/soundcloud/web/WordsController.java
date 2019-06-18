/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.peterbjo.soundcloud.web;

import com.peterbjo.soundcloud.service.WordSearchCriteria;
import com.peterbjo.soundcloud.service.WordSearchResult;
import com.peterbjo.soundcloud.service.WordService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class WordsController {

    private static final DateTimeFormatter fromDateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");


    private final WordService wordService;

    public WordsController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/words")
    @ResponseBody
    @Transactional(readOnly = true)
    public WordSearchResult words(@RequestParam("word") String word, @RequestParam("from") String from, @RequestParam("to") String to) {
        Assert.notNull(word, "word cannot be null");
        Assert.notNull(from, "from cannot be null");
        Assert.notNull(to, "to cannot be null");
        WordSearchCriteria wordSearchCriteria = new WordSearchCriteria(word.trim().toLowerCase(), LocalDate.parse(from, fromDateFormat), LocalDate.parse(to, fromDateFormat));
        return this.wordService.findWord(wordSearchCriteria);
    }

}
