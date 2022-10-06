package com.telegrambot.jd501.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private TelegramBot telegramBot;

    public TelegramBotUpdatesListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    /**
     * Устанавливаем Слушателя для получения входящих обновлений
     * (сообщений пользователя Телеграм-бота)
     */
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Получаем в цикле входящие обновления
     * @param updates список входящих обновлений, не может быть Null
     * @return id последнего обработанного (подтвержденого) обновления
     */
    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            logger.debug("Get income messages: {}", update);
            try {
                /** Получаем идентификатор чата пользователя*/
                Long chatId = update.message().chat().id();
                /** Получаем сообщеие пользователя */
                String userMessage = update.message().text();

                /** --- запускаем обработку вводимого сообщения  -- */
//                if (result.equals("start") && !isSaidHello) {
//                    textToUser = messagesService.sayHelloToUser(update);
//                    isSaidHello = true;
//                }

                String textToUser = userMessage;
                /** Запускаем метод обратной отправки сообщения пользователю.
                 *  Для начала просто отзеркаливаем вводимое пользователем сообщение
                 */
                printMessageToUser(chatId, textToUser);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Отправляем сообщение пользователю
     * @param chatId идентификатор чата пользователя
     * @param messageText сообщение пользователю
     */
    private void printMessageToUser(Long chatId, String messageText) {
        SendMessage request = new SendMessage(chatId, messageText);
        SendResponse response = telegramBot.execute(request);
        boolean ok = response.isOk();
        Message message = response.message();
    }

}
