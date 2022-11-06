package com.telegrambot.jd501.controllers.cat;

import com.telegrambot.jd501.model.cat.CatInformationMessage;
import com.telegrambot.jd501.service.cat_service.CatInformationMessageService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * class for work with InformationMessage
 * has CRUD operation
 */
@RestController
@RequestMapping("/cat/informationMessage")
public class CatInformationMessageController {

    private final CatInformationMessageService catInformationMessageService;

    public CatInformationMessageController(CatInformationMessageService catInformationMessageService) {
        this.catInformationMessageService = catInformationMessageService;
    }

    /**
     * change InformationMessage in DataBase
     * Use method of Service {@link CatInformationMessageService#updateInformationMessage(CatInformationMessage)}
     *
     * @param catInformationMessage (object)
     * @return CatInformationMessage
     * @throws com.telegrambot.jd501.exceptions.InformationMessageNotFoundException if CatInformationMessage with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Change CatInformationMessage By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatInformationMessage.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "InformationMessage not found"
            )
    })
    @PutMapping
    public ResponseEntity<CatInformationMessage> updateInformationMessage(@RequestBody CatInformationMessage catInformationMessage) {
        return ResponseEntity.ok(catInformationMessageService.updateInformationMessage(catInformationMessage));
    }
}
