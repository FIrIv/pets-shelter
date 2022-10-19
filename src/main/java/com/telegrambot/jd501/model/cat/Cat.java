package com.telegrambot.jd501.model.cat;

import com.telegrambot.jd501.model.Pet;

import javax.persistence.*;

@Entity
@Table(name = "cat")
public class Cat extends Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
