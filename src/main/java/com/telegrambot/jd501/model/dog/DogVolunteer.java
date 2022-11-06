package com.telegrambot.jd501.model.dog;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dog_volunteer")
public class DogVolunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    private String name;

    public Long getId() {
        return id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DogVolunteer that = (DogVolunteer) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }



    @Override
    public String toString() {
        return "DogVolunteer{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                '}';
    }

    public DogVolunteer() {
    }

    public DogVolunteer(Long id, Long chatId, String name) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
    }
}
