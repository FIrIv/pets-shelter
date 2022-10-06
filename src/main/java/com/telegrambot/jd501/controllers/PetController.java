package com.telegrambot.jd501.controllers;

import com.telegrambot.jd501.model.Pet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
@RestController
@RequestMapping("/pet")
public class PetController {
    @GetMapping
    public Collection<Pet> getAllPet () {
        return petServise.getAllPet();
    }
    @PostMapping
    public ResponseEntity<Pet> createPet(@RequestBody Pet pet){
        return ResponseEntity.ok(petServise.createPet(pet));
    }
    @PutMapping
    public ResponseEntity <Pet> updatePet(@RequestBody Pet pet){
        return ResponseEntity.ok(petServise.updatePet(pet));
    }
    @DeleteMapping("{id}")
    ResponseEntity <Pet> deletePet (@PathVariable Long id){
        return ResponseEntity.ok(petServise.deletePet(id));
    }
}
