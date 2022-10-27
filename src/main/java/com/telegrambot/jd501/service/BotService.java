package com.telegrambot.jd501.service;


import com.telegrambot.jd501.model.cat.CatReport;
import com.telegrambot.jd501.model.cat.CatUser;
import com.telegrambot.jd501.model.cat.CatVolunteer;
import com.telegrambot.jd501.model.dog.DogReport;
import com.telegrambot.jd501.model.dog.DogUser;
import com.telegrambot.jd501.model.dog.DogVolunteer;
import com.telegrambot.jd501.service.CatService.CatInformationMessageService;
import com.telegrambot.jd501.service.CatService.CatReportService;
import com.telegrambot.jd501.service.CatService.CatUserService;
import com.telegrambot.jd501.service.CatService.CatVolunteerService;
import com.telegrambot.jd501.service.DogService.DogInformationMessageService;
import com.telegrambot.jd501.service.DogService.DogReportService;
import com.telegrambot.jd501.service.DogService.DogUserService;
import com.telegrambot.jd501.service.DogService.DogVolunteerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.time.LocalDate;
import java.util.*;


@Service
public class BotService {
    private final Logger logger = LoggerFactory.getLogger(TelegramBot.class);

    private final CatUserService catUserService;
    private final DogUserService dogUserService;
    private final CatVolunteerService catVolunteerService;
    private final DogVolunteerService dogVolunteerService;
    private final CatInformationMessageService catInformationMessageService;
    private final DogInformationMessageService dogInformationMessageService;
    private final CatReportService catReportService;
    private final DogReportService dogReportService;

    public BotService(CatUserService catUserService, DogUserService dogUserService,
                      CatVolunteerService catVolunteerService, DogVolunteerService dogVolunteerService,
                      CatInformationMessageService catInformationMessageService, DogInformationMessageService dogInformationMessageService,
                      CatReportService catReportService, DogReportService dogReportService) {
        this.catUserService = catUserService;
        this.dogUserService = dogUserService;
        this.catVolunteerService = catVolunteerService;
        this.dogVolunteerService = dogVolunteerService;
        this.catInformationMessageService = catInformationMessageService;
        this.dogInformationMessageService = dogInformationMessageService;
        this.catReportService = catReportService;
        this.dogReportService = dogReportService;
    }

    /**
     * String with start command in chat
     */
    private final String START_FIRST_COMMAND = "/start";
    private final String START_PHRASE_TO_VOLUNTEER = "*** Панель бота для волонтёров ***";
    private final String CHOOSE_MENU_ITEM_STRING = "*** Выберите интересующий пункт меню ***";

    private final String TEXT_ABOUT_REPORT = "Расскажите, как чувствует себя питомец. Какой сегодняшний рацион, " +
            "общее самочувствие, и как он привыкает к новому месту. " +
            "Меняется ли поведение: отказ от старых привычек, приобретение новых и т.д.\n" +
            "<>Также просим приложить фотографию (нажав на 'скрепочку').";

    private boolean isDog;
    private boolean isReportButtonPressed = false;

    /**
     * ArrayList with button's names
     */
    private final List<String> BUTTONS_NAMES = new ArrayList<>(List.of(
            // ===== Этап 0 (Меню 1) ======
            // ===  Меню выбора приютов (кошки/собаки) =====
            "Приют для кошек", "Приют для собак",
            // =========== 0 ================ 1 ===========

            // === Этап 1 (Меню 2) =====
            // === Меню "Узнать информацию о приюте"
            "Информация о приюте", "Как приютить питомца?",
            // =========== 2 ================ 3 ===========
            "Прислать отчет", "Оставить контакт для связи",
            // =========== 4 ================ 5 ===========
            "Позвать волонтера", "Выбрать приют (собаки/кошки)",
            // =========== 6 ================ 7 ===========

            // === Этап 1 (Меню 3) =====
            // === Меню "Консультация с новым пользователем"
            "Общая информация", "Расписание работы, адрес",
            // =========== 8 ================ 9 ===========
            "Оформить пропуск", "Техника безопасности",
            // =========== 10 ================ 11 ===========
            "Оставить контакт для связи", "Позвать волонтера",
            // =========== 12 ================ 13 ===========
            "в Главное меню",
            // =========== 14 ================

            // === Этап 2 (Меню 4) =====
            // === Меню "Консультация с потенциальным хозяином"
            "Знакомство с питомцем", "Необходимые документы", "Как перевозить",
            // ==== 15 ===================== 16 =================== 17 ===================
            "Дом для детеныша", "Дом для взрослого питомца", "Дом для питомца с огр.возможностями",
            // ==== 18 ===================== 19 =================== 20 ===================
            "Причины, по которым могут отказать", "в Главное меню", "Позвать волонтера",
            // ============ 21 ======================== 22 =============== 23 ================
            "Советы кинолога", "Контакты кинологов"
            // === 24 =============== 25 ===========
    ));

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
     * @param chatId           Id of chat, must be not Null
     * @param namesOfButtons   list of button's names, must be not Null
     * @param startIndexButton start index of button
     * @param numberOfButtons  number of used buttons
     * @return message to reply
     */
    public SendMessage setButtons(long chatId, List<String> namesOfButtons,
                                  int startIndexButton, int numberOfButtons) {
        logger.info("Setting of keyboard... First button - " + namesOfButtons.get(startIndexButton));

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

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
        if (numberOfButtons < 7) {
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
        boolean isExistsVolunteer;
        if (isDog) {
            isExistsVolunteer = dogVolunteerService.isExistsVolunteer(chatId);
        } else {
            isExistsVolunteer = catVolunteerService.isExistsVolunteer(chatId);
        }
        if (isExistsVolunteer) {
            messageToSend.setText(START_PHRASE_TO_VOLUNTEER);
            messageToSend.setChatId(chatId);
            return messageToSend;
        }
        // ****************************************************
        // Get index of list's items
        int itemInList = -1;
        for (int i = 0; i < BUTTONS_NAMES.size(); i++) {
            if (BUTTONS_NAMES.get(i).equals(messageText)) {
                itemInList = i;
                break;
            }
        }
        // Check index and choose action
        switch (itemInList) {
            // --- "Приют для кошек" button is pressed  (0)---
            case 0:
                isDog = false;
                messageToSend = informAboutShelter(chatId);
                break;
            // --- "Приют для собак" button is pressed  (1)---
            case 1:
                isDog = true;
                messageToSend = informAboutShelter(chatId);
                break;
            // --- "Информация о приюте" button is pressed  (2)---
            case 2:
                messageToSend = consultNewUser(chatId);
                break;
            // --- "в Главное меню" button is pressed  (14, 22)---
            case 14:
            case 22:
                messageToSend = informAboutShelter(chatId);
                break;
            // --- "Как приютить питомца?" button is pressed (3)---
            case 3:
                messageToSend = informToPotentialAdopter(chatId);
                break;
            // ---- "Прислать отчет" button is pressed (4)---
            case 4:
                messageToSend = waitForReport(chatId);
                break;
//            // -- - "Оставить данные для связи" button is pressed (5)---
//            case 5:
//                //   messageToSend = getContact(update);
//                break;

            // --- "Позвать волонтера" (6) button is pressed ---
            case 6:
            case 13:
            case 23:
                messageToSend = callToVolunteer(update);
                break;
            // --- "Выбрать приют (собаки/кошки)" (8) button is pressed ---
            case 7:
                messageToSend = startCommandReceived(chatId);
                isReportButtonPressed = false;
                break;

            // -- - "Общая информация" (8) button is pressed ---
            case 8:
                messageToSend = getInfo(chatId, 11);
                break;

            // --- "Расписание работы, адрес" (9) button is pressed ---
            case 9:
                messageToSend = getInfo(chatId, 12);
                break;

            // --- "Оформить пропуск" (10) button is pressed ---
            case 10:
                messageToSend = getInfo(chatId, 30);
                break;

            // ---  "Техника безопасности" (11) button is pressed ---
            case 11:
                messageToSend = getInfo(chatId, 13);
                break;

            // ---  Знакомство с питомцем  (15) button is pressed ---
            case 15:
                messageToSend = getInfo(chatId, 21);
                break;

            // ---  Необходимые документы (16) button is pressed ---
            case 16:
                messageToSend = getInfo(chatId, 22);
                break;

            // ---  Как перевозить (17) button is pressed ---
            case 17:
                messageToSend = getInfo(chatId, 23);
                break;

            // ---  Дом для детеныша (18) button is pressed ---
            case 18:
                messageToSend = getInfo(chatId, 24);
                break;

            // --- Дом для взрослого питомца (19) button is pressed ---
            case 19:
                messageToSend = getInfo(chatId, 25);
                break;

            // ---  Дом для питомца с огр.возможностями (20) button is pressed ---
            case 20:
                messageToSend = getInfo(chatId, 26);
                break;

            // --- Причины, по которым могут отказать (21) button is pressed ---
            case 21:
                messageToSend = getInfo(chatId, 29);
                break;

            // ---  Советы кинолога (24) button is pressed ---
            case 24:
                messageToSend = getInfo(chatId, 27);
                break;

            // ---  Контакты кинологов (25) button is pressed ---
            case 25:
                messageToSend = getInfo(chatId, 28);
                break;

            // -------- any other command / string -----
            default:
                if (isReportButtonPressed) {
                    messageToSend = checkSavingReportToDB(update);
                } else {
                    messageToSend = setupSendMessage(chatId, CHOOSE_MENU_ITEM_STRING);
                }
                break;
        }

        // --- /start is pressed
        if (messageText.equals(START_FIRST_COMMAND)) {
            messageToSend = startCommandReceived(chatId);
        }
        return messageToSend;
    }

    /**
     * Greetings to user on start.
     * We say about our shelter and offer to take a choice of menu item .
     *
     * @param chatId           Id of chat, must be not Null
     * @return message to reply
     */
    private SendMessage startCommandReceived(long chatId) {
        String greeting = "Здравствуйте! Это официальный телеграмм-бот приютов для животных PetShelter. Мы помогаем людям, которые задумались приютить питомца. " +
                "Для многих из Вас это первый опыт. Не волнуйтесь. Мы поможем с этим нелегким, но важным делом!\n" +
                CHOOSE_MENU_ITEM_STRING;
        SendMessage message = setButtons(chatId, BUTTONS_NAMES, 0, 2);
        message.setText(greeting);
        return message;
    }

    /**
     * Information menu about shelter
     *
     * @param chatId           Id of chat, must be not Null
     * @return message to reply
     */
    private SendMessage informAboutShelter(long chatId) {
        logger.info("Change keyboard to menu informAboutShelter, isDog = " + isDog);
        String kindOfPet;
        if (isDog) {
            kindOfPet = "собак.";
        } else {
            kindOfPet = "кошек.";
        }
        String chooseItem = "Здесь Вы можете получить информацию о нашем приюте для " + kindOfPet + "\n"
                + CHOOSE_MENU_ITEM_STRING;
        SendMessage message = setButtons(chatId, BUTTONS_NAMES, 2, 6);
        message.setText(chooseItem);
        return message;
    }

    /**
     * Menu of new user consulting
     *
     * @param chatId           Id of chat, must be not Null
     * @return message to reply
     */
    private SendMessage consultNewUser(long chatId) {
        logger.info("Change keyboard to menu consultNewUser, isDog = " + isDog);
        String chooseItem = CHOOSE_MENU_ITEM_STRING;
        SendMessage message = setButtons(chatId, BUTTONS_NAMES, 8, 7);
        message.setText(chooseItem);
        return message;
    }

    /**
     * Information menu for Potential Adopter
     *
     * @param chatId           Id of chat, must be not Null
     * @return message to reply
     */
    private SendMessage informToPotentialAdopter(long chatId) {
        logger.info("Change keyboard to menu informToPotentialAdopter");
        String chooseItem = "Здесь мы собрали полезную информацию, которая поможет вам подготовиться ко встрече с новым членом семьи.\n" +
                CHOOSE_MENU_ITEM_STRING;
        int nubButt;
        if (isDog) {
            nubButt = 11;
        } else {
            nubButt = 9;
        }
        SendMessage message = setButtons(chatId, BUTTONS_NAMES, 15, nubButt);
        message.setText(chooseItem);
        return message;
    }

    /**
     * Pet's report menu
     *
     * @param userChatId           Id of chat, must be not Null
     * @return message to reply
     */
    private SendMessage waitForReport(long userChatId) {
        logger.info("Wait for report......");
        SendMessage message = new SendMessage();
        String text = "Вы не являетесь опекуном. Пожалуйста, обратитесь к волонтеру!";
        message.setText(text);
        message.setChatId(userChatId);
        // --- check User:
        boolean userIsExists = false;
        boolean userIsAdopted = false;
        if (isDog) {
            // --- 1) exists? 2) adopted? ---
            userIsExists = dogUserService.isExistsUser(userChatId);
            if (userIsExists) {
                userIsAdopted = dogUserService.findUserByChatId(userChatId).getAdopted();
            }
            message = goToTakingForReport(message, userIsExists, userIsAdopted);
        } else {
            // --- 1) exists? 2) adopted? ---
            userIsExists = catUserService.isExistsUser(userChatId);
            if (userIsExists) {
                userIsAdopted = catUserService.findUserByChatId(userChatId).getAdopted();
            }
            message = goToTakingForReport(message, userIsExists, userIsAdopted);
        }
        return message;
    }

    private SendMessage goToTakingForReport(SendMessage message, boolean isExists, boolean isAdopted) {
        long chatId = Long.parseLong(message.getChatId());
        logger.info(String.format("chatId-%s, isExists-%s, isAdopted-%s", chatId, isExists, isAdopted));
        if (isExists && isAdopted) {
            logger.info("User is exists and adopted. Take report ");
            // --- change keyboard to sending report
            message = setButtons(chatId, BUTTONS_NAMES, 14, 1);
            message.setText(TEXT_ABOUT_REPORT);
            isReportButtonPressed = true;
        }
        return message;
    }

    /**
     * Save report to data base
     *
     * @param update list of incoming updates, must be not Null
     * @return message to reply
     */
    private SendMessage checkSavingReportToDB(Update update) {
        logger.info("checkSavingReportToDB method is running...");
        boolean weCanSaveReport = true;
        long chatId = update.getMessage().getChatId();
        String reportText = update.getMessage().getText();
        String answerText = " ";
        // |||||||||  DOGS |||||||||
        // *** Check who is sending report: dogUser or catUser.
        //     Check saving report from this user today
        if (isDog) {
            Collection<DogReport> reports = dogReportService.getAllPetReportsByChatId(chatId);
            // ---  if reports are filled in
            if (!reports.isEmpty()) {
                logger.info("check for existing reports...");
                for (DogReport existReport : reports) {
                    // if the reports contain entries for today
                    if (existReport.getDateOfReport().isEqual(LocalDate.now())) {
                        weCanSaveReport = false;
                        logger.info("... today report is it:  " + existReport);
                        // === check for existing text report ==
                        if (!existReport.getTextOfReport().contains("textOfReport='null'")) {
                            logger.warn("* Today report already was saved! *");
                            answerText = "Вы уже сегодня направляли отчет. Для его корректировки, обратитесь к волонтеру.";
                        }
                        // === check for existing photo in report ==
                        if (existReport.getPhoto() == null) {
                            logger.warn("# Report doesn't have photo! #");
                            answerText += "\n* Не забудьте приложить фотографию питомца! *";
                        }
                        break;
                    }
                }
            }
        }
        if (!isDog) {
            // |||||||||  CATS |||||||||
            // *** Check who is sending report: dogUser or catUser.
            //     Check saving report from this user today
            Collection<CatReport> reports = catReportService.getAllPetReportsByChatId(chatId);
            // ---  if reports are filled in
            if (!reports.isEmpty()) {
                logger.info("check for existing reports...");
                for (CatReport existReport : reports) {
                    // if the reports contain entries for today
                    if (existReport.getDateOfReport().isEqual(LocalDate.now())) {
                        weCanSaveReport = false;
                        logger.info("... today report is it:  " + existReport);
                        // === check for existing text report ==
                        if (!existReport.getTextOfReport().contains("textOfReport='null'")) {
                            logger.warn("* Today report already was saved! *");
                            answerText = "Вы уже сегодня направляли отчет. Для его корректировки, обратитесь к волонтеру.";
                        }
                        // === check for existing photo in report ==
                        if (existReport.getPhoto() == null) {
                            logger.warn("# Report doesn't have photo! #");
                            answerText += "\n* Не забудьте приложить фотографию питомца! *";
                        }
                        break;
                    }
                }
            }
        }
         if (weCanSaveReport){
             saveReportToDB(chatId, reportText);
             answerText = "Спасибо. Ваш отчет записан";
         }
        return setupSendMessage(chatId, answerText);
    }

    private void saveReportToDB(long chatId, String reportText) {
        logger.info("Report for today doesn't exist. Saving text of report into DB...");
        if (isDog) {
            // save into db Dog new report ==================================================
            DogReport dogReport = new DogReport();
            DogUser dogUser = dogUserService.findUserByChatId(chatId);
            dogReport.setDateOfReport(LocalDate.now());
            dogReport.setTextOfReport(reportText);
            dogReport.setDogUser(dogUser);
            dogReportService.createPetReport(dogReport);
            // =========================================================================
        } else {
            // save into db Cat new report ==================================================
            CatReport catReport = new CatReport();
            CatUser catUser = catUserService.findUserByChatId(chatId);
            catReport.setDateOfReport(LocalDate.now());
            catReport.setTextOfReport(reportText);
            catReport.setCatUser(catUser);
            catReportService.createPetReport(catReport);
            // =========================================================================
        }
    }

    /**
     * /**
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

    SendMessage getPicture(Update update) {
        long chatId = update.getMessage().getChatId();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        logger.info("getting picture...");
        if (!isReportButtonPressed) {
            message.setText(CHOOSE_MENU_ITEM_STRING);
            return message;
        }
        Document getFile = update.getMessage().getDocument();
        String fileName = getFile.getFileName();
        String fileType = getFile.getMimeType();
        //   String fileTemp = getFile.getFileUniqueId();
        String text = String.format("Получен файл %s, fileType: %s", fileName, fileType);
        message.setText(text);
        return message;
    }

    /**
     * Send user's phone number to volunteer and save it into DB
     *
     * @param update list of incoming updates, must be not Null
     * @return message to send
     */
    SendMessage sendUserPhoneToVolunteer(Update update) {
        // --- Make message for sending contact to volunteer ---
        SendMessage message = callToVolunteer(update);
        String textToVolunter = message.getText();
        // --- extract from contact's data: chatId, first name and phone number
        Contact contact = getContact(update);
        long userChatId = contact.getUserId();
        String phoneNumber = contact.getPhoneNumber();
        String firstName = contact.getFirstName();
        String addPhoneNumberToVolunteerText;
        if (textToVolunter.contains("*** Извините, произошла ошибка!")) {
            addPhoneNumberToVolunteerText = textToVolunter;
        } else {
            addPhoneNumberToVolunteerText = textToVolunter + " . Его телефон " + phoneNumber + " chatId-" + userChatId;
        }
        message.setText(addPhoneNumberToVolunteerText);
        // --- work with data base ---
        if (isDog) {
            // ---- check contact in base -----
            boolean userIsExists = dogUserService.isExistsUser(userChatId);
            if (!userIsExists) {
                // *** save phone number into DB, if contact doesn't exist
                DogUser newUser = new DogUser();
                newUser.setChatId(userChatId);
                newUser.setName(firstName);
                newUser.setPhone(phoneNumber);
                newUser.setAdopted(false);
                dogUserService.createUser(newUser);
            }
        } else {
            // ---- (2) check contact in base -----
            boolean userIsExists = catUserService.isExistsUser(userChatId);
            if (!userIsExists) {
                // *** save phone number into DB, if contact doesn't exist
                CatUser newUser = new CatUser();
                newUser.setChatId(userChatId);
                newUser.setName(firstName);
                newUser.setPhone(phoneNumber);
                newUser.setAdopted(false);
                catUserService.createUser(newUser);
            }
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
        DogVolunteer firstDogVolunteer;
        CatVolunteer firstCatVolunteer;
        // --- Make message for sending contact to volunteer ---
        // *** get first volunteer ***
        message.setText("\n*** Извините, произошла ошибка! *** \n Мы уже работаем над ее устранением. \n" +
                "Просьба повторить запрос позднее.");
        message.setChatId(update.getMessage().getChatId());
        try {
            if (isDog) {
                firstDogVolunteer = dogVolunteerService.getAllVolunteers()
                        .stream()
                        .findFirst()
                        .orElseThrow();
                logger.info(firstDogVolunteer.toString());
                message.setChatId(firstDogVolunteer.getChatId());
                message.setText("Уважаемый волонтер! Просьба связаться с пользователем: " + firstName +
                        "  https://t.me/" + userName);
            } else {
                firstCatVolunteer = catVolunteerService.getAllVolunteers()
                        .stream()
                        .findFirst()
                        .orElseThrow();
                logger.info(firstCatVolunteer.toString());
                message.setChatId(firstCatVolunteer.getChatId());
                message.setText("Уважаемый волонтер! Просьба связаться с пользователем: " + firstName +
                        "  https://t.me/" + userName);
            }
        } catch (NoSuchElementException e) {
            logger.error("Volunteer doesn't exist in DB ### " + e.getMessage());
        }
        logger.info("Contact of volunteer is ::::::::::::::: " + message.toString());
        return message;
    }

    /**
     * Method of getting information from Dog or Cat service of general information about shelter
     *
     * @param chatId         identificator of chat
     * @param menuItemNumber id number of menu item
     */
    private SendMessage getInfo(long chatId, long menuItemNumber) {
        String info;
        if (isDog) {
            info = dogInformationMessageService.findInformationMessageById(menuItemNumber).getText();
        } else {
            info = catInformationMessageService.findInformationMessageById(menuItemNumber).getText();
        }
        return setupSendMessage(chatId, info);
    }


    // =========================================================================

    /**
     * Every day test DB in 20:00 to check all yesterday reports are present in DB.
     * Photo and text about pet should be there.
     */
//    @Scheduled(cron = "0 20 * * * *")
//    public void runTestForReports() {
//        // get all users with trial period
//        List<User> toTestWithTrialPeriod = userService.findUsersByAdoptedIsTrue();
//
//        // test, if users have sent reports yesterday and two days ago
//        for (User petsMaster : toTestWithTrialPeriod) {
//            CatReport petReportYesterday = petReportService.getPetReportByPetAndDateOfReport(petsMaster.getPet(), LocalDate.now().minusDays(1L));
//            CatReport petReportTwoDaysAgo = petReportService.getPetReportByPetAndDateOfReport(petsMaster.getPet(), LocalDate.now().minusDays(2L));
//            if (petReportYesterday == null && petReportTwoDaysAgo == null) {
//                /* позвать волонтера!!!
//                 * если пользователь не присылал 2 дня никакой информации (текст или фото), отправлять запрос волонтеру на связь с усыновителем.
//                 * Текст: "Усыновитель petsMaster не отправляет информацию уже 2 дня".*/
//                break;
//            }
//            if (petReportYesterday == null) {
//                /* если пользователь не присылал вчера информацию (текст и фото),
//                напоминаем: "Добрый день, мы не получили отчет о питомце за вчерашний день, пожалуйста, пришлите сегодня фотоотчет и информациюю о питомце".*/
//                break;
//            }
//            if (petReportYesterday.getTextOfReport() == null && petReportYesterday.getPhoto() != null) {
//                /* если пользователь не присылал вчера текст,
//                напоминаем: "Добрый день, мы не получили рассказ о питомце за вчерашний день, пожалуйста, пришлите сегодня информацию о питомце". */
//                break;
//            }
//            if (petReportYesterday.getTextOfReport() != null && petReportYesterday.getPhoto() == null) {
//                /* если пользователь не присылал вчера фотоотчет,
//                напоминаем: "Добрый день, мы не получили фотоотчет о питомце за вчерашний день, пожалуйста, пришлите сегодня фотоотчет о питомце". */
//                break;
//            }
//        }
//    }

    // =========================================================================

    /**
     * Every day test DB in 12:00 to check trial period has expired.
     * Photo and text about pet should be there.
     */
//    @Scheduled(cron = "00 12 * * * *")
//    public void runTestTrialPeriodHasExpired() {
//        // get all users with trial period
//        List<User> toTestWithTrialPeriod = userService.findUsersByAdoptedIsTrue();
//
//        // test, users have the last day of trial period
//        for (User petsMaster : toTestWithTrialPeriod) {
//            if (petsMaster.getFinishDate().isBefore(LocalDate.now()) && petsMaster.getAdopted()) {
//                /* если день>N и усыновитель в статусе "на проверке", бот отправляет запрос волонтеру.
//                Текст: "N-ый день уже прошел! Срочно примите решение об успешном/неуспешном прохождении усыновителем испытательного срока или продлите испытательный срок".
//                 */
//                break;
//            }
//            if (petsMaster.getFinishDate().equals(LocalDate.now()) && petsMaster.getAdopted()) {
//                /* если день=N и хозяин еще на испытательном сроке, бот отправляет запрос волонтеру.
//                Текст: "Сегодня истекает N-ый день. Примите решение об успешном/неуспешном прохождении усыновителем испытательного срока или продлите испытательный срок". */
//                break;
//            }
//        }
//    }

}
