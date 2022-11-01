package com.telegrambot.jd501.repository;

import com.telegrambot.jd501.model.MailingList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailingListRepository extends JpaRepository<MailingList, Long> {
}
