package com.telegrambot.jd501.controllers;
import com.telegrambot.jd501.model.Volunteer;
import com.telegrambot.jd501.service.VolunteerService;
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
 * have CRUD operation
 */
@RestController
@RequestMapping("/volunteer")
public class VolunteerController {
    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    /**
     * get All Volunteer from DataBase
     * Use method of servise {@link VolunteerService#getAllVolunteer()} (Collection< Volunteer >)}
     *
     * @return collection of Volunteer
     */
    @ApiResponses({
            @ApiResponse(
             responseCode = "200",
                    description = "Show all Volunteer",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Collection.class)
                    )
            )
    })
    @GetMapping
    public Collection <Volunteer> getAllVolunteer() {
        return volunteerService.getAllVolunteer();
    }

    /**
     * add new Volunteer in DataBase
     *
     * @param volunteer Use method of Servise {@link VolunteerService#createVolunteer(Volunteer)}}
     * @return Volunteer
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Create new Volunteer",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Volunteer.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<Volunteer> createVolunteer(@RequestBody Volunteer volunteer) {
        return ResponseEntity.ok(volunteerService.createVolunteer(volunteer));
    }

    /**
     * change Volunteer in DataBase
     * Use method of Servise {@link VolunteerService#updateVolunteer(Volunteer)}
     *
     * @param volunteer
     * @return Volunteer
     * @throws com.telegrambot.jd501.Exceptions.VolunteerNotFoundException if Volunteer with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Change Volunteer By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Volunteer.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Volunteer not found"
            )
    })
    @PutMapping
    public ResponseEntity<Volunteer> updateVolunteer(@RequestBody Volunteer volunteer) {
        return ResponseEntity.ok(volunteerService.updateVolunteer(volunteer));
    }

    /**
     * delete Volunteer from DataBase by id
     * Use method of Servise {@link VolunteerService#deleteVolunteer(Long id)}
     *
     * @param id
     * @return Deleted Volunteer
     * @throws com.telegrambot.jd501.Exceptions.VolunteerNotFoundException if Volunteer with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete Volunteer By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Volunteer.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Volunteer not found"
            )
    })
    @DeleteMapping("{id}")
    ResponseEntity<Volunteer> deleteVolunteer(@PathVariable Long id) {
        return ResponseEntity.ok(volunteerService.deleteVolunteer(id));
    }
}
