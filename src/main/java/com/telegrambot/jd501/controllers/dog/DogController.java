package com.telegrambot.jd501.controllers.dog;

import com.telegrambot.jd501.model.dog.Dog;
import com.telegrambot.jd501.service.dog_service.DogService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * class for work with Dog
 * have CRUD operation
 */
@RestController
@RequestMapping("/dog/dog")
public class DogController {
    private final DogService dogService;

    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    /**
     * get All Pets from DataBase
     * Use method of service {@link DogService#getAllPets()} (Collection< Dog >)}
     *
     * @return collection of Dog
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Show all Dogs",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Collection.class)
                    )
            )
    })
    @GetMapping
    public Collection<Dog> getAllPets() {
        return dogService.getAllPets();
    }

    /**
     * add new Dog in DataBase
     *
     * @param dog Use method of Service {@link DogService#createPet(Dog)}
     * @return Dog
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Create new Dog",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Dog.class)
                    )
            )
    })

    @PostMapping
    public ResponseEntity<Dog> createPet(@RequestBody Dog dog) {
        return ResponseEntity.ok(dogService.createPet(dog));
    }

    /**
     * change Dog in DataBase
     * Use method of Service {@link DogService#updatePet(Dog)}
     *
     * @param dog
     * @return Dog
     * @throws com.telegrambot.jd501.exceptions.PetReportNotFoundException if Dog with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Change Dog By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Dog.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pet not found"
            )
    })
    @PutMapping
    public ResponseEntity<Dog> updatePet(@RequestBody Dog dog) {
        return ResponseEntity.ok(dogService.updatePet(dog));
    }

    /**
     * delete Dog from DataBase by id
     * Use method of Service {@link DogService#deletePet(Long id)}}
     *
     * @param id
     * @return Deleted Dog
     * @throws com.telegrambot.jd501.exceptions.PetReportNotFoundException if Dog with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete Dog By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Dog.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Dog not found"
            )
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Dog> deletePet(@PathVariable Long id) {
        return ResponseEntity.ok(dogService.deletePet(id));
    }
}
