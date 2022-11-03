package com.telegrambot.jd501.service;

import com.telegrambot.jd501.model.cat.CatUser;
import com.telegrambot.jd501.model.dog.DogUser;
import com.telegrambot.jd501.service.cat_service.CatReportService;
import com.telegrambot.jd501.service.cat_service.CatUserService;
import com.telegrambot.jd501.service.cat_service.CatVolunteerService;
import com.telegrambot.jd501.service.dog_service.DogReportService;
import com.telegrambot.jd501.service.dog_service.DogUserService;
import com.telegrambot.jd501.service.dog_service.DogVolunteerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDate;
import java.util.List;

import static com.telegrambot.jd501.Constants.*;
import static com.telegrambot.jd501.ConstantsUpdate.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BotServiceTest {
    @Mock
    private CatUserService catUserService;
    @Mock
    private DogUserService dogUserService;
    @Mock
    private CatVolunteerService catVolunteerService;
    @Mock
    private DogVolunteerService dogVolunteerService;
    @Mock
    private CatReportService catReportService;
    @Mock
    private DogReportService dogReportService;
    @InjectMocks
    private BotService out;

    @Test
    void setupSendMessage() {
        assertEquals(out.setupSendMessage(CHAT_ID_1, TEXT1), TEST_MESSAGE);
    }

    @Test
    void setButtons() {
        SendMessage message = out.setButtons(CHAT_ID_1, TEST_NAMES_OF_BUTTONS, ZERO, TWO);
        message.setText(TEXT2);

        assertEquals(message,TEST_MESSAGE_BUTTON);
    }

    @Test
    void checkInputMessage() {
        assertEquals(out.checkInputMessage(TEST_UPDATE_1),TEST_SEND_MESSAGE);
    }

    @Test
    void replyAboutSavingContact() {

        assertEquals(out.replyAboutSavingContact(TEST_UPDATE_1),REPLY_SEND_MESSAGE);
    }

    @Test
    void getPicture() {
    }

    @Test
    void sendUserPhoneToVolunteer() {
        when(catUserService.isExistsUser(anyLong())).thenReturn(TRUE);
        when(catUserService.findUserByChatId(anyLong())).thenReturn(CAT_USER_1);
        when(catVolunteerService.getAllVolunteers()).thenReturn(CAT_VOLUNTEER_LIST);

        assertEquals(out.sendUserPhoneToVolunteer(TEST_UPDATE_1),TEST_SEND_MESSAGE_TO_VOLUNTEER);
    }

    @Test
    void checkReportsOfTwoLastDaysCats() {
        when(catUserService.findUsersByAdoptedIsTrue()).thenReturn(List.of(CAT_USER_3));
        when(catReportService.getPetReportByUserAndDateOfReport(any(CatUser.class), any(LocalDate.class))).thenReturn(null);
        when(catUserService.findUserByChatId(anyLong())).thenReturn(CAT_USER_3);
        when(catVolunteerService.getAllVolunteers()).thenReturn(CAT_VOLUNTEER_LIST);

        assertEquals(out.checkReportsOfTwoLastDaysCats(), List.of(TEST_MESSAGE_22,TEST_MESSAGE_2));
    }

    @Test
    void checkReportsOfTwoLastDaysDogs() {
        when(dogUserService.findUsersByAdoptedIsTrue()).thenReturn(List.of(DOG_USER_3));
        when(dogReportService.getPetReportByUserAndDateOfReport(any(DogUser.class), any(LocalDate.class))).thenReturn(DOG_REPORT_3);

        assertEquals(out.checkReportsOfTwoLastDaysDogs(), List.of(TEST_MESSAGE_4));
    }

    @Test
    void checkDogsAdoptersForTrialPeriodHasExpired() {
        when(dogUserService.findUsersByAdoptedIsTrue()).thenReturn(List.of(DOG_USER_33));
        when(dogUserService.findUserByChatId(anyLong())).thenReturn(DOG_USER_33);
        when(dogVolunteerService.getAllVolunteers()).thenReturn(DOG_VOLUNTEER_LIST);

        assertEquals(out.checkDogsAdoptersForTrialPeriodHasExpired(), List.of(TEST_MESSAGE_34));
    }

    @Test
    void checkCatsAdoptersForTrialPeriodHasExpired() {
        when(catUserService.findUsersByAdoptedIsTrue()).thenReturn(List.of(CAT_USER_33));
        when(catUserService.findUserByChatId(anyLong())).thenReturn(CAT_USER_33);
        when(catVolunteerService.getAllVolunteers()).thenReturn(CAT_VOLUNTEER_LIST);

        assertEquals(out.checkCatsAdoptersForTrialPeriodHasExpired(), List.of(TEST_MESSAGE_33));
    }
}