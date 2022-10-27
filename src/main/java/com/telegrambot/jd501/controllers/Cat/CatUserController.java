package com.telegrambot.jd501.controllers.Cat;



import com.telegrambot.jd501.Exceptions.UserNotFoundException;
import com.telegrambot.jd501.model.cat.CatUser;
import com.telegrambot.jd501.repository.Cat.CatUserRepository;
import com.telegrambot.jd501.service.CatService.CatUserService;
import com.telegrambot.jd501.service.TelegramBot;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PUT;
import java.util.Collection;

/**
 * class for work with CatUser
 * have CRUD operation
 */
@RestController
@RequestMapping("/cat/user")
public class CatUserController {
    private final CatUserService catUserService;

    public CatUserController(CatUserService catUserService) {
        this.catUserService = catUserService;
    }

    /**
     * get All CatUser-s from DataBase
     * Use method of CatUser servise {@link CatUserService#getAllUsers()} (Collection<CatUser>)}
     *
     * @return collection of CatUser
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Show all CatUser",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Collection.class)
                    )
            )
    })
    @GetMapping
    public Collection<CatUser> getAllUsers() {
        return catUserService.getAllUsers();
    }

    /**
     * add new CatUser in DataBase
     *
     * @param catUser Use method of Servise {@link CatUserService#createUser(CatUser)}
     * @return CatUser
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Create new CatUser",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatUser.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<CatUser> createUser(@RequestBody CatUser catUser) {
        return ResponseEntity.ok(catUserService.createUser(catUser));
    }

    /**
     * change CatUser in DataBase
     * Use method of Servise {@link CatUserService#updateUser(CatUser)}
     *
     * @param catUser
     * @return CatUser
     * @throws com.telegrambot.jd501.Exceptions.UserNotFoundException if CatUser with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Change CatUser By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatUser.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "CatUser not found"
            )
    })
    @PutMapping
    public ResponseEntity<CatUser> updateUser(@RequestBody CatUser catUser) {
        return ResponseEntity.ok(catUserService.updateUser(catUser));
    }

    /**
     * find CatUser by id and change  status of The Adopter,
     * add adopted Cat, Date of adoption, and set test day at 30 days
     * Use method User repository {@link }
     *
     * @param userId - catUser id for fing catUser in repository,
     * @param petId  - pet id for fing user in repository,
     * @return Changed User
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "find cat user by id and change him adoption status, add adoption Pet, adoption Date, and set test day at 30 days"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "When User or Cat not found"
            )
    })
    @PutMapping("/adoption/{userId}/{petId}")
    public CatUser changeStatusOfTheAdopter(@PathVariable Long userId, @PathVariable Long petId) {
        return catUserService.changeStatusOfTheAdopter(userId, petId);
    }

    /**
     * find CatUser by id and change amount of probation period
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
    public CatUser probationPeriodExtension(@PathVariable Long id, @PathVariable Integer days) {
        return catUserService.probationPeriodExtension(id, days);
    }


    /**
     * delete CatUser from DataBase by id
     * Use method of Servise {@link CatUserService#deleteUser(Long)}
     *
     * @param id
     * @return Deleted CatUser
     * @throws com.telegrambot.jd501.Exceptions.UserNotFoundException if CatUser with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete CatUser By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatUser.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @DeleteMapping("{id}")
    public ResponseEntity<CatUser> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(catUserService.deleteUser(id));
    }
    /**
     * Use catUserService to Sent custom message to Cat User with chat Id.
     *
     * Use method CatUserService {@link CatUserService#sendMessageToUserWithChatId(Long, String)}
     * @param chatId
     * @param message
     * @return String that a message has been sent to the user
     * @throws UserNotFoundException when user with chat id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Sent message to cat user",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatUser.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @PutMapping("sent_message_to_catUser/{chatId}/{message}")
    public String sendMessageToUserWithChatId(@PathVariable Long chatId, @PathVariable String message){
        return catUserService.sendMessageToUserWithChatId(chatId, message);
    }
    /**
     *
     * finds a user by chat id. changes him status. and sends him a message that he has passed the trial period
     *
     * Use method CatUserService {@link CatUserService#changeStatusUserPassedProbationPeriod(Long)}
     * @param chatId
     * @return CatUser
     * @throws UserNotFoundException when user with chatId not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "user passed the trial period",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatUser.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @PutMapping("user_passed_probation_period/{chatId}")
    public CatUser changeStatusUserPassedProbationPeriod(@PathVariable Long chatId){
       return catUserService.changeStatusUserPassedProbationPeriod(chatId);
    }
    /**
     *
     * finds a user by chat id. changes him status. and sends him a message that he has not passed the trial period
     *
     * Use method CatUserService {@link CatUserService#changeStatusUserNotPassedProbationPeriod(Long)}
     * @param chatId
     * @return CatUser
     * @throws UserNotFoundException when user with chatId not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = " user not passed probation period",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatUser.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @PutMapping("user_not_passed_probation_period/{chatId}")
    public CatUser changeStatusUserNotPassedProbationPeriod(@PathVariable Long chatId){
        return catUserService.changeStatusUserPassedProbationPeriod(chatId);
    }
}
