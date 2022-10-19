package com.telegrambot.jd501.model.dog;

import com.telegrambot.jd501.model.PetReport;

import javax.persistence.*;

@Entity
@Table(name = "dog_pet_report")
public class DogPetReport extends PetReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private DogUser user;

    @Override
    public DogUser getUser() {
        return user;
    }

    public void setUser(DogUser user) {
        this.user = user;
    }
}
