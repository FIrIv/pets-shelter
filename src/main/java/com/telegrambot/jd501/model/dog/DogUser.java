package com.telegrambot.jd501.model.dog;



import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "dog_user")
public class DogUser {
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
    private Dog pet;

    public Dog getPet() {
        return pet;
    }

    public void setPet(Dog dog) {
        this.pet = dog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DogUser dogUser = (DogUser) o;
        return Objects.equals(id, dogUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

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

    @Override
    public String toString() {
        return "DogUser{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", isAdopted=" + isAdopted +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", dog=" + pet +
                '}';
    }

    public DogUser() {
    }

    public DogUser(Long id, Long chatId, String name, String phone) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
        this.phone = phone;
    }

    public DogUser(Long id, Long chatId, String name, String phone, Boolean isAdopted, LocalDate startDate, LocalDate finishDate, Dog dog) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
        this.phone = phone;
        this.isAdopted = isAdopted;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.pet = dog;
    }
}
