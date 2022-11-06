package com.telegrambot.jd501.service.dog_service;

import com.telegrambot.jd501.exceptions.PetNotFoundException;

import com.telegrambot.jd501.model.dog.Dog;

import com.telegrambot.jd501.repository.dog.DogRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DogService {
    private final DogRepository dogRepository;

    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    /**
     * get All Dog from DataBase
     * Use method of Dog repository {@link DogRepository#findAll()} ()} (Collection< Dog >)}
     *
     * @return collection of Dog
     */
    public Collection<Dog> getAllPets() {
        return dogRepository.findAll();
    }
    /**
     * add new Dog in DataBase
     *
     * @param dog
     * Use  method Dog repository {@link DogRepository#save(Object)} (Dog)}
     * @return Dog
     */
    public Dog createPet(Dog dog) {
        return dogRepository.save(dog);
    }
    /**
     * change Dog in DataBase
     * Use  method Dog repository {@link DogRepository#save(Object)} (Dog)}
     *
     * @param dog (object)
     * @return Dog
     * @throws com.telegrambot.jd501.exceptions.PetNotFoundException if Dog with id not found
     */
    public Dog updatePet(Dog dog) {
        dogRepository.findById(dog.getId()).orElseThrow(() -> new PetNotFoundException("Dog Not Found"));
        return dogRepository.save(dog);
    }
    /**
     * delete Dog from DataBase by id
     * Use  method Dog repository {@link DogRepository#deleteById(Object)} } (Long id)}
     *
     * @param id of Dog
     * @return Deleted Dog
     * @throws com.telegrambot.jd501.exceptions.PetNotFoundException if Dog with id not found
     */
    public Dog deletePet(Long id) {
        Dog temp = dogRepository.findById(id).orElseThrow(() -> new PetNotFoundException("Dog not found"));
        dogRepository.deleteById(id);
        return temp;
    }
}
