package com.example.demo.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
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


    private boolean isSaidHello = false;
    //    @Autowired
    private TelegramBot telegramBot;

    public TelegramBotUpdatesListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    // **********  Запускаем Телеграм-Бота  ******
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    // **********  Получаем входные данные от пользователей  ******
    public int process(List<Update> updates) {

        for (Update update : updates) {
            logger.debug("Processing update: {}", update);
            try {

                String userMessage = update.message().text();
                Long chatId = update.message().chat().id();
                //  --- запускаем обработку вводимого сообщения  -----------
                // ------------------------------------
//                if (result.equals("start") && !isSaidHello) {
//                    textToUser = messagesService.sayHelloToUser(update);
//                    isSaidHello = true;
//                }
                // -----------------------------------


                String textToUser = userMessage;
                printMessageToUser(chatId, textToUser);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    // -----------  печатаем пользователю сообщение ----------
    private void printMessageToUser(Long chatId, String messageText) {
        SendMessage message = new SendMessage(chatId, messageText);
        SendResponse response = telegramBot.execute(message);
    }

}
