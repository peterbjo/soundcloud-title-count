package com.peterbjo.soundcloud.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NaturalId
    @Column(nullable = false, unique = true)
    private String letters;

    protected Word() {

    }

    public Word(String letters) {
        this.letters = Objects.requireNonNull(letters, "letters cannot be null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(id, word.id) &&
                letters.equals(word.letters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, letters);
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", letters='" + letters + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getLetters() {
        return letters;
    }
}
