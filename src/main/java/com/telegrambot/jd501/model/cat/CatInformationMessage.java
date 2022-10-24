package com.telegrambot.jd501.model.cat;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cat_information_message")
public class CatInformationMessage {

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
    public String toString() {
        return "CatInformationMessage{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CatInformationMessage that = (CatInformationMessage) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatInformationMessage that = (CatInformationMessage) o;
        return Objects.equals(id, that.id) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }

    public CatInformationMessage() {
    }

    public CatInformationMessage(Long id, String text) {
        this.id = id;
        this.text = text;
    }
}
