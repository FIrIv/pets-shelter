package com.telegrambot.jd501.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@ExtendWith(MockitoExtension.class)
class TelegramBotTest {
    private TelegramBot telegramBot;

    @BeforeEach
    public void init() {
        telegramBot = Mockito.mock(TelegramBot.class);
    }

    @Test
    void onUpdateReceived() {
        long chatId = -1L;
        String textMessage = "Тестовое сообщение";

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(textMessage);
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(textMessage);
        sendMessage.enableHtml(true);


    }

    @Test
    void sendMessageToUser() throws TelegramApiException {
        long chatId = -1L;
        String textMessage = "Тестовое сообщение";
        SendMessage mes = new SendMessage();
        mes.setChatId(chatId);
        mes.setText(textMessage);

        telegramBot = Mockito.mock(TelegramBot.class);

        //when()
        telegramBot.sendMessageToUser(mes);
        Mockito.verify(telegramBot).execute(mes);
    }

    @Test
    void sendMessageToUserByChatId() {
        long chatId = -1L;
        String textMessage = "Тестовое сообщение";
        SendMessage mes = new SendMessage();
        mes.setChatId(chatId);
        mes.setText(textMessage);



    }
}