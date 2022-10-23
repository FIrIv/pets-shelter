package com.telegrambot.jd501.service.DogService;

import com.telegrambot.jd501.Exceptions.PetNotFoundException;
import com.telegrambot.jd501.Exceptions.UserNotFoundException;
import com.telegrambot.jd501.model.dog.DogUser;
import com.telegrambot.jd501.repository.Dog.DogRepository;
import com.telegrambot.jd501.repository.Dog.DogUserRepository;
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
class DogUserServiceTest {
    @Mock
    private DogUserRepository dogUserRepository;

    @Mock
    private DogRepository dogRepository;

    @InjectMocks
    private DogUserService out;

    @Test
    void getAllDogUser()  {
        when(dogUserRepository.findAll()).thenReturn(DOG_USER_LIST);

        assertEquals(out.getAllDogUser(), DOG_USER_LIST);
    }

    @Test
    void createDogUser() {
        when(dogUserRepository.save(any(DogUser.class))).thenReturn(DOG_USER_1);

        assertEquals(out.createDogUser(DOG_USER_1), DOG_USER_1);
    }

    @Test
    void updateDogUser() {
        when(dogUserRepository.findById(anyLong())).thenReturn(Optional.of(DOG_USER_1));
        when(dogUserRepository.save(any(DogUser.class))).thenReturn(DOG_USER_11);

        assertEquals(out.updateDogUser(DOG_USER_11), DOG_USER_11);
    }

    @Test
    void deleteDogUser() {
        when(dogUserRepository.findById(anyLong())).thenReturn(Optional.of(DOG_USER_1));

        assertEquals(out.deleteDogUser(ID1), DOG_USER_1);
    }

    private static Stream<Arguments> parametersForProbationPeriodExtension() {
        return Stream.of(
                Arguments.of(14),
                Arguments.of(30)
        );
    }

    @ParameterizedTest
    @MethodSource("parametersForProbationPeriodExtension")
    void probationPeriodExtension(Integer days) {
        when(dogUserRepository.findById(anyLong())).thenReturn(Optional.of(DOG_USER_3));
        when(dogUserRepository.save(any(DogUser.class))).thenReturn(DOG_USER_3);

        DOG_USER_4.setFinishDate(DOG_USER_3.getFinishDate().plusDays(days));
        assertEquals(out.probationPeriodExtension(ID1, days), DOG_USER_4);
    }

    @Test
    void changeStatusOfTheAdopter() {
        when(dogUserRepository.findById(anyLong())).thenReturn(Optional.of(DOG_USER_1));
        when(dogRepository.findById(anyLong())).thenReturn(Optional.of(DOG_1));
        when(dogUserRepository.save(any(DogUser.class))).thenReturn(DOG_USER_3);

        assertEquals(out.changeStatusOfTheAdopter(ID1, ID1), DOG_USER_3);
    }
    
    @Test
    void changeStatusOfTheAdopterException1Test() {
        when(dogUserRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> out.changeStatusOfTheAdopter(ID1, ID1));
    }

    @Test
    void changeStatusOfTheAdopterException2Test() {
        when(dogUserRepository.findById(anyLong())).thenReturn(Optional.of(DOG_USER_1));
        when(dogRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class, () -> out.changeStatusOfTheAdopter(ID1, ID1));
    }
    
    @Test
    void findDogUsersByAdoptedIsTrue() {
        when(dogUserRepository.findDogUsersByIsAdoptedIsTrue()).thenReturn(List.of(DOG_USER_3,DOG_USER_4));

        assertEquals(out.findDogUsersByAdoptedIsTrue(), DOG_USER_ADOPTERS_LIST);
    }

    @Test
    void isExistsDogUser() {
        when(dogUserRepository.existsByChatId(anyLong())).thenReturn(TRUE);

        assertEquals(out.isExistsDogUser(CHAT_ID_1), TRUE);
    }

    @Test
    void findDogUserByChatId() {
        when(dogUserRepository.findDogUserByChatId(anyLong())).thenReturn(DOG_USER_2);

        assertEquals(out.findDogUserByChatId(CHAT_ID_2), DOG_USER_2);
    }

    @Test
    void userNotFoundExceptionTest1() {
        when(dogUserRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,() ->out.deleteDogUser(ID2));
    }
    @Test
    void userNotFoundExceptionTest2() {
        when(dogUserRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,() ->out.updateDogUser(DOG_USER_2));
    }
}