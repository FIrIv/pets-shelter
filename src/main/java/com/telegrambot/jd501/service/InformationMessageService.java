package com.telegrambot.jd501.service;

import com.telegrambot.jd501.Exceptions.InformationMessageNotFoundException;
import com.telegrambot.jd501.model.InformationMessage;
import com.telegrambot.jd501.repository.InformationMessageRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class InformationMessageService {
    private final InformationMessageRepository informationMessageRepository;

    public InformationMessageService(InformationMessageRepository informationMessageRepository) {
        this.informationMessageRepository = informationMessageRepository;
    }
    /**
     * get All InformationMessage from DataBase
     * Use method of InformationMessage repository {@link InformationMessageRepository#findAll()} ()} (Collection< InformationMessage >)}
     *
     * @return collection of InformationMessage
     */
    public Collection<InformationMessage> getAllInformationMessage() {
        return informationMessageRepository.findAll();
    }
    /**
     * add new InformationMessage in DataBase
     *
     * @param informationMessage
     * Use  method InformationMessage repository {@link InformationMessageRepository#save(Object)} (InformationMessage)}
     * @return InformationMessage
     */
    public InformationMessage createInformationMessage(InformationMessage informationMessage) {
        return informationMessageRepository.save(informationMessage);
    }
    /**
     * change InformationMessage in DataBase
     * Use  method InformationMessage repository {@link InformationMessageRepository#save(Object)} (InformationMessage)}
     *
     * @param informationMessage
     * @return InformationMessage
     * @throws com.telegrambot.jd501.Exceptions.InformationMessageNotFoundException if InformationMessage with id not found
     */
    public InformationMessage updateInformationMessage(InformationMessage informationMessage) {
        informationMessageRepository.findById(informationMessage.getId()).orElseThrow(() -> new InformationMessageNotFoundException("InformationMessage not found"));
        return informationMessageRepository.save(informationMessage);
    }
    /**
     * delete InformationMessage from DataBase by id
     * Use  method InformationMessage repository {@link InformationMessageRepository#deleteById(Object)} } (Long id)}
     *
     * @param id
     * @return Deleted InformationMessage
     * @throws com.telegrambot.jd501.Exceptions.InformationMessageNotFoundException if InformationMessage with id not found
     */
    public InformationMessage deleteInformationMessage(Long id) {
        InformationMessage temp = informationMessageRepository.findById(id).orElseThrow(() -> new InformationMessageNotFoundException("InformationMessage not found"));
        informationMessageRepository.deleteById(id);
        return temp;
    }
}
