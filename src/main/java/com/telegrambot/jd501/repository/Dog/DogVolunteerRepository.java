package com.telegrambot.jd501.repository.dog;

import com.telegrambot.jd501.model.dog.DogVolunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogVolunteerRepository extends JpaRepository <DogVolunteer, Long> {

    boolean existsByChatId(Long chatId);
}
