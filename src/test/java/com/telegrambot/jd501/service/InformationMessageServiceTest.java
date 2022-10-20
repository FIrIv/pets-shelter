package com.telegrambot.jd501.service;

import com.telegrambot.jd501.Exceptions.InformationMessageNotFoundException;
import com.telegrambot.jd501.model.InformationMessage;
import com.telegrambot.jd501.repository.InformationMessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.telegrambot.jd501.service.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InformationMessageServiceTest {

    @Mock
    private InformationMessageRepository informationMessageRepository;

    @InjectMocks
    private InformationMessageService out;

    @Test
    void getAllInformationMessageTest() {
        when(informationMessageRepository.findAll()).thenReturn(INFORMATION_MESSAGE_LIST);

        assertEquals(out.getAllInformationMessage(), INFORMATION_MESSAGE_LIST);
    }

    @Test
    void createInformationMessageTest() {
        when(informationMessageRepository.save(any(InformationMessage.class))).thenReturn(INFORMATION_MESSAGE_1);

        assertEquals(out.createInformationMessage(INFORMATION_MESSAGE_1), INFORMATION_MESSAGE_1);
    }

    @Test
    void deleteInformationMessageTest() {
        when(informationMessageRepository.findById(anyLong())).thenReturn(Optional.of(INFORMATION_MESSAGE_1));

        assertEquals(out.deleteInformationMessage(ID1), INFORMATION_MESSAGE_1);
    }

    @Test
    void informationMessageNotFoundExceptionTest1() {
        when(informationMessageRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(InformationMessageNotFoundException.class,() ->out.deleteInformationMessage(anyLong()));
    }
    @Test
    void informationMessageNotFoundExceptionTest2() {
        when(informationMessageRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(InformationMessageNotFoundException.class,() ->out.updateInformationMessage(INFORMATION_MESSAGE_2));
    }

    @Test
    void updateInformationMessageTest() {
        when(informationMessageRepository.findById(ID1)).thenReturn(Optional.of(INFORMATION_MESSAGE_1));
        when(informationMessageRepository.save(INFORMATION_MESSAGE_11)).thenReturn(INFORMATION_MESSAGE_11);

        assertEquals(out.updateInformationMessage(INFORMATION_MESSAGE_11), INFORMATION_MESSAGE_11);
    }
}