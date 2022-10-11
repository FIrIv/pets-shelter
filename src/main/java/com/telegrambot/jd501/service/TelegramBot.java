package com.telegrambot.jd501.service;


import com.telegrambot.jd501.configuration.TelegramBotConfiguration;
import com.telegrambot.jd501.configuration.TelegramBotSetButtons;
import com.telegrambot.jd501.repository.InformationMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TelegramBot extends TelegramLongPollingBot {
    final TelegramBotConfiguration config;
    private final TelegramBotSetButtons buttons = new TelegramBotSetButtons();
    private final Logger logger = LoggerFactory.getLogger(TelegramBot.class);
    /** String with start command in chat
     *
     */
    private final String START_FIRST_COMMAND = "/start";
    private final String CHOOSE_MENU_ITEM_STRING = "*** Выберите интересующий пункт меню ***";

    private final InformationMessageRepository infoRepository;

    /**
     * ArrayList with button's names
     */
    private final List<String> BUTTONS_NAMES = new ArrayList<>(List.of(
            "Информация о приюте", "Как приютить питомца?",
            "Прислать отчет", "Оставить данные для связи",
            "Позвать волонтера",

            "Общая информация", "Расписание работы, адрес",
            "Техника безопасности", "вернуться в главное меню"

    ));

    //              *******  Меню 1 *******
    //        1 - Информация о приюте       (0)     2 - Как приютить питомца?     (1)
    //        3 - Прислать отчет            (2)     4 - Оставить данные для связи (3)
    //        5 - Позвать волонтера         (4)
    //             *******  Меню 2 *******
    //        11 - Общая информация         (5)     12 - Расписание работы, адрес (6)
    //        13 - Техника безопасности     (7)     0  - вернуться в главное меню (8)
    public TelegramBot(TelegramBotConfiguration config, InformationMessageRepository infoRepository) {
        this.config = config;
        this.infoRepository = infoRepository;
    }

    /**
     * Get name of bot
     *
     * @return string with BotName
     */
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    /**
     * Get bot's token
     *
     * @return string with token's data
     */
    @Override
    public String getBotToken() {
        return config.getToken();
    }

    /**
     * Get incoming updates
     *
     * @param update list of incoming updates, must be not Null
     */
    @Override
    public void onUpdateReceived(Update update) { // ********* здесь ошибка (при вводе произвольной строки)
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                // --- send incoming message (or pressed key) for checking -----
                checkInputMessage(update);
            }
        } catch (Exception e) {
            logger.error("Error occured in method onUpdateReceived: " + e.getMessage());
        }
    }

    /**
     * Check message from user (or what key is pressed)
     *
     * @param update list of incoming updates, must be not Null
     */
    private void checkInputMessage(Update update) {

        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        SendMessage messageToSend = new SendMessage();

        int idMessage = -1;
        for (int i = 0; i < BUTTONS_NAMES.size(); i++) {
            if (BUTTONS_NAMES.get(i).equals(messageText)) {
                idMessage = i;
                break;
            }
        }
        switch (idMessage) {
            // --- 1 - "Информация о приюте" button is pressed  (0)---
            case 0:
                messageToSend = informAboutShelter(chatId);
                break;
            // --- 2 - "Как приютить питомца?" button is pressed (1)---
            case 1:
                messageToSend = informToPotentialAdopter(chatId);
                break;
            // --- 3 - "Прислать отчет" button is pressed (2)---
            case 2:
                messageToSend = waitForReport(chatId);
                break;
            // --- 4 - "Оставить данные для связи" button is pressed (3)---
            case 3:
                messageToSend = getContact(chatId);
                break;
            // --- 5 - "Позвать волонтера" (4) button is pressed ---
            case 4:
                messageToSend = callToVolunteer(chatId);
                break;
            // --- 0 - вернуться в главное меню (8) button is pressed ---
            case 8:
                // call main menu ---
                messageToSend = buttons.setButtons(chatId, BUTTONS_NAMES, 0, 3);
                messageToSend.setText(CHOOSE_MENU_ITEM_STRING);
                break;
             // --- 11 - Общая информация (5) button is pressed ---
            case 5:
                messageToSend = getGeneralInfo(chatId);
                break;
            // -------- any other command / string -----
            default:
                messageToSend = buttons.setupSendMessage(chatId, CHOOSE_MENU_ITEM_STRING);
                break;
        }
        // --- /start is pressed
        if (messageText.equals(START_FIRST_COMMAND)) {
            messageToSend = startCommandReceived(chatId);
        }
        sendMessageToUser(messageToSend);
    }

    /**
     * Greetings to user on start.
     * We say about our shelter and offer to take a choice of menu item .
     *
     * @param chatId identificator of user
     */
    private SendMessage startCommandReceived(long chatId) {
        String greeting = "Здравствуйте! Это официальный телеграмм-бот приюта животных PetShelter. Мы помогаем людям, которые задумались приютить питомца. " +
                "Для многих из Вас это первый опыт. Не волнуйтесь. Мы поможем с этим нелегким, но важным делом!\n" +
                CHOOSE_MENU_ITEM_STRING;
        SendMessage message = buttons.setButtons(chatId, BUTTONS_NAMES, 0, 3);
        message.setText(greeting);
        return message;
    }

    /**
     * Information menu about shelter
     *
     * @param chatId identificator of chat
     */
    private SendMessage informAboutShelter(long chatId) {
        logger.info("Change keyboard to");
        String chooseItem = "Здесь Вы можете получить информацию о нашем приюте.\n" +
                CHOOSE_MENU_ITEM_STRING;
        SendMessage message = buttons.setButtons(chatId, BUTTONS_NAMES, 5, 2);
        message.setText(chooseItem);
        return message;
    }

    /**
     * Information menu for Potential Adopter
     *
     * @param chatId identificator of chat
     */
    private SendMessage informToPotentialAdopter(long chatId) {
        String example = "Нажата кнопка " + "''" + BUTTONS_NAMES.get(1) + "''";
        return buttons.setupSendMessage(chatId, example);
    }

    /**
     * Pet's report menu
     *
     * @param chatId identificator of chat
     */
    private SendMessage waitForReport(long chatId) {
        String example = "Нажата кнопка " + "''" + BUTTONS_NAMES.get(2) + "''";
        return buttons.setupSendMessage(chatId, example);
    }

    /**
     * Contact getting menu
     *
     * @param chatId identificator of chat
     */
    private SendMessage getContact(long chatId) {
        String example = "Нажата кнопка " + "''" + BUTTONS_NAMES.get(3) + "''";
        return buttons.setupSendMessage(chatId, example);
    }

    /**
     * Menu of volunteer's calling
     *
     * @param chatId identificator of chat
     */
    private SendMessage callToVolunteer(long chatId) {
        String example = "Нажата кнопка " + "''" + BUTTONS_NAMES.get(4) + "''";
        return buttons.setupSendMessage(chatId, example);
    }

    /**
     * Menu of general information about shelter
     *
     * @param chatId identificator of chat
     */
    // *** Menu item 11 *** (5)
    private SendMessage getGeneralInfo(long chatId) {
        long itemNumber = 11;
        String info = infoRepository.findById(itemNumber).orElseThrow().getText();
        return buttons.setupSendMessage(chatId, info);
    }


    // =========================================================================
    /**
     * Send message to user.
     *
     * @param message message to User
     */
    private void sendMessageToUser(SendMessage message) {
        try {
            if (!message.getText().isEmpty()) {
                execute(message);
            }
        } catch (TelegramApiException e) {
            logger.error("Error occured in method sendMessageToUser: " + e.getMessage());
        }
    }
}
