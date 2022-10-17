package com.telegrambot.jd501.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telegrambot.jd501.model.Pet;
import com.telegrambot.jd501.model.PetReport;
import com.telegrambot.jd501.repository.PetReportRepository;
import com.telegrambot.jd501.service.PetReportService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = PetReportController.class)
class PetReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetReportRepository petReportRepository;

    @SpyBean
    private PetReportService petReportService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllPetReport() throws Exception {
        Long id1 = 100L;
        LocalDate dateOfReport1 = LocalDate.now();
        String textOfReport1 = "Текст первого отчета";
        String photoLink1 = "Ссылка на фото1";
        Pet pet1 = new Pet();
        pet1.setId(1L);
        pet1.setName("Бакс");
        PetReport expected1 = new PetReport();
        expected1.setId(id1);
        expected1.setPet(pet1);
        expected1.setDateOfReport(dateOfReport1);
        expected1.setTextOfReport(textOfReport1);
        expected1.setPhotoLink(photoLink1);

        Long id2 = 101L;
        LocalDate dateOfReport2 = LocalDate.now();
        String textOfReport2 = "Текст второго отчета";
        String photoLink2 = "Ссылка на фото2";
        Pet pet2 = new Pet();
        pet2.setId(2L);
        pet2.setName("Банни");
        PetReport expected2 = new PetReport();
        expected2.setId(id2);
        expected2.setPet(pet2);
        expected2.setDateOfReport(dateOfReport2);
        expected2.setTextOfReport(textOfReport2);
        expected2.setPhotoLink(photoLink2);

        when(petReportRepository.findAll()).thenReturn(List.of(expected1, expected2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/petReport")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(expected1, expected2))));
    }

    /*@Test
    void getAllPetReportByPet() throws Exception {
        Long id1 = 100L;
        LocalDate dateOfReport1 = LocalDate.now();
        String textOfReport1 = "Текст первого отчета";
        String photoLink1 = "Ссылка на фото1";
        Pet pet1 = new Pet();
        pet1.setId(1L);
        pet1.setName("Бакс");
        PetReport expected1 = new PetReport();
        expected1.setId(id1);
        expected1.setPet(pet1);
        expected1.setDateOfReport(dateOfReport1);
        expected1.setTextOfReport(textOfReport1);
        expected1.setPhotoLink(photoLink1);

        Long id2 = 101L;
        LocalDate dateOfReport2 = LocalDate.now();
        String textOfReport2 = "Текст второго отчета";
        String photoLink2 = "Ссылка на фото2";
        Pet pet2 = new Pet();
        pet2.setId(2L);
        pet2.setName("Банни");
        PetReport expected2 = new PetReport();
        expected2.setId(id2);
        expected2.setPet(pet2);
        expected2.setDateOfReport(dateOfReport2);
        expected2.setTextOfReport(textOfReport2);
        expected2.setPhotoLink(photoLink2);

        Long id3 = 102L;
        LocalDate dateOfReport3 = LocalDate.now().minusDays(1);
        String textOfReport3 = "Текст третьего отчета";
        String photoLink3 = "Ссылка на фото3";
        PetReport expected3 = new PetReport();
        expected3.setId(id3);
        expected3.setPet(pet1);
        expected3.setDateOfReport(dateOfReport3);
        expected3.setTextOfReport(textOfReport3);
        expected3.setPhotoLink(photoLink3);

        when(petReportRepository.findPetReportsByPetOrderByDateOfReport(eq(pet1))).thenReturn(List.of(expected3, expected1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/petReport/pet_report")
                        .queryParam("pet", String.valueOf(pet1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(expected3, expected1))));
    }*/

    /*@Test
    void createPetReport() throws Exception {
        Long id1 = 100L;
        LocalDate dateOfReport1 = LocalDate.now();
        String textOfReport1 = "Текст первого отчета";
        String photoLink1 = "Ссылка на фото1";
        Pet pet1 = new Pet();
        pet1.setId(1L);
        pet1.setName("Бакс");
        PetReport expected1 = new PetReport();
        expected1.setId(id1);
        expected1.setPet(pet1);
        expected1.setDateOfReport(dateOfReport1);
        expected1.setTextOfReport(textOfReport1);
        expected1.setPhotoLink(photoLink1);

        JSONObject petReportObject = new JSONObject();
        petReportObject.put("id", id1);
        petReportObject.put("petId", pet1.getId());
        petReportObject.put("dateOfReport", dateOfReport1);
        petReportObject.put("textOfReport", textOfReport1);
        petReportObject.put("photoLink", photoLink1);

        when(petReportRepository.save(expected1)).thenReturn(expected1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/petReport")
                        .content(petReportObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id1))
                .andExpect(jsonPath("$.petId").value(pet1.getId()))
                .andExpect(jsonPath("$.dateOfReport").value(DateTimeFormatter.ISO_LOCAL_DATE.format(dateOfReport1)))
                .andExpect(jsonPath("$.textOfReport").value(textOfReport1))
                .andExpect(jsonPath("$.photoLink").value(photoLink1));
    }*/

    /*@Test
    void updatePetReport() throws Exception {
        Long id1 = 100L;
        LocalDate dateOfReport1 = LocalDate.now();
        String textOfReport1 = "Текст первого отчета";
        String photoLink1 = "Ссылка на фото1";
        Pet pet1 = new Pet();
        pet1.setId(1L);
        pet1.setName("Бакс");
        PetReport expected1 = new PetReport();
        expected1.setId(id1);
        expected1.setPet(pet1);
        expected1.setDateOfReport(dateOfReport1);
        expected1.setTextOfReport(textOfReport1);
        expected1.setPhotoLink(photoLink1);

        JSONObject petReportObject = new JSONObject();
        petReportObject.put("id", id1);
        petReportObject.put("petId", pet1.getId());
        petReportObject.put("dateOfReport", dateOfReport1);
        petReportObject.put("textOfReport", textOfReport1);
        petReportObject.put("photoLink", photoLink1);

        when(petReportRepository.save(expected1)).thenReturn(expected1);
        when(petReportRepository.findById(eq(id1))).thenReturn(Optional.of(expected1));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/petReport")
                        .content(petReportObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id1))
                .andExpect(jsonPath("$.petId").value(pet1.getId()))
                .andExpect(jsonPath("$.dateOfReport").value(dateOfReport1))
                .andExpect(jsonPath("$.textOfReport").value(textOfReport1))
                .andExpect(jsonPath("$.photoLink").value(photoLink1));
    }*/

    @Test
    void deletePetReport() throws Exception {
        Long id1 = 100L;
        LocalDate dateOfReport1 = LocalDate.now();
        String textOfReport1 = "Текст первого отчета";
        String photoLink1 = "Ссылка на фото1";
        Pet pet1 = new Pet();
        pet1.setId(1L);
        pet1.setName("Бакс");
        PetReport expected1 = new PetReport();
        expected1.setId(id1);
        expected1.setPet(pet1);
        expected1.setDateOfReport(dateOfReport1);
        expected1.setTextOfReport(textOfReport1);
        expected1.setPhotoLink(photoLink1);

        when(petReportRepository.findById(eq(id1))).thenReturn(Optional.of(expected1));
        doNothing().when(petReportRepository).deleteById(eq(id1));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/petReport/" + id1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}