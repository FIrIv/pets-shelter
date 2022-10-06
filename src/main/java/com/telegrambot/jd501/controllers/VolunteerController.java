package com.telegrambot.jd501.controllers;

import com.telegrambot.jd501.model.Volunteer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/volunteer")
public class VolunteerController {
    VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }
    @GetMapping
    public Collection<Volunteer> getAllVolunteer () {
        return volunteerService.getAllVolunteer();
    }
    @PostMapping
    public ResponseEntity<Volunteer> createVolunteer(@RequestBody Volunteer volunteer){
        return ResponseEntity.ok(volunteerService.createVolunteer(volunteer));
    }
    @PutMapping
    public ResponseEntity <Volunteer> updateVolunteer(@RequestBody Volunteer volunteer){
        return ResponseEntity.ok(volunteerService.updateVolunteer(volunteer));
    }
    @DeleteMapping("{id}")
    ResponseEntity <Volunteer> deleteVolunteer (@PathVariable Long id){
        return ResponseEntity.ok(volunteerService.deleteVolunteer(id));
    }
}
