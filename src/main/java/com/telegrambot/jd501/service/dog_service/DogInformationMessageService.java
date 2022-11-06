package com.telegrambot.jd501.service.dog_service;

import com.telegrambot.jd501.exceptions.InformationMessageNotFoundException;

import com.telegrambot.jd501.model.dog.DogInformationMessage;
import com.telegrambot.jd501.repository.dog.DogInformationMessageRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DogInformationMessageService {
    private final DogInformationMessageRepository dogInformationMessageRepository;

    public DogInformationMessageService(DogInformationMessageRepository dogInformationMessageRepository) {
        this.dogInformationMessageRepository = dogInformationMessageRepository;
    }
    /**
     * get All DogInformationMessage from DataBase
     * Use method of DogInformationMessage repository {@link DogInformationMessageRepository#findAll()} ()} (Collection< DogInformationMessage >)}
     *
     * @return collection of DogInformationMessage
     */
    public Collection<DogInformationMessage> getAllInformationMessages() {
        return dogInformationMessageRepository.findAll();
    }
    /**
     * add new DogInformationMessage in DataBase
     *
     * @param dogInformationMessage
     * Use  method DogInformationMessage repository {@link DogInformationMessageRepository#save(Object)} (DogInformationMessageRepository)}
     * @return DogInformationMessage
     */
    public DogInformationMessage createInformationMessage(DogInformationMessage dogInformationMessage) {
        return dogInformationMessageRepository.save(dogInformationMessage);
    }
    /**
     * change DogInformationMessage in DataBase
     * Use  method DogInformationMessage repository {@link DogInformationMessageRepository#save(Object)} (DogInformationMessageRepository)}
     *
     * @param dogInformationMessage (object)
     * @return DogInformationMessage
     * @throws com.telegrambot.jd501.exceptions.InformationMessageNotFoundException if InformationMessage with id not found
     */
    public DogInformationMessage updateInformationMessage(DogInformationMessage dogInformationMessage) {
        dogInformationMessageRepository.findById(dogInformationMessage.getId()).orElseThrow(() -> new InformationMessageNotFoundException("DogInformationMessage not found"));
        return dogInformationMessageRepository.save(dogInformationMessage);
    }
    /**
     * delete InformationMessage from DataBase by id
     * Use  method DogInformationMessage repository {@link DogInformationMessageRepository#deleteById(Object)} } (Long id)}
     *
     * @param id of InformationMessage
     * @return Deleted DogInformationMessage
     * @throws com.telegrambot.jd501.exceptions.InformationMessageNotFoundException if DogInformationMessage with id not found
     */
    public DogInformationMessage deleteInformationMessage(Long id) {
        DogInformationMessage temp = dogInformationMessageRepository.findById(id).orElseThrow(() -> new InformationMessageNotFoundException("DogInformationMessage not found"));
        dogInformationMessageRepository.deleteById(id);
        return temp;
    }

    /**
     * get DogInformationMessage from DataBase By Id
     * Use method of DogInformationMessage repository {@link DogInformationMessageRepository#findById(Long)} ()} (DogInformationMessage)}
     *
     * @return DogInformationMessage
     */
    public DogInformationMessage findInformationMessageById(Long id) {
        return dogInformationMessageRepository.findById(id).orElseThrow();
    }
}
