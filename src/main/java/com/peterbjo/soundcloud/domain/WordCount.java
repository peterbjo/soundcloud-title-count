package com.peterbjo.soundcloud.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class WordCount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Temporal(TemporalType.DATE)
    private Date created;
    @ManyToOne
    private Word word;
}
