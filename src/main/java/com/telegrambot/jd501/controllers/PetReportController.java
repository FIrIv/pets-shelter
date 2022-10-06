package com.telegrambot.jd501.controllers;

import com.telegrambot.jd501.model.PetReport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/petReport")
public class PetReportController {
    PetReportService petReportService;

    public PetReportController(PetReportService petReportService) {
        this.petReportService = petReportService;
    }
    // get All PetReport from DataBase
    @GetMapping
    public Collection <PetReport> getAllPetReport () {
        return petReportService.getAllPetReport();
    }
    // add new PetReport in DataBase
    @PostMapping
    public ResponseEntity<PetReport> createPetReport(@RequestBody PetReport petReport){
        return ResponseEntity.ok(petReportService.createPetReport(petReport));
    }
    // change PetReport in DataBase
    @PutMapping
    public ResponseEntity <PetReport> updatePetReport(@RequestBody PetReport petReport){
        return ResponseEntity.ok(petReportService.updatePetReport(petReport));
    }
    // delete PetReport from DataBase
    @DeleteMapping("{id}")
    ResponseEntity <PetReport> deletePetReport (@PathVariable Long id){
        return ResponseEntity.ok(petReportService.deletePetReport(id));
    }
}
