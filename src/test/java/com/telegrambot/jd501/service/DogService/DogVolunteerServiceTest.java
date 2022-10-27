package com.telegrambot.jd501.service.DogService;

import com.telegrambot.jd501.Exceptions.VolunteerNotFoundException;
import com.telegrambot.jd501.model.dog.DogVolunteer;
import com.telegrambot.jd501.repository.Dog.DogVolunteerRepository;
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
class DogVolunteerServiceTest {
    
    @Mock
    private DogVolunteerRepository dogVolunteerRepository;

    @InjectMocks
    private DogVolunteerService out;
    
    @Test
    void getAllDogVolunteer() {
        when(dogVolunteerRepository.findAll()).thenReturn(DOG_VOLUNTEER_LIST);

        assertEquals(out.getAllVolunteers(), DOG_VOLUNTEER_LIST);
    }

    @Test
    void createDogVolunteer() {
        when(dogVolunteerRepository.save(any(DogVolunteer.class))).thenReturn(DOG_VOLUNTEER_1);

        assertEquals(out.createVolunteer(DOG_VOLUNTEER_1), DOG_VOLUNTEER_1);
    }

    @Test
    void updateDogVolunteer() {
        when(dogVolunteerRepository.findById(ID1)).thenReturn(Optional.of(DOG_VOLUNTEER_1));
        when(dogVolunteerRepository.save(DOG_VOLUNTEER_11)).thenReturn(DOG_VOLUNTEER_11);

        assertEquals(out.updateVolunteer(DOG_VOLUNTEER_11), DOG_VOLUNTEER_11);
    }

    @Test
    void deleteDogVolunteer() {
        when(dogVolunteerRepository.findById(anyLong())).thenReturn(Optional.of(DOG_VOLUNTEER_1));

        assertEquals(out.deleteVolunteer(ID1),DOG_VOLUNTEER_1);
    }

    @Test
    void isExistsDogVolunteer() {
        when(dogVolunteerRepository.existsByChatId(anyLong())).thenReturn(TRUE);

        assertEquals(out.isExistsVolunteer(CHAT_ID_1),TRUE);
    }

    @Test
    void volunteerNotFoundExceptionTest1() {
        when(dogVolunteerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(VolunteerNotFoundException.class,() ->out.deleteVolunteer(ID2));
    }
    @Test
    void volunteerNotFoundExceptionTest2() {
        when(dogVolunteerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(VolunteerNotFoundException.class,() ->out.updateVolunteer(DOG_VOLUNTEER_2));
    }
}