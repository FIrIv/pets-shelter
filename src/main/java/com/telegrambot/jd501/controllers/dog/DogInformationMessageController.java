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
 * have CRUD operation
 */
@RestController
@RequestMapping("/dog/informationMessage")
public class DogInformationMessageController {

    private final DogInformationMessageService dogInformationMessageService;

    public DogInformationMessageController(DogInformationMessageService dogInformationMessageService) {
        this.dogInformationMessageService = dogInformationMessageService;
    }

    /**
     * get All DogInformationMessage from DataBase
     * Use method of DogInformationMessage service {@link DogInformationMessageService#getAllInformationMessages()} ()} (Collection< DogInformationMessage >)}
     *
     * @return collection of DogInformationMessage
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Show all DogInformationMessage",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Collection.class)
                    )
            )
    })
    @GetMapping
    public Collection <DogInformationMessage> getAllInformationMessage() {
        return dogInformationMessageService.getAllInformationMessages();
    }

    /*/**
     * add new DogInformationMessage in DataBase
     *
     * @param dogInformationMessage Use method of DogInformationMessageService {@link DogInformationMessageService#createInformationMessage(DogInformationMessage)}
     * @return DogInformationMessage
     */
    /*@ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Create new DogInformationMessage",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DogInformationMessage.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<DogInformationMessage> createInformationMessage(@RequestBody DogInformationMessage dogInformationMessage) {
        return ResponseEntity.ok(dogInformationMessageService.createInformationMessage(dogInformationMessage));
    }*/

    /**
     * change DogInformationMessage in DataBase
     * Use method of Service {@link DogInformationMessageService#updateInformationMessage(DogInformationMessage)}
     *
     * @param dogInformationMessage
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

    /*/**
     * delete DogInformationMessage from DataBase by id
     * Use method of Service {@link DogInformationMessageService#deleteInformationMessage(Long id)}
     *
     * @param id
     * @return Deleted DogInformationMessage
     * @throws com.telegrambot.jd501.exceptions.InformationMessageNotFoundException if DogInformationMessage with id not found
     */
    /*@ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete DogInformationMessage By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DogInformationMessage.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "DogInformationMessage not found"
            )
    })
    @DeleteMapping("{id}")
    public ResponseEntity<DogInformationMessage> deleteInformationMessage(@PathVariable Long id) {
        return ResponseEntity.ok(dogInformationMessageService.deleteInformationMessage(id));
    }*/
}
