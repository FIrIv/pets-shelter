package com.telegrambot.jd501.service;


import com.telegrambot.jd501.configuration.TelegramBotConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramBot extends TelegramLongPollingBot {
    final TelegramBotConfiguration config;
    private final Logger logger = LoggerFactory.getLogger(TelegramBot.class);

    public TelegramBot(TelegramBotConfiguration config) {
        this.config = config;
    }

    /**
     * Получаем имя бота
     * @return строка с BotUsername
     */
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    /**
     * Получаем токен бота
     * @return строка с данными токена
     */
    @Override
    public String getBotToken() {
        return config.getToken();
    }

    /**
     * Получаем входящие обновления
     * @param update список входящих обновлений, не может быть Null
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default:
                    sendMessage(chatId, "Sorry, command was not recognized");
                    break;
            }
        }
    }

    /**
     * При нажатии /start, приветствуем пользователя
     * @param chatId идентификаор чата пользователя
     * @param userName имя пользователя
     */
    private void startCommandReceived(long chatId, String userName)  {
        String answer = "Hi, " + userName + ", nice to meet you!";
        sendMessage(chatId, answer);
    }

    /**
     * Отправляем произволльное сообщение пользователю
     * @param chatId идентификаор чата пользователя
     * @param messageText текст сообщения
     * @throws TelegramApiException
     */
    private void sendMessage(long chatId, String messageText)  {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);
        try {
            execute(message);
            logger.info("Message to user: {}", message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


//
//    private void makeKeybordInChat() {
//        Keyboard replyKeybordMarkup = new ReplyKeyboardMarkup(
//          new String[]{"FirstRowButton1", "FirstRowButton2"},
//          new String[]{"SecondRowButton1", "SecondRowButton2"})
//                .oneTimeKeyboard(true)
//                .selective(true);
//    }

}
