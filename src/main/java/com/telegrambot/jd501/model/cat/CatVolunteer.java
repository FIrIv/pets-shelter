package com.telegrambot.jd501.model.cat;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cat_volunteer")
public class CatVolunteer {
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
        if (!super.equals(o)) return false;
        CatVolunteer that = (CatVolunteer) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CatVolunteer{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                '}';
    }

    public CatVolunteer() {
    }

    public CatVolunteer(Long id, Long chatId, String name) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
    }
}
