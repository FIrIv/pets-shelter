package com.telegrambot.jd501.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "volunteer")
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        Volunteer volunteer = (Volunteer) o;
        return Objects.equals(id, volunteer.id) && Objects.equals(chatId, volunteer.chatId) && Objects.equals(name, volunteer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, name);
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                '}';
    }

    public Volunteer() {
    }

    public Volunteer(Long id, Long chatId, String name) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
    }
}