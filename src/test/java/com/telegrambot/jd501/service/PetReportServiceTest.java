package com.telegrambot.jd501.service;

import com.telegrambot.jd501.Exceptions.PetReportNotFoundException;
import com.telegrambot.jd501.model.PetReport;
import com.telegrambot.jd501.repository.PetReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.telegrambot.jd501.service.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PetReportServiceTest {

    @Mock
    private PetReportRepository petReportRepository;

    @InjectMocks
    private PetReportService out;

    @Test
    void getAllPetReportTest() {
        when(petReportRepository.findAll()).thenReturn(PET_REPORT_LIST);

        assertEquals(out.getAllPetReport(), PET_REPORT_LIST);
    }

    @Test
    void createPetReportTest() {
        when(petReportRepository.save(any(PetReport.class))).thenReturn(PET_REPORT_1);

        assertEquals(out.createPetReport(PET_REPORT_1), PET_REPORT_1);
    }

    @Test
    void updatePetReportTest() {
        when(petReportRepository.findById(anyLong())).thenReturn(Optional.of(PET_REPORT_1));
        when(petReportRepository.save(PET_REPORT_11)).thenReturn(PET_REPORT_11);

        assertEquals(out.updatePetReport(PET_REPORT_11), PET_REPORT_11);
    }

    @Test
    void deletePetReportTest() {
        when(petReportRepository.findById(anyLong())).thenReturn(Optional.of(PET_REPORT_1));

        assertEquals(out.deletePetReport(ID1), PET_REPORT_1);
    }

    @Test
    void getAllPetReportByPetTest() {
        when(petReportRepository.findPetReportsByPetOrderByDateOfReport(PET_1)).thenReturn(List.of(PET_REPORT_1,PET_REPORT_11));

        assertEquals(out.getAllPetReportByPet(PET_1), PET_REPORT_LIST_ORDERED);
    }

    @Test
    void getPetReportByPetAndDateOfReportTest() {
        when(petReportRepository.findPetReportByPetAndDateOfReport(PET_1,DATE_OF_REPORT_1)).thenReturn(PET_REPORT_1);

        assertEquals(out.getPetReportByPetAndDateOfReport(PET_1,DATE_OF_REPORT_1), PET_REPORT_1);
    }

    @Test
    void petReportNotFoundExceptionTest1() {
        when(petReportRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PetReportNotFoundException.class,() ->out.deletePetReport(anyLong()));
    }

    @Test
    void petReportNotFoundExceptionTest2() {
        when(petReportRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PetReportNotFoundException.class,() ->out.updatePetReport(PET_REPORT_2));
    }
}