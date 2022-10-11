package com.telegrambot.jd501.controllers;

import com.telegrambot.jd501.model.InformationMessage;
import com.telegrambot.jd501.service.InformationMessageService;
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
@RequestMapping("/informationMessage")
public class InformationMessageController {

    private final InformationMessageService informationMessageService;

    public InformationMessageController(InformationMessageService informationMessageService) {
        this.informationMessageService = informationMessageService;
    }

    /**
     * get All InformationMessage from DataBase
     * Use method of user servise {@link InformationMessageService#getAllInformationMessage()} (Collection< InformationMessage >)}
     *
     * @return collection of InformationMessage
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Show all InformationMessage",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Collection.class)
                    )
            )
    })
    @GetMapping
    public Collection <InformationMessage> getAllInformationMessage() {
        return informationMessageService.getAllInformationMessage();
    }

    /**
     * add new InformationMessage in DataBase
     *
     * @param informationMessage Use method of Servise {@link InformationMessageService#createInformationMessage(InformationMessage)}
     * @return InformationMessage
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Create new InformationMessage",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InformationMessage.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<InformationMessage> createInformationMessage(@RequestBody InformationMessage informationMessage) {
        return ResponseEntity.ok(informationMessageService.createInformationMessage(informationMessage));
    }

    /**
     * change InformationMessage in DataBase
     * Use method of Servise {@link InformationMessageService#updateInformationMessage(InformationMessage)}
     *
     * @param informationMessage
     * @return InformationMessage
     * @throws com.telegrambot.jd501.Exceptions.InformationMessageNotFoundException if InformationMessage with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Change InformationMessage By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InformationMessage.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "InformationMessage not found"
            )
    })
    @PutMapping
    public ResponseEntity<InformationMessage> updateInformationMessage(@RequestBody InformationMessage informationMessage) {
        return ResponseEntity.ok(informationMessageService.updateInformationMessage(informationMessage));
    }

    /**
     * delete InformationMessage from DataBase by id
     * Use method of Servise {@link InformationMessageService#deleteInformationMessage(Long id)}
     *
     * @param id
     * @return Deleted InformationMessage
     * @throws com.telegrambot.jd501.Exceptions.InformationMessageNotFoundException if InformationMessage with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete InformationMessage By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InformationMessage.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "InformationMessage not found"
            )
    })
    @DeleteMapping("{id}")
    ResponseEntity<InformationMessage> deleteInformationMessage(@PathVariable Long id) {
        return ResponseEntity.ok(informationMessageService.deleteInformationMessage(id));
    }
}
