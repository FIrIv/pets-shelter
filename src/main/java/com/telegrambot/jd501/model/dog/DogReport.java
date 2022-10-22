package com.telegrambot.jd501.model.dog;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "dog_pet_report")
public class DogReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateOfReport;
    private String textOfReport;
    private String photoLink;

    @OneToOne
    @JoinColumn(name = "user_id")
    private DogUser dogUser;

    public DogUser getDogUser() {
        return dogUser;
    }

    public void setDogUser(DogUser dogUser) {
        this.dogUser = dogUser;
    }

    @Override
    public String toString() {
        return "DogPetReport{" +
                "id=" + id +
                ", dateOfReport=" + dateOfReport +
                ", textOfReport='" + textOfReport + '\'' +
                ", photoLink='" + photoLink + '\'' +
                ", dogUser=" + dogUser +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!this.equals(o)) return false;
        DogReport that = (DogReport) o;
        return Objects.equals(id, that.id);
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDateOfReport() {
        return dateOfReport;
    }

    public void setDateOfReport(LocalDate dateOfReport) {
        this.dateOfReport = dateOfReport;
    }

    public String getTextOfReport() {
        return textOfReport;
    }

    public void setTextOfReport(String textOfReport) {
        this.textOfReport = textOfReport;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
