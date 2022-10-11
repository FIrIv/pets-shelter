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
        message.setChatId(chatId);
        message.setText(messageText);
        return message;
    }

    /**
     * Make buttons in Telegram's chat
     *
     * @param chatId        identificator of chat
     * @param namesOfKeys   list of button's names, must be not Null
     * @param startIndexKey start index of button
     * @param numberOfRows  number of button's rows
     */
    public SendMessage setButtons(long chatId, List<String> namesOfKeys,
                                  int startIndexKey, int numberOfRows) {
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
        int indexOfKey = startIndexKey;
        // First row of keyboard
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Add buttons to First row of keyboard
        keyboardFirstRow.add(new KeyboardButton(namesOfKeys.get(indexOfKey)));
        indexOfKey++;
        keyboardFirstRow.add(new KeyboardButton(namesOfKeys.get(indexOfKey)));

        // Second row of keyboard
        indexOfKey++;
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Add buttons to Second row of keyboard
        keyboardSecondRow.add(new KeyboardButton(namesOfKeys.get(indexOfKey)));
        indexOfKey++;
        keyboardSecondRow.add(new KeyboardButton(namesOfKeys.get(indexOfKey)));

        // Add all rows of keyboard to list
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        // If number of rows 3, than create third row of keyboard
        if (numberOfRows == 3) {
            indexOfKey++;
            KeyboardRow keyboardThirdRow = new KeyboardRow();
            keyboardThirdRow.add(new KeyboardButton(namesOfKeys.get(indexOfKey)));
            keyboard.add(keyboardThirdRow);
        }

        // Set created keyboard
        replyKeyboardMarkup.setKeyboard(keyboard);

        // Return setup message
        return sendMessage;
    }

}
