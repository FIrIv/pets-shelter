package com.telegrambot.jd501.controllers;

import com.telegrambot.jd501.model.InformationMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
@RestController
@RequestMapping("/informationMessage")
public class InformationMessageController {

    InformationMessageService informationMessageService;

    public InformationMessageController(InformationMessageService informationMessageService) {
        this.informationMessageService = informationMessageService;
    }
    // get All InformationMessage from DataBase
    @GetMapping
    public Collection <InformationMessage> getAllInformationMessage () {
        return informationMessageService.getAllInformationMessage();
    }
    // add new InformationMessage in DataBase
    @PostMapping
    public ResponseEntity<InformationMessage> createInformationMessage(@RequestBody InformationMessage informationMessage){
        return ResponseEntity.ok(informationMessageService.createInformationMessage(informationMessage));
    }
    // change InformationMessage in DataBase
    @PutMapping
    public ResponseEntity <InformationMessage> updateInformationMessage(@RequestBody InformationMessage informationMessage){
        return ResponseEntity.ok(informationMessageService.updateInformationMessage(informationMessage));
    }
    // delete InformationMessage from DataBase
    @DeleteMapping("{id}")
    ResponseEntity <InformationMessage> deleteInformationMessage (@PathVariable Long id){
        return ResponseEntity.ok(informationMessageService.deleteInformationMessage(id));
    }
}
