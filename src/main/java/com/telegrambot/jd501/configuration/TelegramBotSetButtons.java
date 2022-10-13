package com.telegrambot.jd501.configuration;

import com.telegrambot.jd501.service.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class TelegramBotSetButtons {
    private final Logger logger = LoggerFactory.getLogger(TelegramBot.class);

    /**
     * Setup sending message
     *
     * @param chatId      identificator of chat
     * @param messageText sending text
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
     */
    public SendMessage setButtons(long chatId, List<String> namesOfButtons,
                                  int startIndexButton, int numberOfButtons) {
        logger.info("Setting of keyboard...");
        SendMessage sendMessage = setupSendMessage(chatId, "");

        // Create keyboard
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

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

        // Return setup message
        return sendMessage;
    }

}
