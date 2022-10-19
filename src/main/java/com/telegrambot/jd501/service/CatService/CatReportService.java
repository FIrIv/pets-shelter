package com.telegrambot.jd501.service.CatService;

import com.telegrambot.jd501.Exceptions.PetReportNotFoundException;

import com.telegrambot.jd501.model.cat.Cat;
import com.telegrambot.jd501.model.cat.CatReport;
import com.telegrambot.jd501.repository.Cat.CatReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class CatReportService {
    private final CatReportRepository catReportRepository;



    public CatReportService(CatReportRepository catReportRepository) {
        this.catReportRepository = catReportRepository;
    }

    /**
     * get All CatReport from DataBase
     * Use method of CatReport repository {@link CatReportRepository#findAll()} ()} (Collection< CatReport >)}
     *
     * @return collection of CatReport
     */
    public Collection<CatReport> getAllCatReport() {
        return catReportRepository.findAll();
    }

    /**
     * add new CatReport in DataBase
     *
     * @param catReport Use  method CatReport repository {@link CatReportRepository#save(Object)} (CatReport)}
     * @return CatReport
     */
    public CatReport createCatReport(CatReport catReport) {
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
    public CatReport updateCatReport(CatReport catReport) {
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
    public CatReport deleteCatReport(Long id) {
        CatReport temp = catReportRepository.findById(id).orElseThrow(() -> new PetReportNotFoundException("CatReport not found"));
        catReportRepository.deleteById(id);
        return temp;
    }

    /**
     * get All CatReport from DataBase by Dog
     * Use method of CatReport repository {@link CatReportRepository#findCatReportsByCatOrderByDateOfReport(Cat)}(List<CatReport>)}
     *
     * @return collection of DogReport With Pet
     */
    public Collection<CatReport> getAllCatReportByCat(Cat cat) {
        return catReportRepository.findCatReportsByCatOrderByDateOfReport(cat);
    }

    /**
     * get one CatReport from DataBase by Pet And DateOfReport
     * Use method of CatReport repository {@link CatReportRepository#findCatReportByCatAndDateOfReport(Cat, LocalDate)} (<CatReport>)}
     *
     * @param cat
     * @param dateOfReport
     * @return DogReport With dog And dateOfReport
     */
    public CatReport getDogReportByDogAndDateOfReport(Cat cat, LocalDate dateOfReport) {
        return catReportRepository.findCatReportByCatAndDateOfReport(cat, dateOfReport);
    }
}
