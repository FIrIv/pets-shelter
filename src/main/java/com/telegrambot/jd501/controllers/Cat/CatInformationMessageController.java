package com.telegrambot.jd501.controllers.Cat;


import com.telegrambot.jd501.model.cat.CatInformationMessage;
import com.telegrambot.jd501.service.CatService.CatInformationMessageService;
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
@RequestMapping("/cat/informationMessage")
public class CatInformationMessageController {

    private final CatInformationMessageService catInformationMessageService;

    public CatInformationMessageController(CatInformationMessageService catInformationMessageService) {
        this.catInformationMessageService = catInformationMessageService;
    }

    /**
     * get All CatInformationMessage from DataBase
     * Use method of CatInformationMessage servise {@link CatInformationMessageService#getAllCatInformationMessage()} ()} (Collection< CatInformationMessage >)}
     *
     * @return collection of CatInformationMessage
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
    public Collection <CatInformationMessage> getAllDogInformationMessage() {
        return catInformationMessageService.getAllCatInformationMessage();
    }

    /**
     * add new CatInformationMessage in DataBase
     *
     * @param catInformationMessage Use method of CatInformationMessageService {@link CatInformationMessageService#createCatInformationMessage(CatInformationMessage)}
     * @return CatInformationMessage
     */
    @ApiResponses({
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
    public ResponseEntity<CatInformationMessage> createDogInformationMessage(@RequestBody CatInformationMessage catInformationMessage) {
        return ResponseEntity.ok(catInformationMessageService.createCatInformationMessage(catInformationMessage));
    }

    /**
     * change CatInformationMessage in DataBase
     * Use method of Servise {@link CatInformationMessageService#updateCatInformationMessage(CatInformationMessage)}
     *
     * @param catInformationMessage
     * @return CatInformationMessage
     * @throws com.telegrambot.jd501.Exceptions.InformationMessageNotFoundException if CatInformationMessage with id not found
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
        return ResponseEntity.ok(catInformationMessageService.updateCatInformationMessage(catInformationMessage));
    }

    /**
     * delete CatInformationMessage from DataBase by id
     * Use method of Servise {@link CatInformationMessageService#deleteCatInformationMessage(Long id)}
     *
     * @param id
     * @return Deleted CatInformationMessage
     * @throws com.telegrambot.jd501.Exceptions.InformationMessageNotFoundException if CatInformationMessage with id not found
     */
    @ApiResponses({
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
                    description = "DogInformationMessage not found"
            )
    })
    @DeleteMapping("{id}")
    ResponseEntity<CatInformationMessage> deleteCatInformationMessage(@PathVariable Long id) {
        return ResponseEntity.ok(catInformationMessageService.deleteCatInformationMessage(id));
    }
}
