package com.telegrambot.jd501.repository.Dog;

import com.telegrambot.jd501.model.dog.DogUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DogUserRepository extends JpaRepository <DogUser, Long> {
    List<DogUser> findDogUsersByIsAdoptedIsTrue ();

    boolean existsByChatId(Long chatId);
    DogUser findDogUserByChatId(long dogUserChatId);


}
