package com.telegrambot.jd501.controllers.cat;

import com.telegrambot.jd501.controllers.Cat.CatController;
import com.telegrambot.jd501.controllers.Cat.CatReportController;
import com.telegrambot.jd501.controllers.Cat.CatUserController;
import com.telegrambot.jd501.model.cat.Cat;
import com.telegrambot.jd501.model.cat.CatReport;
import com.telegrambot.jd501.model.cat.CatUser;
import com.telegrambot.jd501.service.CatService.CatUserService;
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
class CatReportControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    CatController catController;

    @Autowired
    CatUserService catUserService;

    @Autowired
    CatUserController catUserController;

    @Autowired
    CatReportController catReportController;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(catReportController).isNotNull();
    }

    @Test
    void getAllReports() {
        // pet 1
        Cat pet1 = new Cat(-1L, "тестБакс1234567890");
        pet1 = catController.createPet(pet1).getBody();
        Long petId1 = pet1.getId();

        // user 1
        Long userId1 = -1L;
        Long userChatId1 = -12341L;
        String userName1 = "Тестовый юзер1";
        String userPhone1 = "+1234567891";
        CatUser expectedUser1 = new CatUser(userId1, userChatId1, userName1, userPhone1);
        expectedUser1 = catUserController.createUser(expectedUser1).getBody();
        userId1 = expectedUser1.getId();
        catUserService.changeStatusOfTheAdopter(userId1, petId1);

        // report 1
        Long id1 = -1L;
        LocalDate dateOfReport1 = LocalDate.now();
        String textOfReport1 = "Текст первого отчета";
        byte[] photo1 = {1};
        CatReport expected1 = new CatReport(id1, dateOfReport1, textOfReport1, photo1, expectedUser1);

        // pet 2
        Cat pet2 = new Cat(-2L, "тестБакс2234567890");
        pet2 = catController.createPet(pet2).getBody();
        Long petId2 = pet2.getId();

        // user 2
        Long userId2 = -2L;
        Long userChatId2 = -22342L;
        String userName2 = "Тестовый юзер2";
        String userPhone2 = "+2234567892";
        CatUser expectedUser2 = new CatUser(userId2, userChatId2, userName2, userPhone2);
        expectedUser2 = catUserController.createUser(expectedUser2).getBody();
        userId2 = expectedUser2.getId();
        catUserService.changeStatusOfTheAdopter(userId2, petId2);

        // report 2
        Long id2 = -2L;
        LocalDate dateOfReport2 = LocalDate.now();
        String textOfReport2 = "Текст первого отчета";
        byte[] photo2 = {2};
        CatReport expected2 = new CatReport(id2, dateOfReport2, textOfReport2, photo2, expectedUser2);

        // create reports 1 & 2 in DB
        expected1 = catReportController.createReport(expected1).getBody();
        id1 = expected1.getId();
        expected2 = catReportController.createReport(expected2).getBody();
        id2 = expected2.getId();

        // test
        ResponseEntity<List<CatReport>> response = restTemplate.exchange("http://localhost:" + port + "/cat/report", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CatReport>>() {});
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().size()).isGreaterThan(1);
        Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(expected1);
        Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(expected2);

        catReportController.deleteReport(id1);
        catReportController.deleteReport(id2);

        catUserController.deleteUser(userId1);
        catUserController.deleteUser(userId2);

        catController.deletePet(petId1);
        catController.deletePet(petId2);
    }

    @Test
    void getAllReportsByChatId() {
        // pet 1
        Cat pet1 = new Cat(-1L, "тестБакс1234567890");
        pet1 = catController.createPet(pet1).getBody();
        Long petId1 = pet1.getId();

        // user 1
        Long userId1 = -1L;
        Long userChatId1 = -12341L;
        String userName1 = "Тестовый юзер1";
        String userPhone1 = "+1234567891";
        CatUser expectedUser1 = new CatUser(userId1, userChatId1, userName1, userPhone1);
        expectedUser1 = catUserController.createUser(expectedUser1).getBody();
        userId1 = expectedUser1.getId();
        catUserService.changeStatusOfTheAdopter(userId1, petId1);

        // report 1
        Long id1 = -1L;
        LocalDate dateOfReport1 = LocalDate.now();
        String textOfReport1 = "Текст первого отчета";
        byte[] photo1 = {1};
        CatReport expected1 = new CatReport(id1, dateOfReport1, textOfReport1, photo1, expectedUser1);

        // pet 2
        Cat pet2 = new Cat(-2L, "тестБакс2234567890");
        pet2 = catController.createPet(pet2).getBody();
        Long petId2 = pet2.getId();

        // user 2
        Long userId2 = -2L;
        Long userChatId2 = -22342L;
        String userName2 = "Тестовый юзер2";
        String userPhone2 = "+2234567892";
        CatUser expectedUser2 = new CatUser(userId2, userChatId2, userName2, userPhone2);
        expectedUser2 = catUserController.createUser(expectedUser2).getBody();
        userId2 = expectedUser2.getId();
        catUserService.changeStatusOfTheAdopter(userId2, petId2);

        // report 2
        Long id2 = -2L;
        LocalDate dateOfReport2 = LocalDate.now();
        String textOfReport2 = "Текст первого отчета";
        byte[] photo2 = {2};
        CatReport expected2 = new CatReport(id2, dateOfReport2, textOfReport2, photo2, expectedUser2);

        // report 3
        Long id3 = -3L;
        LocalDate dateOfReport3 = LocalDate.now();
        String textOfReport3 = "Текст третьего отчета";
        byte[] photo3 = {3};
        CatReport expected3 = new CatReport(id3, dateOfReport3, textOfReport3, photo3, expectedUser1);

        // create reports 1 & 2 & 3 in DB
        expected1 = catReportController.createReport(expected1).getBody();
        id1 = expected1.getId();
        expected2 = catReportController.createReport(expected2).getBody();
        id2 = expected2.getId();
        expected3 = catReportController.createReport(expected3).getBody();
        id3 = expected3.getId();

        // test
        ResponseEntity<List<CatReport>> response = restTemplate.exchange("http://localhost:" + port + "/cat/report/pet_report/{chatId}", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CatReport>>() {}, userChatId1);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().size()).isGreaterThan(1);
        Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(expected1);
        Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(expected3);
        Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).doesNotContain(expected2);

        catReportController.deleteReport(id1);
        catReportController.deleteReport(id2);
        catReportController.deleteReport(id3);

        catUserController.deleteUser(userId1);
        catUserController.deleteUser(userId2);

        catController.deletePet(petId1);
        catController.deletePet(petId2);
    }

    @Test
    void createReport() {
        // pet 1
        Cat pet1 = new Cat(-1L, "тестБакс1234567890");
        pet1 = catController.createPet(pet1).getBody();
        Long petId1 = pet1.getId();

        // user 1
        Long userId1 = -1L;
        Long userChatId1 = -12341L;
        String userName1 = "Тестовый юзер1";
        String userPhone1 = "+1234567891";
        CatUser expectedUser1 = new CatUser(userId1, userChatId1, userName1, userPhone1);
        expectedUser1 = catUserController.createUser(expectedUser1).getBody();
        userId1 = expectedUser1.getId();

        // report 1
        Long id1 = -1L;
        LocalDate dateOfReport1 = LocalDate.now();
        String textOfReport1 = "Текст первого отчета";
        byte[] photo1 = {1};
        CatReport expected1 = new CatReport(id1, dateOfReport1, textOfReport1, photo1, expectedUser1);

        expected1 = catReportController.createReport(expected1).getBody();
        id1 = expected1.getId();

        // test
        ResponseEntity<CatReport> response = restTemplate.postForEntity("http://localhost:" + port + "/cat/report", expected1, CatReport.class);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getCatUser().getId()).isEqualTo(expectedUser1.getId());
        Assertions.assertThat(response.getBody().getTextOfReport()).isEqualTo(textOfReport1);
        Assertions.assertThat(response.getBody().getDateOfReport()).isEqualTo(dateOfReport1.format(DateTimeFormatter.ISO_LOCAL_DATE));
        Assertions.assertThat(response.getBody().getPhoto()).isEqualTo(photo1);

        catReportController.deleteReport(id1);
        catUserController.deleteUser(userId1);
        catController.deletePet(petId1);
    }

    @Test
    void updateReport() {
        // pet 1
        Cat pet1 = new Cat(-1L, "тестБакс1234567890");
        pet1 = catController.createPet(pet1).getBody();
        Long petId1 = pet1.getId();

        // user 1
        Long userId1 = -1L;
        Long userChatId1 = -12341L;
        String userName1 = "Тестовый юзер1";
        String userPhone1 = "+1234567891";
        CatUser expectedUser1 = new CatUser(userId1, userChatId1, userName1, userPhone1);
        expectedUser1 = catUserController.createUser(expectedUser1).getBody();
        userId1 = expectedUser1.getId();

        // report 1
        Long id1 = -1L;
        LocalDate dateOfReport1 = LocalDate.now();
        String textOfReport1 = "Текст первого отчета";
        byte[] photo1 = {1};
        CatReport expected1 = new CatReport(id1, dateOfReport1, textOfReport1, photo1, expectedUser1);

        // report to change
        CatReport reportToChange  = catReportController.createReport(expected1).getBody();
        reportToChange.setTextOfReport("Новое имя");
        HttpEntity<CatReport> entityUp = new HttpEntity<CatReport>(reportToChange);

        // test
        ResponseEntity<CatReport> response = restTemplate.exchange("http://localhost:" + port + "/cat/report", HttpMethod.PUT, entityUp, CatReport.class);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getTextOfReport()).isEqualTo("Новое имя");
        Assertions.assertThat(response.getBody().getId()).isEqualTo(reportToChange.getId());

        catReportController.deleteReport(response.getBody().getId());
        catUserController.deleteUser(userId1);
        catController.deletePet(petId1);
    }

    @Test
    void deleteReport() {
        // pet 1
        Cat pet1 = new Cat(-1L, "тестБакс1234567890");
        pet1 = catController.createPet(pet1).getBody();
        Long petId1 = pet1.getId();

        // user 1
        Long userId1 = -1L;
        Long userChatId1 = -12341L;
        String userName1 = "Тестовый юзер1";
        String userPhone1 = "+1234567891";
        CatUser expectedUser1 = new CatUser(userId1, userChatId1, userName1, userPhone1);
        expectedUser1 = catUserController.createUser(expectedUser1).getBody();
        userId1 = expectedUser1.getId();
        catUserService.changeStatusOfTheAdopter(userId1, petId1);

        // report 1
        Long id1 = -1L;
        LocalDate dateOfReport1 = LocalDate.now();
        String textOfReport1 = "Текст первого отчета";
        byte[] photo1 = {1};
        CatReport expected1 = new CatReport(id1, dateOfReport1, textOfReport1, photo1, expectedUser1);

        Long id = catReportController.createReport(expected1).getBody().getId();

        ResponseEntity<CatReport> response = restTemplate.exchange("/cat/report/{id}", HttpMethod.DELETE, null,
                CatReport.class, id);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        catUserController.deleteUser(userId1);
        catController.deletePet(petId1);
    }
}