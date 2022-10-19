package com.telegrambot.jd501.service.DogService;

import com.telegrambot.jd501.Exceptions.PetNotFoundException;

import com.telegrambot.jd501.model.dog.Dog;

import com.telegrambot.jd501.repository.Dog.DogRepository;
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
    public Collection<Dog> getAllDog() {
        return dogRepository.findAll();
    }
    /**
     * add new Dog in DataBase
     *
     * @param dog
     * Use  method Dog repository {@link DogRepository#save(Object)} (Dog)}
     * @return Dog
     */
    public Dog createDog(Dog dog) {
        return dogRepository.save(dog);
    }
    /**
     * change Dog in DataBase
     * Use  method Dog repository {@link DogRepository#save(Object)} (Dog)}
     *
     * @param dog
     * @return Dog
     * @throws com.telegrambot.jd501.Exceptions.PetNotFoundException if Dog with id not found
     */
    public Dog updateDog(Dog dog) {
        dogRepository.findById(dog.getId()).orElseThrow(() -> new PetNotFoundException("Dog Not Found"));
        return dogRepository.save(dog);
    }
    /**
     * delete Dog from DataBase by id
     * Use  method Dog repository {@link DogRepository#deleteById(Object)} } (Long id)}
     *
     * @param id
     * @return Deleted Dog
     * @throws com.telegrambot.jd501.Exceptions.PetNotFoundException if Dog with id not found
     */
    public Dog deleteDog(Long id) {
        Dog temp = dogRepository.findById(id).orElseThrow(() -> new PetNotFoundException("Dog not found"));
        dogRepository.deleteById(id);
        return temp;
    }
}
