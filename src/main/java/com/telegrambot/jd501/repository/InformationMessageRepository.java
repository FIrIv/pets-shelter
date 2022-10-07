package com.telegrambot.jd501.repository;

import com.telegrambot.jd501.model.InformationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationMessageRepository extends JpaRepository<InformationMessage,Long> {
}
