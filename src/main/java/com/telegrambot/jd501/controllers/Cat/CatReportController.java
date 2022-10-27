package com.telegrambot.jd501.controllers.Cat;




import com.telegrambot.jd501.model.cat.CatReport;
import com.telegrambot.jd501.service.CatService.CatReportService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * class for work with CatReports
 * have CRUD operation
 */
@RestController
@RequestMapping("/cat/report")
public class CatReportController {
    private final CatReportService catReportService;

    public CatReportController(CatReportService catReportService) {
        this.catReportService = catReportService;
    }

    /**
     * get All CatReport-s from DataBase
     * Use method of service {@link CatReportService#getAllPetReports()}}
     *
     * @return collection of CatReport
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Show all CatReport",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Collection.class)
                    )
            )
    })

    @GetMapping
    public Collection<CatReport> getAllReports() {
        return catReportService.getAllPetReports();
    }

    /**
     * get All CatReport-s By ChatId
     * Use method of service {@link CatReportService#getAllPetReportsByChatId(Long)}
     *
     * @return collection of CatReport With ChatId Ordered By Date
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Show all CatReport with Cat Ordered By Date",
                    content = @Content(
                            schema = @Schema(implementation = Collection.class)
                    )
            )
    })
    @GetMapping("/pet_report/{chatId}")
    public Collection<CatReport> getAllReportsByChatId(@PathVariable Long chatId) {
        return catReportService.getAllPetReportsByChatId(chatId);
    }

    /**
     * add new CatReport in DataBase
     *
     * @param catReport Use method of Servise {@link CatReportService#createPetReport(CatReport)}
     * @return CatReport
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Create new CatReport",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatReport.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<CatReport> createReport(@RequestBody CatReport catReport) {
        return ResponseEntity.ok(catReportService.createPetReport(catReport));
    }

    /**
     * change CatReport in DataBase
     * Use method of Servise {@link CatReportService#updatePetReport(CatReport)}
     *
     * @param catReport
     * @return CatReport
     * @throws com.telegrambot.jd501.Exceptions.PetReportNotFoundException if CatReport with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Change CatReport By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatReport.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "CatReport not found"
            )
    })
    @PutMapping
    public ResponseEntity<CatReport> updateReport(@RequestBody CatReport catReport) {
        return ResponseEntity.ok(catReportService.updatePetReport(catReport));
    }

    /**
     * delete CatReport from DataBase by id
     * Use method of Servise {@link CatReportService#deletePetReport(Long id)}}
     *
     * @param id
     * @return Deleted CatReport
     * @throws com.telegrambot.jd501.Exceptions.PetReportNotFoundException if CatReport with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete CatReport By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatReport.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "CatReport not found"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CatReport> deleteReport(@PathVariable Long id) {
        return ResponseEntity.ok(catReportService.deletePetReport(id));
    }
}
