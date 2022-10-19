//package com.telegrambot.jd501.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.telegrambot.jd501.model.pet.Dog;
//import com.telegrambot.jd501.repository.DogRepository;
//import com.telegrambot.jd501.service.PetService;
//import org.json.JSONObject;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.SpyBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(controllers = PetController.class)
//class PetControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private DogRepository petRepository;
//
//    @SpyBean
//    private PetService petService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void getAllPet() throws Exception {
//        Long id1 = 1L;
//        String name1 = "Бакс";
//        Dog expected1 = new Dog();
//        expected1.setId(id1);
//        expected1.setName(name1);
//
//        Long id2 = 2L;
//        String name2 = "Банни";
//        Dog expected2 = new Dog();
//        expected2.setId(id2);
//        expected2.setName(name2);
//
//        when(petRepository.findAll()).thenReturn(List.of(expected1, expected2));
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/pet")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(List.of(expected1, expected2))));
//    }
//
//    @Test
//    void createPet() throws Exception {
//        Long id = 1L;
//        String name = "Бакс";
//
//        Dog expected = new Dog();
//        expected.setId(id);
//        expected.setName(name);
//
//        JSONObject petObject = new JSONObject();
//        petObject.put("id", id);
//        petObject.put("name", name);
//
//        when(petRepository.save(expected)).thenReturn(expected);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/pet")
//                        .content(petObject.toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(id))
//                .andExpect(jsonPath("$.name").value(name));
//    }
//
//    @Test
//    void updatePet() throws Exception {
//        Long id = 1L;
//        String name = "Бакс";
//        Dog expected = new Dog();
//        expected.setId(id);
//        expected.setName(name);
//
//        JSONObject petObject = new JSONObject();
//        petObject.put("id", id);
//        petObject.put("name", name);
//
//        when(petRepository.save(expected)).thenReturn(expected);
//        when(petRepository.findById(eq(id))).thenReturn(Optional.of(expected));
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/pet")
//                        .content(petObject.toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(id))
//                .andExpect(jsonPath("$.name").value(name));
//    }
//
//    @Test
//    void deletePet() throws Exception {
//        Long id = 1L;
//        String name = "Бакс";
//        Dog expected = new Dog();
//        expected.setId(id);
//        expected.setName(name);
//
//        when(petRepository.findById(eq(id))).thenReturn(Optional.of(expected));
//        doNothing().when(petRepository).deleteById(eq(id));
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .delete("/pet/" + id)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//}