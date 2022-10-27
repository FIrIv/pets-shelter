package com.telegrambot.jd501.controllers.dog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telegrambot.jd501.controllers.Dog.DogVolunteerController;
import com.telegrambot.jd501.model.dog.DogVolunteer;
import com.telegrambot.jd501.repository.Dog.DogVolunteerRepository;
import com.telegrambot.jd501.service.DogService.DogVolunteerService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = DogVolunteerController.class)
class DogVolunteerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DogVolunteerRepository dogVolunteerRepository;

    @SpyBean
    private DogVolunteerService dogVolunteerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllDogVolunteers() throws Exception {
        Long id1 = 1L;
        String name1 = "Мария";
        DogVolunteer expected1 = new DogVolunteer(id1, null, name1);

        Long id2 = 2L;
        String name2 = "Анна";
        DogVolunteer expected2 = new DogVolunteer(id2, null, name2);

        when(dogVolunteerRepository.findAll()).thenReturn(List.of(expected1, expected2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/dog/volunteer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(expected1, expected2))));
    }

    @Test
    void createVolunteer() throws Exception {
        Long id = 1L;
        Long chatId = null;
        String name = "Максим";
        DogVolunteer expected = new DogVolunteer(id, chatId, name);

        JSONObject volunteerObject = new JSONObject();
        volunteerObject.put("id", id);
        volunteerObject.put("chatId", chatId);
        volunteerObject.put("name", name);
        System.out.println(volunteerObject);

        when(dogVolunteerRepository.save(expected)).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/dog/volunteer")
                        .content(volunteerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void updateVolunteer() throws Exception {
        Long id = 1L;
        Long chatId = null;
        String name = "Максим";

        DogVolunteer expected = new DogVolunteer(id, chatId, name);

        JSONObject volunteerObject = new JSONObject();
        volunteerObject.put("id", id);
        volunteerObject.put("chatId", chatId);
        volunteerObject.put("name", name);

        when(dogVolunteerRepository.save(expected)).thenReturn(expected);
        when(dogVolunteerRepository.findById(eq(id))).thenReturn(Optional.of(expected));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/dog/volunteer")
                        .content(volunteerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void deleteDogVolunteer() throws Exception {
        Long id = 1L;
        Long chatId = null;
        String name = "Максим";

        DogVolunteer expected = new DogVolunteer(id, chatId, name);

        when(dogVolunteerRepository.findById(eq(id))).thenReturn(Optional.of(expected));
        doNothing().when(dogVolunteerRepository).deleteById(eq(id));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/dog/volunteer/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}