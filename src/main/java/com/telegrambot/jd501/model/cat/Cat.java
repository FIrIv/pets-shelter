package com.telegrambot.jd501.model.cat;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cat")
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Long getId() {
        return id;
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
        //if (!super.equals(o)) return false;
        Cat cat = (Cat) o;
        return Objects.equals(id, cat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Cat() {
    }

    public Cat(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
