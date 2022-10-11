package com.telegrambot.jd501.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "information_message")
public class InformationMessage {
    @Id
    private Long id;

    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InformationMessage that = (InformationMessage) o;
        return Objects.equals(id, that.id) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }

    @Override
    public String toString() {
        return "InformationMessage{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
