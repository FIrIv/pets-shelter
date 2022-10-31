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
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

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
            "Также просим приложить фотографию (нажав на 'скрепочку').";

    private boolean isDog;
    private boolean isReportButtonPressed = false;

    private byte[] data;
    private boolean isPhoto;

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
     * @param chatId      identification of chat
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
     * @param chatId           identification of chat
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
                messageToSend = checkUserForPossibilityGetReport(chatId);
                break;

            // -- - "Оставить данные для связи" button is pressed (5)---
            // ----  Checked in TelegramBot (onUpdateReceived method)

            // --- "Позвать волонтера" (6) button is pressed ---
            case 6:
            case 13:
            case 23:
                messageToSend = callToVolunteer(chatId, "contact", update.getMessage().getChat().getUserName());
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
                if (isReportButtonPressed && !messageText.equals(START_FIRST_COMMAND)) {
                    isPhoto = false;
                    messageToSend = saveReportToDB(chatId, isPhoto, messageText);
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
     * @param chatId identification of chat
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
     * @param chatId identification of chat
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
     * @param chatId identification of chat
     * @return message to reply
     */
    private SendMessage consultNewUser(long chatId) {
        logger.info("Change keyboard to menu consultNewUser, isDog = " + isDog);
        SendMessage message = setButtons(chatId, BUTTONS_NAMES, 8, 7);
        message.setText(CHOOSE_MENU_ITEM_STRING);
        return message;
    }

    /**
     * Information menu for Potential Adopter
     *
     * @param chatId identification of chat
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
     * @param userChatId identification of chat
     * @return message to reply
     */
    private SendMessage checkUserForPossibilityGetReport(long userChatId) {
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
            message = setButtonsForReport(message, userIsExists, userIsAdopted);
        } else {
            // --- 1) exists? 2) adopted? ---
            userIsExists = catUserService.isExistsUser(userChatId);
            if (userIsExists) {
                userIsAdopted = catUserService.findUserByChatId(userChatId).getAdopted();
            }
            message = setButtonsForReport(message, userIsExists, userIsAdopted);
        }
        logger.info("userIsExists-" + userIsExists + ", userIsAdopted-" + userIsAdopted);
        return message;
    }

    /**
     * Setup button "в Главное меню"
     *
     * @param message   incoming message from other method
     * @param isExists  sign of existing of user
     * @param isAdopted sign of this user is adopter
     * @return message to reply
     */
    private SendMessage setButtonsForReport(SendMessage message, boolean isExists, boolean isAdopted) {
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
     * @param chatId  identification of chat
     * @param isPhoto sign of incoming data (photo or text)
     * @param text    user's text of report
     * @return message to reply
     */

    private SendMessage saveReportToDB(long chatId, boolean isPhoto, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        String textOfReport = "Произошла ошибка! Обратитесь к волонтеру";

        // |||||||||  DOGS |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
        if (isDog) {
            DogUser dogUser = dogUserService.findUserByChatId(chatId);
            DogReport dogReport = dogReportService
                    .getPetReportByUserAndDateOfReport(dogUser, LocalDate.now());
            // ************ if report exists ******
            if (dogReport != null) {
                logger.info("dogReport: " + dogReport.toString());
                if (isPhoto) {
                    dogReport.setPhoto(data);
                } else {
                    dogReport.setTextOfReport(text);
                }
                dogReportService.updatePetReport(dogReport);
                // --- check for saving report
                dogReport = dogReportService.getPetReportByUserAndDateOfReport(dogUser, LocalDate.now());
                if (isPhoto && dogReport.getPhoto() != null) {
                    textOfReport = "Спасибо! Фотография загружена в отчет.";
                }
                if (!isPhoto && dogReport.getTextOfReport() != null) {
                    textOfReport = "Спасибо! Текст отчета загружен.";
                }
            }
            // ************ if report doesn't exist ******
            if (dogReport == null) {
                // save into db Dog new report ==================================================
                dogReport = new DogReport();
                dogReport.setDateOfReport(LocalDate.now());
                if (isPhoto) {
                    dogReport.setPhoto(data);
                } else {
                    dogReport.setTextOfReport(text);
                }
                dogReport.setDogUser(dogUser);
                dogReportService.createPetReport(dogReport);
                // --- check for saving report
                dogReport = dogReportService.getPetReportByUserAndDateOfReport(dogUser, LocalDate.now());
                if (isPhoto && dogReport.getPhoto() != null) {
                    textOfReport = "Спасибо! Фотография загружена в отчет.";
                }
                if (!isPhoto && dogReport.getTextOfReport() != null) {
                    textOfReport = "Спасибо! Текст отчета загружен.";
                }
            }
        }
        // |||||||||  CATS |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
        if (!isDog) {
            CatUser catUser = catUserService.findUserByChatId(chatId);
            CatReport catReport = catReportService
                    .getPetReportByUserAndDateOfReport(catUser, LocalDate.now());
            // ************ if report exists ******
            if (catReport != null) {
                logger.info("catReport: " + catReport.toString());
                if (isPhoto) {
                    catReport.setPhoto(data);
                } else {
                    catReport.setTextOfReport(text);
                }
                catReportService.updatePetReport(catReport);
                // --- check for saving report
                catReport = catReportService.getPetReportByUserAndDateOfReport(catUser, LocalDate.now());
                if (isPhoto && catReport.getPhoto() != null) {
                    textOfReport = "Спасибо! Фотография загружена в отчет.";
                }
                if (!isPhoto && catReport.getTextOfReport() != null) {
                    textOfReport = "Спасибо! Текст отчета загружен.";
                }
            }
            // ************ if report doesn't exist ******
            if (catReport == null) {
                // save into db Dog new report ==================================================
                catReport = new CatReport();
                catReport.setDateOfReport(LocalDate.now());
                if (isPhoto) {
                    catReport.setPhoto(data);
                } else {
                    catReport.setTextOfReport(text);
                }
                catReport.setCatUser(catUser);
                catReportService.createPetReport(catReport);
                // --- check for saving report
                catReport = catReportService.getPetReportByUserAndDateOfReport(catUser, LocalDate.now());
                if (isPhoto && catReport.getPhoto() != null) {
                    textOfReport = "Спасибо! Фотография загружена в отчет.";
                }
                if (!isPhoto && catReport.getTextOfReport() != null) {
                    textOfReport = "Спасибо! Текст отчета загружен.";
                }
            }
        }
        message.setText(textOfReport);
        return message;
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
     * @return contact of user
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
     * Extract photo in report from User data as document.
     * And then converse document into byte array
     *
     * @param update list of incoming updates, must be not Null
     * @return message to reply (with calling method {@link #saveReportToDB(long, boolean, String)})
     * @throws IOException if document hasn't gotten
     */
    SendMessage getPicture(Update update) throws IOException {
        long chatId = update.getMessage().getChatId();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        logger.info("getting picture...");
        if (!isReportButtonPressed) {
            message.setText(CHOOSE_MENU_ITEM_STRING);
            return message;
        }
        // ---- get photo as document -------
        Document getFile = update.getMessage().getDocument();
        // ---- Converse document into byte array -----
        logger.info("The conversion of the received image into a byte array...");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(getFile);
        oos.flush();
        data = bos.toByteArray();
        logger.info("... has been performed");

        // update or save photo into data base ******
        isPhoto = true;
        return saveReportToDB(chatId, isPhoto, "");
    }

    /**
     * Send user's phone number to volunteer and save it into DB
     *
     * @param update list of incoming updates, must be not Null
     * @return message to send
     */
    SendMessage sendUserPhoneToVolunteer(Update update) {
        // --- Make message for sending contact to volunteer ---
        SendMessage message = callToVolunteer(update.getMessage().getChatId(), "", update.getMessage().getChat().getUserName());
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
     * Setup message to sending to volunteer
     *
     * @param chatId   identification of chat
     * @param isButton sign of pressing of Contact button
     * @param userName user name in telegram chat or sign of type of DB (cats/dogs),
     *                 if this method is called from methods: {@link #checkCatsAdoptersForTrialPeriodHasExpired(),
     * @return message to reply
     * @link #checkDogsAdoptersForTrialPeriodHasExpired(),
     * @link #checkReportsOfTwoLastDaysCats(),
     * @link #checkReportsOfTwoLastDaysDogs()}
     */
    private SendMessage callToVolunteer(long chatId, String isButton, String userName) {
        SendMessage message = new SendMessage();
        boolean tempIsDog = isDog;
        if (userName.equals("#caTs*+-")) {
            isDog = false;
            userName = "";
        }
        if (userName.equals("--+3DoGs?+*^")) {
            isDog = true;
            userName = "";
        }
        String firstName;
        if (isDog) {
            firstName = dogUserService.findUserByChatId(chatId).getName();
        } else {
            firstName = catUserService.findUserByChatId(chatId).getName();
        }
        DogVolunteer firstDogVolunteer;
        CatVolunteer firstCatVolunteer;
        String tempText = "Уважаемый волонтер! Просьба связаться с пользователем: " + firstName +
                "  https://t.me/" + userName;
        // --- Make message for sending contact to volunteer ---
        // *** get first volunteer ***
        message.setText("\n*** Извините, произошла ошибка! *** \n Мы уже работаем над ее устранением. \n" +
                "Просьба повторить запрос позднее.");
        message.setChatId(chatId);
        try {
            if (isDog) {
                firstDogVolunteer = dogVolunteerService.getAllVolunteers()
                        .stream()
                        .findFirst()
                        .orElseThrow();
                logger.info(firstDogVolunteer.toString());
                message.setChatId(firstDogVolunteer.getChatId());
                if (isButton.equals("contact")) {
                    message.setText(tempText);
                } else {
                    message.setText(isButton + tempText);
                }
            }
            if (!isDog) {
                firstCatVolunteer = catVolunteerService.getAllVolunteers()
                        .stream()
                        .findFirst()
                        .orElseThrow();
                logger.info(firstCatVolunteer.toString());
                message.setChatId(firstCatVolunteer.getChatId());

                if (isButton.equals("contact")) {
                    message.setText(tempText);
                } else {
                    message.setText(isButton + tempText);
                }
            }
        } catch (NoSuchElementException e) {
            logger.error("Volunteer doesn't exist in DB ### " + e.getMessage());
        }
        logger.info("Contact of volunteer is ::::::::::::::: " + message.toString());
        isDog = tempIsDog;
        return message;
    }

    /**
     * Method of getting information from Dog or Cat service of general information about shelter
     *
     * @param chatId         identification of chat
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
     * See all reports in cats DB and check last reports for two days.
     * If something's wrong (photo or text of report or both are absent),
     * tell to user or to volunteer about that.
     *
     * @return List of SendMessage
     */
    public List<SendMessage> checkReportsOfTwoLastDaysCats() {
        List<SendMessage> sendMessage = new ArrayList<>();
        String textToSend = "";
        SendMessage sm = new SendMessage();
        sm.setText(textToSend);
        sendMessage.add(sm);
        // get all users with trial period
        List<CatUser> toTestWithTrialPeriod = catUserService.findUsersByAdoptedIsTrue();
        logger.info("====  List toTestWithTrialPeriod: " + toTestWithTrialPeriod);
        // Check, if users have sent reports yesterday and two days ago
        for (CatUser petsMaster : toTestWithTrialPeriod) {
            CatReport petReportYesterday = catReportService.getPetReportByUserAndDateOfReport(petsMaster, LocalDate.now().minusDays(1L));
            CatReport petReportTwoDaysAgo = catReportService.getPetReportByUserAndDateOfReport(petsMaster, LocalDate.now().minusDays(2L));
            if (petReportYesterday == null && petReportTwoDaysAgo == null) {
//                 if we haven't any information about pet (text or photo)
//                 more than 2 days, we must send request to volunteer to call to adopter.
                textToSend = "Усыновитель не отправляет информацию уже 2 дня!\n";
                sendMessage.add(callToVolunteer(petsMaster.getChatId(), textToSend, "#caTs*+-"));
            }
            if (petReportYesterday == null) {
                // if user hasn't sent report yesterday (text & photo), remind to him
                textToSend = "Добрый день, мы не получили отчет о питомце за вчерашний день, пожалуйста, " +
                        "пришлите сегодня фотоотчет и информацию о питомце";
                sm.setChatId(petsMaster.getChatId());
                sm.setText(textToSend);
                sendMessage.add(sm);
            }
            if (petReportYesterday.getTextOfReport() == null && petReportYesterday.getPhoto() != null) {
                // if user hasn't sent text of report yesterday, remind to him
                textToSend = "Добрый день, мы не получили рассказ о питомце за вчерашний день, " +
                        "пожалуйста, пришлите его сегодня.";
                sm.setChatId(petsMaster.getChatId());
                sm.setText(textToSend);
                sendMessage.add(sm);
            }
            if (petReportYesterday.getTextOfReport() != null && petReportYesterday.getPhoto() == null) {
                //  if user hasn't sent photo yesterday, remind to him
                textToSend = "Добрый день, мы не получили фотоотчет о питомце за вчерашний день, " +
                        "пожалуйста, пришлите его сегодня.";
                sm.setChatId(petsMaster.getChatId());
                sm.setText(textToSend);
                sendMessage.add(sm);
            }
        }
        return sendMessage;
    }

    /**
     * See all reports in dogs DB and check last reports for two days.
     * If something's wrong (photo or text of report or both are absent),
     * tell to user or to volunteer about that.
     *
     * @return List of SendMessage
     */
    public List<SendMessage> checkReportsOfTwoLastDaysDogs() {
        List<SendMessage> sendMessage = new ArrayList<>();
        String textToSend = "";
        SendMessage sm = new SendMessage();
        sm.setText(textToSend);
        sendMessage.add(sm);
        // get all users with trial period
        List<DogUser> toTestWithTrialPeriod2 = dogUserService.findUsersByAdoptedIsTrue();
        logger.info("====  List toTestWithTrialPeriod2: " + toTestWithTrialPeriod2);
        // Check, if users have sent reports yesterday and two days ago
        for (DogUser petsMaster : toTestWithTrialPeriod2) {
            DogReport petReportYesterday = dogReportService.getPetReportByUserAndDateOfReport(petsMaster, LocalDate.now().minusDays(1L));
            DogReport petReportTwoDaysAgo = dogReportService.getPetReportByUserAndDateOfReport(petsMaster, LocalDate.now().minusDays(2L));
            if (petReportYesterday == null && petReportTwoDaysAgo == null) {
//                 if we haven't any information about pet (text or photo)
//                 more than 2 days, we must send request to volunteer to call to adopter.
                textToSend = "Усыновитель не отправляет информацию уже 2 дня!\n";
                sendMessage.add(callToVolunteer(petsMaster.getChatId(), textToSend, "--+3DoGs?+*^"));
            }
            if (petReportYesterday == null) {
                // if user hasn't sent report yesterday (text & photo), remind to him
                textToSend = "Добрый день, мы не получили отчет о питомце за вчерашний день, пожалуйста, " +
                        "пришлите сегодня фотоотчет и информацию о питомце";
                sm.setChatId(petsMaster.getChatId());
                sm.setText(textToSend);
                sendMessage.add(sm);
            }
            if (petReportYesterday.getTextOfReport() == null && petReportYesterday.getPhoto() != null) {
                // if user hasn't sent text of report yesterday, remind to him
                textToSend = "Добрый день, мы не получили рассказ о питомце за вчерашний день, " +
                        "пожалуйста, пришлите его сегодня.";
                sm.setChatId(petsMaster.getChatId());
                sm.setText(textToSend);
                sendMessage.add(sm);
            }
            if (petReportYesterday.getTextOfReport() != null && petReportYesterday.getPhoto() == null) {
                //  if user hasn't sent photo yesterday, remind to him
                textToSend = "Добрый день, мы не получили фотоотчет о питомце за вчерашний день, " +
                        "пожалуйста, пришлите его сегодня.";
                sm.setChatId(petsMaster.getChatId());
                sm.setText(textToSend);
                sendMessage.add(sm);
            }
        }
        return sendMessage;
    }

    /**
     * See all reports in dogs DB and check for trial period - is expired?
     *
     * @return SendMessage
     */
    public List<SendMessage> checkDogsAdoptersForTrialPeriodHasExpired() {
        List<SendMessage> sendMessage = new ArrayList<>();
        String textToSend = "";
        SendMessage sm = new SendMessage();
        sm.setText(textToSend);
        sendMessage.add(sm);
        // get all users with trial period
        List<DogUser> toTestWithTrialPeriod = dogUserService.findUsersByAdoptedIsTrue();
        logger.info("====  List toTestWithTrialPeriod: " + toTestWithTrialPeriod);
        // Check users for the last day of trial period

        for (DogUser petsMaster : toTestWithTrialPeriod) {
            if (petsMaster.getFinishDate().isBefore(LocalDate.now()) && petsMaster.getAdopted()) {
                // if day > N & adopter is in status "adopted,"
                // than we must send request to volunteer to decide about adopter:
                // probation period passed or not
                textToSend = "N-ый день уже прошел! Срочно примите решение " +
                        "об успешном/неуспешном прохождении усыновителем испытательного срока" +
                        " или продлите испытательный срок\n";
                sendMessage.add(callToVolunteer(petsMaster.getChatId(), textToSend, "--+3DoGs?+*^"));
            }
            if (petsMaster.getFinishDate().equals(LocalDate.now()) && petsMaster.getAdopted()) {
                // if day = N & adopter is in status "adopted,"
                // than we must send request to volunteer to decide about adopter:
                // the trial period expires today. You have to decide about adopter:
                // probation period passed or not
                textToSend = "Сегодня истекает N-ый день. " +
                        "Примите решение об успешном/неуспешном прохождении " +
                        "усыновителем испытательного срока или продлите испытательный срок\n";
                sendMessage.add(callToVolunteer(petsMaster.getChatId(), textToSend, "--+3DoGs?+*^"));
            }
        }
        return sendMessage;
    }

    /**
     * See all reports in cats DB and check for trial period - is expired?
     *
     * @return SendMessage
     */
    public List<SendMessage> checkCatsAdoptersForTrialPeriodHasExpired() {
        List<SendMessage> sendMessage = new ArrayList<>();
        String textToSend = "";
        SendMessage sm = new SendMessage();
        sm.setText(textToSend);
        sendMessage.add(sm);
        // get all users with trial period
        List<CatUser> toTestWithTrialPeriod = catUserService.findUsersByAdoptedIsTrue();
        logger.info("====  List toTestWithTrialPeriod: " + toTestWithTrialPeriod);
        // Check users for the last day of trial period

        for (CatUser petsMaster : toTestWithTrialPeriod) {
            if (petsMaster.getFinishDate().isBefore(LocalDate.now()) && petsMaster.getAdopted()) {
                // if day > N & adopter is in status "adopted,"
                // than we must send request to volunteer to decide about adopter:
                // probation period passed or not
                textToSend = "N-ый день уже прошел! Срочно примите решение " +
                        "об успешном/неуспешном прохождении усыновителем испытательного срока" +
                        " или продлите испытательный срок\n";
                sendMessage.add(callToVolunteer(petsMaster.getChatId(), textToSend, "#caTs*+-"));
            }
            if (petsMaster.getFinishDate().equals(LocalDate.now()) && petsMaster.getAdopted()) {
                // if day = N & adopter is in status "adopted,"
                // than we must send request to volunteer to decide about adopter:
                // the trial period expires today. You have to decide about adopter:
                // probation period passed or not
                textToSend = "Сегодня истекает N-ый день. " +
                        "Примите решение об успешном/неуспешном прохождении " +
                        "усыновителем испытательного срока или продлите испытательный срок\n";
                sendMessage.add(callToVolunteer(petsMaster.getChatId(), textToSend, "#caTs*+-"));
            }
        }
        return sendMessage;
    }
}
