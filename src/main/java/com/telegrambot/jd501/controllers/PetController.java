package com.telegrambot.jd501.controllers;

import com.telegrambot.jd501.model.Pet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/pet")
public class PetController {
    PetServise petServise;

    public PetController(PetServise petServise) {
        this.petServise = petServise;
    }

    // get All Pet from DataBase
    @GetMapping
    public Collection<Pet> getAllPet() {
        return petServise.getAllPet();
    }

    // add new Pet in DataBase
    @PostMapping
    public ResponseEntity<Pet> createPet(@RequestBody Pet pet) {
        return ResponseEntity.ok(petServise.createPet(pet));
    }

    // change Pet in DataBase
    @PutMapping
    public ResponseEntity<Pet> updatePet(@RequestBody Pet pet) {
        return ResponseEntity.ok(petServise.updatePet(pet));
    }

    // delete Pet from DataBase
    @DeleteMapping("{id}")
    ResponseEntity<Pet> deletePet(@PathVariable Long id) {
        return ResponseEntity.ok(petServise.deletePet(id));
    }
}
