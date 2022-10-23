package com.telegrambot.jd501.service.CatService;

import com.telegrambot.jd501.Exceptions.PetNotFoundException;
import com.telegrambot.jd501.Exceptions.UserNotFoundException;
import com.telegrambot.jd501.model.cat.CatUser;
import com.telegrambot.jd501.repository.Cat.CatRepository;
import com.telegrambot.jd501.repository.Cat.CatUserRepository;
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

    @InjectMocks
    private CatUserService out;

    @Test
    void getAllCatUser() {
        when(catUserRepository.findAll()).thenReturn(CAT_USER_LIST);

        assertEquals(out.getAllCatUser(), CAT_USER_LIST);
    }

    @Test
    void createCatUser() {
        when(catUserRepository.save(any(CatUser.class))).thenReturn(CAT_USER_1);

        assertEquals(out.createCatUser(CAT_USER_1), CAT_USER_1);
    }

    @Test
    void updateCatUser() {
        when(catUserRepository.findById(anyLong())).thenReturn(Optional.of(CAT_USER_1));
        when(catUserRepository.save(any(CatUser.class))).thenReturn(CAT_USER_11);

        assertEquals(out.updateCatUser(CAT_USER_11), CAT_USER_11);
    }

    @Test
    void deleteCatUser() {
        when(catUserRepository.findById(anyLong())).thenReturn(Optional.of(CAT_USER_1));

        assertEquals(out.deleteCatUser(ID1), CAT_USER_1);
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

        CAT_USER_4.setFinishDate(CAT_USER_3.getFinishDate().plusDays(days));
        assertEquals(out.probationPeriodExtension(ID1, days), CAT_USER_4);
    }

    @Test
    void changeStatusOfTheAdopter() {
        when(catUserRepository.findById(anyLong())).thenReturn(Optional.of(CAT_USER_1));
        when(catRepository.findById(anyLong())).thenReturn(Optional.of(CAT_1));
        when(catUserRepository.save(any(CatUser.class))).thenReturn(CAT_USER_3);

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

        assertEquals(out.findCatUsersByAdoptedIsTrue(), CAT_USER_ADOPTERS_LIST);
    }

    @Test
    void isExistsCatUser() {
        when(catUserRepository.existsByChatId(anyLong())).thenReturn(TRUE);

        assertEquals(out.isExistsCatUser(CHAT_ID_1), TRUE);
    }

    @Test
    void findCatUserByChatId() {
        when(catUserRepository.findCatUserByChatId(anyLong())).thenReturn(CAT_USER_2);

        assertEquals(out.findCatUserByChatId(CHAT_ID_2), CAT_USER_2);
    }

    @Test
    void userNotFoundExceptionTest1() {
        when(catUserRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,() ->out.deleteCatUser(ID2));
    }
    @Test
    void userNotFoundExceptionTest2() {
        when(catUserRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,() ->out.updateCatUser(CAT_USER_2));
    }
}