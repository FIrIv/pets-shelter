package com.telegrambot.jd501.service;

import com.telegrambot.jd501.Exceptions.PetNotFoundException;
import com.telegrambot.jd501.Exceptions.UserNotFoundException;
import com.telegrambot.jd501.model.Pet;
import com.telegrambot.jd501.model.User;
import com.telegrambot.jd501.repository.PetRepository;
import com.telegrambot.jd501.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final TelegramBot telegramBot;

    public UserService(UserRepository userRepository, PetRepository petRepository, TelegramBot telegramBot) {
        this.userRepository = userRepository;
        this.petRepository = petRepository;
        this.telegramBot = telegramBot;
    }

    /**
     * get All User from DataBase
     * Use method of User repository {@link UserRepository#findAll()} ()} (Collection< User >)}
     *
     * @return collection of User
     */
    public Collection<User> getAllUser() {
        return userRepository.findAll();
    }

    /**
     * add new User in DataBase
     *
     * @param user Use  method InformationMessage repository {@link UserRepository#save(Object)} (User)}
     * @return InformationMessage
     */
    public User createUser(User user) {
        user.setStartDate(null);
        user.setFinishDate(null);
        user.setPet(null);
        user.setAdopted(false);
        return userRepository.save(user);
    }

    /**
     * change User in DataBase
     * Use  method User repository {@link UserRepository#save(Object)} (User)}
     *
     * @param user
     * @return User
     * @throws com.telegrambot.jd501.Exceptions.InformationMessageNotFoundException if User with id not found
     */
    public User updateUser(User user) {
        userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        return userRepository.save(user);
    }

    /**
     * delete user from DataBase by id
     * Use  method User repository {@link UserRepository#deleteById(Object)} } (Long id)}
     *
     * @param id
     * @return Deleted User
     * @throws com.telegrambot.jd501.Exceptions.UserNotFoundException if User with id not found
     */
    public User deleteUser(Long id) {
        User temp = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.deleteById(id);
        return temp;
    }

    /**
     * find user by id and change amount of probation period, and send message to user
     * Use method User repository {@link UserRepository#findById(Object)}
     * Use  method User repository {@link UserRepository#save(Object)} }
     * Use method TelegramBot {@link TelegramBot#sendMessageToUserByChatId(long, String)}
     *
     * @param id   - user id for fing user in repository,
     * @param days - number of days to increase the term of the transfer
     * @return notification that probationary period has been extended (String)
     */
    public String probationPeriodExtension(Long id, Integer days) {
        User temp = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        temp.setFinishDate(temp.getFinishDate().plusDays(days));
        userRepository.save(temp);
        telegramBot.sendMessageToUserByChatId(temp.getChatId(), "Ваш испытательный срок увеличен на " + days + " дней");
        return "Probationary period " + temp.getName() + " increased by" + days + " days";
    }

    /**
     * find user by id and change  status of The Adopter, add adopted Pet, Date of adoption, set test day by 30, and send message to user
     * Use method User repository {@link UserRepository#findById(Object)}
     * Use  method User repository {@link UserRepository#save(Object)} }
     * Use  method Pet repository {@link PetRepository#findById(Object)} }
     * Use method TelegramBot {@link TelegramBot#sendMessageToUserByChatId(long, String)}
     * @param userId - user id for fing user in repository,
     * @param petId  - pet id for fing user in repository,
     * @return Changed User
     */
    public User changeStatusOfTheAdopter(Long userId, Long petId) {
        User userTemp = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Pet petTemp = petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException("Pet not found"));
        userTemp.setAdopted(true);
        userTemp.setPet(petTemp);
        userTemp.setStartDate(LocalDate.now());
        userTemp.setFinishDate(LocalDate.now().plusDays(30));
        telegramBot.sendMessageToUserByChatId(userTemp.getChatId(), "Поздравляем! Вы стали усыновителем." +
                " Вам назначен испытательный срок 30 дней." +
                " По истечении которых мы примем решение, можем ли оставить вам питомца," +
                "этот срок так же может быть увеличен если мы сочтем что в этом есть необходимость."
                );
        return userRepository.save(userTemp);
    }
    /**
     * find user by id and send message for him about successful completion of probation period.
     * and delete Pet from PetRepository
     *
     * Use method User repository {@link UserRepository#save(Object)}
     * Use method Pet repository {@link PetRepository#deleteById(Object)}
     * Use method TelegramBot {@link TelegramBot#sendMessageToUserByChatId(long, String)}
     * @param userId   - user id,
     * @return notification that probationary period successful completing (String)
     * or UserNotFoundException or PetNotFoundException
     */
    public String successfulCompletionOfTheProbationaryPeriod (Long userId){
        User userTemp = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
        Pet petTemp = petRepository.findById(userTemp.getPet().getId()).orElseThrow(()->new PetNotFoundException("Питомец не найден"));
        userTemp.setAdopted(false);
        userTemp.setStartDate(null);
        userTemp.setPet(null);
        userTemp.setFinishDate(null);
        userRepository.save(userTemp);
        petRepository.deleteById(petTemp.getId());
        telegramBot.sendMessageToUserByChatId(userTemp.getChatId(),"Поздравляем! Вы успешно прошли испытательный период.");
        return "Пользователю " + userTemp.getName() + " c chatId: " + userTemp.getChatId() + " отправлено сообщение об успешном прохождении испытательного срока. " +
                " Питомец удален из списка питомцев приюта";
    }
    /**
     * find user by id and send message for him about the fact that he did not pass the probationary period
     * Use method User repository {@link UserRepository#findById(Object)}
     * Use method User repository {@link UserRepository#save(Object)}
     * Use method Pet repository {@link PetRepository#findById(Object)}
     * Use method TelegramBot {@link TelegramBot#sendMessageToUserByChatId(long, String)}
     * @param userId   - user id,
     * @return notification that probationary period did not pass (String)
     * or UserNotFoundException
     */
    public String didNotPassProbationPeriod (Long userId){
        User userTemp = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
        userTemp.setAdopted(false);
        userTemp.setStartDate(null);
        userTemp.setPet(null);
        userTemp.setFinishDate(null);
        userRepository.save(userTemp);
        telegramBot.sendMessageToUserByChatId(userTemp.getChatId(),"К сожалению вы не прошли испытательный период.");
        return "Пользователю " + userTemp.getName() + " c chatId: " + userTemp.getChatId() + " отправлено сообщение о том что он не прошел испытательный период";
    }
    /**
     * find users with status "adopted = true"
     * Use method User repository {@link UserRepository#findUsersByIsAdoptedIsTrue()}
     *
     * @return List<User>
     */
    public List<User> findUsersByAdoptedIsTrue() {
        return userRepository.findUsersByIsAdoptedIsTrue();
    }

    /**
     * find user if he exists by his ID
     * Use method User repository {@link UserRepository#existsById(Object)}
     *
     * @return boolean
     */
    public boolean isExistsUser(long userChatId) {
        return userRepository.existsByChatId(userChatId);
    }

    /**
     * find user with by ID
     * Use method User repository {@link UserRepository#findById(Object)}
     *
     * @return User
     */
    public User findUserByChatId(long userChatId) {
        return userRepository.findUserByChatId(userChatId);
    }


}
