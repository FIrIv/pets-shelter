package com.telegrambot.jd501.controllers.Dog;


import com.telegrambot.jd501.model.dog.Dog;
import com.telegrambot.jd501.model.dog.DogReport;
import com.telegrambot.jd501.service.DogService.DogReportService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * class for work with PetReport
 * have CRUD operation
 */
@RestController
@RequestMapping("/dog/dogReport")
public class DogReportController {
    private final DogReportService dogReportService;

    public DogReportController(DogReportService dogReportService) {
        this.dogReportService = dogReportService;
    }

    /**
     * get All DogReport from DataBase
     * Use method of service {@link DogReportService#getAllDogReport()}}
     *
     * @return collection of DogReport
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Show all DogReport",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Collection.class)
                    )
            )
    })

    @GetMapping
    public Collection<DogReport> getAllDogReport() {
        return dogReportService.getAllDogReport();
    }

    /**
     * get All DogReport By ChatId
     * Use method of service {@link DogReportService#getAllReportsByChatId(Long)}
     *
     * @return collection of DogReport With ChatId Ordered By Date
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Show all PetReport with ChatId Ordered By Date",
                    content = @Content(
                            schema = @Schema(implementation = Collection.class)
                    )
            )
    })
    @GetMapping("/pet_report/{chatId}")
    public Collection<DogReport> getAllPetReportsByChatId(@PathVariable Long chatId) {
        return dogReportService.getAllReportsByChatId(chatId);
    }

    /**
     * add new DogReport in DataBase
     *
     * @param dogReport Use method of Servise {@link DogReportService#createDogReport(DogReport)}
     * @return DogReport
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Create new DogReport",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DogReport.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<DogReport> createPetReport(@RequestBody DogReport dogReport) {
        return ResponseEntity.ok(dogReportService.createDogReport(dogReport));
    }

    /**
     * change DogReport in DataBase
     * Use method of Servise {@link DogReportService#updateDogReport(DogReport)}
     *
     * @param dogReport
     * @return DogReport
     * @throws com.telegrambot.jd501.Exceptions.PetReportNotFoundException if DogReport with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Change DogReport By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DogReport.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "DogReport not found"
            )
    })
    @PutMapping
    public ResponseEntity<DogReport> updatePetReport(@RequestBody DogReport dogReport) {
        return ResponseEntity.ok(dogReportService.updateDogReport(dogReport));
    }

    /**
     * delete DogReport from DataBase by id
     * Use method of Servise {@link DogReportService#deleteDogReport(Long id)}}
     *
     * @param id
     * @return Deleted DogReport
     * @throws com.telegrambot.jd501.Exceptions.PetReportNotFoundException if DogReport with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete DogReport By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DogReport.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "DogReport not found"
            )
    })
    @DeleteMapping("{id}")
    ResponseEntity<DogReport> deletePetReport(@PathVariable Long id) {
        return ResponseEntity.ok(dogReportService.deleteDogReport(id));
    }
}
