package com.telegrambot.jd501.service;

import com.telegrambot.jd501.Exceptions.PetNotFoundException;
import com.telegrambot.jd501.Exceptions.UserNotFoundException;
import com.telegrambot.jd501.model.User;
import com.telegrambot.jd501.repository.PetRepository;
import com.telegrambot.jd501.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.telegrambot.jd501.service.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private UserService out;

    @Test
    void getAllUserTest() {
        when(userRepository.findAll()).thenReturn(USER_LIST);

        assertEquals(out.getAllUser(), USER_LIST);
    }

    @Test
    void createUserTest() {
        when(userRepository.save(any(User.class))).thenReturn(USER_1);

        assertEquals(out.createUser(USER_1), USER_1);
    }

    @Test
    void updateUserTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(USER_1));
        when(userRepository.save(USER_11)).thenReturn(USER_11);

        assertEquals(out.updateUser(USER_11), USER_11);
    }

    @Test
    void deleteUserTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(USER_1));

        assertEquals(out.deleteUser(ID1), USER_1);
    }

    @Test
    void userNotFoundExceptionTest1() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> out.deleteUser(anyLong()));
    }

    @Test
    void userNotFoundExceptionTest2() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> out.updateUser(USER_2));
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
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(USER_3));
        when(userRepository.save(any(User.class))).thenReturn(USER_3);

        LocalDate updatedDate = USER_3.getFinishDate().plusDays(days);
        String result = "Probationary period " + USER_3.getName() + " increased by" + days + "days. Up to " + updatedDate;
        assertEquals(out.probationPeriodExtension(any(Long.class), days), result);
    }

    @Test
    void changeStatusOfTheAdopterTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(USER_1));
        when(petRepository.findById(1L)).thenReturn(Optional.of(PET_1));
        when(userRepository.save(any(User.class))).thenReturn(USER_3);

        assertEquals(out.changeStatusOfTheAdopter(1L, 1L), USER_3);

    }

    @Test
    void changeStatusOfTheAdopterException1Test() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> out.changeStatusOfTheAdopter(anyLong(), 1L));
    }

    @Test
    void changeStatusOfTheAdopterException2Test() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(USER_1));
        when(petRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class, () -> out.changeStatusOfTheAdopter(1L, anyLong()));
    }

    @Test
    void findUsersByAdoptedIsTrueTest() {
        when(userRepository.findUsersByIsAdoptedIsTrue()).thenReturn(List.of(USER_3));

        assertEquals(out.findUsersByAdoptedIsTrue(), List.of(USER_3));
    }

    @Test
    void isExistsUserTest() {
        when(userRepository.existsByChatId(anyLong())).thenReturn(TRUE);

        assertEquals(out.isExistsUser(anyLong()), TRUE);
    }

    @Test
    void findUserByChatIdTest() {
        when(userRepository.findUserByChatId(anyLong())).thenReturn(USER_2);

        assertEquals(out.findUserByChatId(anyLong()), USER_2);
    }
}