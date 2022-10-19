package com.telegrambot.jd501.model;

import com.telegrambot.jd501.model.dog.Dog;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;


public class User {
    private Long id;
    private Long chatId;
    private String name;
    private String phone;
    private Pet pet;
    private Boolean isAdopted;
    private LocalDate startDate;
    private LocalDate finishDate;

    public User() {
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getAdopted() {
        return isAdopted;
    }

    public void setAdopted(Boolean adopted) {
        isAdopted = adopted;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(pet, user.pet) && Objects.equals(chatId, user.chatId) && Objects.equals(name, user.name) && Objects.equals(phone, user.phone) && Objects.equals(isAdopted, user.isAdopted) && Objects.equals(startDate, user.startDate) && Objects.equals(finishDate, user.finishDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, name, phone, isAdopted, startDate, finishDate);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", pet='" + pet + '\'' +
                ", isAdopted=" + isAdopted +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                '}';
    }
}
