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
     * get All CatInformationMessage-s from DataBase
     * Use method of CatInformationMessage service {@link CatInformationMessageService#getAllInformationMessages()} ()} (Collection< CatInformationMessage >)}
     *
     * @return collection of CatInformationMessage
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Show all CatInformationMessage",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Collection.class)
                    )
            )
    })
    @GetMapping
    public Collection <CatInformationMessage> getAllInformationMessages() {
        return catInformationMessageService.getAllInformationMessages();
    }

    /*/**
     * add new CatInformationMessage in DataBase
     *
     * @param catInformationMessage Use method of CatInformationMessageService {@link CatInformationMessageService#createInformationMessage(CatInformationMessage)}
     * @return CatInformationMessage
     */
    /*@ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Create new CatInformationMessage",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatInformationMessage.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<CatInformationMessage> createInformationMessage(@RequestBody CatInformationMessage catInformationMessage) {
        return ResponseEntity.ok(catInformationMessageService.createInformationMessage(catInformationMessage));
    }*/

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

    /*/**
     * delete CatInformationMessage from DataBase by id
     * Use method of Service {@link CatInformationMessageService#deleteInformationMessage(Long id)}
     *
     * @param id
     * @return Deleted CatInformationMessage
     * @throws com.telegrambot.jd501.exceptions.InformationMessageNotFoundException if CatInformationMessage with id not found
     */
    /*@ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete CatInformationMessage By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatInformationMessage.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "CatInformationMessage not found"
            )
    })
    @DeleteMapping("{id}")
    public ResponseEntity<CatInformationMessage> deleteInformationMessage(@PathVariable Long id) {
        return ResponseEntity.ok(catInformationMessageService.deleteInformationMessage(id));
    }*/
}
