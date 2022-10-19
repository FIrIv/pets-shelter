//package com.telegrambot.jd501.controllers;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//
//import static org.mockito.ArgumentMatchers.eq;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class PetReportControllerTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Autowired
//    PetController petController;
//
//    @Autowired
//    PetReportController petReportController;
//
//    @Test
//    void contextLoads() throws Exception {
//        Assertions.assertThat(petReportController).isNotNull();
//    }
//
//    /*@Test
//    void getAllPetReport() throws Exception {
//        Long id1 = 100L;
//        LocalDate dateOfReport1 = LocalDate.now();
//        String textOfReport1 = "Текст первого отчета";
//        String photoLink1 = "Ссылка на фото1";
//        Pet pet1 = new Pet();
//        pet1.setName("тестБакс1234567890");
//        Long petId1 = petController.createPet(pet1).getBody().getId();
//        pet1.setId(petId1);
//        PetReport expected1 = new PetReport();
//
//        expected1.setPet(pet1);
//        expected1.setDateOfReport(dateOfReport1);
//        expected1.setTextOfReport(textOfReport1);
//        expected1.setPhotoLink(photoLink1);
//
//        Long id2 = 101L;
//        LocalDate dateOfReport2 = LocalDate.now();
//        String textOfReport2 = "Текст второго отчета";
//        String photoLink2 = "Ссылка на фото2";
//        Pet pet2 = new Pet();
//        pet2.setName("тестБанни123456789");
//        Long petId2 = petController.createPet(pet2).getBody().getId();
//        pet2.setId(petId2);
//        PetReport expected2 = new PetReport();
//        expected2.setPet(pet2);
//        expected2.setDateOfReport(dateOfReport2);
//        expected2.setTextOfReport(textOfReport2);
//        expected2.setPhotoLink(photoLink2);
//
//        id1 = petReportController.createPetReport(expected1).getBody().getId();
//        expected1.setId(id1);
//        id2 = petReportController.createPetReport(expected2).getBody().getId();
//        expected2.setId(id2);
//
//        ResponseEntity<List<PetReport>> response = restTemplate.exchange("http://localhost:" + port + "/petReport", HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<PetReport>>() {});
//        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
//        Assertions.assertThat(response.getBody().size()).isGreaterThan(1);
//        Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(expected1);
//        Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(expected2);
//
//        petReportController.deletePetReport(id1);
//        petReportController.deletePetReport(id2);
//        petController.deletePet(petId1);
//        petController.deletePet(petId2);
//    }
//
//   /* @Test
//    void getAllPetReportByPet() throws Exception {
//        Long id1 = 100L;
//        LocalDate dateOfReport1 = LocalDate.now();
//        String textOfReport1 = "Текст первого отчета";
//        String photoLink1 = "Ссылка на фото1";
//        Pet pet1 = new Pet();
//        pet1.setName("тестБакс1234567890");
//        Pet petToFind = petController.createPet(pet1).getBody();
//        Long petId1 = petToFind.getId();
//        pet1.setId(petId1);
//        PetReport expected1 = new PetReport();
//
//        expected1.setPet(pet1);
//        expected1.setDateOfReport(dateOfReport1);
//        expected1.setTextOfReport(textOfReport1);
//        expected1.setPhotoLink(photoLink1);
//
//        Long id2 = 101L;
//        LocalDate dateOfReport2 = LocalDate.now();
//        String textOfReport2 = "Текст второго отчета";
//        String photoLink2 = "Ссылка на фото2";
//        Pet pet2 = new Pet();
//        pet2.setName("тестБанни123456789");
//        Long petId2 = petController.createPet(pet2).getBody().getId();
//        pet2.setId(petId2);
//        PetReport expected2 = new PetReport();
//        expected2.setPet(pet2);
//        expected2.setDateOfReport(dateOfReport2);
//        expected2.setTextOfReport(textOfReport2);
//        expected2.setPhotoLink(photoLink2);
//
//        Long id3 = 101L;
//        LocalDate dateOfReport3 = LocalDate.now();
//        String textOfReport3 = "Текст третьего отчета";
//        String photoLink3 = "Ссылка на фото3";
//        PetReport expected3 = new PetReport();
//        expected3.setPet(pet1);
//        expected3.setDateOfReport(dateOfReport3);
//        expected3.setTextOfReport(textOfReport3);
//        expected3.setPhotoLink(photoLink3);
//
//        id1 = petReportController.createPetReport(expected1).getBody().getId();
//        expected1.setId(id1);
//        id2 = petReportController.createPetReport(expected2).getBody().getId();
//        expected2.setId(id2);
//        id3 = petReportController.createPetReport(expected3).getBody().getId();
//        expected3.setId(id3);
//
//        RequestEntity<Pet> petRequest = RequestEntity
//                .get("http://localhost:" + port + "/petReport/pet_report/{pet}")
//                .accept(MediaType.APPLICATION_JSON)
//                .body(petToFind);
//
//        //ResponseEntity<List<PetReport>> response = restTemplate.exchange("http://localhost:" + port + "/petReport/pet_report/{petToFind}", HttpMethod.GET, null,
//        ResponseEntity<List<PetReport>> response = restTemplate.exchange(petRequest,
//                new ParameterizedTypeReference<List<PetReport>>() {});
//        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
//        Assertions.assertThat(response.getBody().size()).isGreaterThan(1);
//        Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(expected1);
//        Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(expected3);
//        Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).doesNotContain(expected2);
//
//        petReportController.deletePetReport(id1);
//        petReportController.deletePetReport(id2);
//        petReportController.deletePetReport(id3);
//        petController.deletePet(petId1);
//        petController.deletePet(petId2);
//    }
//
//    /*@Test
//    void createPetReport() throws Exception {
//        Long id1 = 100L;
//        LocalDate dateOfReport1 = LocalDate.now();
//        String textOfReport1 = "Текст первого отчета";
//        String photoLink1 = "Ссылка на фото1";
//        Pet pet1 = new Pet();
//        pet1.setId(1L);
//        pet1.setName("Бакс");
//        PetReport expected1 = new PetReport();
//        expected1.setId(id1);
//        expected1.setPet(pet1);
//        expected1.setDateOfReport(dateOfReport1);
//        expected1.setTextOfReport(textOfReport1);
//        expected1.setPhotoLink(photoLink1);
//
//        JSONObject petReportObject = new JSONObject();
//        petReportObject.put("id", id1);
//        petReportObject.put("petId", pet1.getId());
//        petReportObject.put("dateOfReport", dateOfReport1);
//        petReportObject.put("textOfReport", textOfReport1);
//        petReportObject.put("photoLink", photoLink1);
//
//        when(petReportRepository.save(expected1)).thenReturn(expected1);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/petReport")
//                        .content(petReportObject.toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(id1))
//                .andExpect(jsonPath("$.petId").value(pet1.getId()))
//                .andExpect(jsonPath("$.dateOfReport").value(DateTimeFormatter.ISO_LOCAL_DATE.format(dateOfReport1)))
//                .andExpect(jsonPath("$.textOfReport").value(textOfReport1))
//                .andExpect(jsonPath("$.photoLink").value(photoLink1));
//    }*/
//
//    /*@Test
//    void updatePetReport() throws Exception {
//        Long id1 = 100L;
//        LocalDate dateOfReport1 = LocalDate.now();
//        String textOfReport1 = "Текст первого отчета";
//        String photoLink1 = "Ссылка на фото1";
//        Pet pet1 = new Pet();
//        pet1.setId(1L);
//        pet1.setName("Бакс");
//        PetReport expected1 = new PetReport();
//        expected1.setId(id1);
//        expected1.setPet(pet1);
//        expected1.setDateOfReport(dateOfReport1);
//        expected1.setTextOfReport(textOfReport1);
//        expected1.setPhotoLink(photoLink1);
//
//        JSONObject petReportObject = new JSONObject();
//        petReportObject.put("id", id1);
//        petReportObject.put("petId", pet1.getId());
//        petReportObject.put("dateOfReport", dateOfReport1);
//        petReportObject.put("textOfReport", textOfReport1);
//        petReportObject.put("photoLink", photoLink1);
//
//        when(petReportRepository.save(expected1)).thenReturn(expected1);
//        when(petReportRepository.findById(eq(id1))).thenReturn(Optional.of(expected1));
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/petReport")
//                        .content(petReportObject.toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(id1))
//                .andExpect(jsonPath("$.petId").value(pet1.getId()))
//                .andExpect(jsonPath("$.dateOfReport").value(dateOfReport1))
//                .andExpect(jsonPath("$.textOfReport").value(textOfReport1))
//                .andExpect(jsonPath("$.photoLink").value(photoLink1));
//    }
//
//    @Test
//    void deletePetReport() throws Exception {
//        Long id1 = 100L;
//        LocalDate dateOfReport1 = LocalDate.now();
//        String textOfReport1 = "Текст первого отчета";
//        String photoLink1 = "Ссылка на фото1";
//        Pet pet1 = new Pet();
//        pet1.setId(1L);
//        pet1.setName("Бакс");
//        PetReport expected1 = new PetReport();
//        expected1.setId(id1);
//        expected1.setPet(pet1);
//        expected1.setDateOfReport(dateOfReport1);
//        expected1.setTextOfReport(textOfReport1);
//        expected1.setPhotoLink(photoLink1);
//
//        when(petReportRepository.findById(eq(id1))).thenReturn(Optional.of(expected1));
//        doNothing().when(petReportRepository).deleteById(eq(id1));
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .delete("/petReport/" + id1)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }*/
//}