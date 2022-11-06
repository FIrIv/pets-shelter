package com.telegrambot.jd501.controllers.dog;

import com.telegrambot.jd501.model.dog.DogVolunteer;
import com.telegrambot.jd501.service.dog_service.DogVolunteerService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * class for work with Volunteer
 * has CRUD operation
 */
@RestController
@RequestMapping("/dog/volunteer")
public class DogVolunteerController {
    private final DogVolunteerService dogVolunteerService;

    public DogVolunteerController(DogVolunteerService dogVolunteerService) {
        this.dogVolunteerService = dogVolunteerService;
    }

    /**
     * get All DogVolunteer-s from DataBase
     * Use method of service {@link DogVolunteerService#getAllVolunteers()} (Collection< DogVolunteer >)}
     *
     * @return collection of DogVolunteer
     */
    @ApiResponses({
            @ApiResponse(
             responseCode = "200",
                    description = "Show all DogVolunteer",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Collection.class)
                    )
            )
    })
    @GetMapping
    public Collection <DogVolunteer> getAllVolunteers() {
        return dogVolunteerService.getAllVolunteers();
    }

    /**
     * add new DogVolunteer in DataBase
     *
     * @param dogVolunteer Use method of Service {@link DogVolunteerService#createVolunteer(DogVolunteer)}}
     * @return DogVolunteer
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Create new Volunteer",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DogVolunteer.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<DogVolunteer> createVolunteer(@RequestBody DogVolunteer dogVolunteer) {
        return ResponseEntity.ok(dogVolunteerService.createVolunteer(dogVolunteer));
    }

    /**
     * change DogVolunteer in DataBase
     * Use method of Service {@link DogVolunteerService#updateVolunteer(DogVolunteer)}
     *
     * @param dogVolunteer (object)
     * @return DogVolunteer
     * @throws com.telegrambot.jd501.exceptions.VolunteerNotFoundException if DogVolunteer with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Change DogVolunteer By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DogVolunteer.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "DogVolunteer not found"
            )
    })
    @PutMapping
    public ResponseEntity<DogVolunteer> updateVolunteer(@RequestBody DogVolunteer dogVolunteer) {
        return ResponseEntity.ok(dogVolunteerService.updateVolunteer(dogVolunteer));
    }

    /**
     * delete Volunteer from DataBase by chatId
     * Use method of Service {@link DogVolunteerService#deleteVolunteer(Long chatId)}
     *
     * @param chatId of DogVolunteer
     * @return Deleted DogVolunteer
     * @throws com.telegrambot.jd501.exceptions.VolunteerNotFoundException if DogVolunteer with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete DogVolunteer By chatId",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DogVolunteer.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "DogVolunteer not found"
            )
    })
    @DeleteMapping("{chatId}")
    public ResponseEntity<DogVolunteer> deleteVolunteer(@PathVariable Long chatId) {
        return ResponseEntity.ok(dogVolunteerService.deleteVolunteer(chatId));
    }
}
