package com.telegrambot.jd501.controllers;

import com.telegrambot.jd501.model.Pet;
import com.telegrambot.jd501.model.PetReport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PetReportControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    PetController petController;

    @Autowired
    PetReportController petReportController;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(petReportController).isNotNull();
    }

    @Test
    void getAllPetReport() throws Exception {
        // report 1
        Long id1 = -100L;
        LocalDate dateOfReport1 = LocalDate.now();
        String textOfReport1 = "Текст первого отчета";
        String photoLink1 = "Ссылка на фото1";
        Pet pet1 = new Pet();
        pet1.setName("тестБакс1234567890");
        Long petId1 = petController.createPet(pet1).getBody().getId();
        pet1.setId(petId1);
        PetReport expected1 = new PetReport();
        expected1.setPet(pet1);
        expected1.setDateOfReport(dateOfReport1);
        expected1.setTextOfReport(textOfReport1);
        expected1.setPhotoLink(photoLink1);

        // report 2
        Long id2 = -101L;
        LocalDate dateOfReport2 = LocalDate.now();
        String textOfReport2 = "Текст второго отчета";
        String photoLink2 = "Ссылка на фото2";
        Pet pet2 = new Pet();
        pet2.setName("тестБанни123456789");
        Long petId2 = petController.createPet(pet2).getBody().getId();
        pet2.setId(petId2);
        PetReport expected2 = new PetReport();
        expected2.setPet(pet2);
        expected2.setDateOfReport(dateOfReport2);
        expected2.setTextOfReport(textOfReport2);
        expected2.setPhotoLink(photoLink2);

        // create reports 1 & 2 in DB
        id1 = petReportController.createPetReport(expected1).getBody().getId();
        expected1.setId(id1);
        id2 = petReportController.createPetReport(expected2).getBody().getId();
        expected2.setId(id2);

        // test
        ResponseEntity<List<PetReport>> response = restTemplate.exchange("http://localhost:" + port + "/petReport", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<PetReport>>() {});
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().size()).isGreaterThan(1);
        Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(expected1);
        Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(expected2);

        petReportController.deletePetReport(id1);
        petReportController.deletePetReport(id2);
        petController.deletePet(petId1);
        petController.deletePet(petId2);
    }

    // почему-то не может преобразовать JSON (???)
    @Test
    void getAllPetReportByPetId() throws Exception {
        // report 1
        Long id1 = 100L;
        LocalDate dateOfReport1 = LocalDate.now();
        String textOfReport1 = "Текст первого отчета";
        String photoLink1 = "Ссылка на фото1";
        Pet pet1 = new Pet();
        pet1.setName("тестБакс1234567890");
        Pet petToFind = petController.createPet(pet1).getBody();
        Long petId1 = petToFind.getId();
        pet1.setId(petId1);
        PetReport expected1 = new PetReport();
        expected1.setPet(pet1);
        expected1.setDateOfReport(dateOfReport1);
        expected1.setTextOfReport(textOfReport1);
        expected1.setPhotoLink(photoLink1);

        // report 2
        Long id2 = 101L;
        LocalDate dateOfReport2 = LocalDate.now();
        String textOfReport2 = "Текст второго отчета";
        String photoLink2 = "Ссылка на фото2";
        Pet pet2 = new Pet();
        pet2.setName("тестБанни123456789");
        Long petId2 = petController.createPet(pet2).getBody().getId();
        pet2.setId(petId2);
        PetReport expected2 = new PetReport();
        expected2.setPet(pet2);
        expected2.setDateOfReport(dateOfReport2);
        expected2.setTextOfReport(textOfReport2);
        expected2.setPhotoLink(photoLink2);

        // report 3
        Long id3 = 101L;
        LocalDate dateOfReport3 = LocalDate.now();
        String textOfReport3 = "Текст третьего отчета";
        String photoLink3 = "Ссылка на фото3";
        PetReport expected3 = new PetReport();
        expected3.setPet(pet1);
        expected3.setDateOfReport(dateOfReport3);
        expected3.setTextOfReport(textOfReport3);
        expected3.setPhotoLink(photoLink3);

        // create reports 1 & 2 & 3 in DB
        id1 = petReportController.createPetReport(expected1).getBody().getId();
        expected1.setId(id1);
        id2 = petReportController.createPetReport(expected2).getBody().getId();
        expected2.setId(id2);
        id3 = petReportController.createPetReport(expected3).getBody().getId();
        expected3.setId(id3);

        // Data attached to the request.
        //HttpEntity<Pet> requestBody = new HttpEntity<Pet>(petToFind);

        // test
        ResponseEntity<List<PetReport>> response = restTemplate.exchange("http://localhost:" + port + "/petReport/pet_report/{petId}", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<PetReport>>() {}, petId1);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().size()).isGreaterThan(1);
        Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(expected1);
        Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(expected3);
        Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).doesNotContain(expected2);

        petReportController.deletePetReport(id1);
        petReportController.deletePetReport(id2);
        petReportController.deletePetReport(id3);
        petController.deletePet(petId1);
        petController.deletePet(petId2);
    }

    @Test
    void createPetReport() throws Exception {
        // user 1
        Long id1 = -100L;
        LocalDate dateOfReport1 = LocalDate.now();
        String textOfReport1 = "Текст первого отчета";
        String photoLink1 = "Ссылка на фото1";
        Pet pet1 = new Pet();
        pet1.setName("тестБакс1234567890");
        Pet petToFind = petController.createPet(pet1).getBody();
        Long petId1 = petToFind.getId();
        pet1.setId(petId1);
        PetReport expected1 = new PetReport();
        expected1.setPet(pet1);
        expected1.setDateOfReport(dateOfReport1);
        expected1.setTextOfReport(textOfReport1);
        expected1.setPhotoLink(photoLink1);

        // test
        ResponseEntity<PetReport> response = restTemplate.postForEntity("http://localhost:" + port + "/petReport", expected1, PetReport.class);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getPet()).isEqualTo(petToFind);
        Assertions.assertThat(response.getBody().getTextOfReport()).isEqualTo(textOfReport1);
        Assertions.assertThat(response.getBody().getDateOfReport()).isEqualTo(dateOfReport1.format(DateTimeFormatter.ISO_LOCAL_DATE));
        Assertions.assertThat(response.getBody().getPhotoLink()).isEqualTo(photoLink1);

        petReportController.deletePetReport(response.getBody().getId());
        petController.deletePet(petId1);
    }

    @Test
    void updatePetReport() throws Exception {
        // user 1
        Long id1 = -100L;
        LocalDate dateOfReport1 = LocalDate.now();
        String textOfReport1 = "Текст первого отчета";
        String photoLink1 = "Ссылка на фото1";
        Pet pet1 = new Pet();
        pet1.setName("тестБакс1234567890");
        Pet petToFind = petController.createPet(pet1).getBody();
        Long petId1 = petToFind.getId();
        pet1.setId(petId1);
        PetReport expected1 = new PetReport();
        expected1.setPet(pet1);
        expected1.setDateOfReport(dateOfReport1);
        expected1.setTextOfReport(textOfReport1);
        expected1.setPhotoLink(photoLink1);

        // report to change
        PetReport reportToChange  = petReportController.createPetReport(expected1).getBody();
        reportToChange.setTextOfReport("Новое имя");
        HttpEntity<PetReport> entityUp = new HttpEntity<PetReport>(reportToChange);

        // test
        ResponseEntity<PetReport> response = restTemplate.exchange("http://localhost:" + port + "/petReport", HttpMethod.PUT, entityUp, PetReport.class);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getTextOfReport()).isEqualTo("Новое имя");
        Assertions.assertThat(response.getBody().getId()).isEqualTo(reportToChange.getId());

        petReportController.deletePetReport(reportToChange.getId());
        petController.deletePet(petId1);
    }

    @Test
    void deletePetReport() throws Exception {
        Long id1 = -100L;
        LocalDate dateOfReport1 = LocalDate.now();
        String textOfReport1 = "Текст первого отчета";
        String photoLink1 = "Ссылка на фото1";
        Pet pet1 = new Pet();
        pet1.setName("тестБакс1234567890");
        Pet petToFind = petController.createPet(pet1).getBody();
        Long petId1 = petToFind.getId();
        pet1.setId(petId1);
        PetReport expected1 = new PetReport();
        expected1.setPet(pet1);
        expected1.setDateOfReport(dateOfReport1);
        expected1.setTextOfReport(textOfReport1);
        expected1.setPhotoLink(photoLink1);

        Long id = petReportController.createPetReport(expected1).getBody().getId();

        ResponseEntity<PetReport> response = restTemplate.exchange("/petReport/{id}", HttpMethod.DELETE, null,
                PetReport.class, id);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }
}