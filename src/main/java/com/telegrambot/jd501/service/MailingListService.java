package com.telegrambot.jd501.service;

import com.telegrambot.jd501.model.MailingList;
import com.telegrambot.jd501.repository.MailingListRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MailingListService {
    private final MailingListRepository mailingListRepository;

    public MailingListService(MailingListRepository mailingListRepository) {
        this.mailingListRepository = mailingListRepository;
    }

    /**
     * get All MailingList from DataBase
     * Use method of MailingList repository {@link com.telegrambot.jd501.repository.MailingListRepository#findAll()} ()} (Collection< MailingList >)}
     *
     * @return collection of MailingList
     */
    public Collection<MailingList> getAllMailingList() {
        return mailingListRepository.findAll();
    }

    /**
     * add new MailingList in DataBase
     * @param chatId
     * @param message
     * Use method MailingList repository {@link com.telegrambot.jd501.repository.MailingListRepository#save(Object)} (MailingList)}
     * @return DogInformationMessage
     */
    public MailingList sendMessageToUserByChatId(Long chatId, String message) {
        return mailingListRepository.save(new MailingList(chatId, message));
    }

    /**
     * remove All MailingList from DataBase
     * Use method of MailingList repository {@link com.telegrambot.jd501.repository.MailingListRepository#deleteAll()}
     *
     * @return collection of MailingList
     */
    public void cleanMailingList() {
        mailingListRepository.deleteAll();
    }
}
