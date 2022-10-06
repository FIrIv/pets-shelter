package com.telegrambot.jd501.controllers;

import com.telegrambot.jd501.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * class for work with User
 * have CRUD operation
 */
@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * get All User from DataBase
     * Use method of user servise {@link UserService#getAllUsers(Collection<User>)}
     *
     * @return collection of Users
     */
    @GetMapping
    public Collection<User> getAllUsers() {
        return userService.getAllUser();
    }

    /**
     * add new User in DataBase
     *
     * @param user Use method of Servise {@link userService#createUser(User)}
     * @return User
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    /**
     * change User in DataBase
     * Use method of Servise {@link userService#updateUser(User)}
     *
     * @param user
     * @return User
     * @throws UserNotFoundException if User with id not found
     */
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    /**
     * delete User from DataBase by id
     * Use method of Servise {@link userService#deleteUser(User)}
     *
     * @param id
     * @return Deleted User
     * @throws UserNotFoundException if User with id not found
     */
    @DeleteMapping("{id}")
    ResponseEntity<User> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

}
