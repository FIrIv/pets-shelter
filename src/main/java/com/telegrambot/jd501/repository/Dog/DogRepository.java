package com.telegrambot.jd501.repository.Dog;

import com.telegrambot.jd501.model.dog.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog, Long> {
}
