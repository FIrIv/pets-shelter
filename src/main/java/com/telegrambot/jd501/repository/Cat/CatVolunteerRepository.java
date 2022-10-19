package com.telegrambot.jd501.repository.Cat;

import com.telegrambot.jd501.model.cat.CatVolunteer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatVolunteerRepository extends JpaRepository <CatVolunteer, Long> {

    boolean existsByChatId(Long chatId);
}
