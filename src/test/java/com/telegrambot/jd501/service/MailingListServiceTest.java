package com.telegrambot.jd501.service;

import com.telegrambot.jd501.model.MailingList;
import com.telegrambot.jd501.repository.MailingListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.telegrambot.jd501.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailingListServiceTest {

    @Mock
    private MailingListRepository mailingListRepository;

    @InjectMocks
    private MailingListService out;

    @Test
    void getAllMailingList() {
        when(mailingListRepository.findAll()).thenReturn(MAILING_LISTS);

        assertEquals(out.getAllMailingList(), MAILING_LISTS);
    }

    @Test
    void sendMessageToUserByChatId() {
        when(mailingListRepository.save(any(MailingList.class))).thenReturn(MAILING_LIST_1);

        assertEquals(out.sendMessageToUserByChatId(CHAT_ID_1, TEXT1), MAILING_LIST_1);
    }

    @Test
    void cleanMailingList() {
        doNothing().when(mailingListRepository).deleteAll();
        out.cleanMailingList();
    }
}