package com.telegrambot.jd501.repository;

import com.telegrambot.jd501.model.Pet;
import com.telegrambot.jd501.model.PetReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PetReportRepository extends JpaRepository<PetReport, Long> {

    List<PetReport> findPetReportsByPetOrderByDateOfReport(Pet pet);

    PetReport findPetReportByPetAndDateOfReport (Pet pet, LocalDate dateOfReport);
}
