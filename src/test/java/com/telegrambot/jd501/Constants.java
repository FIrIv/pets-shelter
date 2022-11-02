package com.telegrambot.jd501;

import com.telegrambot.jd501.model.MailingList;
import com.telegrambot.jd501.model.cat.*;
import com.telegrambot.jd501.model.dog.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static final Boolean TRUE = Boolean.TRUE;
    public static final Boolean FALSE = Boolean.FALSE;
    public static final Long ID1 = 1L;
    public static final Long ID2 = 2L;

    public static final Long CHAT_ID_1 = 1111L;
    public static final Long CHAT_ID_2 = 2222L;

    public static final String TEXT1 = "TEST_TEXT_1";
    public static final String TEXT2 = "TEST_TEXT_2";

    public static final String NAME_1 = "TEST_NAME_1";
    public static final String NAME_2 = "TEST_NAME_2";

    public static final String PHONE_1 = "79179661111";
    public static final String PHONE_2 = "79179662222";

    public static final String TEXT_OF_REPORT_1 = "TEST_REPORT_1";
    public static final String TEXT_OF_REPORT_2 = "TEST_REPORT_2";

    public static final byte[] PHOTO_1 = {1};
    public static final byte[] PHOTO_2 = {2};
    public static final LocalDate START_DATE = LocalDate.of(2022, 10, 1);
    public static final LocalDate FINISH_DATE = LocalDate.of(2022, 10, 30);
    public static final LocalDate DATE_OF_REPORT_1 = LocalDate.of(2022, 10, 15);
    public static final LocalDate DATE_OF_REPORT_2 = LocalDate.of(2022, 10, 17);

    public static final CatInformationMessage CAT_INFORMATION_MESSAGE_1 = new CatInformationMessage(ID1, TEXT1);
    public static final CatInformationMessage CAT_INFORMATION_MESSAGE_11 = new CatInformationMessage(ID1, TEXT2);
    public static final CatInformationMessage CAT_INFORMATION_MESSAGE_2 = new CatInformationMessage(ID2, TEXT2);

    public static final DogInformationMessage DOG_INFORMATION_MESSAGE_1 = new DogInformationMessage(ID1, TEXT1);
    public static final DogInformationMessage DOG_INFORMATION_MESSAGE_11 = new DogInformationMessage(ID1, TEXT2);
    public static final DogInformationMessage DOG_INFORMATION_MESSAGE_2 = new DogInformationMessage(ID2, TEXT2);

    public static final Cat CAT_1 = new Cat(ID1, NAME_1);
    public static final Cat CAT_11 = new Cat(ID1, NAME_2);
    public static final Cat CAT_2 = new Cat(ID2, NAME_2);
    public static final Dog DOG_1 = new Dog(ID1, NAME_1);
    public static final Dog DOG_11 = new Dog(ID1, NAME_2);
    public static final Dog DOG_2 = new Dog(ID2, NAME_2);

    public static final CatUser CAT_USER_1 = new CatUser(ID1, CHAT_ID_1, NAME_1, PHONE_1);
    public static final CatUser CAT_USER_11 = new CatUser(ID1, CHAT_ID_1, NAME_1, PHONE_2);
    public static final CatUser CAT_USER_2 = new CatUser(ID2, CHAT_ID_2, NAME_2, PHONE_2);
    public static final CatUser CAT_USER_3 = new CatUser(ID1, CHAT_ID_1, NAME_1, PHONE_1, TRUE, START_DATE, FINISH_DATE, CAT_1);
    public static final CatUser CAT_USER_4 = new CatUser(ID1, CHAT_ID_1, NAME_1, PHONE_1, TRUE, START_DATE, FINISH_DATE, CAT_1);

    public static final DogUser DOG_USER_1 = new DogUser(ID1, CHAT_ID_1, NAME_1, PHONE_1);
    public static final DogUser DOG_USER_11 = new DogUser(ID1, CHAT_ID_1, NAME_1, PHONE_2);
    public static final DogUser DOG_USER_2 = new DogUser(ID2, CHAT_ID_2, NAME_2, PHONE_2);
    public static final DogUser DOG_USER_3 = new DogUser(ID1, CHAT_ID_1, NAME_1, PHONE_1, TRUE, START_DATE, FINISH_DATE, DOG_1);
    public static final DogUser DOG_USER_31 = new DogUser(ID1, CHAT_ID_1, NAME_1, PHONE_1, TRUE, START_DATE, FINISH_DATE, DOG_1);
    public static final DogUser DOG_USER_4 = new DogUser(ID1, CHAT_ID_1, NAME_1, PHONE_1, TRUE, START_DATE, FINISH_DATE, DOG_1);

    public static final CatVolunteer CAT_VOLUNTEER_1 = new CatVolunteer(ID1, CHAT_ID_1, NAME_1);
    public static final CatVolunteer CAT_VOLUNTEER_11 = new CatVolunteer(ID1, CHAT_ID_1, NAME_2);
    public static final CatVolunteer CAT_VOLUNTEER_2 = new CatVolunteer(ID2, CHAT_ID_2, NAME_2);

    public static final DogVolunteer DOG_VOLUNTEER_1 = new DogVolunteer(ID1, CHAT_ID_1, NAME_1);
    public static final DogVolunteer DOG_VOLUNTEER_11 = new DogVolunteer(ID1, CHAT_ID_1, NAME_2);
    public static final DogVolunteer DOG_VOLUNTEER_2 = new DogVolunteer(ID2, CHAT_ID_2, NAME_2);

    public static final CatReport CAT_REPORT_1 = new CatReport(ID1, DATE_OF_REPORT_1, TEXT_OF_REPORT_1, PHOTO_1, CAT_USER_1);
    public static final CatReport CAT_REPORT_11 = new CatReport(ID1, DATE_OF_REPORT_2, TEXT_OF_REPORT_1, PHOTO_1, CAT_USER_1);
    public static final CatReport CAT_REPORT_2 = new CatReport(ID2, DATE_OF_REPORT_2, TEXT_OF_REPORT_2, PHOTO_2, CAT_USER_2);
    public static final DogReport DOG_REPORT_1 = new DogReport(ID1, DATE_OF_REPORT_1, TEXT_OF_REPORT_1, PHOTO_1, DOG_USER_1);
    public static final DogReport DOG_REPORT_11 = new DogReport(ID1, DATE_OF_REPORT_2, TEXT_OF_REPORT_1, PHOTO_1, DOG_USER_1);
    public static final DogReport DOG_REPORT_2 = new DogReport(ID2, DATE_OF_REPORT_2, TEXT_OF_REPORT_2, PHOTO_2, DOG_USER_2);

    public static final List<CatInformationMessage> CAT_INFORMATION_MESSAGE_LIST = new ArrayList<>(List.of(CAT_INFORMATION_MESSAGE_1, CAT_INFORMATION_MESSAGE_2));
    public static final List<DogInformationMessage> DOG_INFORMATION_MESSAGE_LIST = new ArrayList<>(List.of(DOG_INFORMATION_MESSAGE_1, DOG_INFORMATION_MESSAGE_2));

    public static final List<Cat> CAT_LIST = new ArrayList<>(List.of(CAT_1, CAT_2));
    public static final List<Dog> DOG_LIST = new ArrayList<>(List.of(DOG_1, DOG_2));

    public static final List<CatUser> CAT_USER_LIST = new ArrayList<>(List.of(CAT_USER_1, CAT_USER_2));
    public static final List<CatUser> CAT_USER_ADOPTERS_LIST = new ArrayList<>(List.of(CAT_USER_3, CAT_USER_4));
    public static final List<DogUser> DOG_USER_LIST = new ArrayList<>(List.of(DOG_USER_1, DOG_USER_2));
    public static final List<DogUser> DOG_USER_ADOPTERS_LIST = new ArrayList<>(List.of(DOG_USER_3, DOG_USER_4));

    public static final List<CatVolunteer> CAT_VOLUNTEER_LIST = new ArrayList<>(List.of(CAT_VOLUNTEER_1, CAT_VOLUNTEER_2));
    public static final List<DogVolunteer> DOG_VOLUNTEER_LIST = new ArrayList<>(List.of(DOG_VOLUNTEER_1, DOG_VOLUNTEER_2));

    public static final List<CatReport> CAT_REPORT_LIST = new ArrayList<>(List.of(CAT_REPORT_1, CAT_REPORT_2));
    public static final List<CatReport> CAT_REPORT_LIST_BY_USER = new ArrayList<>(List.of(CAT_REPORT_1));
    public static final List<CatReport> CAT_REPORT_LIST_ORDERED = new ArrayList<>(List.of(CAT_REPORT_1, CAT_REPORT_11));
    public static final List<DogReport> DOG_REPORT_LIST = new ArrayList<>(List.of(DOG_REPORT_1, DOG_REPORT_2));
    public static final List<DogReport> DOG_REPORT_LIST_BY_USER = new ArrayList<>(List.of(DOG_REPORT_1));
    public static final List<DogReport> DOG_REPORT_LIST_ORDERED = new ArrayList<>(List.of(DOG_REPORT_1, DOG_REPORT_11));


    public static final String CHAT_ID_STRING = "1111";
    public static final String PARSE_MODE = "HTML";

    public static final SendMessage TEST_MESSAGE = new SendMessage(CHAT_ID_STRING, TEXT1, PARSE_MODE, null, null, null, null, null, null, null);
    public static final String TEXT_TO_USER = "Добрый день, мы не получили рассказ о питомце за вчерашний день, пожалуйста, пришлите его сегодня.";
    public static final String TEXT_TO_33 = "Сегодня истекает N-ый день. Примите решение об успешном/неуспешном прохождении усыновителем испытательного срока или продлите испытательный срок\nУважаемый волонтер! Просьба связаться с пользователем: " + NAME_1 + "  https://t.me/";
    public static final String TEXT_TO_34 = "N-ый день уже прошел! Срочно примите решение об успешном/неуспешном прохождении усыновителем испытательного срока или продлите испытательный срок\nУважаемый волонтер! Просьба связаться с пользователем: " + NAME_1 + "  https://t.me/";
    public static final SendMessage TEST_MESSAGE_2 = new SendMessage(CHAT_ID_STRING, TEXT_TO_USER, null, null, null, null, null, null, null, null);
    public static final SendMessage TEST_MESSAGE_33 = new SendMessage(CHAT_ID_STRING, TEXT_TO_33, null, null, null, null, null, null, null, null);
    public static final SendMessage TEST_MESSAGE_34 = new SendMessage(CHAT_ID_STRING, TEXT_TO_34, null, null, null, null, null, null, null, null);
//    public static final SendMessage TEST_MESSAGE_NULL = new SendMessage(null, "", null, null, null, null, null, null, null, null);
    public static final List<String> TEST_NAMES_OF_BUTTONS = new ArrayList<>(List.of(
            "Приют для кошек", "Приют для собак"
    ));
    public static final Integer ZERO = 0;
    public static final Integer ONE = 1;
    public static final Integer TWO = 2;
    //for button test
    public static final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
            new ArrayList<>(List.of(new KeyboardRow(new ArrayList<>(List.of(new KeyboardButton("Приют для кошек"), new KeyboardButton("Приют для собак")))))),
            TRUE, FALSE, TRUE, null);
    public static final List<KeyboardRow> KEYBOARD = new ArrayList<>(List.of(new KeyboardRow(new ArrayList<>(List.of(new KeyboardButton("Приют для кошек"), new KeyboardButton("Приют для собак"))))));

    public static final SendMessage TEST_MESSAGE_BUTTON = new SendMessage(
            CHAT_ID_STRING, TEXT2, null,
            null, null, null,
            replyKeyboardMarkup, null, null, null
    );
    public static final MailingList MAILING_LIST_1 = new MailingList(CHAT_ID_1, TEXT1);
    public static final MailingList MAILING_LIST_2 = new MailingList(CHAT_ID_2, TEXT2);
    public static final List<MailingList> MAILING_LISTS = new ArrayList<>(List.of(MAILING_LIST_1, MAILING_LIST_2));

    public static final LocalDate DATE_OF_REPORT_3 = LocalDate.now().minusDays(1L);
    public static final LocalDate DATE_OF_REPORT_4 = LocalDate.now().minusDays(2L);

    public static final CatReport CAT_REPORT_3 = new CatReport(ID1, DATE_OF_REPORT_3, null, PHOTO_1, CAT_USER_3);

    public static final CatUser CAT_USER_33 = new CatUser(ID1, CHAT_ID_1, NAME_1, PHONE_1, TRUE, START_DATE, LocalDate.now(), CAT_1);
    public static final DogUser DOG_USER_33 = new DogUser(ID1, CHAT_ID_1, NAME_1, PHONE_1, TRUE, START_DATE, LocalDate.now().minusDays(1L), DOG_1);

}
