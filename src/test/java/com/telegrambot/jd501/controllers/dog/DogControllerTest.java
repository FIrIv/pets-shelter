package com.telegrambot.jd501.controllers.dog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telegrambot.jd501.controllers.Dog.DogController;
import com.telegrambot.jd501.model.dog.Dog;
import com.telegrambot.jd501.repository.Dog.DogRepository;
import com.telegrambot.jd501.repository.Dog.DogRepository;
import com.telegrambot.jd501.service.DogService.DogService;
import com.telegrambot.jd501.service.DogService.DogService;
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

@WebMvcTest(controllers = DogController.class)
class DogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DogRepository dogRepository;

    @SpyBean
    private DogService dogService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllDogs() throws Exception {
        Dog expected1 = new Dog(-1L, "Бакс");
        Dog expected2 = new Dog(-2L, "Банни");

        when(dogRepository.findAll()).thenReturn(List.of(expected1, expected2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/dog/dog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(expected1, expected2))));
    }

    @Test
    void createDog() throws Exception {
        Long id = -1L;
        String name = "Бакс";
        Dog expected = new Dog(id, name);

        JSONObject dogObject = new JSONObject();
        dogObject.put("id", id);
        dogObject.put("name", name);

        when(dogRepository.save(expected)).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/dog/dog")
                        .content(dogObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void updateDog() throws Exception {
        Long id = 1L;
        String name = "Бакс";
        Dog expected = new Dog(id, name);

        JSONObject dogObject = new JSONObject();
        dogObject.put("id", id);
        dogObject.put("name", name);

        when(dogRepository.save(expected)).thenReturn(expected);
        when(dogRepository.findById(eq(id))).thenReturn(Optional.of(expected));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/dog/dog")
                        .content(dogObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void deleteDog() throws Exception {
        Long id = -1L;
        String name = "Бакс";
        Dog expected = new Dog(id, name);

        when(dogRepository.findById(eq(id))).thenReturn(Optional.of(expected));
        doNothing().when(dogRepository).deleteById(eq(id));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/dog/dog/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}