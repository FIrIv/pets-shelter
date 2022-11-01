package com.telegrambot.jd501.repository.cat;

import com.telegrambot.jd501.model.cat.CatVolunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatVolunteerRepository extends JpaRepository <CatVolunteer, Long> {

    boolean existsByChatId(Long chatId);
}

