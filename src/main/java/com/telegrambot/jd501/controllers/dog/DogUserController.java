package com.telegrambot.jd501.controllers.dog;


import com.telegrambot.jd501.exceptions.UserNotFoundException;
import com.telegrambot.jd501.model.dog.DogUser;
import com.telegrambot.jd501.service.dog_service.DogUserService;
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
 * has CRUD operation
 */
@RestController
@RequestMapping("/dog/user")
public class DogUserController {
    private final DogUserService dogUserService;

    public DogUserController(DogUserService dogUserService) {
        this.dogUserService = dogUserService;
    }

    /**
     * get All DogUser-s from DataBase
     * Use method of DogUser service {@link DogUserService#getAllUsers()} (Collection<DogUser>)}
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
    public Collection<DogUser> getAllUsers() {
        return dogUserService.getAllUsers();
    }

    /**
     * add new DogUser in DataBase
     *
     * @param dogUser Use method of Service {@link DogUserService#createUser(DogUser)}
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
        return ResponseEntity.ok(dogUserService.createUser(dogUser));
    }

    /**
     * change DogUser in DataBase
     * Use method of Service {@link DogUserService#updateUser(DogUser)}
     *
     * @param dogUser (object)
     * @return DogUser
     * @throws com.telegrambot.jd501.exceptions.UserNotFoundException if DogUser with id not found
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
        return ResponseEntity.ok(dogUserService.updateUser(dogUser));
    }

    /**
     * find DogUser by id and change  status of The Adopter,
     * add adopted Pet, Date of adoption, and set test day at 30 days
     * Use method User repository {@link }
     *
     * @param userChatId - dogUser ChatId for find dogUser in repository,
     * @param petId  - pet id for find user in repository,
     * @return Changed User
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "find user by ChatId and change him adoption status, add adoption Pet, adoption Date, and set test day at 30 days"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "When User or Pet not found"
            )
    })
    @PutMapping("/adoption/{userChatId}/{petId}")
    public DogUser changeStatusOfTheAdopter(@PathVariable Long userChatId, @PathVariable Long petId) {
        return dogUserService.changeStatusOfTheAdopter(userChatId, petId);
    }

    /**
     * find DogUser by ChatId and change amount of probation period
     *
     * @param userChatId   - user ChatId,
     * @param days - number of days to increase the term of the transfer
     * @return notification that probationary period has been extended (String)
     * @throws  UserNotFoundException
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "find user by ChatId and change amount of probation period"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "When User not found"
            )
    })
    @PutMapping("/change_period/{userChatId}/{days}")
    public DogUser probationPeriodExtension(@PathVariable Long userChatId, @PathVariable Integer days) {
        return dogUserService.probationPeriodExtension(userChatId, days);
    }

    /**
     * delete DogUser from DataBase by chatId
     * Use method of DogUserService {@link DogUserService#deleteUser(Long)}
     *
     * @param chatId of DogUser
     * @return Deleted DogUser
     * @throws com.telegrambot.jd501.exceptions.UserNotFoundException if DogUser with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete User By chatId",
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
    @DeleteMapping("{chatId}")
    public ResponseEntity<DogUser> deleteUser(@PathVariable Long chatId) {
        return ResponseEntity.ok(dogUserService.deleteUser(chatId));
    }
    /**
     * Use dogUserService to Sent custom message to Dog User with chat Id.
     * Use method DogUserService {@link DogUserService#sendMessageToUserWithChatId(Long, String)}
     * @param chatId person to send
     * @param message to send
     * @return String that a message has been sent to the user
     * @throws UserNotFoundException when user with chat id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Sent message to dog user",
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
    @PutMapping("/send_message_to_dogUser/{chatId}/{message}")
    public String sendMessageToUserWithChatId(@PathVariable Long chatId, @PathVariable String message){
        return dogUserService.sendMessageToUserWithChatId(chatId, message);
    }
    /**
     *
     * finds a user by chat id. changes him status. and sends him a message that he has passed the trial period
     * Use method DogUserService {@link DogUserService#changeStatusUserPassedProbationPeriod(Long)}
     * @param chatId of DogUser
     * @return DogUser
     * @throws UserNotFoundException when user with chatId not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "user passed the trial period",
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
    @PutMapping("/user_passed_probation_period/{chatId}")
    public DogUser changeStatusUserPassedProbationPeriod(@PathVariable Long chatId){
        return dogUserService.changeStatusUserPassedProbationPeriod(chatId);
    }
    /**
     *
     * finds a user by chat id. changes him status. and sends him a message that he has not passed the trial period
     * Use method DogUserService {@link DogUserService#changeStatusUserNotPassedProbationPeriod(Long)}
     * @param chatId of DogUser
     * @return DogUser
     * @throws UserNotFoundException when user with chatId not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = " user not passed probation period",
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
    @PutMapping("/user_not_passed_probation_period/{chatId}")
    public DogUser changeStatusUserNotPassedProbationPeriod(@PathVariable Long chatId){
        return dogUserService.changeStatusUserNotPassedProbationPeriod(chatId);
    }
}
