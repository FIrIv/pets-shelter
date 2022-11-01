package com.telegrambot.jd501.service.cat_service;

import com.telegrambot.jd501.exceptions.InformationMessageNotFoundException;
import com.telegrambot.jd501.model.cat.CatInformationMessage;
import com.telegrambot.jd501.repository.cat.CatInformationMessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.telegrambot.jd501.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatInformationMessageServiceTest {
    @Mock
    private CatInformationMessageRepository catInformationMessageRepository;

    @InjectMocks
    private CatInformationMessageService out;

    @Test
    void getAllCatInformationMessage() {
        when(catInformationMessageRepository.findAll()).thenReturn(CAT_INFORMATION_MESSAGE_LIST);

        assertEquals(out.getAllInformationMessages(), CAT_INFORMATION_MESSAGE_LIST);
    }

    @Test
    void createCatInformationMessage() {
        when(catInformationMessageRepository.save(any(CatInformationMessage.class))).thenReturn(CAT_INFORMATION_MESSAGE_1);

        assertEquals(out.createInformationMessage(CAT_INFORMATION_MESSAGE_1), CAT_INFORMATION_MESSAGE_1);
    }

    @Test
    void updateCatInformationMessage() {
        when(catInformationMessageRepository.findById(anyLong())).thenReturn(Optional.of(CAT_INFORMATION_MESSAGE_1));
        when(catInformationMessageRepository.save(any(CatInformationMessage.class))).thenReturn(CAT_INFORMATION_MESSAGE_11);

        assertEquals(out.updateInformationMessage(CAT_INFORMATION_MESSAGE_11), CAT_INFORMATION_MESSAGE_11);
    }

    @Test
    void deleteCatInformationMessage() {
        when(catInformationMessageRepository.findById(anyLong())).thenReturn(Optional.of(CAT_INFORMATION_MESSAGE_1));

        assertEquals(out.deleteInformationMessage(ID1), CAT_INFORMATION_MESSAGE_1);
    }

    @Test
    void findCatInformationMessageById() {
        when(catInformationMessageRepository.findById(anyLong())).thenReturn(Optional.of(CAT_INFORMATION_MESSAGE_2));

        assertEquals(out.findInformationMessageById(ID2), CAT_INFORMATION_MESSAGE_2);
    }
    @Test
    void informationMessageNotFoundExceptionTest1() {
        when(catInformationMessageRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(InformationMessageNotFoundException.class,() ->out.deleteInformationMessage(ID2));
    }
    @Test
    void informationMessageNotFoundExceptionTest2() {
        when(catInformationMessageRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(InformationMessageNotFoundException.class,() ->out.updateInformationMessage(CAT_INFORMATION_MESSAGE_2));
    }
}