package com.telegrambot.jd501.service.cat_service;

import com.telegrambot.jd501.exceptions.PetNotFoundException;
import com.telegrambot.jd501.exceptions.UserNotFoundException;
import com.telegrambot.jd501.model.cat.Cat;
import com.telegrambot.jd501.model.cat.CatUser;
import com.telegrambot.jd501.repository.cat.CatRepository;
import com.telegrambot.jd501.repository.cat.CatUserRepository;
import com.telegrambot.jd501.service.MailingListService;
import com.telegrambot.jd501.service.MessageTextService;
import com.telegrambot.jd501.service.TelegramBot;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class CatUserService {
    private final CatUserRepository catUserRepository;
    private final CatRepository catRepository;
    private final MailingListService mailingListService;

    private final MessageTextService messageTextService;

    public CatUserService(CatUserRepository catUserRepository, CatRepository catRepository, MailingListService mailingListService, MessageTextService messageTextService) {
        this.catUserRepository = catUserRepository;
        this.catRepository = catRepository;
        this.mailingListService = mailingListService;
        this.messageTextService = messageTextService;
    }

    /**
     * get All CatUser from DataBase
     * Use method of CatUser repository {@link CatUserRepository#findAll()} ()} (Collection< CatUser >)}
     *
     * @return collection of CatUser
     */
    public Collection<CatUser> getAllUsers() {
        return catUserRepository.findAll();
    }

    /**
     * add new User in DataBase
     *
     * @param catUser Use  method InformationMessage repository {@link CatUserRepository#save(Object)} (User)}
     * @return InformationMessage
     */
    public CatUser createUser(CatUser catUser) {
        catUser.setStartDate(null);
        catUser.setFinishDate(null);
        catUser.setPet(null);
        catUser.setAdopted(false);
        return catUserRepository.save(catUser);
    }

    /**
     * change CatUser in DataBase
     * Use  method User repository {@link CatUserRepository#save(Object)} (CatUser)}
     *
     * @param catUser (object)
     * @return CatUser
     * @throws UserNotFoundException if CatUser with id not found
     */
    public CatUser updateUser(CatUser catUser) {
        catUserRepository.findById(catUser.getId()).orElseThrow(() -> new UserNotFoundException("CatUser not found"));
        return catUserRepository.save(catUser);
    }

    /**
     * delete CatUser from DataBase by chatId
     * Use  method CatUser repository {@link CatUserRepository#deleteById(Object)}
     *
     * @param chatId of CatUser
     * @return Deleted CatUser
     * @throws UserNotFoundException if CatUser with id not found
     */
    public CatUser deleteUser(Long chatId) {
        CatUser temp = catUserRepository.findCatUserByChatId(chatId);
        if (temp == null){
            throw new UserNotFoundException("User not found");
        }
        catUserRepository.deleteById(temp.getId());
        return temp;
    }

    /**
     * find CatUser by chatId, change amount of probation period, and sent message to user about change probation period
     * Use method CatUser repository {@link CatUserRepository#findCatUserByChatId(Long)}
     * Use  method CatUser repository {@link CatUserRepository#save(Object)}
     *
     * @param chatId   - CatUser id for find CatUser in repository,
     * @param days - number of days to increase the term of the transfer
     * @return CatUser
     */
    public CatUser probationPeriodExtension(Long chatId, Integer days) {
        CatUser temp = catUserRepository.findCatUserByChatId(chatId);
        if (temp == null){
           throw new UserNotFoundException("User not Found");
        }
        temp.setFinishDate(temp.getFinishDate().plusDays(days));
        catUserRepository.save(temp);
        sendMessageToUserWithChatId(temp.getChatId(),
        messageTextService.get("probation.period.extension", days));
        return temp;
    }

    /**
     * find user by id and change  status of The Adopter, add adopted Pet, Date of adoption, set test day by 30,
     * and sent message to user about change him status.
     * <p>
     * Use method CatUser repository {@link CatUserRepository#findCatUserByChatId(Long)}
     * Use  method CatUser repository {@link CatUserRepository#save(Object)}
     * Use  method CatUser repository {@link CatRepository#findById(Object)}
     * Use method CatUserService {@link CatUserService#sendMessageToUserWithChatId(Long, String)}
     *
     * @param userChatId - user id for find user in repository,
     * @param catId  - pet id for find user in repository,
     * @return Changed User
     */
    public CatUser changeStatusOfTheAdopter(Long userChatId, Long catId) {
        CatUser userTemp = catUserRepository.findCatUserByChatId(userChatId);
        if (userTemp == null){
            throw new UserNotFoundException("User Not Found");
        }
        Cat petTemp = catRepository.findById(catId).orElseThrow(() -> new PetNotFoundException("Cat not found"));
        userTemp.setAdopted(true);
        userTemp.setPet(petTemp);
        userTemp.setStartDate(LocalDate.now());
        sendMessageToUserWithChatId(userTemp.getChatId(), messageTextService.get("congrat.u.are.new.adopter"));
        return catUserRepository.save(userTemp);
    }

    /**
     * find CatUser with status "adopted = true"
     * Use method CatUser repository {@link CatUserRepository#findCatUsersByIsAdoptedIsTrue()}
     *
     * @return List<CatUser>
     */
    public List<CatUser> findUsersByAdoptedIsTrue() {
        return catUserRepository.findCatUsersByIsAdoptedIsTrue();
    }

    /**
     * find CatUser if he exists by his ID
     * Use method CatUser repository {@link CatUserRepository#existsById(Object)}
     *
     * @return boolean
     */
    public boolean isExistsUser(long catUserChatId) {
        return catUserRepository.existsByChatId(catUserChatId);
    }

    /**
     * find CatUser with by ID
     * Use method User repository {@link CatUserRepository#findById(Object)}
     *
     * @return CatUser
     */
    public CatUser findUserByChatId(long userChatId) {
        return catUserRepository.findCatUserByChatId(userChatId);
    }

    /**
     * Use TelegramBot to Sent custom message to Cat User with chat ID.
     * <p>
     * Use method CatUserService {@link CatUserService#findUserByChatId(long)}
     * Use method TelegramBot {@link TelegramBot#sendMessageToUserByChatId(long, String)}
     *
     * @param chatId person to send
     * @param message to send
     * @return String that a message has been sent to the user
     * @throws UserNotFoundException when user with chat id not found
     */
    public String sendMessageToUserWithChatId(Long chatId, String message) {
        CatUser temp = findUserByChatId(chatId);
        if (temp == null) {
            throw new UserNotFoundException("User with chatId " + chatId + " not found");
        }
        mailingListService.sendMessageToUserByChatId(chatId, message);
        return "Message: " + message + "sent to User with chat Id: " + chatId;
    }

    /**
     * finds a user by chat id. changes him status. and sends him a message stating that he has passed the trial period
     * <p>
     * Use method CatUserService {@link CatUserService#findUserByChatId(long)}
     * Use method CatUserRepository {@link CatUserRepository#save(Object)}
     *
     * @param chatId of CatUser
     * @return CatUser
     * @throws UserNotFoundException when user with chatId not found
     */
    public CatUser changeStatusUserPassedProbationPeriod(Long chatId) {
        CatUser temp = findUserUtilityMethod(chatId);
        sendMessageToUserWithChatId(temp.getChatId(), messageTextService.get("passed.probation.period"));
        return catUserRepository.save(temp);
    }

    /**
     * finds a user by chat id. changes him status. and sends him a message stating that he has not passed the trial period
     * <p>
     * Use method CatUserService {@link CatUserService#findUserByChatId(long)}
     * Use method CatUserRepository {@link CatUserRepository#save(Object)}
     *
     * @param chatId of CatUser
     * @return CatUser
     * @throws UserNotFoundException when user with chatId not found
     */
    public CatUser changeStatusUserNotPassedProbationPeriod(Long chatId) {
        CatUser temp = findUserUtilityMethod(chatId);
        sendMessageToUserWithChatId(temp.getChatId(), messageTextService.get("not.passed.probation.period"));
        return catUserRepository.save(temp);
    }


    private CatUser findUserUtilityMethod(Long chatId) {
        CatUser temp = findUserByChatId(chatId);
        if (temp == null) {
            throw new UserNotFoundException("User with chat Id not found");
        }
        temp.setFinishDate(null);
        temp.setAdopted(false);
        temp.setPet(null);
        temp.setStartDate(null);
        return temp;
    }
}
