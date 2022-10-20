package com.telegrambot.jd501.controllers.Dog;


import com.telegrambot.jd501.model.dog.DogUser;
import com.telegrambot.jd501.service.DogService.DogUserService;
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
@RequestMapping("/dog/user")
public class DogUserController {
    private final DogUserService dogUserService;

    public DogUserController(DogUserService dogUserService) {
        this.dogUserService = dogUserService;
    }

    /**
     * get All DogUser from DataBase
     * Use method of DogUser servise {@link DogUserService#getAllDogUser()} (Collection<DogUser>)}
     *
     * @return collection of DogUser
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Show all Dog DogUser",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Collection.class)
                    )
            )
    })
    @GetMapping
    public Collection<DogUser> getAllDogUser() {
        return dogUserService.getAllDogUser();
    }

    /**
     * add new DogUser in DataBase
     *
     * @param dogUser Use method of Servise {@link DogUserService#createDogUser(DogUser)}
     * @return DogUser
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Create new DogUser",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DogUser.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<DogUser> createUser(@RequestBody DogUser dogUser) {
        return ResponseEntity.ok(dogUserService.createDogUser(dogUser));
    }

    /**
     * change DogUser in DataBase
     * Use method of Servise {@link DogUserService#updateDogUser(DogUser)}
     *
     * @param dogUser
     * @return DogUser
     * @throws com.telegrambot.jd501.Exceptions.UserNotFoundException if DogUser with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Change DogUser By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DogUser.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "DogUser not found"
            )
    })
    @PutMapping
    public ResponseEntity<DogUser> updateUser(@RequestBody DogUser dogUser) {
        return ResponseEntity.ok(dogUserService.updateDogUser(dogUser));
    }

    /**
     * find user by id and change  status of The Adopter,
     * add adopted Pet, Date of adoption, and set test day at 30 days
     * Use method User repository {@link }
     *
     * @param dogUserId - dogUser id for fing dogUser in repository,
     * @param dogId  - pet id for fing user in repository,
     * @return Changed User
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "find user by id and change him adoption status, add adoption Pet, adoption Date, and set test day at 30 days"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "When User or Pet not found"
            )
    })
    @PutMapping("/adoption/{userId}/{petId}")
    public DogUser changeStatusOfTheAdopter(@PathVariable Long userId, @PathVariable Long petId) {
        return dogUserService.changeStatusOfTheAdopter(UserId, petId);
    }

    /**
     * find user by id and change amount of probation period
     *
     * @param id   - user id,
     * @param days - number of days to increase the term of the transfer
     * @return notification that probationary period has been extended (String)
     * or UserNotFoundException
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "find user by id and change amount of probation period"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "When User not found"
            )
    })
    @PutMapping("/change_period/{id}/{days}")
    public DogUser probationPeriodExtension(@PathVariable Long id, @PathVariable Integer days) {
        return dogUserService.probationPeriodExtension(id, days);
    }

    /**
     * delete DogUser from DataBase by id
     * Use method of Servise {@link }
     *
     * @param id
     * @return Deleted DogUser
     * @throws com.telegrambot.jd501.Exceptions.UserNotFoundException if DogUser with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete User By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DogUser.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @DeleteMapping("{id}")
    ResponseEntity<DogUser> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(dogUserService.deleteDogUser(id));
    }

}
