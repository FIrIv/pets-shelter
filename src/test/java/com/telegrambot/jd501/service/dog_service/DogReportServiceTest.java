package com.telegrambot.jd501.service.dog_service;

import com.telegrambot.jd501.exceptions.PetReportNotFoundException;
import com.telegrambot.jd501.model.dog.DogReport;
import com.telegrambot.jd501.model.dog.DogUser;
import com.telegrambot.jd501.repository.dog.DogReportRepository;
import com.telegrambot.jd501.repository.dog.DogUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.telegrambot.jd501.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DogReportServiceTest {
    
    @Mock
    private DogReportRepository dogReportRepository;
    @Mock
    private DogUserRepository dogUserRepository;

    @InjectMocks
    private DogReportService out;

    @Test
    void getAllDogReport() {
        when(dogReportRepository.findAll()).thenReturn(DOG_REPORT_LIST);

        assertEquals(out.getAllPetReports(), DOG_REPORT_LIST);
    }

    @Test
    void createDogReport() {
        when(dogReportRepository.save(any(DogReport.class))).thenReturn(DOG_REPORT_1);

        assertEquals(out.createPetReport(DOG_REPORT_1), DOG_REPORT_1);
    }

    @Test
    void updateDogReport() {
        when(dogReportRepository.findById(anyLong())).thenReturn(Optional.of(DOG_REPORT_1));
        when(dogReportRepository.save(any(DogReport.class))).thenReturn(DOG_REPORT_11);

        assertEquals(out.updatePetReport(DOG_REPORT_11), DOG_REPORT_11);
    }

    @Test
    void deleteDogReport()  {
        when(dogReportRepository.findById(anyLong())).thenReturn(Optional.of(DOG_REPORT_1));

        assertEquals(out.deletePetReport(ID1), DOG_REPORT_1);
    }

    @Test
    void getDogReportByUserAndDateOfReport() {
        when(dogReportRepository.findDogReportByDogUserAndDateOfReport((any(DogUser.class)), any(LocalDate.class))).thenReturn(DOG_REPORT_1);

        assertEquals(out.getPetReportByUserAndDateOfReport(DOG_USER_1,DATE_OF_REPORT_1),DOG_REPORT_1);
    }

    @Test
    void getAllReportsByChatId() {
        when(dogUserRepository.findDogUserByChatId(anyLong())).thenReturn(DOG_USER_1);
        when(dogReportRepository.findAllByDogUser(any(DogUser.class))).thenReturn(List.of(DOG_REPORT_1));

        assertEquals(out.getAllPetReportsByChatId(CHAT_ID_1), DOG_REPORT_LIST_BY_USER);
    }
    @Test
    void petReportNotFoundExceptionTest1() {
        when(dogReportRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PetReportNotFoundException.class,() ->out.deletePetReport(ID2));
    }
    @Test
    void petReportNotFoundExceptionTest2() {
        when(dogReportRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PetReportNotFoundException.class,() ->out.updatePetReport(DOG_REPORT_2));
    }

    @Test
    void getPhotoById() {
        when(dogReportRepository.findById(anyLong())).thenReturn(Optional.of(DOG_REPORT_1));

        assertEquals(out.getPhotoById(ID1), PHOTO_1);
    }

    @Test
    void petReportNotFoundExceptionTest3() {
        when(dogReportRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PetReportNotFoundException.class,() ->out.getPhotoById(ID2));
    }
}