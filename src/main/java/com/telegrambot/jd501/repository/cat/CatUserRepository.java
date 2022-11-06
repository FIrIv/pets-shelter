package com.telegrambot.jd501.repository.cat;

import com.telegrambot.jd501.model.cat.CatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatUserRepository extends JpaRepository <CatUser, Long> {
    List<CatUser> findCatUsersByIsAdoptedIsTrue ();

    boolean existsByChatId(Long chatId);
    CatUser findCatUserByChatId(Long catUserChatId);
}
