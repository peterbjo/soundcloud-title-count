package com.peterbjo.soundcloud.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Table(
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"created", "word_id"})
)

@Entity
public class WordCount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private LocalDate created;

    @Column(nullable = false)
    private Long count;

    @ManyToOne(optional = false)
    @JoinColumn(name = "word_id", nullable = false)
    private Word word;

    protected WordCount() {

    }

    public WordCount(LocalDate created, Long count, Word word) {
        this.created = Objects.requireNonNull(created, "created cannot be null");
        this.count = Objects.requireNonNull(count, "count cannot be null");
        this.word = Objects.requireNonNull(word, "word cannot be null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordCount wordCount = (WordCount) o;
        return Objects.equals(id, wordCount.id) &&
                created.equals(wordCount.created) &&
                count.equals(wordCount.count) &&
                word.equals(wordCount.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created, count, word);
    }

    @Override
    public String toString() {
        return "WordCount{" +
                "id=" + id +
                ", created=" + created +
                ", count=" + count +
                ", word=" + word +
                '}';
    }

    public Long getId() {
        return id;
    }

    public LocalDate getCreated() {
        return created;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Word getWord() {
        return word;
    }
}
