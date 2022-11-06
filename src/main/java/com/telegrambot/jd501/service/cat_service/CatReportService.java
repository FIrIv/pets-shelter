package com.telegrambot.jd501.service.cat_service;

import com.telegrambot.jd501.exceptions.PetReportNotFoundException;

import com.telegrambot.jd501.model.cat.CatReport;
import com.telegrambot.jd501.model.cat.CatUser;
import com.telegrambot.jd501.repository.cat.CatReportRepository;
import com.telegrambot.jd501.repository.cat.CatUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class CatReportService {
    private final CatReportRepository catReportRepository;
    private final CatUserRepository catUserRepository;


    public CatReportService(CatReportRepository catReportRepository, CatUserRepository catUserRepository) {
        this.catReportRepository = catReportRepository;
        this.catUserRepository = catUserRepository;
    }

    /**
     * get All CatReport from DataBase
     * Use method of CatReport repository {@link CatReportRepository#findAll()} ()} (Collection< CatReport >)}
     *
     * @return collection of CatReport
     */
    public Collection<CatReport> getAllPetReports() {
        return catReportRepository.findAll();
    }

    /**
     * add new CatReport in DataBase
     *
     * @param catReport Use  method CatReport repository {@link CatReportRepository#save(Object)} (CatReport)}
     * @return CatReport
     */
    public CatReport createPetReport(CatReport catReport) {
        return catReportRepository.save(catReport);
    }

    /**
     * change CatReport in DataBase
     * Use  method CatReport repository {@link CatReportRepository#save(Object)} (CatReport)}
     *
     * @param catReport (object)
     * @return CatReport
     * @throws PetReportNotFoundException if CatReport with id not found
     */
    public CatReport updatePetReport(CatReport catReport) {
        catReportRepository.findById(catReport.getId()).orElseThrow(() -> new PetReportNotFoundException("CatReport not found"));
        return catReportRepository.save(catReport);
    }

    /**
     * delete CatReport from DataBase by id
     * Use  method CatReport repository {@link CatReportRepository#deleteById(Object)} } (Long id)}
     *
     * @param id of CatReport
     * @return Deleted CatReport
     * @throws PetReportNotFoundException if CatReport with id not found
     */
    public CatReport deletePetReport(Long id) {
        CatReport temp = catReportRepository.findById(id).orElseThrow(() -> new PetReportNotFoundException("CatReport not found"));
        catReportRepository.deleteById(id);
        return temp;
    }

    /**
     * get All CatReport from DataBase by User
     * Use method of CatReport repository {@link CatReportRepository#findCatReportsByCatUserOrderByDateOfReport(CatUser)}(List<CatReport>)}
     *
     * @return collection of CatReport With User
     */
    public Collection<CatReport> getAllPetReportsByUser(CatUser user) {
        return catReportRepository.findCatReportsByCatUserOrderByDateOfReport(user);
    }

    /**
     * get one CatReport from DataBase by User And DateOfReport
     * Use method of CatReport repository {@link CatReportRepository#findCatReportByCatUserAndDateOfReport(CatUser, LocalDate)} (<CatReport>)}
     *
     * @param user (object)
     * @param dateOfReport date of report
     * @return CatReport With Cat And dateOfReport
     */
    public CatReport getPetReportByUserAndDateOfReport(CatUser user, LocalDate dateOfReport) {
        return catReportRepository.findCatReportByCatUserAndDateOfReport(user, dateOfReport);
    }

    /**
     * Find all CatReport By ChatId
     * Use method CatReport repository {@link CatReportRepository # }
     * @param chatId of chat history
     * @return Collection CatReport
     */
    public List<CatReport> getAllPetReportsByChatId(Long chatId){
        CatUser tempCatUser = catUserRepository.findCatUserByChatId(chatId);
        return catReportRepository.findAllByCatUser(tempCatUser);
    }

    /**
     * Find CatReport by ID and get byte[] Photo from Report
     * Use method of catReportRepository {@link CatReportRepository#findById(Object)}
     * @param id of CatReport
     * @return byte[]
     * @throws com.telegrambot.jd501.exceptions.PetReportNotFoundException when CatReport not found
     */
    public byte[] getPhotoById(Long id) {
        CatReport temp = catReportRepository.findById(id).orElseThrow(() -> new PetReportNotFoundException("CatReport not found"));
        return temp.getPhoto();
    }
}
