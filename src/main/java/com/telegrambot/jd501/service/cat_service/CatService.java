package com.telegrambot.jd501.service.cat_service;

import com.telegrambot.jd501.exceptions.PetNotFoundException;


import com.telegrambot.jd501.model.cat.Cat;
import com.telegrambot.jd501.repository.cat.CatRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CatService {
    private final CatRepository catRepository;

    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    /**
     * get All Cat-s from DataBase
     * Use method of Cat repository {@link CatRepository#findAll()} ()} (Collection < Cat >)}
     *
     * @return collection of Cat
     */
    public Collection<Cat> getAllPets() {
        return catRepository.findAll();
    }
    /**
     * add new Cat in DataBase
     *
     * @param cat
     * Use  method Cat repository {@link CatRepository#save(Object)} (Cat)}
     * @return Cat
     */
    public Cat createPet(Cat cat) {
        return catRepository.save(cat);
    }
    /**
     * change Cat in DataBase
     * Use  method cat repository {@link CatRepository#save(Object)} (Cat)}
     *
     * @param cat
     * @return Cat
     * @throws PetNotFoundException if Cat with id not found
     */
    public Cat updatePet(Cat cat) {
        catRepository.findById(cat.getId()).orElseThrow(() -> new PetNotFoundException("Cat Not Found"));
        return catRepository.save(cat);
    }
    /**
     * delete Cat from DataBase by id
     * Use  method Cat repository {@link CatRepository#deleteById(Object)} } (Long id)}
     *
     * @param id
     * @return Deleted cat
     * @throws PetNotFoundException if Cat with id not found
     */
    public Cat deletePet(Long id) {
        Cat temp = catRepository.findById(id).orElseThrow(() -> new PetNotFoundException("Cat not found"));
        catRepository.deleteById(id);
        return temp;
    }
}
