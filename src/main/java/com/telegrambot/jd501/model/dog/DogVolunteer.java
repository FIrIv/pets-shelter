package com.telegrambot.jd501.model.dog;

import com.telegrambot.jd501.model.Volunteer;

import javax.persistence.*;

@Entity
@Table(name = "dog_volunteer")
public class DogVolunteer extends Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
