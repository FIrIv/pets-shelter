package com.telegrambot.jd501.controllers;

import com.telegrambot.jd501.model.Volunteer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/volunteer")
public class VolunteerController {

    @GetMapping
    public Collection<Volunteer> getAllVolunteer () {
        return volunteerServise.getAllVolunteer();
    }
    @PostMapping
    public ResponseEntity<Volunteer> createVolunteer(@RequestBody Volunteer volunteer){
        return ResponseEntity.ok(volunteerServise.createVolunteer(volunteer));
    }
    @PutMapping
    public ResponseEntity <Volunteer> updateVolunteer(@RequestBody Volunteer volunteer){
        return ResponseEntity.ok(volunteerServise.updateVolunteer(volunteer));
    }
    @DeleteMapping("{id}")
    ResponseEntity <Volunteer> deleteVolunteer (@PathVariable Long id){
        return ResponseEntity.ok(volunteerServise.deleteVolunteer(id));
    }
}
