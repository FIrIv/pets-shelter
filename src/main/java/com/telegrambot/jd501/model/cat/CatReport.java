package com.telegrambot.jd501.model.cat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "cat_pet_report")
public class CatReport  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateOfReport;
    private String textOfReport;
    private String photoLink;

    @OneToOne
    @JoinColumn(name = "user_id")
    private CatUser catUser;

    @Override
    public String toString() {
        return "CatReport{" +
                "id=" + id +
                ", dateOfReport=" + dateOfReport +
                ", textOfReport='" + textOfReport + '\'' +
                ", photoLink='" + photoLink + '\'' +
                ", catUser=" + catUser +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatReport catReport = (CatReport) o;
        return Objects.equals(id, catReport.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

    public CatUser getCatUser() {
        return catUser;
    }

    public void setCatUser(CatUser catUser) {
        this.catUser = catUser;
    }
}
