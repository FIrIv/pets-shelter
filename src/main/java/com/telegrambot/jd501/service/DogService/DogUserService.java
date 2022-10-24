package com.telegrambot.jd501.service.DogService;

import com.telegrambot.jd501.Exceptions.PetNotFoundException;
import com.telegrambot.jd501.Exceptions.UserNotFoundException;
import com.telegrambot.jd501.model.dog.Dog;
import com.telegrambot.jd501.model.dog.DogUser;

import com.telegrambot.jd501.repository.Dog.DogRepository;
import com.telegrambot.jd501.repository.Dog.DogUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Service
public class DogUserService {
    private final DogUserRepository dogUserRepository;
    private final DogRepository dogRepository;

    public DogUserService(DogUserRepository dogUserRepository, DogRepository dogRepository) {
        this.dogUserRepository = dogUserRepository;
        this.dogRepository = dogRepository;
    }

    /**
     * get All DogUser from DataBase
     * Use method of DogUser repository {@link DogUserRepository#findAll()} ()} (Collection< DogUser >)}
     *
     * @return collection of DogUser
     */
    public Collection <DogUser> getAllUsers() {
        return dogUserRepository.findAll();
    }

    /**
     * add new User in DataBase
     *
     * @param dogUser Use  method InformationMessage repository {@link DogUserRepository#save(Object)} (User)}
     * @return InformationMessage
     */
    public DogUser createUser(DogUser dogUser) {
        dogUser.setStartDate(null);
        dogUser.setFinishDate(null);
        dogUser.setDog(null);
        dogUser.setAdopted(false);
        return dogUserRepository.save(dogUser);
    }

    /**
     * change DogUser in DataBase
     * Use  method User repository {@link DogUserRepository#save(Object)} (DogUser)}
     *
     * @param dogUser
     * @return DogUser
     * @throws com.telegrambot.jd501.Exceptions.UserNotFoundException if DogUser with id not found
     */
    public DogUser updateUser(DogUser dogUser) {
        dogUserRepository.findById(dogUser.getId()).orElseThrow(() -> new UserNotFoundException("DogUser not found"));
        return dogUserRepository.save(dogUser);
    }

    /**
     * delete DogUser from DataBase by id
     * Use  method DogUser repository {@link DogUserRepository#deleteById(Object)} } (Long id)}
     *
     * @param id
     * @return Deleted DogUser
     * @throws com.telegrambot.jd501.Exceptions.UserNotFoundException if DogUser with id not found
     */
    public DogUser deleteUser(Long id) {
        DogUser temp = dogUserRepository.findById(id).orElseThrow(() -> new UserNotFoundException("DogUser not found"));
        dogUserRepository.deleteById(id);
        return temp;
    }

    /**
     * find DogUser by id and change amount of probation period
     * Use method DogUser repository {@link DogUserRepository#findById(Object)}
     * Use  method DogUser repository {@link DogUserRepository#save(Object)} }
     *
     * @param id   - DogUser id for fing DogUser in repository,
     * @param days - number of days to increase the term of the transfer
     * @return notification that probationary period has been extended (String)
     */
    public DogUser probationPeriodExtension(Long id, Integer days) {
        DogUser temp = dogUserRepository.findById(id).orElseThrow(() -> new UserNotFoundException("DogUser not found"));
        temp.setFinishDate(temp.getFinishDate().plusDays(days));
        dogUserRepository.save(temp);
        return temp;
    }

    /**
     * find user by id and change  status of The Adopter, add adopted Pet, Date of adoption, and set test day by 30
     * Use method User repository {@link DogUserRepository#findById(Object)}
     * Use  method User repository {@link DogUserRepository#save(Object)} }
     * Use  method Pet repository {@link DogRepository#findById(Object)} }
     *
     * @param dogUserId - user id for fing user in repository,
     * @param dogId  - pet id for fing user in repository,
     * @return Changed User
     */
    public DogUser changeStatusOfTheAdopter(Long dogUserId, Long dogId) {
        DogUser userTemp = dogUserRepository.findById(dogUserId).orElseThrow(() -> new UserNotFoundException("DogUser not found"));
        Dog petTemp = dogRepository.findById(dogId).orElseThrow(() -> new PetNotFoundException("Dog not found"));
        userTemp.setAdopted(true);
        userTemp.setDog(petTemp);
        userTemp.setStartDate(LocalDate.now());
        userTemp.setFinishDate(LocalDate.now().plusDays(30));
        return dogUserRepository.save(userTemp);
    }

    /**
     * find DogUser with status "adopted = true"
     * Use method DogUser repository {@link DogUserRepository#findDogUsersByIsAdoptedIsTrue()} (long)} ()}
     *
     * @return List<DogUser>
     */
    public List<DogUser> findUsersByAdoptedIsTrue() {
        return dogUserRepository.findDogUsersByIsAdoptedIsTrue();
    }

    /**
     * find DogUser if he exists by his ID
     * Use method DogUser repository {@link DogUserRepository#existsById(Object)}
     *
     * @return boolean
     */
    public boolean isExistsUser(long dogUserChatId) {
        return dogUserRepository.existsByChatId(dogUserChatId);
    }

    /**
     * find user with by ID
     * Use method User repository {@link DogUserRepository#findById(Object)}
     *
     * @return User
     */
    public DogUser findUserByChatId(long userChatId) {
        return dogUserRepository.findDogUserByChatId(userChatId);
    }


}
