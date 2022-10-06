package com.telegrambot.jd501.controllers;

import com.telegrambot.jd501.model.InformationMessage;
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

    InformationMessageService informationMessageService;

    public InformationMessageController(InformationMessageService informationMessageService) {
        this.informationMessageService = informationMessageService;
    }

    /**
     * get All InformationMessage from DataBase
     * Use method of user servise {@link informationMessageService#getAllInformationMessage(Collection< InformationMessage >)}
     *
     * @return collection of InformationMessage
     */
    @GetMapping
    public Collection<InformationMessage> getAllInformationMessage() {
        return informationMessageService.getAllInformationMessage();
    }

    /**
     * add new InformationMessage in DataBase
     *
     * @param InformationMessage Use method of Servise {@link informationMessageService#createInformationMessage(InformationMessage)}
     * @return InformationMessage
     */
    @PostMapping
    public ResponseEntity<InformationMessage> createInformationMessage(@RequestBody InformationMessage informationMessage) {
        return ResponseEntity.ok(informationMessageService.createInformationMessage(informationMessage));
    }

    /**
     * change InformationMessage in DataBase
     * Use method of Servise {@link informationMessageService#updateInformationMessage(InformationMessage)}
     *
     * @param InformationMessage
     * @return InformationMessage
     * @throws InformationMessageNotFoundException if InformationMessage with id not found
     */
    @PutMapping
    public ResponseEntity<InformationMessage> updateInformationMessage(@RequestBody InformationMessage informationMessage) {
        return ResponseEntity.ok(informationMessageService.updateInformationMessage(informationMessage));
    }

    /**
     * delete InformationMessage from DataBase by id
     * Use method of Servise {@link informationMessageService#deleteInformationMessage(InformationMessage)}
     *
     * @param id
     * @return Deleted InformationMessage
     * @throws InformationMessageNotFoundException if InformationMessage with id not found
     */
    @DeleteMapping("{id}")
    ResponseEntity<InformationMessage> deleteInformationMessage(@PathVariable Long id) {
        return ResponseEntity.ok(informationMessageService.deleteInformationMessage(id));
    }
}
