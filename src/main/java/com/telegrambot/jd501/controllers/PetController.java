package com.telegrambot.jd501.controllers;

import com.telegrambot.jd501.model.InformationMessage;
import com.telegrambot.jd501.model.Pet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
/**
 * class for work with Pet
 * have CRUD operation
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    PetServise petServise;

    public PetController(PetServise petServise) {
        this.petServise = petServise;
    }

    /**
     * get All Pet from DataBase
     * Use method of servise {@link petServise#getAllPet(Collection< Pet >)}
     *
     * @return collection of Pet
     */
    @GetMapping
    public Collection<Pet> getAllPet() {
        return petServise.getAllPet();
    }

    /**
     * add new Pet in DataBase
     *
     * @param pet Use method of Servise {@link petServise#createPet(Pet)}
     * @return Pet
     */
    @PostMapping
    public ResponseEntity<Pet> createPet(@RequestBody Pet pet) {
        return ResponseEntity.ok(petServise.createPet(pet));
    }

    /**
     * change Pet in DataBase
     * Use method of Servise {@link petServise#updatePet(InformationMessage)}
     *
     * @param pet
     * @return Pet
     * @throws PetNotFoundException if InformationMessage with id not found
     */
    @PutMapping
    public ResponseEntity<Pet> updatePet(@RequestBody Pet pet) {
        return ResponseEntity.ok(petServise.updatePet(pet));
    }

    /**
     * delete Pet from DataBase by id
     * Use method of Servise {@link petServise#deletePet(InformationMessage)}
     *
     * @param id
     * @return Deleted Pet
     * @throws PetNotFoundException if Pet with id not found
     */
    @DeleteMapping("{id}")
    ResponseEntity<Pet> deletePet(@PathVariable Long id) {
        return ResponseEntity.ok(petServise.deletePet(id));
    }
}
