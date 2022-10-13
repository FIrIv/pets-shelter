package com.telegrambot.jd501.service;

import com.telegrambot.jd501.Exceptions.PetNotFoundException;
import com.telegrambot.jd501.Exceptions.UserNotFoundException;
import com.telegrambot.jd501.model.Pet;
import com.telegrambot.jd501.model.User;
import com.telegrambot.jd501.repository.PetRepository;
import com.telegrambot.jd501.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    public UserService(UserRepository userRepository, PetRepository petRepository) {
        this.userRepository = userRepository;
        this.petRepository = petRepository;
    }

    /**
     * get All User from DataBase
     * Use method of User repository {@link UserRepository#findAll()} ()} (Collection< User >)}
     *
     * @return collection of User
     */
    public Collection <User> getAllUser() {
        return userRepository.findAll();
    }

    /**
     * add new User in DataBase
     *
     * @param user Use  method InformationMessage repository {@link UserRepository#save(Object)} (User)}
     * @return InformationMessage
     */
    public User createUser(User user) {
        user.setStartDate(null);
        user.setFinishDate(null);
        user.setPet(null);
        user.setAdopted(false);
        return userRepository.save(user);
    }

    /**
     * change User in DataBase
     * Use  method User repository {@link UserRepository#save(Object)} (User)}
     *
     * @param user
     * @return User
     * @throws com.telegrambot.jd501.Exceptions.InformationMessageNotFoundException if User with id not found
     */
    public User updateUser(User user) {
        userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        return userRepository.save(user);
    }

    /**
     * delete user from DataBase by id
     * Use  method User repository {@link UserRepository#deleteById(Object)} } (Long id)}
     *
     * @param id
     * @return Deleted User
     * @throws com.telegrambot.jd501.Exceptions.UserNotFoundException if User with id not found
     */
    public User deleteUser(Long id) {
        User temp = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.deleteById(id);
        return temp;
    }

    /**
     * find user by id and change amount of probation period
     * Use method User repository {@link UserRepository#findById(Object)}
     * Use  method User repository {@link UserRepository#save(Object)} }
     *
     * @param id   - user id for fing user in repository,
     * @param days - number of days to increase the term of the transfer
     * @return notification that probationary period has been extended (String)
     */
    public String probationPeriodExtension(Long id, Integer days) {
        User temp = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        temp.setFinishDate(temp.getFinishDate().plusDays(days));
        userRepository.save(temp);
        return "Probationary period" + temp.getName() + "increased by" + days + "days";
    }

    /**
     * find user by id and change  status of The Adopter, add adopted Pet, Date of adoption, and set test day by 30
     * Use method User repository {@link UserRepository#findById(Object)}
     * Use  method User repository {@link UserRepository#save(Object)} }
     * Use  method Pet repository {@link PetRepository#findById(Object)} }
     *
     * @param userId - user id for fing user in repository,
     * @param petId  - pet id for fing user in repository,
     * @return Changed User
     */
    public User changeStatusOfTheAdopter(Long userId, Long petId) {
        User userTemp = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Pet petTemp = petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException("Pet not found"));
        userTemp.setAdopted(true);
        userTemp.setPet(petTemp);
        userTemp.setStartDate(LocalDate.now());
        userTemp.setFinishDate(LocalDate.now().plusDays(30));
        return userRepository.save(userTemp);
    }

    /**
     * find users with status "adopted = true"
     * Use method User repository {@link UserRepository#findUsersByAdoptedIsTrue()}
     *
     * @return List<User>
     */
    public List<User> findUsersByAdoptedIsTrue () {
        return userRepository.findUsersByAdoptedIsTrue();
    }


}
