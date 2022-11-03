package com.telegrambot.jd501.controllers.dog;


import com.telegrambot.jd501.model.dog.DogReport;
import com.telegrambot.jd501.service.dog_service.DogReportService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

/**
 * class for work with DogReports
 * has CRUD operation
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
    public Collection <DogReport> getAllReports() {
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
     * Use method of Service {@link DogReportService#updatePetReport(DogReport)}
     *
     * @param dogReport (object)
     * @return DogReport
     * @throws com.telegrambot.jd501.exceptions.PetReportNotFoundException if DogReport with id not found
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

    /* /**
     * delete DogReport from DataBase by id
     * Use method of Service {@link DogReportService#deletePetReport(Long id)}}
     *
     * @param id
     * @return Deleted DogReport
     * @throws com.telegrambot.jd501.exceptions.PetReportNotFoundException if DogReport with id not found
     */
    /*@ApiResponses({
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
    public ResponseEntity<DogReport> deleteReport(@PathVariable Long id) {
        return ResponseEntity.ok(dogReportService.deletePetReport(id));
    }*/

    /**
     * Find DogReport by ID and get byte[] Photo from Report
     * Use method of dogReportService {@link DogReportService#getPhotoById(Long)}
     * @param id of DogReport
     * @return byte[]
     * @throws com.telegrambot.jd501.exceptions.PetReportNotFoundException when DogReport not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Get byte Array photo of DogReport by DogReport Id",
                    content = @Content(
                            mediaType = IMAGE_JPEG_VALUE,
                            schema = @Schema(implementation = byte[].class)
                                                )
            ),
            @ApiResponse(
              responseCode = "404",
              description = "DogReport not found"
            )
    })
    @GetMapping ("/photo/{id}")
    public ResponseEntity <byte []> getPhotoById (@PathVariable Long id){
        byte [] temp = dogReportService.getPhotoById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(IMAGE_JPEG);
        headers.setContentLength(temp.length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(temp);
    }
}
