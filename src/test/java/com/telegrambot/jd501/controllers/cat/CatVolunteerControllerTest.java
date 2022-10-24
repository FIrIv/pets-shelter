package com.telegrambot.jd501.controllers.cat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telegrambot.jd501.controllers.Cat.CatVolunteerController;
import com.telegrambot.jd501.model.cat.CatVolunteer;
import com.telegrambot.jd501.repository.Cat.CatVolunteerRepository;
import com.telegrambot.jd501.service.CatService.CatVolunteerService;
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

@WebMvcTest(controllers = CatVolunteerController.class)
class CatVolunteerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatVolunteerRepository catVolunteerRepository;

    @SpyBean
    private CatVolunteerService catVolunteerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllCatVolunteers() throws Exception {
        Long id1 = 1L;
        String name1 = "Мария";
        CatVolunteer expected1 = new CatVolunteer(id1, null, name1);

        Long id2 = 2L;
        String name2 = "Анна";
        CatVolunteer expected2 = new CatVolunteer(id2, null, name2);

        when(catVolunteerRepository.findAll()).thenReturn(List.of(expected1, expected2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cat/volunteer")
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
        CatVolunteer expected = new CatVolunteer(id, chatId, name);

        JSONObject volunteerObject = new JSONObject();
        volunteerObject.put("id", id);
        volunteerObject.put("chatId", chatId);
        volunteerObject.put("name", name);
        System.out.println(volunteerObject);

        when(catVolunteerRepository.save(expected)).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/cat/volunteer")
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

        CatVolunteer expected = new CatVolunteer(id, chatId, name);

        JSONObject volunteerObject = new JSONObject();
        volunteerObject.put("id", id);
        volunteerObject.put("chatId", chatId);
        volunteerObject.put("name", name);
        System.out.println(volunteerObject);

        when(catVolunteerRepository.save(expected)).thenReturn(expected);
        when(catVolunteerRepository.findById(eq(id))).thenReturn(Optional.of(expected));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/cat/volunteer")
                        .content(volunteerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void deleteCatVolunteer() throws Exception {
        Long id = 1L;
        Long chatId = null;
        String name = "Максим";

        CatVolunteer expected = new CatVolunteer(id, chatId, name);

        when(catVolunteerRepository.findById(eq(id))).thenReturn(Optional.of(expected));
        doNothing().when(catVolunteerRepository).deleteById(eq(id));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/cat/volunteer/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}