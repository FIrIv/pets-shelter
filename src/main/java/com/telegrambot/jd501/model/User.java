package com.example.demo.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    private String name;
    private String phone;
    @OneToOne
    @JoinColumn(name = "pet id")
    private Pet pet;
    private Boolean isAdopted;
    private LocalDateTime startDate;
    private Integer quantityTestDays;

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

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Boolean getAdopted() {
        return isAdopted;
    }

    public void setAdopted(Boolean adopted) {
        isAdopted = adopted;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Integer getQuantityTestDays() {
        return quantityTestDays;
    }

    public void setQuantityTestDays(Integer quantityTestDays) {
        this.quantityTestDays = quantityTestDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(chatId, user.chatId) && Objects.equals(name, user.name) && Objects.equals(phone, user.phone) && Objects.equals(pet, user.pet) && Objects.equals(isAdopted, user.isAdopted) && Objects.equals(startDate, user.startDate) && Objects.equals(quantityTestDays, user.quantityTestDays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, name, phone, pet, isAdopted, startDate, quantityTestDays);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", pet=" + pet +
                ", isAdopted=" + isAdopted +
                ", startDate=" + startDate +
                ", quantityTestDays=" + quantityTestDays +
                '}';
    }
}
