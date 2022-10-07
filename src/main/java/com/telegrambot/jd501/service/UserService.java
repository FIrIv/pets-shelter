package com.telegrambot.jd501.service;

import com.telegrambot.jd501.Exceptions.UserNotFoundException;
import com.telegrambot.jd501.model.User;
import com.telegrambot.jd501.repository.InformationMessageRepository;
import com.telegrambot.jd501.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    /**
     * get All User from DataBase
     * Use method of User repository {@link UserRepository#findAll()} ()} (Collection< User >)}
     *
     * @return collection of User
     */
    public Collection<User> getAllUser() {
        return userRepository.findAll();
    }
    /**
     * add new InformationMessage in DataBase
     *
     * @param user
     * Use  method InformationMessage repository {@link UserRepository#save(Object)} (User)}
     * @return InformationMessage
     */
    public User createUser(User user) {
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
}
