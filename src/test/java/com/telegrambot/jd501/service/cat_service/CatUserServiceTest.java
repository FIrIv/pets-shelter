package com.telegrambot.jd501.service.cat_service;

import com.telegrambot.jd501.exceptions.PetNotFoundException;
import com.telegrambot.jd501.exceptions.UserNotFoundException;
import com.telegrambot.jd501.model.cat.CatUser;
import com.telegrambot.jd501.repository.cat.CatRepository;
import com.telegrambot.jd501.repository.cat.CatUserRepository;
import com.telegrambot.jd501.service.MailingListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.telegrambot.jd501.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatUserServiceTest {
    @Mock
    private CatUserRepository catUserRepository;

    @Mock
    private CatRepository catRepository;

    @Mock
    private MailingListService mailingListService;

    @InjectMocks
    private CatUserService out;

    @Test
    void getAllCatUser() {
        when(catUserRepository.findAll()).thenReturn(CAT_USER_LIST);

        assertEquals(out.getAllUsers(), CAT_USER_LIST);
    }

    @Test
    void createCatUser() {
        when(catUserRepository.save(any(CatUser.class))).thenReturn(CAT_USER_1);

        assertEquals(out.createUser(CAT_USER_1), CAT_USER_1);
    }

    @Test
    void updateCatUser() {
        when(catUserRepository.findById(anyLong())).thenReturn(Optional.of(CAT_USER_1));
        when(catUserRepository.save(any(CatUser.class))).thenReturn(CAT_USER_11);

        assertEquals(out.updateUser(CAT_USER_11), CAT_USER_11);
    }

    @Test
    void deleteCatUser() {
        when(catUserRepository.findById(anyLong())).thenReturn(Optional.of(CAT_USER_1));

        assertEquals(out.deleteUser(ID1), CAT_USER_1);
    }
    private static Stream<Arguments> parametersForProbationPeriodExtension() {
        return Stream.of(
                Arguments.of(14),
                Arguments.of(30)
        );
    }

    @ParameterizedTest
    @MethodSource("parametersForProbationPeriodExtension")
    void probationPeriodExtensionTest(Integer days) {
        when(catUserRepository.findById(anyLong())).thenReturn(Optional.of(CAT_USER_3));
        when(catUserRepository.save(any(CatUser.class))).thenReturn(CAT_USER_3);
        when(catUserRepository.findCatUserByChatId(anyLong())).thenReturn(CAT_USER_3);
        when(mailingListService.sendMessageToUserByChatId(anyLong(), anyString())).thenReturn(null);

        CAT_USER_4.setFinishDate(CAT_USER_3.getFinishDate().plusDays(days));
        assertEquals(out.probationPeriodExtension(ID1, days), CAT_USER_4);
    }

    @Test
    void changeStatusOfTheAdopter() {
        when(catUserRepository.findById(anyLong())).thenReturn(Optional.of(CAT_USER_1));
        when(catRepository.findById(anyLong())).thenReturn(Optional.of(CAT_1));
        when(catUserRepository.save(any(CatUser.class))).thenReturn(CAT_USER_3);
        when(catUserRepository.findCatUserByChatId(anyLong())).thenReturn(CAT_USER_3);
        when(mailingListService.sendMessageToUserByChatId(anyLong(), anyString())).thenReturn(null);

        assertEquals(out.changeStatusOfTheAdopter(ID1, ID1), CAT_USER_3);
    }

    @Test
    void changeStatusOfTheAdopterException1Test() {
        when(catUserRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> out.changeStatusOfTheAdopter(ID1, ID1));
    }

    @Test
    void changeStatusOfTheAdopterException2Test() {
        when(catUserRepository.findById(anyLong())).thenReturn(Optional.of(CAT_USER_1));
        when(catRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class, () -> out.changeStatusOfTheAdopter(ID1, ID1));
    }

    @Test
    void findCatUsersByAdoptedIsTrue() {
        when(catUserRepository.findCatUsersByIsAdoptedIsTrue()).thenReturn(List.of(CAT_USER_3,CAT_USER_4));

        assertEquals(out.findUsersByAdoptedIsTrue(), CAT_USER_ADOPTERS_LIST);
    }

    @Test
    void isExistsCatUser() {
        when(catUserRepository.existsByChatId(anyLong())).thenReturn(TRUE);

        assertEquals(out.isExistsUser(CHAT_ID_1), TRUE);
    }

    @Test
    void findCatUserByChatId() {
        when(catUserRepository.findCatUserByChatId(anyLong())).thenReturn(CAT_USER_2);

        assertEquals(out.findUserByChatId(CHAT_ID_2), CAT_USER_2);
    }

    @Test
    void userNotFoundExceptionTest1() {
        when(catUserRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,() ->out.deleteUser(ID2));
    }
    @Test
    void userNotFoundExceptionTest2() {
        when(catUserRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,() ->out.updateUser(CAT_USER_2));
    }

    @Test
    void userNotFoundByChatIdExceptionTest1() {
        when(catUserRepository.findCatUserByChatId(anyLong())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> out.sendMessageToUserWithChatId(CHAT_ID_2, "text"));
    }

    @Test
    void userNotFoundByChatIdExceptionTest2() {
        when(catUserRepository.findCatUserByChatId(anyLong())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> out.changeStatusUserPassedProbationPeriod(CHAT_ID_2));
    }

    @Test
    void userNotFoundByChatIdExceptionTest3() {
        when(catUserRepository.findCatUserByChatId(anyLong())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> out.changeStatusUserNotPassedProbationPeriod(CHAT_ID_2));
    }

    @Test
    void sendMessageToUserWithChatId() {
        String message = "text";
        when(catUserRepository.findCatUserByChatId(anyLong())).thenReturn(CAT_USER_1);
        when(mailingListService.sendMessageToUserByChatId(anyLong(), anyString())).thenReturn(null);

        String expected = "Message: " + message + "sent to User with chat Id: " + CHAT_ID_1;

        assertEquals(out.sendMessageToUserWithChatId(CHAT_ID_1, message), expected);
    }

    @Test
    void changeStatusUserPassedProbationPeriod() {
        when(catUserRepository.findCatUserByChatId(anyLong())).thenReturn(CAT_USER_3);
        when(mailingListService.sendMessageToUserByChatId(anyLong(), anyString())).thenReturn(null);
        when(catUserRepository.save(any(CatUser.class))).thenReturn(CAT_USER_1);

        assertEquals(out.changeStatusUserPassedProbationPeriod(CHAT_ID_1), CAT_USER_1);
    }

    @Test
    void changeStatusUserNotPassedProbationPeriod() {
        when(catUserRepository.findCatUserByChatId(anyLong())).thenReturn(CAT_USER_3);
        when(mailingListService.sendMessageToUserByChatId(anyLong(), anyString())).thenReturn(null);
        when(catUserRepository.save(any(CatUser.class))).thenReturn(CAT_USER_1);

        assertEquals(out.changeStatusUserNotPassedProbationPeriod(CHAT_ID_1), CAT_USER_1);
    }
}