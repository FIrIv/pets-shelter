package com.telegrambot.jd501.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "pet_report")
public class PetReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
    private LocalDate dateOfReport;
    private String textOfReport;
    private String photoLink;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetReport petReport = (PetReport) o;
        return Objects.equals(id, petReport.id) && Objects.equals(pet, petReport.pet) && Objects.equals(dateOfReport, petReport.dateOfReport) && Objects.equals(textOfReport, petReport.textOfReport) && Objects.equals(photoLink, petReport.photoLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pet, dateOfReport, textOfReport, photoLink);
    }

    @Override
    public String toString() {
        return "PetReport{" +
                "id=" + id +
                ", pet=" + pet +
                ", dateOfReport=" + dateOfReport +
                ", textOfReport='" + textOfReport + '\'' +
                ", photoLink='" + photoLink + '\'' +
                '}';
    }

    public PetReport() {
    }

    public PetReport(Long id, Pet pet, LocalDate dateOfReport, String textOfReport, String photoLink) {
        this.id = id;
        this.pet = pet;
        this.dateOfReport = dateOfReport;
        this.textOfReport = textOfReport;
        this.photoLink = photoLink;
    }
}
