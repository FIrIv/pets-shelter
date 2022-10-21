package com.telegrambot.jd501.repository.Dog;

import com.telegrambot.jd501.model.dog.Dog;
import com.telegrambot.jd501.model.dog.DogReport;
import com.telegrambot.jd501.model.dog.DogUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DogReportRepository extends JpaRepository<DogReport, Long> {

    List <DogReport> findDogReportsByDogOrderByDateOfReport(Dog dog);

    DogReport findDogReportByDogAndDateOfReport (Dog dog, LocalDate dateOfReport);
    List <DogReport> findAllByDogUser (DogUser tempDogUser);
}
