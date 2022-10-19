package com.telegrambot.jd501.model.cat;

import com.telegrambot.jd501.model.Volunteer;

import javax.persistence.*;

@Entity
@Table(name = "cat_volunteer")
public class CatVolunteer extends Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
