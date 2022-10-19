package com.telegrambot.jd501.service;

import com.telegrambot.jd501.model.PetReport;
import com.telegrambot.jd501.model.User;
import com.telegrambot.jd501.model.Volunteer;
import com.telegrambot.jd501.repository.InformationMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class BotService {
    private final Logger logger = LoggerFactory.getLogger(TelegramBot.class);

    private final UserService userService;
    private final VolunteerService volunteerService;
    private final InformationMessageRepository infoRepository;
    private final PetReportService petReportService;
    /**
     * String with start command in chat
     */
    private final String START_FIRST_COMMAND = "/start";
    private final String START_PHRASE_TO_VOLUNTEER = "*** Панель бота для волонтёров ***";
    private final String CHOOSE_MENU_ITEM_STRING = "*** Выберите интересующий пункт меню ***";

    private long messageId = 0;
    private boolean isReportButtonPressed = false;

    /**
     * ArrayList with button's names
     */
    private final List<String> BUTTONS_NAMES = new ArrayList<>(List.of(
            "Информация о приюте", "Как приютить питомца?",
            "Прислать отчет", "Оставить контакт для связи",
            "Позвать волонтера",

            "Общая информация", "Расписание работы, адрес",
            "Техника безопасности", "вернуться в главное меню",

            "Знакомство с питомцем", "Необходимые документы", "Как перевозить",
            "Дом для щенка", "Дом для взрослой собаки", "Дом для собаки с огр.возможностями",
            "Советы кинолога", "Контакты кинологов", "Причины, по которым могут отказать",
            "вернуться в главное меню",
            "вернуться в главное меню"

    ));


    public BotService(UserService userService, VolunteerService volunteerService, InformationMessageRepository infoRepository, PetReportService petReportService) {
        this.userService = userService;
        this.volunteerService = volunteerService;
        this.infoRepository = infoRepository;
        this.petReportService = petReportService;
    }


    //              *******  Меню 1 *******
    //        1 - Информация о приюте       (0)     2 - Как приютить питомца?     (1)
    //        3 - Прислать отчет            (2)     4 - Оставить данные для связи (3)
    //        5 - Позвать волонтера         (4)
    //             *******  Меню 2 *******
    //        11 - Общая информация         (5)     12 - Расписание работы, адрес (6)
    //        13 - Техника безопасности     (7)     0  - вернуться в главное меню (8)
    //             *******  Меню 3 *******
    //        21 - Знакомство с питомцем  (9)       22 - Необходимые документы (10)   23 - Как перевозить (11)
    //        24 - Дом для щенка (12)               25 - Дом для взрослой собаки (13) 26 - Дом для собаки с огр.возможностями (14)
    //        27 - Советы кинолога (15)             28 - Контакты кинологов (16)      29 - Причины, по которым могут отказать (17)
    //        0  - вернуться в главное меню (18)
    //        0  - вернуться в главное меню (19)

    /**
     * Setup sending message
     *
     * @param chatId      identificator of chat
     * @param messageText sending text
     * @return message to reply
     */
    public SendMessage setupSendMessage(long chatId, String messageText) {
        SendMessage message = new SendMessage();
        message.setParseMode("HTML");
        message.setChatId(chatId);
        message.setText(messageText);
        return message;
    }

    /**
     * Make buttons in Telegram's chat
     *
     * @param chatId           identificator of chat
     * @param namesOfButtons   list of button's names, must be not Null
     * @param startIndexButton start index of button
     * @param numberOfButtons  number of used buttons
     * @return message to reply
     */
    public SendMessage setButtons(Update update, List<String> namesOfButtons,
                                  int startIndexButton, int numberOfButtons) {
        logger.info("Setting of keyboard...");

        long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = setupSendMessage(chatId, "");

        // Create keyboard
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setResizeKeyboard(true);

        // Create list of strings of keyboard
        List<KeyboardRow> keyboard = new ArrayList<>();
        int indexOfButton = startIndexButton;
        // ==== if number of buttons is less than 6, we'll create 3 rows by 2 buttons in row.
        // ==== Else create 4 rows by 3 buttons in row.
        int numberButtonsInRow;
        int counterOfButtons = 1;
        int rows;
        if (numberOfButtons < 6) {
            rows = 3;
            numberButtonsInRow = 2;
        } else {
            rows = 4;
            numberButtonsInRow = 3;
        }
        for (int i = 0; i < rows; i++) {
            // Next row of keyboard
            KeyboardRow keyboardNextRow = new KeyboardRow();
            for (int f = 0; f < numberButtonsInRow; f++) {
                // Add buttons to this row of keyboard
                KeyboardButton keyboardButton = new KeyboardButton();
                // Check for contact's button
                if (namesOfButtons.get(indexOfButton).equals("Оставить контакт для связи")) {
                    keyboardButton.setRequestContact(true);
                }
                keyboardButton.setText(namesOfButtons.get(indexOfButton));
                keyboardNextRow.add(keyboardButton);
                indexOfButton++;
                counterOfButtons++;
                if (counterOfButtons > numberOfButtons) {
                    break;
                }
            }
            // Add row of keyboard to list
            keyboard.add(keyboardNextRow);
            if (counterOfButtons > numberOfButtons) {
                break;
            }
        }
        // Set created keyboard
        replyKeyboardMarkup.setKeyboard(keyboard);
        messageId = update.getMessage().getMessageId();
        // Return setup message
        return sendMessage;
    }

    /**
     * Check message from user (or what key is pressed)
     *
     * @param update list of incoming updates, must be not Null
     * @return message to reply
     */
    SendMessage checkInputMessage(Update update) {
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        SendMessage messageToSend = new SendMessage();
        // *** check chatId in volunteer db.
        // *** If it exists, return start greeting to volunteer.
        boolean isExistsVolunteer = volunteerService.isExistsVolunteer(chatId);
        if (isExistsVolunteer) {
            messageToSend.setText(START_PHRASE_TO_VOLUNTEER);
            messageToSend.setChatId(chatId);
            return messageToSend;
        }
        // ****************************************************
        int itemInList = -1;
        for (int i = 0; i < BUTTONS_NAMES.size(); i++) {
            if (BUTTONS_NAMES.get(i).equals(messageText)) {
                itemInList = i;
                break;
            }
        }
        switch (itemInList) {
            // --- 1 - "Информация о приюте" button is pressed  (0)---
            case 0:
                messageToSend = informAboutShelter(update);
                break;
            // --- 2 - "Как приютить питомца?" button is pressed (1)---
            case 1:
                messageToSend = informToPotentialAdopter(update);
                break;
            // --- 3 - "Прислать отчет" button is pressed (2)---
            case 2:
                messageToSend = waitForReport(update);
                isReportButtonPressed = true;
                break;
//            // --- 4 - "Оставить данные для связи" button is pressed (3)---
//            case 3:
//                //   messageToSend = getContact(update);
//                break;
            // --- 5 - "Позвать волонтера" (4) button is pressed ---
            case 4:
                messageToSend = callToVolunteer(update);
                break;
            // --- 0 - "вернуться в главное меню" (8) button is pressed ---
            case 8:
            case 18:
            case 19:
                // call main menu ---
                messageToSend = setButtons(update, BUTTONS_NAMES, 0, 5);
                messageToSend.setText(CHOOSE_MENU_ITEM_STRING);
                isReportButtonPressed = false;
                break;
            // --- 11 - "Общая информация" (5) button is pressed ---
            case 5:
                messageToSend = getInfo(chatId, 11);
                break;
            // --- 12 - "Расписание работы, адрес" (6) button is pressed ---
            case 6:
                messageToSend = getInfo(chatId, 12);
                break;
            // --- 13 - "Техника безопасности" (7) button is pressed ---
            case 7:
                messageToSend = getInfo(chatId, 13);
                break;

            //
            //        0  - вернуться в главное меню (18)
            // --- 21 - Знакомство с питомцем  (9) button is pressed ---
            case 9:
                messageToSend = getInfo(chatId, 21);
                break;
            // --- 22 - Необходимые документы (10) button is pressed ---
            case 10:
                messageToSend = getInfo(chatId, 22);
                break;
            // --- 23 - Как перевозить (11) button is pressed ---
            case 11:
                messageToSend = getInfo(chatId, 23);
                break;
            // --- 24 - Дом для щенка (12) button is pressed ---
            case 12:
                messageToSend = getInfo(chatId, 24);
                break;
            // --- 25 - Дом для взрослой собаки (13) button is pressed ---
            case 13:
                messageToSend = getInfo(chatId, 25);
                break;
            // --- 26 - Дом для собаки с огр.возможностями (14) button is pressed ---
            case 14:
                messageToSend = getInfo(chatId, 26);
                break;
            // --- 27 - Советы кинолога (15) button is pressed ---
            case 15:
                messageToSend = getInfo(chatId, 27);
                break;
            // --- 28 - Контакты кинологов (16) button is pressed ---
            case 16:
                messageToSend = getInfo(chatId, 28);
                break;
            // --- 29 - Причины, по которым могут отказать (17) button is pressed ---
            case 17:
                messageToSend = getInfo(chatId, 29);
                break;

            // -------- any other command / string -----
            default:
                if (isReportButtonPressed) {
                    messageToSend = saveReportToDB(update);
                } else {
                    messageToSend = setupSendMessage(chatId, CHOOSE_MENU_ITEM_STRING);
                }
                break;
        }
        // --- /start is pressed
        if (messageText.equals(START_FIRST_COMMAND)) {
            messageToSend = startCommandReceived(update);
        }
        return messageToSend;
    }

    /**
     * Greetings to user on start.
     * We say about our shelter and offer to take a choice of menu item .
     *
     * @param chatId identificator of user
     * @return message to reply
     */
    private SendMessage startCommandReceived(Update update) {
        String greeting = "Здравствуйте! Это официальный телеграмм-бот приюта животных PetShelter. Мы помогаем людям, которые задумались приютить питомца. " +
                "Для многих из Вас это первый опыт. Не волнуйтесь. Мы поможем с этим нелегким, но важным делом!\n" +
                CHOOSE_MENU_ITEM_STRING;
        SendMessage message = setButtons(update, BUTTONS_NAMES, 0, 5);
        message.setText(greeting);
        return message;
    }

    /**
     * Information menu about shelter
     *
     * @param chatId identificator of chat
     * @return message to reply
     */
    private SendMessage informAboutShelter(Update update) {
        logger.info("Change keyboard to menu informAboutShelter");
        String chooseItem = "Здесь Вы можете получить информацию о нашем приюте.\n" +
                CHOOSE_MENU_ITEM_STRING;
        SendMessage message = setButtons(update, BUTTONS_NAMES, 5, 4);
        message.setText(chooseItem);
        return message;
    }

    /**
     * Information menu for Potential Adopter
     *
     * @param chatId identificator of chat
     * @return message to reply
     */
    private SendMessage informToPotentialAdopter(Update update) {
        logger.info("Change keyboard to menu informToPotentialAdopter");
        String chooseItem = "Здесь мы собрали полезную информацию, которая поможет вам подготовиться ко встрече с новым членом семьи.\n" +
                CHOOSE_MENU_ITEM_STRING;
        SendMessage message = setButtons(update, BUTTONS_NAMES, 9, 10);
        message.setText(chooseItem);
        return message;
    }

    /**
     * Pet's report menu
     *
     * @param update list of incoming updates, must be not Null
     * @return message to reply
     */
    private SendMessage waitForReport(Update update) {
        long userChatId = update.getMessage().getChatId();
        SendMessage message = new SendMessage();
        message.setChatId(userChatId);
        message.setText("Вы не являетесь опекуном. Пожалуйста, обратитесь к волонтеру!");
        // --- check User:
        boolean userIsExists = userService.isExistsUser(userChatId);
        logger.info("userIsExists-" + userIsExists);
        // --- 1) exists?
        if (userIsExists) {
            User existUser = userService.findUserByChatId(userChatId);
            // --- 2) is adopted?
            if (existUser.getAdopted()) {
                // --- change keyboard to sending report
                String text = "Просим рассказать, как чувствует Ваш питомец. Какой сегодняшний рацион, " +
                        "общее самочувствие и привыкание к новому месту. " +
                        "Изменение в поведении: отказ от старых привычек, приобретение новых." +
                        "Также просим приложить фотографию.";
                message = setButtons(update, BUTTONS_NAMES, 19, 1);
                message.setText(text);
            }
        }
        return message;
    }

    private SendMessage saveReportToDB(Update update) {
        PetReport petReport = new PetReport();
        petReport.setDateOfReport(LocalDate.now());
    //    petReport.setUserId(update.getMessage().getChatId());
        petReport.setTextOfReport(update.getMessage().getText());
        petReportService.createPetReport(petReport);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Спасибо. Ваш отчет записан");
        return sendMessage;
    }

    /**

    /**
     * Reply to user that his phone number has saved
     *
     * @param update list of incoming updates, must be not Null
     * @return message to reply
     */
    SendMessage replyAboutSavingContact(Update update) {
        SendMessage message = new SendMessage();
        String textToShow = "Телефонный номер отсутствует";
        Contact contact = getContact(update);
        String userName = contact.getFirstName();
        textToShow = "Уважаемый " + userName + "! " + "С Вами обязательно свяжутся";
        message.setText(textToShow);
        long chatId = update.getMessage().getChatId();
        message.setChatId(chatId);
        logger.info("ChatId is " + chatId);
        message.setParseMode(ParseMode.MARKDOWN);
        return message;
    }

    /**
     * Extract contact from User data
     *
     * @param update list of incoming updates, must be not Null
     */
    private Contact getContact(Update update) {
        logger.info("getContact processing...");
        logger.info(update.toString());
        logger.info("=================================");
        Contact gettingContact = update.getMessage().getContact();
        logger.info(gettingContact.toString());
        return gettingContact;
    }

    /**
     * Send user's phone number to volunteer and save it into DB
     *
     * @param update list of incoming updates, must be not Null
     * @return message to send
     */
    SendMessage sendUserPhoneToVolunteer(Update update) {
        SendMessage message = new SendMessage();
        Contact contact = getContact(update);
        long userChatId = contact.getUserId();
        String phoneNumber = contact.getPhoneNumber();
        String firstName = contact.getFirstName();
        String userName = update.getMessage().getChat().getUserName();
        // --- (1) send contact to volunteer ---
        // *** get first volunteer ***
        Volunteer firstVolunteer = volunteerService.getAllVolunteer()
                .stream()
                .findFirst()
                .orElseThrow();
        logger.info(firstVolunteer.toString());
        message.setChatId(firstVolunteer.getChatId());
        message.setText("Уважаемый волонтер! Просьба связаться с пользователем: " + firstName + ". Его телефон " +
                phoneNumber + "  https://t.me/" + userName);
        // ---- (2) check contact in base -----
        boolean userIsExists = userService.isExistsUser(userChatId);
        if (!userIsExists) {
            // *** save phone number into DB, if contact doesn't exist
            User newUser = new User();
            newUser.setChatId(userChatId);
            newUser.setName(firstName);
            newUser.setPhone(phoneNumber);
            newUser.setAdopted(false);
            userService.createUser(newUser);
        }
        return message;
    }

    /**
     * Menu of volunteer's calling
     *
     * @param update identificator of chat
     */
    private SendMessage callToVolunteer(Update update) {
        SendMessage message = new SendMessage();
        String firstName = update.getMessage().getChat().getFirstName();
        String userName = update.getMessage().getChat().getUserName();
        // --- (1) send contact to volunteer ---
        // *** get first volunteer ***
        Volunteer firstVolunteer = volunteerService.getAllVolunteer()
                .stream()
                .findFirst()
                .orElseThrow();
        logger.info(firstVolunteer.toString());
        message.setChatId(firstVolunteer.getChatId());
        message.setText("Уважаемый волонтер! Просьба связаться с пользователем: " + firstName + " " +
                "https://t.me/" + userName);
        return message;
    }

    /**
     * Method of getting information from repository {@link InformationMessageRepository#findById(Object)} of general information about shelter
     *
     * @param chatId         identificator of chat
     * @param menuItemNumber id number of menu item
     */
    private SendMessage getInfo(long chatId, long menuItemNumber) {
        String info = infoRepository.findById(menuItemNumber).orElseThrow().getText();
        return setupSendMessage(chatId, info);
    }


    // =========================================================================

    /**
     * Every day test DB in 20:00 to check all yesterday reports are present in DB.
     * Photo and text about pet should be there.
     */
    @Scheduled(cron = "0 20 * * * *")
    public void runTestForReports() {
        // get all users with trial period
        List<User> toTestWithTrialPeriod = userService.findUsersByAdoptedIsTrue();

        // test, if users have sent reports yesterday and two days ago
        for (User petsMaster : toTestWithTrialPeriod) {
            PetReport petReportYesterday = petReportService.getPetReportByPetAndDateOfReport(petsMaster.getPet(), LocalDate.now().minusDays(1L));
            PetReport petReportTwoDaysAgo = petReportService.getPetReportByPetAndDateOfReport(petsMaster.getPet(), LocalDate.now().minusDays(2L));
            if (petReportYesterday == null && petReportTwoDaysAgo == null) {
                /* позвать волонтера!!!
                 * если пользователь не присылал 2 дня никакой информации (текст или фото), отправлять запрос волонтеру на связь с усыновителем.
                 * Текст: "Усыновитель petsMaster не отправляет информацию уже 2 дня".*/
                break;
            }
            if (petReportYesterday == null) {
                /* если пользователь не присылал вчера информацию (текст и фото),
                напоминаем: "Добрый день, мы не получили отчет о питомце за вчерашний день, пожалуйста, пришлите сегодня фотоотчет и информациюю о питомце".*/
                break;
            }
            if (petReportYesterday.getTextOfReport() == null && petReportYesterday.getPhotoLink() != null) {
                /* если пользователь не присылал вчера текст,
                напоминаем: "Добрый день, мы не получили рассказ о питомце за вчерашний день, пожалуйста, пришлите сегодня информацию о питомце". */
                break;
            }
            if (petReportYesterday.getTextOfReport() != null && petReportYesterday.getPhotoLink() == null) {
                /* если пользователь не присылал вчера фотоотчет,
                напоминаем: "Добрый день, мы не получили фотоотчет о питомце за вчерашний день, пожалуйста, пришлите сегодня фотоотчет о питомце". */
                break;
            }
        }
    }

    // =========================================================================

    /**
     * Every day test DB in 12:00 to check trial period has expired.
     * Photo and text about pet should be there.
     */
    @Scheduled(cron = "00 12 * * * *")
    public void runTestTrialPeriodHasExpired() {
        // get all users with trial period
        List<User> toTestWithTrialPeriod = userService.findUsersByAdoptedIsTrue();

        // test, users have the last day of trial period
        for (User petsMaster : toTestWithTrialPeriod) {
            if (petsMaster.getFinishDate().isBefore(LocalDate.now()) && petsMaster.getAdopted()) {
                /* если день>N и усыновитель в статусе "на проверке", бот отправляет запрос волонтеру.
                Текст: "N-ый день уже прошел! Срочно примите решение об успешном/неуспешном прохождении усыновителем испытательного срока или продлите испытательный срок".
                 */
                break;
            }
            if (petsMaster.getFinishDate().equals(LocalDate.now()) && petsMaster.getAdopted()) {
                /* если день=N и хозяин еще на испытательном сроке, бот отправляет запрос волонтеру.
                Текст: "Сегодня истекает N-ый день. Примите решение об успешном/неуспешном прохождении усыновителем испытательного срока или продлите испытательный срок". */
                break;
            }
        }
    }

}
