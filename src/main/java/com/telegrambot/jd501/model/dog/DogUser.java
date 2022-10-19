package com.telegrambot.jd501.model.dog;

import com.telegrambot.jd501.model.User;

import javax.persistence.*;

@Entity
@Table(name = "dog_user")
public class DogUser extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pet_id")
    private Dog pet;

    @Override
    public Dog getPet() {
        return pet;
    }

    public void setPet(Dog pet) {
        this.pet = pet;
    }
}
