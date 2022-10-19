package com.telegrambot.jd501.service.CatService;


import com.telegrambot.jd501.Exceptions.VolunteerNotFoundException;

import com.telegrambot.jd501.model.cat.CatVolunteer;
import com.telegrambot.jd501.repository.Cat.CatVolunteerRepository;
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
     * Use method of DogVolunteer repository {@link CatVolunteerRepository#findAll()} ()} (Collection< CatVolunteer >)}
     *
     * @return collection of CatVolunteer
     */
    public Collection<CatVolunteer> getAllCatVolunteer() {
        return catVolunteerRepository.findAll();
    }

    /**
     * add new CatVolunteer in DataBase
     *
     * @param catVolunteer
     * Use  method DogVolunteer repository {@link CatVolunteerRepository#save(Object)} (CatVolunteer)}
     * @return CatVolunteer
     */
    public CatVolunteer createCatVolunteer(CatVolunteer catVolunteer) {
        return catVolunteerRepository.save(catVolunteer);
    }

    /**
     * change CatVolunteer in DataBase
     * Use  method DogVolunteer repository {@link CatVolunteerRepository#save(Object)} (CatVolunteer)}
     *
     * @param catVolunteer
     * @return CatVolunteer
     * @throws VolunteerNotFoundException if CatVolunteer with id not found
     */
    public CatVolunteer updateCatVolunteer(CatVolunteer catVolunteer) {
        catVolunteerRepository.findById(catVolunteer.getId()).orElseThrow(() -> new VolunteerNotFoundException("CatVolunteer not found"));
        return catVolunteerRepository.save(catVolunteer);
    }
    /**
     * delete CatVolunteer from DataBase by id
     * Use  method CatVolunteer repository {@link CatVolunteerRepository#deleteById(Object)} } (Long id)}
     *
     * @param id
     * @return Deleted CatVolunteer
     * @throws VolunteerNotFoundException if CatVolunteer with id not found
     */
    public CatVolunteer deleteCatVolunteer(Long id) {
        CatVolunteer temp = catVolunteerRepository.findById(id).orElseThrow(() -> new VolunteerNotFoundException("DogVolunteer not found"));
        catVolunteerRepository.deleteById(id);
        return temp;
    }

    /**
     * find CatVolunteer if he exists by his ID
     * Use method CatVolunteer repository {@link CatVolunteerRepository#existsById(Object)}
     *
     * @return boolean
     */
    public boolean isExistsCatVolunteer(long userChatId) {
        return catVolunteerRepository.existsByChatId(userChatId);
    }
}
