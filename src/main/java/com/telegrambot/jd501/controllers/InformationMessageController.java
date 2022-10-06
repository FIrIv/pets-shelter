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

    @GetMapping
    public Collection <InformationMessage> getAllInformationMessage () {
        return informationMessageService.getAllInformationMessage();
    }
    @PostMapping
    public ResponseEntity<InformationMessage> createInformationMessage(@RequestBody InformationMessage informationMessage){
        return ResponseEntity.ok(informationMessageService.createInformationMessage(informationMessage));
    }
    @PutMapping
    public ResponseEntity <InformationMessage> updateInformationMessage(@RequestBody InformationMessage informationMessage){
        return ResponseEntity.ok(informationMessageService.updateInformationMessage(informationMessage));
    }
    @DeleteMapping("{id}")
    ResponseEntity <InformationMessage> deleteInformationMessage (@PathVariable Long id){
        return ResponseEntity.ok(informationMessageService.deleteInformationMessage(id));
    }
}
