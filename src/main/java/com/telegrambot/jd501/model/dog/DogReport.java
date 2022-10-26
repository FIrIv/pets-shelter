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
    private byte[] photo;

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
                ", photo='" + photo + '\'' +
                ", dogUser=" + dogUser +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhotoLink(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public DogReport() {
    }

    public DogReport(Long id, LocalDate dateOfReport, String textOfReport, byte[] photo, DogUser dogUser) {
        this.id = id;
        this.dateOfReport = dateOfReport;
        this.textOfReport = textOfReport;
        this.photo = photo;
        this.dogUser = dogUser;
    }
}
