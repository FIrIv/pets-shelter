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
 * has CRUD operation
 */
@RestController
@RequestMapping("/cat/report")
public class CatReportController {
    private final CatReportService catReportService;

    public CatReportController(CatReportService catReportService) {
        this.catReportService = catReportService;
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
    @GetMapping("/{chatId}")
    public Collection<CatReport> getAllReportsByChatId(@PathVariable Long chatId) {
        return catReportService.getAllPetReportsByChatId(chatId);
    }

    /**
     * Find CatReport by ID and get byte[] Photo from Report
     * Use method of catReportService {@link CatReportService#getPhotoById(Long)}
     * @param id of CatReport
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
