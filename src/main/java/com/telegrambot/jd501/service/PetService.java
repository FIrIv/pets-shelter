package com.telegrambot.jd501.service;

import com.telegrambot.jd501.Exceptions.PetNotFoundException;
import com.telegrambot.jd501.model.Pet;
import com.telegrambot.jd501.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PetService {
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    /**
     * get All Pet from DataBase
     * Use method of Pet repository {@link PetRepository#findAll()} ()} (Collection< Pet >)}
     *
     * @return collection of Pet
     */
    public Collection<Pet> getAllPet() {
        return petRepository.findAll();
    }
    /**
     * add new Pet in DataBase
     *
     * @param pet
     * Use  method InformationMessage repository {@link PetRepository#save(Object)} (Pet)}
     * @return Pet
     */
    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }
    /**
     * change Pet in DataBase
     * Use  method Pet repository {@link PetRepository#save(Object)} (Pet)}
     *
     * @param pet
     * @return Pet
     * @throws com.telegrambot.jd501.Exceptions.PetNotFoundException if Pet with id not found
     */
    public Pet updatePet(Pet pet) {
        petRepository.findById(pet.getId()).orElseThrow(() -> new PetNotFoundException("Pet Not Found"));
        return petRepository.save(pet);
    }
    /**
     * delete Pet from DataBase by id
     * Use  method Pet repository {@link PetRepository#deleteById(Object)} } (Long id)}
     *
     * @param id
     * @return Deleted Pet
     * @throws com.telegrambot.jd501.Exceptions.PetNotFoundException if Pet with id not found
     */
    public Pet deletePet(Long id) {
        Pet temp = petRepository.findById(id).orElseThrow(() -> new PetNotFoundException("Pet not found"));
        petRepository.deleteById(id);
        return temp;
    }
}
