package com.telegrambot.jd501.repository.cat;

import com.telegrambot.jd501.model.cat.CatInformationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatInformationMessageRepository extends JpaRepository<CatInformationMessage,Long> {
}
