package com.telegrambot.jd501.model.cat;

import com.telegrambot.jd501.model.User;

import javax.persistence.*;

@Entity
@Table(name = "cat_user")
public class CatUser extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pet_id")
    private Cat pet;

    @Override
    public Cat getPet() {
        return pet;
    }

    public void setPet(Cat pet) {
        this.pet = pet;
    }
}
