package com.telegrambot.jd501.repository.Dog;

import com.telegrambot.jd501.model.dog.DogInformationMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogInformationMessageRepository extends JpaRepository<DogInformationMessage,Long> {
}
