package com.telegrambot.jd501.controllers.Dog;


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
 * class for work with DogReports
 * have CRUD operation
 */
@RestController
@RequestMapping("/dog/report")
public class DogReportController {
    private final DogReportService dogReportService;

    public DogReportController(DogReportService dogReportService) {
        this.dogReportService = dogReportService;
    }

    /**
     * get All DogReport-s from DataBase
     * Use method of service {@link DogReportService#getAllPetReports()}}
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
    public Collection<DogReport> getAllReports() {
        return dogReportService.getAllPetReports();
    }

    /**
     * get All DogReport-s By ChatId
     * Use method of service {@link DogReportService#getAllPetReportsByChatId(Long)}
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
    public Collection<DogReport> getAllReportsByChatId(@PathVariable Long chatId) {
        return dogReportService.getAllPetReportsByChatId(chatId);
    }

    /**
     * add new DogReport in DataBase
     *
     * @param dogReport Use method of Service {@link DogReportService#createPetReport(DogReport)}
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
    public ResponseEntity<DogReport> createReport(@RequestBody DogReport dogReport) {
        return ResponseEntity.ok(dogReportService.createPetReport(dogReport));
    }

    /**
     * change DogReport in DataBase
     * Use method of Servise {@link DogReportService#updatePetReport(DogReport)}
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
    public ResponseEntity<DogReport> updateReport(@RequestBody DogReport dogReport) {
        return ResponseEntity.ok(dogReportService.updatePetReport(dogReport));
    }

    /**
     * delete DogReport from DataBase by id
     * Use method of Servise {@link DogReportService#deletePetReport(Long id)}}
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
    ResponseEntity<DogReport> deleteReport(@PathVariable Long id) {
        return ResponseEntity.ok(dogReportService.deletePetReport(id));
    }
}
