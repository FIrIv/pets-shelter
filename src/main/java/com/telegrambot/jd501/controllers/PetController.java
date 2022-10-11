package com.telegrambot.jd501.controllers;


import com.telegrambot.jd501.model.Pet;
import com.telegrambot.jd501.service.PetService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * class for work with Pet
 * have CRUD operation
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    /**
     * get All Pet from DataBase
     * Use method of servise {@link PetService#getAllPet()} (Collection< Pet >)}
     *
     * @return collection of Pet
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Show all Pet",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Collection.class)
                    )
            )
    })
    @GetMapping
    public Collection<Pet> getAllPet() {
        return petService.getAllPet();
    }

    /**
     * add new Pet in DataBase
     *
     * @param pet Use method of Servise {@link PetService#createPet(Pet)}
     * @return Pet
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Create new Pet",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Pet.class)
                    )
            )
    })

    @PostMapping
    public ResponseEntity<Pet> createPet(@RequestBody Pet pet) {
        return ResponseEntity.ok(petService.createPet(pet));
    }

    /**
     * change Pet in DataBase
     * Use method of Servise {@link PetService#updatePet(Pet)}
     *
     * @param pet
     * @return Pet
     * @throws com.telegrambot.jd501.Exceptions.PetReportNotFoundException if InformationMessage with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Change Pet By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Pet.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pet not found"
            )
    })
    @PutMapping
    public ResponseEntity<Pet> updatePet(@RequestBody Pet pet) {
        return ResponseEntity.ok(petService.updatePet(pet));
    }

    /**
     * delete Pet from DataBase by id
     * Use method of Servise {@link PetService#deletePet(Long id)}}
     *
     * @param id
     * @return Deleted Pet
     * @throws com.telegrambot.jd501.Exceptions.PetReportNotFoundException if Pet with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete Pet By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Pet.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pet not found"
            )
    })
    @DeleteMapping("{id}")
    ResponseEntity<Pet> deletePet(@PathVariable Long id) {
        return ResponseEntity.ok(petService.deletePet(id));
    }
}
