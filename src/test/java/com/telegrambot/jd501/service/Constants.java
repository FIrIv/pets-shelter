package com.telegrambot.jd501.service;

import com.telegrambot.jd501.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static final Boolean TRUE = Boolean.TRUE;
    public static final Long ID1= 1L;
    public static final Long ID2= 2L;

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

    public static final String PHOTO_LINK_1 = "PHOTO_LINK_1";
    public static final String PHOTO_LINK_2 = "PHOTO_LINK_2";
    public static final LocalDate START_DATE = LocalDate.of(2022,10,1);
    public static final LocalDate FINISH_DATE = LocalDate.of(2022,10,30);
    public static final LocalDate DATE_OF_REPORT_1 = LocalDate.of(2022,10,15);
    public static final LocalDate DATE_OF_REPORT_2 = LocalDate.of(2022,10,17);

    public static final InformationMessage INFORMATION_MESSAGE_1 = new InformationMessage(ID1,TEXT1);
    public static final InformationMessage INFORMATION_MESSAGE_11 = new InformationMessage(ID1,TEXT2);
    public static final InformationMessage INFORMATION_MESSAGE_2 = new InformationMessage(ID2,TEXT2);

    public static final Pet PET_1 = new Pet(ID1, NAME_1);
    public static final Pet PET_11 = new Pet(ID1, NAME_2);
    public static final Pet PET_2 = new Pet(ID2, NAME_2);

    public static final User USER_1 = new User(ID1, CHAT_ID_1, NAME_1, PHONE_1);
    public static final User USER_11 = new User(ID1, CHAT_ID_1, NAME_1, PHONE_2);
    public static final User USER_2 = new User(ID2, CHAT_ID_2, NAME_2, PHONE_2);
    public static final User USER_3 = new User(ID1, CHAT_ID_1, NAME_1, PHONE_1, PET_1, TRUE, START_DATE, FINISH_DATE);

    public static final Volunteer VOLUNTEER_1 = new Volunteer(ID1, CHAT_ID_1, NAME_1);
    public static final Volunteer VOLUNTEER_11 = new Volunteer(ID1, CHAT_ID_1, NAME_2);
    public static final Volunteer VOLUNTEER_2 = new Volunteer(ID2, CHAT_ID_2, NAME_2);

    public static final PetReport PET_REPORT_1 = new PetReport(ID1, PET_1, DATE_OF_REPORT_1, TEXT_OF_REPORT_1, PHOTO_LINK_1);
    public static final PetReport PET_REPORT_11 = new PetReport(ID1, PET_1, DATE_OF_REPORT_2, TEXT_OF_REPORT_1, PHOTO_LINK_1);
    public static final PetReport PET_REPORT_2 = new PetReport(ID2, PET_2, DATE_OF_REPORT_2, TEXT_OF_REPORT_2, PHOTO_LINK_2);
    public static final List<InformationMessage> INFORMATION_MESSAGE_LIST = new ArrayList<>(List.of(INFORMATION_MESSAGE_1, INFORMATION_MESSAGE_2));
    public static final List<Pet> PET_LIST = new ArrayList<>(List.of(PET_1, PET_2));
    public static final List<User> USER_LIST = new ArrayList<>(List.of(USER_1, USER_2));
    public static final List<Volunteer> VOLUNTEER_LIST = new ArrayList<>(List.of(VOLUNTEER_1,VOLUNTEER_2));
    public static final List<PetReport> PET_REPORT_LIST = new ArrayList<>(List.of(PET_REPORT_1, PET_REPORT_2));
    public static final List<PetReport> PET_REPORT_LIST_ORDERED = new ArrayList<>(List.of(PET_REPORT_1, PET_REPORT_11));
}
