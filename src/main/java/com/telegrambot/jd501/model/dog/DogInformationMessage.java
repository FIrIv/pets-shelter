package com.telegrambot.jd501.model.dog;



import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dog_information_message")
public class DogInformationMessage  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @Override
    public String toString() {
        return "DogInformationMessage{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DogInformationMessage that = (DogInformationMessage) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

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

    public DogInformationMessage() {
    }

    public DogInformationMessage(Long id, String text) {
        this.id = id;
        this.text = text;
    }
}
