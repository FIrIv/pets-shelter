package com.telegrambot.jd501.repository;

import com.telegrambot.jd501.model.PetReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetReportRepository extends JpaRepository<PetReport, Long> {

    List<PetReport> findByPetIdOrderByDateOfReport(Long petId);
}
