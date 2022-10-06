package com.telegrambot.jd501.controllers;
import com.telegrambot.jd501.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public Collection<User> getAllUsers () {
       return userService.getAllUser();
    }
    @PostMapping
    public ResponseEntity <User> createUser(@RequestBody User user){
    return ResponseEntity.ok(userService.createUser(user));
    }
    @PutMapping
    public ResponseEntity <User> updateUser(@RequestBody User user){
        return ResponseEntity.ok(userService.updateUser(user));
    }
    @DeleteMapping ("{id}") ResponseEntity <User> deleteUser (@PathVariable Long id){
       return ResponseEntity.ok(userService.deleteUser(id));
    }

}
