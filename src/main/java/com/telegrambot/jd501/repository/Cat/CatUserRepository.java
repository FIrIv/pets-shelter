package com.telegrambot.jd501.repository.Cat;

import com.telegrambot.jd501.model.cat.CatUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatUserRepository extends JpaRepository <CatUser, Long> {
    List<CatUser> findCatUsersByIsAdoptedIsTrue ();

    boolean existsByChatId(Long chatId);
    CatUser findCatUserByChatId(Long catUserChatId);
}
