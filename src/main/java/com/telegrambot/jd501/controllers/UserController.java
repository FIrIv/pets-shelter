package com.telegrambot.jd501.controllers;
import com.telegrambot.jd501.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping
    public Collection<User> getAllUsers () {
       return userServise.getAllUser();
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
       return ResponseEntity.ok(userServise.deleteUser(id));
    }

}
