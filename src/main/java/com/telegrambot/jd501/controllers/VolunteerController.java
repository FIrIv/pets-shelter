package com.telegrambot.jd501.controllers;

import com.telegrambot.jd501.model.Volunteer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * class for work with Volunteer
 * have CRUD operation
 */
@RestController
@RequestMapping("/volunteer")
public class VolunteerController {
    VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    /**
     * get All Volunteer from DataBase
     * Use method of servise {@link volunteerService#getAllVolunteer(Collection< Volunteer >)}
     *
     * @return collection of Volunteer
     */
    @GetMapping
    public Collection<Volunteer> getAllVolunteer() {
        return volunteerService.getAllVolunteer();
    }

    /**
     * add new Volunteer in DataBase
     *
     * @param volunteer Use method of Servise {@link volunteerService#createVolunteer(Volunteer)}
     * @return Volunteer
     */
    @PostMapping
    public ResponseEntity<Volunteer> createVolunteer(@RequestBody Volunteer volunteer) {
        return ResponseEntity.ok(volunteerService.createVolunteer(volunteer));
    }

    /**
     * change Volunteer in DataBase
     * Use method of Servise {@link volunteerService#updateVolunteer(Volunteer)}
     *
     * @param volunteer
     * @return Volunteer
     * @throws VolunteerNotFoundException if Volunteer with id not found
     */
    @PutMapping
    public ResponseEntity<Volunteer> updateVolunteer(@RequestBody Volunteer volunteer) {
        return ResponseEntity.ok(volunteerService.updateVolunteer(volunteer));
    }

    /**
     * delete Volunteer from DataBase by id
     * Use method of Servise {@link volunteerService#deleteVolunteer(Volunteer)}
     *
     * @param id
     * @return Deleted Volunteer
     * @throws VolunteerNotFoundException if Volunteer with id not found
     */
    @DeleteMapping("{id}")
    ResponseEntity<Volunteer> deleteVolunteer(@PathVariable Long id) {
        return ResponseEntity.ok(volunteerService.deleteVolunteer(id));
    }
}
