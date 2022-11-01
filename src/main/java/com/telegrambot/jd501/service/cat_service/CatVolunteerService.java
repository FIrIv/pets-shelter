package com.telegrambot.jd501.service.cat_service;


import com.telegrambot.jd501.exceptions.VolunteerNotFoundException;

import com.telegrambot.jd501.model.cat.CatVolunteer;
import com.telegrambot.jd501.repository.cat.CatVolunteerRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CatVolunteerService {
    private final CatVolunteerRepository catVolunteerRepository;

    public CatVolunteerService(CatVolunteerRepository catVolunteerRepository) {
        this.catVolunteerRepository = catVolunteerRepository;
    }
    /**
     * get All CatVolunteer from DataBase
     * Use method of CatVolunteer repository {@link CatVolunteerRepository#findAll()} ()} (Collection< CatVolunteer >)}
     *
     * @return collection of CatVolunteer
     */
    public Collection<CatVolunteer> getAllVolunteers() {
        return catVolunteerRepository.findAll();
    }

    /**
     * add new CatVolunteer in DataBase
     *
     * @param catVolunteer
     * Use  method CatVolunteer repository {@link CatVolunteerRepository#save(Object)} (CatVolunteer)}
     * @return CatVolunteer
     */
    public CatVolunteer createVolunteer(CatVolunteer catVolunteer) {
        return catVolunteerRepository.save(catVolunteer);
    }

    /**
     * change CatVolunteer in DataBase
     * Use  method CatVolunteer repository {@link CatVolunteerRepository#save(Object)} (CatVolunteer)}
     *
     * @param catVolunteer
     * @return CatVolunteer
     * @throws com.telegrambot.jd501.exceptions.VolunteerNotFoundException if CatVolunteer with id not found
     */
    public CatVolunteer updateVolunteer(CatVolunteer catVolunteer) {
        catVolunteerRepository.findById(catVolunteer.getId()).orElseThrow(() -> new VolunteerNotFoundException("CatVolunteer not found"));
        return catVolunteerRepository.save(catVolunteer);
    }
    /**
     * delete CatVolunteer from DataBase by id
     * Use  method CatVolunteer repository {@link CatVolunteerRepository#deleteById(Object)} } (Long id)}
     *
     * @param id
     * @return Deleted CatVolunteer
     * @throws com.telegrambot.jd501.exceptions.VolunteerNotFoundException if CatVolunteer with id not found
     */
    public CatVolunteer deleteVolunteer(Long id) {
        CatVolunteer temp = catVolunteerRepository.findById(id).orElseThrow(() -> new VolunteerNotFoundException("CatVolunteer not found"));
        catVolunteerRepository.deleteById(id);
        return temp;
    }

    /**
     * find CatVolunteer if he exists by his ID
     * Use method CatVolunteer repository {@link CatVolunteerRepository#existsById(Object)}
     *
     * @return boolean
     */
    public boolean isExistsVolunteer(long userChatId) {
        return catVolunteerRepository.existsByChatId(userChatId);
    }

}