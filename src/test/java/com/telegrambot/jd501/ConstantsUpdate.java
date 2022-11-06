package com.telegrambot.jd501;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;

import static com.telegrambot.jd501.Constants.*;

public class ConstantsUpdate {
    public static final Integer UPDATE_ID = 34665243;
    public static final Integer MESSAGE_ID = 346;
    public static final Long USER_ID = 346535465L;
    public static final Long CHAT_ID = 346535465L;
    public static final String CHAT_ID_TO_STRING = "346535465";
    public static final String FIRST_NAME = "TEST_FIRST_NAME";
    public static final String LAST_NAME = "TEST_LAST_NAME";
    public static final String RU = "ru";
    public static final String TYPE = "private";
    public static final String TEXT = "*** Панель бота для волонтёров ***";
    public static final String TEXT_ANSWER = "*** Выберите интересующий пункт меню ***";
    public static final Integer DATE = 1667140744;
    public static final String V_CARD = "vCard";
    public static final String PHONE_STRING = "79872344356";
    public static final Chat TEST_CHAT = new Chat(
            CHAT_ID, TYPE, null, FIRST_NAME, LAST_NAME, FIRST_NAME, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    public static final User TEST_USER = new User(
            USER_ID,FIRST_NAME, FALSE, LAST_NAME,FIRST_NAME, RU,
            null,null,null,null,null);

    public static final Contact TEST_CONTACT = new Contact(PHONE_STRING, FIRST_NAME, LAST_NAME, USER_ID, V_CARD);
    public static final Message TEST_UPDATE_MESSAGE = new Message(
            MESSAGE_ID, TEST_USER, 1667140744, TEST_CHAT, null, null, null, TEXT,
            null, null, null, null, null, null, null, TEST_CONTACT, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

    public static final Update TEST_UPDATE_1 = new Update(
            UPDATE_ID, TEST_UPDATE_MESSAGE, null, null, null, null, null, null, null, null, null, null, null, null, null);

    public static final SendMessage TEST_SEND_MESSAGE = new SendMessage(CHAT_ID_TO_STRING, TEXT_ANSWER, PARSE_MODE, null, null, null, null, null, null, null);

    public static final String TEXT_TEMP = "Уважаемый волонтер! Просьба связаться с пользователем: " + NAME_1 +
            "  https://t.me/" + FIRST_NAME;
    public static final String TEXT_TO_VOLUNTEER = TEXT_TEMP + " . Его телефон " + PHONE_STRING + " chatId-" + CHAT_ID;
    public static final SendMessage TEST_SEND_MESSAGE_TO_VOLUNTEER = new SendMessage(CHAT_ID_STRING, TEXT_TO_VOLUNTEER, null, null, null, null, null, null, null, null);

    public static final String TEXT_REPLY = "Уважаемый " + FIRST_NAME + "! " + "С Вами обязательно свяжутся";

    public static final SendMessage REPLY_SEND_MESSAGE = new SendMessage(CHAT_ID_TO_STRING, TEXT_REPLY, "Markdown", null, null, null, null, null, null, null);
}
