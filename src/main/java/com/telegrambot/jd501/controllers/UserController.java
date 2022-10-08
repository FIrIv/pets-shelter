package com.telegrambot.jd501.controllers;
import com.telegrambot.jd501.model.User;
import com.telegrambot.jd501.service.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
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
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * get All User from DataBase
     * Use method of user servise {@link UserService#getAllUser()} (Collection<User>)}
     *
     * @return collection of Users
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Show all User",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Collection.class)
                    )
            )
    })
    @GetMapping
    public Collection<User> getAllUsers() {
        return userService.getAllUser();
    }

    /**
     * add new User in DataBase
     *
     * @param user Use method of Servise {@link UserService#createUser(User)}
     * @return User
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Create new User",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    /**
     * change User in DataBase
     * Use method of Servise {@link UserService#updateUser(User)}
     *
     * @param user
     * @return User
     * @throws com.telegrambot.jd501.Exceptions.UserNotFoundException if User with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Change User By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    /**
     * delete User from DataBase by id
     * Use method of Servise {@link UserService#deleteUser(Long id)}
     *
     * @param id
     * @return Deleted User
     * @throws com.telegrambot.jd501.Exceptions.UserNotFoundException if User with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete User By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @DeleteMapping("{id}")
    ResponseEntity<User> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

}
