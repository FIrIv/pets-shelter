package com.telegrambot.jd501.service.DogService;

import com.telegrambot.jd501.Exceptions.PetReportNotFoundException;
import com.telegrambot.jd501.model.dog.DogReport;
import com.telegrambot.jd501.model.dog.DogUser;
import com.telegrambot.jd501.repository.Dog.DogReportRepository;
import com.telegrambot.jd501.repository.Dog.DogUserRepository;
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

        assertEquals(out.getAllDogReport(), DOG_REPORT_LIST);
    }

    @Test
    void createDogReport() {
        when(dogReportRepository.save(any(DogReport.class))).thenReturn(DOG_REPORT_1);

        assertEquals(out.createDogReport(DOG_REPORT_1), DOG_REPORT_1);
    }

    @Test
    void updateDogReport() {
        when(dogReportRepository.findById(anyLong())).thenReturn(Optional.of(DOG_REPORT_1));
        when(dogReportRepository.save(any(DogReport.class))).thenReturn(DOG_REPORT_11);

        assertEquals(out.updateDogReport(DOG_REPORT_11), DOG_REPORT_11);
    }

    @Test
    void deleteDogReport()  {
        when(dogReportRepository.findById(anyLong())).thenReturn(Optional.of(DOG_REPORT_1));

        assertEquals(out.deleteDogReport(ID1), DOG_REPORT_1);
    }

    @Test
    void getDogReportByUserAndDateOfReport() {
        when(dogReportRepository.findDogReportByDogUserAndDateOfReport((any(DogUser.class)), any(LocalDate.class))).thenReturn(DOG_REPORT_1);

        assertEquals(out.getDogReportByUserAndDateOfReport(DOG_USER_1,DATE_OF_REPORT_1),DOG_REPORT_1);
    }

    @Test
    void getAllReportsByChatId() {
        when(dogUserRepository.findDogUserByChatId(anyLong())).thenReturn(DOG_USER_1);
        when(dogReportRepository.findAllByDogUser(any(DogUser.class))).thenReturn(List.of(DOG_REPORT_1));

        assertEquals(out.getAllReportsByChatId(CHAT_ID_1), DOG_REPORT_LIST_BY_USER);
    }
    @Test
    void petReportNotFoundExceptionTest1() {
        when(dogReportRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PetReportNotFoundException.class,() ->out.deleteDogReport(ID2));
    }
    @Test
    void petReportNotFoundExceptionTest2() {
        when(dogReportRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PetReportNotFoundException.class,() ->out.updateDogReport(DOG_REPORT_2));
    }
}