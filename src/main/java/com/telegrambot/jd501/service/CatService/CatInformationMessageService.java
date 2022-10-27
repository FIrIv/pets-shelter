package com.telegrambot.jd501.service.CatService;

import com.telegrambot.jd501.Exceptions.InformationMessageNotFoundException;
import com.telegrambot.jd501.model.cat.CatInformationMessage;
import com.telegrambot.jd501.repository.Cat.CatInformationMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatInformationMessageService {
    private final CatInformationMessageRepository catInformationMessageRepository;

    public CatInformationMessageService(CatInformationMessageRepository catInformationMessageRepository) {
        this.catInformationMessageRepository = catInformationMessageRepository;
    }
    /**
     * get All CatInformationMessage from DataBase
     * Use method of CatInformationMessage repository {@link CatInformationMessageRepository#findAll()} ()} (Collection< CatInformationMessage >)}
     *
     * @return collection of CatInformationMessage
     */
    public List<CatInformationMessage> getAllInformationMessages() {
        return catInformationMessageRepository.findAll();
    }
    /**
     * add new CatInformationMessage in DataBase
     *
     * @param catInformationMessage
     * Use  method CatInformationMessage repository {@link CatInformationMessageRepository#save(Object)} (CatInformationMessage)}
     * @return CatInformationMessage
     */
    public CatInformationMessage createInformationMessage(CatInformationMessage catInformationMessage) {
        return catInformationMessageRepository.save(catInformationMessage);
    }
    /**
     * change CatInformationMessage in DataBase
     * Use  method CatInformationMessage repository {@link CatInformationMessageRepository#save(Object)} (CatInformationMessage)}
     *
     * @param catinformationMessage
     * @return CatInformationMessage
     * @throws InformationMessageNotFoundException if CatInformationMessage with id not found
     */
    public CatInformationMessage updateInformationMessage(CatInformationMessage catinformationMessage) {
        catInformationMessageRepository.findById(catinformationMessage.getId()).orElseThrow(() -> new InformationMessageNotFoundException("CatInformationMessage not found"));
        return catInformationMessageRepository.save(catinformationMessage);
    }
    /**
     * delete CatInformationMessage from DataBase by id
     * Use  method CatInformationMessage repository {@link CatInformationMessageRepository#deleteById(Object)} } (Long id)}
     *
     * @param id
     * @return Deleted CatInformationMessage
     * @throws InformationMessageNotFoundException if CatInformationMessage with id not found
     */
    public CatInformationMessage deleteInformationMessage(Long id) {
        CatInformationMessage temp = catInformationMessageRepository.findById(id).orElseThrow(() -> new InformationMessageNotFoundException("CatInformationMessage not found"));
        catInformationMessageRepository.deleteById(id);
        return temp;
    }

    public CatInformationMessage findInformationMessageById(Long id) {
        return catInformationMessageRepository.findById(id).orElseThrow(() ->new InformationMessageNotFoundException("CatInformationMessage not found"));
    }
}
