package com.telegrambot.jd501.controllers;
import com.telegrambot.jd501.model.PetReport;
import com.telegrambot.jd501.service.PetReportService;
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
@RequestMapping("/petReport")
public class PetReportController {
    private final PetReportService petReportService;

    public PetReportController(PetReportService petReportService) {
        this.petReportService = petReportService;
    }

    /**
     * get All PetReport from DataBase
     * Use method of servise {@link PetReportService#getAllPetReport()}}
     *
     * @return collection of PetReport
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Show all PetReport",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Collection.class)
                    )
            )
    })
    @GetMapping
    public Collection<PetReport> getAllPetReport() {
        return petReportService.getAllPetReport();
    }

    /**
     * add new PetReport in DataBase
     *
     * @param petReport Use method of Servise {@link PetReportService#createPetReport(PetReport)}
     * @return PetReport
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Create new PetReport",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PetReport.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<PetReport> createPetReport(@RequestBody PetReport petReport) {
        return ResponseEntity.ok(petReportService.createPetReport(petReport));
    }

    /**
     * change PetReport in DataBase
     * Use method of Servise {@link PetReportService#updatePetReport(PetReport)}
     *
     * @param petReport
     * @return PetReport
     * @throws com.telegrambot.jd501.Exceptions.PetReportNotFoundException if PetReport with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Change PetReport By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PetReport.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "PetReport not found"
            )
    })
    @PutMapping
    public ResponseEntity<PetReport> updatePetReport(@RequestBody PetReport petReport) {
        return ResponseEntity.ok(petReportService.updatePetReport(petReport));
    }

    /**
     * delete PetReport from DataBase by id
     * Use method of Servise {@link PetReportService#deletePetReport(Long id)}}
     *
     * @param id
     * @return Deleted PetReport
     * @throws com.telegrambot.jd501.Exceptions.PetReportNotFoundException if PetReport with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete PetReport By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PetReport.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "PetReport not found"
            )
    })
    @DeleteMapping("{id}")
    ResponseEntity<PetReport> deletePetReport(@PathVariable Long id) {
        return ResponseEntity.ok(petReportService.deletePetReport(id));
    }
}
