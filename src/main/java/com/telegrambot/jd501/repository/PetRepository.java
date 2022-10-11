package com.telegrambot.jd501.repository;

import com.telegrambot.jd501.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
}
