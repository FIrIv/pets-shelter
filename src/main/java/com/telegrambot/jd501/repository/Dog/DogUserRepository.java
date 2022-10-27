package com.telegrambot.jd501.repository.Dog;

import com.telegrambot.jd501.model.dog.DogUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogUserRepository extends JpaRepository <DogUser, Long> {
    List<DogUser> findDogUsersByIsAdoptedIsTrue ();

    boolean existsByChatId(Long chatId);
    
    DogUser findDogUserByChatId(long dogUserChatId);
}
