package com.telegrambot.jd501.repository.Dog;

import com.telegrambot.jd501.model.dog.Dog;
import com.telegrambot.jd501.model.dog.DogReport;
import com.telegrambot.jd501.model.dog.DogUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DogReportRepository extends JpaRepository<DogReport, Long> {

    List <DogReport> findDogReportsByDogUserOrderByDateOfReport (DogUser user);

    DogReport findDogReportByDogUserAndDateOfReport (DogUser user, LocalDate dateOfReport);

    List <DogReport> findAllByDogUser (DogUser tempDogUser);
}
