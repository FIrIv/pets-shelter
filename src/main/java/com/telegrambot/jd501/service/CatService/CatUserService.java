package com.telegrambot.jd501.service.CatService;

import com.telegrambot.jd501.Exceptions.PetNotFoundException;
import com.telegrambot.jd501.Exceptions.UserNotFoundException;
import com.telegrambot.jd501.model.cat.Cat;
import com.telegrambot.jd501.model.cat.CatUser;
import com.telegrambot.jd501.repository.Cat.CatRepository;
import com.telegrambot.jd501.repository.Cat.CatUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Service
public class CatUserService {
    private final CatUserRepository catUserRepository;
    private final CatRepository catRepository;

    public CatUserService(CatUserRepository catUserRepository, CatRepository catRepository) {
        this.catUserRepository = catUserRepository;
        this.catRepository = catRepository;
    }

    /**
     * get All CatUser from DataBase
     * Use method of CatUser repository {@link CatUserRepository#findAll()} ()} (Collection< CatUser >)}
     *
     * @return collection of CatUser
     */
    public Collection <CatUser> getAllCatUser() {
        return catUserRepository.findAll();
    }

    /**
     * add new User in DataBase
     *
     * @param catUser Use  method InformationMessage repository {@link CatUserRepository#save(Object)} (User)}
     * @return InformationMessage
     */
    public CatUser createCatUser(CatUser catUser) {
        catUser.setStartDate(null);
        catUser.setFinishDate(null);
        catUser.setCat(null);
        catUser.setAdopted(false);
        return catUserRepository.save(catUser);
    }

    /**
     * change CatUser in DataBase
     * Use  method User repository {@link CatUserRepository#save(Object)} (CatUser)}
     *
     * @param catUser
     * @return CatUser
     * @throws UserNotFoundException if CatUser with id not found
     */
    public CatUser updateCatUser(CatUser catUser) {
        catUserRepository.findById(catUser.getId()).orElseThrow(() -> new UserNotFoundException("CatUser not found"));
        return catUserRepository.save(catUser);
    }

    /**
     * delete CatUser from DataBase by id
     * Use  method CatUser repository {@link CatUserRepository#deleteById(Object)} } (Long id)}
     *
     * @param id
     * @return Deleted CatUser
     * @throws UserNotFoundException if CatUser with id not found
     */
    public CatUser deleteCatUser(Long id) {
        CatUser temp = catUserRepository.findById(id).orElseThrow(() -> new UserNotFoundException("CatUser not found"));
        catUserRepository.deleteById(id);
        return temp;
    }

    /**
     * find CatUser by id and change amount of probation period
     * Use method CatUser repository {@link CatUserRepository#findById(Object)}
     * Use  method CatUser repository {@link CatUserRepository#save(Object)} }
     *
     * @param id   - CatUser id for fing CatUser in repository,
     * @param days - number of days to increase the term of the transfer
     * @return notification that probationary period has been extended (String)
     */
    public String probationPeriodExtension(Long id, Integer days) {
        CatUser temp = catUserRepository.findById(id).orElseThrow(() -> new UserNotFoundException("CatUser not found"));
        temp.setFinishDate(temp.getFinishDate().plusDays(days));
        catUserRepository.save(temp);
        return "Probationary period" + temp.getName() + "increased by" + days + "days";
    }

    /**
     * find user by id and change  status of The Adopter, add adopted Pet, Date of adoption, and set test day by 30
     * Use method User repository {@link CatUserRepository#findById(Object)}
     * Use  method User repository {@link CatUserRepository#save(Object)} }
     * Use  method Pet repository {@link CatRepository#findById(Object)} }
     *
     * @param userId - user id for fing user in repository,
     * @param catId  - pet id for fing user in repository,
     * @return Changed User
     */
    public CatUser changeStatusOfTheAdopter(Long userId, Long catId) {
        CatUser userTemp = catUserRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Cat petTemp = catRepository.findById(catId).orElseThrow(() -> new PetNotFoundException("Pet not found"));
        userTemp.setAdopted(true);
        userTemp.setCat(petTemp);
        userTemp.setStartDate(LocalDate.now());
        userTemp.setFinishDate(LocalDate.now().plusDays(30));
        return catUserRepository.save(userTemp);
    }

    /**
     * find CatUser with status "adopted = true"
     * Use method CatUser repository {@link CatUserRepository#findCatUsersByIsAdoptedIsTrue()}
     *
     * @return List<CatUser>
     */
    public List<CatUser> findCatUsersByAdoptedIsTrue() {
        return catUserRepository.findCatUsersByIsAdoptedIsTrue();
    }

    /**
     * find CatUser if he exists by his ID
     * Use method CatUser repository {@link CatUserRepository#existsById(Object)}
     *
     * @return boolean
     */
    public boolean isExistsCatUser(long catUserChatId) {
        return catUserRepository.existsByChatId(catUserChatId);
    }

    /**
     * find CatUser with by ID
     * Use method User repository {@link CatUserRepository#findById(Object)}
     *
     * @return CatUser
     */
    public CatUser findCatUserByChatId(long userChatId) {
        return catUserRepository.findCatUserByChatId(userChatId);
    }


}
