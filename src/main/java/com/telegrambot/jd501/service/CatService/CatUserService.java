package com.telegrambot.jd501.service.CatService;

import com.telegrambot.jd501.Exceptions.PetNotFoundException;
import com.telegrambot.jd501.Exceptions.UserNotFoundException;
import com.telegrambot.jd501.model.cat.Cat;
import com.telegrambot.jd501.model.cat.CatUser;
import com.telegrambot.jd501.repository.Cat.CatRepository;
import com.telegrambot.jd501.repository.Cat.CatUserRepository;
import com.telegrambot.jd501.service.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Service
public class CatUserService {
    private final CatUserRepository catUserRepository;
    private final CatRepository catRepository;
    @Lazy
    @Autowired
    private TelegramBot telegramBot;

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
    public Collection <CatUser> getAllUsers() {
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
     * @param catUser
     * @return CatUser
     * @throws UserNotFoundException if CatUser with id not found
     */
    public CatUser updateUser(CatUser catUser) {
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
    public CatUser deleteUser(Long id) {
        CatUser temp = catUserRepository.findById(id).orElseThrow(() -> new UserNotFoundException("CatUser not found"));
        catUserRepository.deleteById(id);
        return temp;
    }

    /**
     * find CatUser by id, change amount of probation period, and sent message to user about change probation period
     * Use method CatUser repository {@link CatUserRepository#findById(Object)}
     * Use  method CatUser repository {@link CatUserRepository#save(Object)} }
     *
     * @param id   - CatUser id for find CatUser in repository,
     * @param days - number of days to increase the term of the transfer
     * @return CatUser
     */
    public CatUser probationPeriodExtension(Long id, Integer days) {
        CatUser temp = catUserRepository.findById(id).orElseThrow(() -> new UserNotFoundException("CatUser not found"));
        temp.setFinishDate(temp.getFinishDate().plusDays(days));
        catUserRepository.save(temp);
        sendMessageToUserWithChatId(temp.getChatId(),
                "Ваш испытательный срок был увеличен на: " + days + " дней," +
        " не забывайте как и раньше вовремя направлять нам отчеты " +
        " Если у вас остались вопросы, мы с радостью ответим на них в нашем телеграмм боте");
        return temp;
    }

    /**
     * find user by id and change  status of The Adopter, add adopted Pet, Date of adoption, set test day by 30,
     * and sent message to user about change him status.
     *
     * Use method CatUser repository {@link CatUserRepository#findById(Object)}
     * Use  method CatUser repository {@link CatUserRepository#save(Object)} }
     * Use  method CatUser repository {@link CatRepository#findById(Object)} }
     * Use method CatUserService {@link CatUserService#sendMessageToUserWithChatId(Long, String)}
     * @param userId - user id for fing user in repository,
     * @param catId  - pet id for fing user in repository,
     * @return Changed User
     */
    public CatUser changeStatusOfTheAdopter(Long userId, Long catId) {
        CatUser userTemp = catUserRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Cat User not found"));
        Cat petTemp = catRepository.findById(catId).orElseThrow(() -> new PetNotFoundException("Cat not found"));
        userTemp.setAdopted(true);
        userTemp.setPet(petTemp);
        userTemp.setStartDate(LocalDate.now());
        userTemp.setFinishDate(LocalDate.now().plusDays(30));
        sendMessageToUserWithChatId(userTemp.getChatId(),
                "Поздравляю! Вы стали усыновителем." +
                        " Не забывайте вовремя отправлять отчеты." +
                        " Если у вас остались вопросы, мы с радостью ответим на них в нашем телеграмм боте");
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
     *
     * Use method CatUserService {@link CatUserService#findUserByChatId(long)}
     * Use method TelegramBot {@link TelegramBot#sendMessageToUserByChatId(long, String)}
     * @param chatId
     * @param message
     * @return String that a message has been sent to the user
     * @throws UserNotFoundException when user with chat id not found
     */
    public String sendMessageToUserWithChatId(Long chatId, String message) {
        CatUser temp = findUserByChatId(chatId);
        if (temp == null) {
            throw new UserNotFoundException("User with chatId " + chatId + " not found");
        }
        telegramBot.sendMessageToUserByChatId(chatId, message);
        return "Message: "+ message + "sent to User with chat Id: " + chatId;
    }

    /**
     *
     * finds a user by chat id. changes him status. and sends him a message stating that he has passed the trial period
     *
     * Use method CatUserService {@link CatUserService#findUserByChatId(long)}
     * Use method CatUserRepository {@link CatUserRepository#save(Object)}
     * @param chatId
     * @return CatUser
     * @throws UserNotFoundException when usen with chatId not found
     */
    public CatUser changeStatusUserPassedProbationPeriod(Long chatId){
        CatUser temp = findUserByChatId(chatId);
        if (temp == null){
            throw new UserNotFoundException("User with chat Id not found");
        }
        temp.setFinishDate(null);
        temp.setAdopted(false);
        temp.setPet(null);
        temp.setStartDate(null);
        sendMessageToUserWithChatId(temp.getChatId(), "Поздравляем!" +
                " вы успешно прошли испытательный срок" +
                " вам больше не нужно отправлять отчеты" +
                " Если у вас остались вопросы, мы с радостью ответим на них в нашем телеграмм боте");
        return catUserRepository.save(temp);
    }
    /**

     *finds a user by chat id. changes him status. and sends him a message stating that he has not passed the trial period
     *
     * Use method CatUserService {@link CatUserService#findUserByChatId(long)}
     * Use method CatUserRepository {@link CatUserRepository#save(Object)}
     * @param chatId
     * @return CatUser
     * @throws UserNotFoundException when usen with chatId not found
     */
    public CatUser changeStatusUserNotPassedProbationPeriod(Long chatId){
       CatUser temp = findUserByChatId(chatId);
       if (temp == null){
           throw new UserNotFoundException("User with chat Id not found");
       }
       temp.setFinishDate(null);
       temp.setAdopted(false);
       temp.setPet(null);
       temp.setStartDate(null);
       sendMessageToUserWithChatId(temp.getChatId(), "К сожалению вы не прошли испытательный срок." +
               " Мы не сможем оставить вам питомца." +
               " Если у вас остались вопросы, мы с радостью ответим на них в нашем телеграмм боте");
      return catUserRepository.save(temp);
    }
}
