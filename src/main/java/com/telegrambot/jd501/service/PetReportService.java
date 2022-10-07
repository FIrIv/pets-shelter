package com.telegrambot.jd501.service;

import com.telegrambot.jd501.Exceptions.PetReportNotFoundException;
import com.telegrambot.jd501.model.PetReport;
import com.telegrambot.jd501.repository.InformationMessageRepository;
import com.telegrambot.jd501.repository.PetReportRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PetReportService {
    private final PetReportRepository petReportRepository;

    public PetReportService(PetReportRepository petReportRepository) {
        this.petReportRepository = petReportRepository;
    }
    /**
     * get All PetReport from DataBase
     * Use method of PetReport repository {@link PetReportRepository#findAll()} ()} (Collection< PetReport >)}
     *
     * @return collection of PetReport
     */
    public Collection<PetReport> getAllPetReport() {
        return petReportRepository.findAll();
    }
    /**
     * add new PetReport in DataBase
     *
     * @param petReport
     * Use  method PetReport repository {@link PetReportRepository#save(Object)} (PetReport)}
     * @return PetReport
     */
    public PetReport createPetReport(PetReport petReport) {
        return petReportRepository.save(petReport);
    }
    /**
     * change PetReport in DataBase
     * Use  method PetReport repository {@link PetReportRepository#save(Object)} (PetReport)}
     *
     * @param petReport
     * @return PetReport
     * @throws com.telegrambot.jd501.Exceptions.PetReportNotFoundException if PetReport with id not found
     */
    public PetReport updatePetReport(PetReport petReport) {
        petReportRepository.findById(petReport.getId()).orElseThrow(() -> new PetReportNotFoundException("Report not found"));
        return petReportRepository.save(petReport);
    }
    /**
     * delete PetReport from DataBase by id
     * Use  method PetReport repository {@link PetReportRepository#deleteById(Object)} } (Long id)}
     *
     * @param id
     * @return Deleted PetReport
     * @throws com.telegrambot.jd501.Exceptions.PetReportNotFoundException if PetReport with id not found
     */
    public PetReport deletePetReport(Long id) {
        PetReport temp = petReportRepository.findById(id).orElseThrow(() -> new PetReportNotFoundException("Report not found"));
        petReportRepository.deleteById(id);
        return temp;
    }
}
