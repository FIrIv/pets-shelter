package com.telegrambot.jd501.service.dog_service;


import com.telegrambot.jd501.exceptions.VolunteerNotFoundException;

import com.telegrambot.jd501.model.dog.DogVolunteer;
import com.telegrambot.jd501.repository.dog.DogVolunteerRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DogVolunteerService {
    private final DogVolunteerRepository dogVolunteerRepository;

    public DogVolunteerService(DogVolunteerRepository dogVolunteerRepository) {
        this.dogVolunteerRepository = dogVolunteerRepository;
    }
    /**
     * get All DogVolunteer from DataBase
     * Use method of DogVolunteer repository {@link DogVolunteerRepository#findAll()} ()} (Collection< DogVolunteer >)}
     *
     * @return collection of DogVolunteer
     */
    public Collection<DogVolunteer> getAllVolunteers() {
        return dogVolunteerRepository.findAll();
    }

    /**
     * add new DogVolunteer in DataBase
     *
     * @param dogVolunteer
     * Use  method DogVolunteer repository {@link DogVolunteerRepository#save(Object)} (DogVolunteer)}
     * @return DogVolunteer
     */
    public DogVolunteer createVolunteer(DogVolunteer dogVolunteer) {
        return dogVolunteerRepository.save(dogVolunteer);
    }

    /**
     * change DogVolunteer in DataBase
     * Use  method DogVolunteer repository {@link DogVolunteerRepository#save(Object)} (DogVolunteer)}
     *
     * @param dogVolunteer (object)
     * @return DogVolunteer
     * @throws com.telegrambot.jd501.exceptions.VolunteerNotFoundException if DogVolunteer with id not found
     */
    public DogVolunteer updateVolunteer(DogVolunteer dogVolunteer) {
        dogVolunteerRepository.findById(dogVolunteer.getId()).orElseThrow(() -> new VolunteerNotFoundException("DogVolunteer not found"));
        return dogVolunteerRepository.save(dogVolunteer);
    }
    /**
     * delete DogVolunteer from DataBase by id
     * Use  method DogVolunteer repository {@link DogVolunteerRepository#deleteById(Object)} } (Long id)}
     *
     * @param id of DogVolunteer
     * @return Deleted DogVolunteer
     * @throws com.telegrambot.jd501.exceptions.VolunteerNotFoundException if DogVolunteer with id not found
     */
    public DogVolunteer deleteVolunteer(Long id) {
        DogVolunteer temp = dogVolunteerRepository.findById(id).orElseThrow(() -> new VolunteerNotFoundException("DogVolunteer not found"));
        dogVolunteerRepository.deleteById(id);
        return temp;
    }

    /**
     * find DogVolunteer if he exists by his ID
     * Use method DogVolunteer repository {@link DogVolunteerRepository#existsById(Object)}
     *
     * @return boolean
     */
    public boolean isExistsVolunteer(long userChatId) {
        return dogVolunteerRepository.existsByChatId(userChatId);
    }

}
