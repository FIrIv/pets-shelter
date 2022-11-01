package com.telegrambot.jd501.controllers.cat;




import com.telegrambot.jd501.model.cat.CatReport;
import com.telegrambot.jd501.service.cat_service.CatReportService;
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
     * @param catReport Use method of Service {@link CatReportService#createPetReport(CatReport)}
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
     * Use method of Service {@link CatReportService#updatePetReport(CatReport)}
     *
     * @param catReport
     * @return CatReport
     * @throws com.telegrambot.jd501.exceptions.PetReportNotFoundException if CatReport with id not found
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
     * Use method of Service {@link CatReportService#deletePetReport(Long id)}}
     *
     * @param id
     * @return Deleted CatReport
     * @throws com.telegrambot.jd501.exceptions.PetReportNotFoundException if CatReport with id not found
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

    /**
     * Find CatReport by ID and get byte[] Photo from Report
     * Use method of catReportService {@link CatReportService#getPhotoById(Long)}
     * @param id
     * @return byte[]
     * @throws com.telegrambot.jd501.exceptions.PetReportNotFoundException when CatReport not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Get byte Array photo of CatReport by CatReport Id",
                    content = @Content(
                            mediaType = IMAGE_JPEG_VALUE,
                            schema = @Schema(implementation = byte[].class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "CatReport not found"
            )
    })
    @GetMapping ("/photo/{id}")
    public ResponseEntity <byte []> getPhotoById (@PathVariable Long id){
        byte [] temp = catReportService.getPhotoById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(IMAGE_JPEG);
        headers.setContentLength(temp.length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(temp);
    }
}
