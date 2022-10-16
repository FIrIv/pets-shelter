package com.telegrambot.jd501.repository;

import com.telegrambot.jd501.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer,Long> {

    boolean existsByChatId(Long chatId);
}
