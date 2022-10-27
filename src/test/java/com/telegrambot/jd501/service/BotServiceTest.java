package com.telegrambot.jd501.service;

import com.telegrambot.jd501.repository.InformationMessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static com.telegrambot.jd501.service.Constants.USER_LIST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BotServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private VolunteerService volunteerService;

    @Mock
    private InformationMessageRepository infoRepository;

    @Mock
    private PetReportService petReportService;

    @InjectMocks
    private BotService out;

    @Test
    void setupSendMessage() {
        SendMessage message = new SendMessage();
        message.setParseMode("HTML");
        message.setChatId(1L);
        message.setText("Тест");
        assertEquals(out.setupSendMessage(1L, "Тест"), message);
    }
}