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
    @GetMapping
    public Collection <PetReport> getAllPetReport () {
        return petReportService.getAllPetReport();
    }
    @PostMapping
    public ResponseEntity<PetReport> createPetReport(@RequestBody PetReport petReport){
        return ResponseEntity.ok(petReportService.createPetReport(petReport));
    }
    @PutMapping
    public ResponseEntity <PetReport> updatePetReport(@RequestBody PetReport petReport){
        return ResponseEntity.ok(petReportService.updatePetReport(petReport));
    }
    @DeleteMapping("{id}")
    ResponseEntity <PetReport> deletePetReport (@PathVariable Long id){
        return ResponseEntity.ok(petReportService.deletePetReport(id));
    }
}
