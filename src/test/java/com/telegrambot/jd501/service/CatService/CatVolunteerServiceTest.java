package com.telegrambot.jd501.service.CatService;

import com.telegrambot.jd501.Exceptions.VolunteerNotFoundException;
import com.telegrambot.jd501.model.cat.CatVolunteer;
import com.telegrambot.jd501.repository.Cat.CatVolunteerRepository;
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
class CatVolunteerServiceTest {
    @Mock
    private CatVolunteerRepository catVolunteerRepository;

    @InjectMocks
    private CatVolunteerService out;

    @Test
    void getAllCatVolunteer() {
        when(catVolunteerRepository.findAll()).thenReturn(CAT_VOLUNTEER_LIST);

        assertEquals(out.getAllCatVolunteer(), CAT_VOLUNTEER_LIST);
    }

    @Test
    void createCatVolunteer() {
        when(catVolunteerRepository.save(any(CatVolunteer.class))).thenReturn(CAT_VOLUNTEER_1);

        assertEquals(out.createCatVolunteer(CAT_VOLUNTEER_1), CAT_VOLUNTEER_1);
    }

    @Test
    void updateCatVolunteer() {
        when(catVolunteerRepository.findById(ID1)).thenReturn(Optional.of(CAT_VOLUNTEER_1));
        when(catVolunteerRepository.save(CAT_VOLUNTEER_11)).thenReturn(CAT_VOLUNTEER_11);

        assertEquals(out.updateCatVolunteer(CAT_VOLUNTEER_11), CAT_VOLUNTEER_11);
    }

    @Test
    void deleteCatVolunteer() {
        when(catVolunteerRepository.findById(anyLong())).thenReturn(Optional.of(CAT_VOLUNTEER_1));

        assertEquals(out.deleteCatVolunteer(ID1),CAT_VOLUNTEER_1);
    }

    @Test
    void isExistsCatVolunteer() {
        when(catVolunteerRepository.existsByChatId(anyLong())).thenReturn(TRUE);

        assertEquals(out.isExistsCatVolunteer(CHAT_ID_1),TRUE);
    }

    @Test
    void volunteerNotFoundExceptionTest1() {
        when(catVolunteerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(VolunteerNotFoundException.class,() ->out.deleteCatVolunteer(ID2));
    }
    @Test
    void volunteerNotFoundExceptionTest2() {
        when(catVolunteerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(VolunteerNotFoundException.class,() ->out.updateCatVolunteer(CAT_VOLUNTEER_2));
    }
}