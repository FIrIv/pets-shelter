package com.telegrambot.jd501.controllers.cat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telegrambot.jd501.controllers.Cat.CatController;
import com.telegrambot.jd501.model.cat.Cat;
import com.telegrambot.jd501.repository.Cat.CatRepository;
import com.telegrambot.jd501.service.CatService.CatService;

import net.minidev.json.JSONObject;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CatController.class)
class CatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatRepository catRepository;

    @SpyBean
    private CatService catService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllCats() throws Exception {
        Cat expected1 = new Cat(-1L, "Бакс");
        Cat expected2 = new Cat(-2L, "Банни");

        when(catRepository.findAll()).thenReturn(List.of(expected1, expected2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cat/cat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(expected1, expected2))));
    }

    @Test
    void createCat() throws Exception {
        Long id = -1L;
        String name = "Бакс";
        Cat expected = new Cat(id, name);
        System.out.println(expected);

        JSONObject catObject = new JSONObject();
        catObject.put("id", id);
        catObject.put("name", name);
        System.out.println(catObject);

        when(catRepository.save(expected)).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/cat/cat")
                        .content(catObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void updateCat() throws Exception {
        Long id = -1L;
        String name = "Бакс";
        Cat expected = new Cat(id, name);

        JSONObject catObject = new JSONObject();
        catObject.put("id", id);
        catObject.put("name", name);

        when(catRepository.save(expected)).thenReturn(expected);
        when(catRepository.findById(eq(id))).thenReturn(Optional.of(expected));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/cat/cat")
                        .content(catObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void deleteCat() throws Exception {
        Long id = -1L;
        String name = "Бакс";
        Cat expected = new Cat(id, name);

        when(catRepository.findById(eq(id))).thenReturn(Optional.of(expected));
        doNothing().when(catRepository).deleteById(eq(id));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/cat/cat/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}