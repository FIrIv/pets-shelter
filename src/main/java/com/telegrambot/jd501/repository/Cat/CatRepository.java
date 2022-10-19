package com.telegrambot.jd501.repository.Cat;

import com.telegrambot.jd501.model.cat.Cat;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CatRepository extends JpaRepository <Cat, Long> {
}
