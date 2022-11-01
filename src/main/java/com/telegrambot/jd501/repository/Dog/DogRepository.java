package com.telegrambot.jd501.repository.dog;

import com.telegrambot.jd501.model.dog.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
}
