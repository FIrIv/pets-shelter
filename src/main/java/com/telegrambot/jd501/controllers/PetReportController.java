package com.telegrambot.jd501.controllers;

import com.telegrambot.jd501.model.PetReport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/petReport")
public class PetReportController {

    @GetMapping
    public Collection <PetReport> getAllPetReport () {
        return petReportServise.getAllPetReport();
    }
    @PostMapping
    public ResponseEntity<PetReport> createPetReport(@RequestBody PetReport petReport){
        return ResponseEntity.ok(petReportServise.createPetReport(petReport));
    }
    @PutMapping
    public ResponseEntity <PetReport> updatePetReport(@RequestBody PetReport petReport){
        return ResponseEntity.ok(petReportServise.updatePetReport(petReport));
    }
    @DeleteMapping("{id}")
    ResponseEntity <PetReport> deletePetReport (@PathVariable Long id){
        return ResponseEntity.ok(petReportServise.deletePetReport(id));
    }
}
