package com.telegrambot.jd501.controllers.dog;

import com.telegrambot.jd501.model.dog.DogInformationMessage;
import com.telegrambot.jd501.service.dog_service.DogInformationMessageService;
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
@RequestMapping("/dog/informationMessage")
public class DogInformationMessageController {

    private final DogInformationMessageService dogInformationMessageService;

    public DogInformationMessageController(DogInformationMessageService dogInformationMessageService) {
        this.dogInformationMessageService = dogInformationMessageService;
    }

    /**
     * change DogInformationMessage in DataBase
     * Use method of Service {@link DogInformationMessageService#updateInformationMessage(DogInformationMessage)}
     *
     * @param dogInformationMessage (object)
     * @return DogInformationMessage
     * @throws com.telegrambot.jd501.exceptions.InformationMessageNotFoundException if DogInformationMessage with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Change DogInformationMessage By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DogInformationMessage.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "InformationMessage not found"
            )
    })
    @PutMapping
    public ResponseEntity<DogInformationMessage> updateInformationMessage(@RequestBody DogInformationMessage dogInformationMessage) {
        return ResponseEntity.ok(dogInformationMessageService.updateInformationMessage(dogInformationMessage));
    }
}
