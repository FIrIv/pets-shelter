package com.telegrambot.jd501.controllers;

import com.telegrambot.jd501.model.PetReport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
/**
 * class for work with PetReport
 * have CRUD operation
 */
@RestController
@RequestMapping("/petReport")
public class PetReportController {
    PetReportService petReportService;

    public PetReportController(PetReportService petReportService) {
        this.petReportService = petReportService;
    }

    /**
     * get All PetReport from DataBase
     * Use method of servise {@link petReportService#getAllPetReport(Collection< PetReport >)}
     *
     * @return collection of PetReport
     */
    @GetMapping
    public Collection<PetReport> getAllPetReport() {
        return petReportService.getAllPetReport();
    }

    /**
     * add new PetReport in DataBase
     *
     * @param petReport Use method of Servise {@link petReportService#createPetReport(PetReport)}
     * @return PetReport
     */
    @PostMapping
    public ResponseEntity<PetReport> createPetReport(@RequestBody PetReport petReport) {
        return ResponseEntity.ok(petReportService.createPetReport(petReport));
    }

    /**
     * change PetReport in DataBase
     * Use method of Servise {@link petReportService#updatePetReport(PetReport)}
     *
     * @param petReport
     * @return PetReport
     * @throws PetReportNotFoundException if PetReport with id not found
     */
    @PutMapping
    public ResponseEntity<PetReport> updatePetReport(@RequestBody PetReport petReport) {
        return ResponseEntity.ok(petReportService.updatePetReport(petReport));
    }

    /**
     * delete PetReport from DataBase by id
     * Use method of Servise {@link petReportService#deletePetReport(PetReport)}
     *
     * @param id
     * @return Deleted PetReport
     * @throws PetReportNotFoundException if PetReport with id not found
     */
    @DeleteMapping("{id}")
    ResponseEntity<PetReport> deletePetReport(@PathVariable Long id) {
        return ResponseEntity.ok(petReportService.deletePetReport(id));
    }
}
