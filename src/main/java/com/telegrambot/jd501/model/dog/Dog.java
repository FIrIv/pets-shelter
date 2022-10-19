package com.telegrambot.jd501.model.dog;

import com.telegrambot.jd501.model.Pet;

import javax.persistence.*;

@Entity
@Table(name = "dog")
public class Dog extends Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
