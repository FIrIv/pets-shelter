package com.telegrambot.jd501.model.cat;

import com.telegrambot.jd501.model.PetReport;

import javax.persistence.*;

@Entity
@Table(name = "cat_pet_report")
public class CatPetReport extends PetReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CatUser user;

    @Override
    public CatUser getUser() {
        return user;
    }

    public void setUser(CatUser user) {
        this.user = user;
    }
}
