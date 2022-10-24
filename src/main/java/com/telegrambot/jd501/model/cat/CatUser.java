package com.telegrambot.jd501.model.cat;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "cat_user")
public class CatUser  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    private String name;
    private String phone;
    private Boolean isAdopted;
    private LocalDate startDate;
    private LocalDate finishDate;


    @OneToOne
    @JoinColumn(name = "pet_id")
    private Cat pet;

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

    public Cat getPet() {
        return pet;
    }

    public void setPet(Cat cat) {
        this.pet = cat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        //if (!super.equals(o)) return false;
        CatUser catUser = (CatUser) o;
        return Objects.equals(id, catUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "CatUser{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", isAdopted=" + isAdopted +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", pet=" + pet +
                '}';
    }

    public CatUser() {
    }

    public CatUser(Long id, Long chatId, String name, String phone) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
        this.phone = phone;
        this.isAdopted = false;
        this.startDate = null;
        this.finishDate = null;
        this.pet = null;
    }

    public CatUser(Long id, Long chatId, String name, String phone, Boolean isAdopted, LocalDate startDate, LocalDate finishDate, Cat cat) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
        this.phone = phone;
        this.isAdopted = isAdopted;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.pet = cat;
    }
}
