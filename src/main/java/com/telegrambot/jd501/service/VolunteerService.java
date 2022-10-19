package com.telegrambot.jd501.service;


import com.telegrambot.jd501.Exceptions.VolunteerNotFoundException;
import com.telegrambot.jd501.model.Volunteer;
import com.telegrambot.jd501.repository.VolunteerRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }
    /**
     * get All Volunteer from DataBase
     * Use method of Volunteer repository {@link VolunteerRepository#findAll()} ()} (Collection< Volunteer >)}
     *
     * @return collection of Volunteer
     */
    public Collection<Volunteer> getAllVolunteer() {
        return volunteerRepository.findAll();
    }

    /**
     * add new Volunteer in DataBase
     *
     * @param volunteer
     * Use  method Volunteer repository {@link VolunteerRepository#save(Object)} (Volunteer)}
     * @return Volunteer
     */
    public Volunteer createVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    /**
     * change Volunteer in DataBase
     * Use  method Volunteer repository {@link VolunteerRepository#save(Object)} (Volunteer)}
     *
     * @param volunteer
     * @return Volunteer
     * @throws com.telegrambot.jd501.Exceptions.VolunteerNotFoundException if Volunteer with id not found
     */
    public Volunteer updateVolunteer(Volunteer volunteer) {
        volunteerRepository.findById(volunteer.getId()).orElseThrow(() -> new VolunteerNotFoundException("Volunteer not found"));
        return volunteerRepository.save(volunteer);
    }
    /**
     * delete Volunteer from DataBase by id
     * Use  method Volunteer repository {@link VolunteerRepository#deleteById(Object)} } (Long id)}
     *
     * @param id
     * @return Deleted Volunteer
     * @throws com.telegrambot.jd501.Exceptions.VolunteerNotFoundException if Volunteer with id not found
     */
    public Volunteer deleteVolunteer(Long id) {
        Volunteer temp = volunteerRepository.findById(id).orElseThrow(() -> new VolunteerNotFoundException("Volunteer not found"));
        volunteerRepository.deleteById(id);
        return temp;
    }

    /**
     * find volunteer if he exists by his ID
     * Use method Volunteer repository {@link VolunteerRepository#existsById(Object)}
     *
     * @return boolean
     */
    public boolean isExistsVolunteer(long userChatId) {
        return volunteerRepository.existsByChatId(userChatId);
    }
}
