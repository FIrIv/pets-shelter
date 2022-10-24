package com.telegrambot.jd501.service.DogService;

import com.telegrambot.jd501.Exceptions.InformationMessageNotFoundException;

import com.telegrambot.jd501.model.dog.DogInformationMessage;
import com.telegrambot.jd501.repository.Dog.DogInformationMessageRepository;
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
     * @param doginformationMessage
     * @return DogInformationMessage
     * @throws com.telegrambot.jd501.Exceptions.InformationMessageNotFoundException if InformationMessage with id not found
     */
    public DogInformationMessage updateInformationMessage(DogInformationMessage doginformationMessage) {
        dogInformationMessageRepository.findById(doginformationMessage.getId()).orElseThrow(() -> new InformationMessageNotFoundException("DogInformationMessage not found"));
        return dogInformationMessageRepository.save(doginformationMessage);
    }
    /**
     * delete InformationMessage from DataBase by id
     * Use  method DogInformationMessage repository {@link DogInformationMessageRepository#deleteById(Object)} } (Long id)}
     *
     * @param id
     * @return Deleted DogInformationMessage
     * @throws com.telegrambot.jd501.Exceptions.InformationMessageNotFoundException if DogInformationMessage with id not found
     */
    public DogInformationMessage deleteInformationMessage(Long id) {
        DogInformationMessage temp = dogInformationMessageRepository.findById(id).orElseThrow(() -> new InformationMessageNotFoundException("DogInformationMessage not found"));
        dogInformationMessageRepository.deleteById(id);
        return temp;
    }

    public DogInformationMessage findInformationMessageById(Long id) {
        return dogInformationMessageRepository.findById(id).orElseThrow();
    }
}
