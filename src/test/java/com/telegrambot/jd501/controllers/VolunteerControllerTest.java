package com.telegrambot.jd501.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telegrambot.jd501.model.Volunteer;
import com.telegrambot.jd501.repository.VolunteerRepository;
import com.telegrambot.jd501.service.VolunteerService;
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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = VolunteerController.class)
class VolunteerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VolunteerRepository volunteerRepository;

    @SpyBean
    private VolunteerService volunteerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllVolunteer() throws Exception {
        Long id1 = 1L;
        String name1 = "Мария";
        Volunteer expected1 = new Volunteer();
        expected1.setId(id1);
        expected1.setChatId(null);
        expected1.setName(name1);

        Long id2 = 2L;
        String name2 = "Анна";
        Volunteer expected2 = new Volunteer();
        expected2.setId(id2);
        expected2.setChatId(null);
        expected2.setName(name2);

        when(volunteerRepository.findAll()).thenReturn(List.of(expected1, expected2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/volunteer")
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

        Volunteer expected = new Volunteer();
        expected.setId(id);
        expected.setChatId(chatId);
        expected.setName(name);

        JSONObject volunteerObject = new JSONObject();
        volunteerObject.put("id", id);
        volunteerObject.put("chatId", chatId);
        volunteerObject.put("name", name);

        when(volunteerRepository.save(expected)).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/volunteer")
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

        Volunteer expected = new Volunteer();
        expected.setId(id);
        expected.setChatId(chatId);
        expected.setName(name);

        JSONObject volunteerObject = new JSONObject();
        volunteerObject.put("id", id);
        volunteerObject.put("chatId", chatId);
        volunteerObject.put("name", name);

        when(volunteerRepository.save(expected)).thenReturn(expected);
        when(volunteerRepository.findById(eq(id))).thenReturn(Optional.of(expected));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/volunteer")
                        .content(volunteerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void deleteVolunteer() throws Exception {
        Long id = 1L;
        Long chatId = null;
        String name = "Максим";

        Volunteer expected = new Volunteer();
        expected.setId(id);
        expected.setChatId(chatId);
        expected.setName(name);

        when(volunteerRepository.findById(eq(id))).thenReturn(Optional.of(expected));
        doNothing().when(volunteerRepository).deleteById(eq(id));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/volunteer/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}