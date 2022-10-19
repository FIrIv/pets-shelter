package com.telegrambot.jd501.controllers.Cat;

import com.telegrambot.jd501.model.cat.CatVolunteer;
import com.telegrambot.jd501.model.dog.DogVolunteer;
import com.telegrambot.jd501.service.CatService.CatVolunteerService;
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
@RequestMapping("/cat/volunteer")
public class CatVolunteerController {
    private final CatVolunteerService catVolunteerService;

    public CatVolunteerController(CatVolunteerService catVolunteerService) {
        this.catVolunteerService = catVolunteerService;
    }

    /**
     * get All CatVolunteer from DataBase
     * Use method of servise {@link CatVolunteerService#getAllCatVolunteer()} (Collection< CatVolunteer >)}
     *
     * @return collection of CatVolunteer
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
    public Collection <CatVolunteer> getAllCatVolunteer() {
        return catVolunteerService.getAllCatVolunteer();
    }

    /**
     * add new CatVolunteer in DataBase
     *
     * @param catVolunteer Use method of Servise {@link CatVolunteerService#createCatVolunteer(CatVolunteer)}}
     * @return CatVolunteer
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
    public ResponseEntity<CatVolunteer> createVolunteer(@RequestBody CatVolunteer catVolunteer) {
        return ResponseEntity.ok(catVolunteerService.createCatVolunteer(catVolunteer));
    }

    /**
     * change CatVolunteer in DataBase
     * Use method of Servise {@link CatVolunteerService#updateCatVolunteer(CatVolunteer)}
     *
     * @param catVolunteer
     * @return CatVolunteer
     * @throws com.telegrambot.jd501.Exceptions.VolunteerNotFoundException if CatVolunteer with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Change CatVolunteer By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatVolunteer.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "CatVolunteer not found"
            )
    })
    @PutMapping
    public ResponseEntity<CatVolunteer> updateVolunteer(@RequestBody CatVolunteer catVolunteer) {
        return ResponseEntity.ok(catVolunteerService.updateCatVolunteer(catVolunteer));
    }

    /**
     * delete CatVolunteer from DataBase by id
     * Use method of Servise {@link CatVolunteerService#deleteCatVolunteer(Long id)}
     *
     * @param id
     * @return Deleted CatVolunteer
     * @throws com.telegrambot.jd501.Exceptions.VolunteerNotFoundException if CatVolunteer with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete CatVolunteer By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatVolunteer.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "CatVolunteer not found"
            )
    })
    @DeleteMapping("{id}")
    ResponseEntity<CatVolunteer> deleteCatVolunteer(@PathVariable Long id) {
        return ResponseEntity.ok(catVolunteerService.deleteCatVolunteer(id));
    }
}
