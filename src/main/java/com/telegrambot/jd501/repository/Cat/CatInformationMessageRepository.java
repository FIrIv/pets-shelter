package com.telegrambot.jd501.repository.Cat;

import com.telegrambot.jd501.model.cat.CatInformationMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatInformationMessageRepository extends JpaRepository<CatInformationMessage,Long> {
}
