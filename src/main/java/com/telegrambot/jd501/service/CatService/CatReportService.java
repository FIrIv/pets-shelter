package com.telegrambot.jd501.service.CatService;

import com.telegrambot.jd501.Exceptions.PetReportNotFoundException;

import com.telegrambot.jd501.model.cat.CatReport;
import com.telegrambot.jd501.model.cat.CatUser;
import com.telegrambot.jd501.model.dog.DogReport;
import com.telegrambot.jd501.repository.Cat.CatReportRepository;
import com.telegrambot.jd501.repository.Cat.CatUserRepository;
import com.telegrambot.jd501.repository.Dog.DogReportRepository;
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
     * @param catReport
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
     * @param id
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
     * @param user
     * @param dateOfReport
     * @return CatReport With Cat And dateOfReport
     */
    public CatReport getPetReportByUserAndDateOfReport(CatUser user, LocalDate dateOfReport) {
        return catReportRepository.findCatReportByCatUserAndDateOfReport(user, dateOfReport);
    }

    /**
     * Find all CatReport By ChatId
     *
     * Use method CatReport repository {@link CatReportRepository # }
     * @param chatId
     * @return Collection CatReport
     */
    public List<CatReport> getAllPetReportsByChatId(Long chatId){
        CatUser tempCatUser = catUserRepository.findCatUserByChatId(chatId);
        return catReportRepository.findAllByCatUser(tempCatUser);
    }

    /**
     * Find CatReport by ID and get byte[] Photo from Report
     *
     * Use method of catReportRepository {@link CatReportRepository#findById(Object)}
     * @param id
     * @return byte[]
     * @throws com.telegrambot.jd501.Exceptions.PetReportNotFoundException when CatReport not found
     */
    public byte[] getPhotoById(Long id) {
        CatReport temp = catReportRepository.findById(id).orElseThrow(() -> new PetReportNotFoundException("CatReport not found"));
        return temp.getPhoto();
    }
}
