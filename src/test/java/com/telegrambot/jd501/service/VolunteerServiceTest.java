package com.telegrambot.jd501.service;

import com.telegrambot.jd501.Exceptions.VolunteerNotFoundException;
import com.telegrambot.jd501.model.Volunteer;
import com.telegrambot.jd501.repository.VolunteerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.telegrambot.jd501.service.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VolunteerServiceTest {

    @Mock
    private VolunteerRepository volunteerRepository;

    @InjectMocks
    private VolunteerService out;

    @Test
    void getAllVolunteerTest() {
        when(volunteerRepository.findAll()).thenReturn(VOLUNTEER_LIST);

        assertEquals(out.getAllVolunteer(), VOLUNTEER_LIST);
    }

    @Test
    void createVolunteerTest() {
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(VOLUNTEER_1);

        assertEquals(out.createVolunteer(VOLUNTEER_1), VOLUNTEER_1);

    }

    @Test
    void updateVolunteerTest() {
        when(volunteerRepository.findById(ID1)).thenReturn(Optional.of(VOLUNTEER_1));
        when(volunteerRepository.save(VOLUNTEER_11)).thenReturn(VOLUNTEER_11);

        assertEquals(out.updateVolunteer(VOLUNTEER_11), VOLUNTEER_11);
    }

    @Test
    void deleteVolunteerTest() {
        when(volunteerRepository.findById(ID1)).thenReturn(Optional.of(VOLUNTEER_1));

        assertEquals(out.deleteVolunteer(ID1),VOLUNTEER_1);
    }

    @Test
    void isExistsVolunteerTest() {
        when(volunteerRepository.existsByChatId(CHAT_ID_1)).thenReturn(TRUE);

        assertEquals(out.isExistsVolunteer(CHAT_ID_1),TRUE);
    }
    @Test
    void volunteerNotFoundExceptionTest1() {
        when(volunteerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(VolunteerNotFoundException.class,() ->out.deleteVolunteer(anyLong()));
    }
    @Test
    void volunteerNotFoundExceptionTest2() {
        when(volunteerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(VolunteerNotFoundException.class,() ->out.updateVolunteer(VOLUNTEER_2));
    }
}