package com.telegrambot.jd501.controllers.dog;

import com.telegrambot.jd501.model.dog.Dog;
import com.telegrambot.jd501.model.dog.DogReport;
import com.telegrambot.jd501.model.dog.DogUser;
import com.telegrambot.jd501.service.MailingListService;
import com.telegrambot.jd501.service.dog_service.DogReportService;
import com.telegrambot.jd501.service.dog_service.DogUserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
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

import static org.springframework.http.MediaType.IMAGE_JPEG;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class DogReportControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DogController dogController;

    @Autowired
    @InjectMocks
    private DogUserService dogUserService;

    @Autowired
    private DogUserController dogUserController;

    @Autowired
    private DogReportController dogReportController;

    @Autowired
    private DogReportService dogReportService;

    @Autowired
    @Mock
    private MailingListService mailingListService;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(dogReportController).isNotNull();
    }

    @Test
    void getAllReports() {
        // pet 1
        Dog pet1 = new Dog(-1L, "Песик для первого отчета");
        pet1 = dogController.createPet(pet1).getBody();
        Long petId1 = pet1.getId();

        // user 1
        Long userId1 = -1L;
        Long userChatId1 = -130L;
        String userName1 = "Тестовый юзер1";
        String userPhone1 = "+1234567891";
        DogUser expectedUser1 = new DogUser(userId1, userChatId1, userName1, userPhone1);
        expectedUser1 = dogUserController.createUser(expectedUser1).getBody();
        userId1 = expectedUser1.getId();
        dogUserService.changeStatusOfTheAdopter(userId1, petId1);

        // report 1
        Long id1 = -1L;
        LocalDate dateOfReport1 = LocalDate.now();
        String textOfReport1 = "Текст первого отчета";
        byte[] photo1 = {1};
        DogReport expected1 = new DogReport(id1, dateOfReport1, textOfReport1, photo1, expectedUser1);

        // pet 2
        Dog pet2 = new Dog(-2L, "Песик для второго отчета");
        pet2 = dogController.createPet(pet2).getBody();
        Long petId2 = pet2.getId();

        // user 2
        Long userId2 = -2L;
        Long userChatId2 = -131L;
        String userName2 = "Тестовый юзер2";
        String userPhone2 = "+2234567892";
        DogUser expectedUser2 = new DogUser(userId2, userChatId2, userName2, userPhone2);
        expectedUser2 = dogUserController.createUser(expectedUser2).getBody();
        userId2 = expectedUser2.getId();
        dogUserService.changeStatusOfTheAdopter(userId2, petId2);

        // report 2
        Long id2 = -2L;
        LocalDate dateOfReport2 = LocalDate.now();
        String textOfReport2 = "Текст второго отчета";
        byte[] photo2 = {2};
        DogReport expected2 = new DogReport(id2, dateOfReport2, textOfReport2, photo2, expectedUser2);

        // create reports 1 & 2 in DB
        expected1 = dogReportController.createReport(expected1).getBody();
        id1 = expected1.getId();
        expected2 = dogReportController.createReport(expected2).getBody();
        id2 = expected2.getId();

        // test
        try {
            ResponseEntity<List<DogReport>> response = restTemplate.exchange("http://localhost:" + port + "/dog/report", HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<DogReport>>() {
                    });
            Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody().size()).isGreaterThan(1);
            Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(expected1);
            Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(expected2);
        } finally {
            dogReportService.deletePetReport(id1);
            dogReportService.deletePetReport(id2);

            dogUserController.deleteUser(userId1);
            dogUserController.deleteUser(userId2);

            dogController.deletePet(petId1);
            dogController.deletePet(petId2);
        }
    }

    @Test
    void getAllReportsByChatId() {
        // pet 1
        Dog pet1 = new Dog(-1L, "тестБакс1234567890");
        pet1 = dogController.createPet(pet1).getBody();
        Long petId1 = pet1.getId();

        // user 1
        Long userId1 = -1L;
        Long userChatId1 = -1234910L;
        String userName1 = "Тестовый юзер1";
        String userPhone1 = "+1234567891";
        DogUser expectedUser1 = new DogUser(userId1, userChatId1, userName1, userPhone1);
        expectedUser1 = dogUserController.createUser(expectedUser1).getBody();
        userId1 = expectedUser1.getId();
        dogUserService.changeStatusOfTheAdopter(userId1, petId1);

        // report 1
        Long id1 = -1L;
        LocalDate dateOfReport1 = LocalDate.now();
        String textOfReport1 = "Текст первого отчета";
        byte[] photo1 = {1};
        DogReport expected1 = new DogReport(id1, dateOfReport1, textOfReport1, photo1, expectedUser1);

        // pet 2
        Dog pet2 = new Dog(-2L, "тестБакс2234567890");
        pet2 = dogController.createPet(pet2).getBody();
        Long petId2 = pet2.getId();

        // user 2
        Long userId2 = -2L;
        Long userChatId2 = -223492L;
        String userName2 = "Тестовый юзер2";
        String userPhone2 = "+2234567892";
        DogUser expectedUser2 = new DogUser(userId2, userChatId2, userName2, userPhone2);
        expectedUser2 = dogUserController.createUser(expectedUser2).getBody();
        userId2 = expectedUser2.getId();
        dogUserService.changeStatusOfTheAdopter(userId2, petId2);

        // report 2
        Long id2 = -2L;
        LocalDate dateOfReport2 = LocalDate.now();
        String textOfReport2 = "Текст первого отчета";
        byte[] photo2 = {2};
        DogReport expected2 = new DogReport(id2, dateOfReport2, textOfReport2, photo2, expectedUser2);

        // report 3
        Long id3 = -3L;
        LocalDate dateOfReport3 = LocalDate.now();
        String textOfReport3 = "Текст третьего отчета";
        byte[] photo3 = {3};
        DogReport expected3 = new DogReport(id3, dateOfReport3, textOfReport3, photo3, expectedUser1);

        // create reports 1 & 2 & 3 in DB
        expected1 = dogReportController.createReport(expected1).getBody();
        id1 = expected1.getId();
        expected2 = dogReportController.createReport(expected2).getBody();
        id2 = expected2.getId();
        expected3 = dogReportController.createReport(expected3).getBody();
        id3 = expected3.getId();

        // test
        try {
            ResponseEntity<List<DogReport>> response = restTemplate.exchange("http://localhost:" + port + "/dog/report/pet_report/{chatId}", HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<DogReport>>() {
                    }, userChatId1);
            Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody().size()).isGreaterThan(1);
            Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(expected1);
            Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(expected3);
            Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).doesNotContain(expected2);
        } finally {
            dogReportService.deletePetReport(id1);
            dogReportService.deletePetReport(id2);
            dogReportService.deletePetReport(id3);

            dogUserController.deleteUser(userId1);
            dogUserController.deleteUser(userId2);

            dogController.deletePet(petId1);
            dogController.deletePet(petId2);
        }
    }

    @Test
    void createReport() {
        // pet 1
        Dog pet1 = new Dog(-1L, "тестБакс1234567890");
        pet1 = dogController.createPet(pet1).getBody();
        Long petId1 = pet1.getId();

        // user 1
        Long userId1 = -1L;
        Long userChatId1 = -1234193L;
        String userName1 = "Тестовый юзер1";
        String userPhone1 = "+1234567891";
        DogUser expectedUser1 = new DogUser(userId1, userChatId1, userName1, userPhone1);
        expectedUser1 = dogUserController.createUser(expectedUser1).getBody();
        userId1 = expectedUser1.getId();

        // report 1
        Long id1 = -1L;
        LocalDate dateOfReport1 = LocalDate.now();
        String textOfReport1 = "Текст первого отчета";
        byte[] photo1 = {1};
        DogReport expected1 = new DogReport(id1, dateOfReport1, textOfReport1, photo1, expectedUser1);

        expected1 = dogReportController.createReport(expected1).getBody();
        id1 = expected1.getId();

        // test
        try {
            ResponseEntity<DogReport> response = restTemplate.postForEntity("http://localhost:" + port + "/dog/report", expected1, DogReport.class);
            Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody().getId()).isNotNull();
            Assertions.assertThat(response.getBody().getDogUser().getId()).isEqualTo(expectedUser1.getId());
            Assertions.assertThat(response.getBody().getTextOfReport()).isEqualTo(textOfReport1);
            Assertions.assertThat(response.getBody().getDateOfReport()).isEqualTo(dateOfReport1.format(DateTimeFormatter.ISO_LOCAL_DATE));
            Assertions.assertThat(response.getBody().getPhoto()).isEqualTo(photo1);
        } finally {
            dogReportService.deletePetReport(id1);
            dogUserController.deleteUser(userId1);
            dogController.deletePet(petId1);
        }
    }

    @Test
    void updateReport() {
        // pet 1
        Dog pet1 = new Dog(-1L, "тестБакс1234567890");
        pet1 = dogController.createPet(pet1).getBody();
        Long petId1 = pet1.getId();

        // user 1
        Long userId1 = -1L;
        Long userChatId1 = -1234195L;
        String userName1 = "Тестовый юзер1";
        String userPhone1 = "+1234567891";
        DogUser expectedUser1 = new DogUser(userId1, userChatId1, userName1, userPhone1);
        expectedUser1 = dogUserController.createUser(expectedUser1).getBody();
        userId1 = expectedUser1.getId();

        // report 1
        Long id1 = -1L;
        LocalDate dateOfReport1 = LocalDate.now();
        String textOfReport1 = "Текст первого отчета";
        byte[] photo1 = {1};
        DogReport expected1 = new DogReport(id1, dateOfReport1, textOfReport1, photo1, expectedUser1);

        // report to change
        DogReport reportToChange  = dogReportController.createReport(expected1).getBody();
        reportToChange.setTextOfReport("Новое имя");
        HttpEntity<DogReport> entityUp = new HttpEntity<DogReport>(reportToChange);

        // test
        try {
            ResponseEntity<DogReport> response = restTemplate.exchange("http://localhost:" + port + "/dog/report", HttpMethod.PUT, entityUp, DogReport.class);
            Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody().getId()).isNotNull();
            Assertions.assertThat(response.getBody().getTextOfReport()).isEqualTo("Новое имя");
            Assertions.assertThat(response.getBody().getId()).isEqualTo(reportToChange.getId());
        } finally {
            dogReportService.deletePetReport(reportToChange.getId());
            dogUserController.deleteUser(userId1);
            dogController.deletePet(petId1);
        }
    }

    /*@Test
    void deleteReport() {
        // pet 1
        Dog pet1 = new Dog(-1L, "тестБакс1234567890");
        pet1 = dogController.createPet(pet1).getBody();
        Long petId1 = pet1.getId();

        // user 1
        Long userId1 = -1L;
        Long userChatId1 = -1234196L;
        String userName1 = "Тестовый юзер1";
        String userPhone1 = "+1234567891";
        DogUser expectedUser1 = new DogUser(userId1, userChatId1, userName1, userPhone1);
        expectedUser1 = dogUserController.createUser(expectedUser1).getBody();
        userId1 = expectedUser1.getId();
        dogUserService.changeStatusOfTheAdopter(userId1, petId1);

        // report 1
        Long id1 = -1L;
        LocalDate dateOfReport1 = LocalDate.now();
        String textOfReport1 = "Текст первого отчета";
        byte[] photo1 = {1};
        DogReport expected1 = new DogReport(id1, dateOfReport1, textOfReport1, photo1, expectedUser1);

        Long id = dogReportController.createReport(expected1).getBody().getId();

        try {
            ResponseEntity<DogReport> response = restTemplate.exchange("/dog/report/{id}", HttpMethod.DELETE, null,
                    DogReport.class, id);
            Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        } finally {
            dogUserController.deleteUser(userId1);
            dogController.deletePet(petId1);
        }
    }*/

    @Test
    void getPhotoById() {
        // pet 1
        Dog pet1 = new Dog(-333L, "тестБакс1234567890");
        pet1 = dogController.createPet(pet1).getBody();
        Long petId1 = pet1.getId();

        // user 1
        Long userId1 = -333L;
        Long userChatId1 = -333333L;
        String userName1 = "Тестовый юзер1";
        String userPhone1 = "+1234567891";
        DogUser expectedUser1 = new DogUser(userId1, userChatId1, userName1, userPhone1);
        expectedUser1 = dogUserController.createUser(expectedUser1).getBody();
        userId1 = expectedUser1.getId();
        dogUserService.changeStatusOfTheAdopter(userId1, petId1);

        // report 1
        Long id1 = -333L;
        LocalDate dateOfReport1 = LocalDate.now();
        String textOfReport1 = "Текст первого отчета";
        byte[] photo1 = {1};
        DogReport expected1 = new DogReport(id1, dateOfReport1, textOfReport1, photo1, expectedUser1);

        // pet 2
        Dog pet2 = new Dog(-444L, "тестБакс2234567890");
        pet2 = dogController.createPet(pet2).getBody();
        Long petId2 = pet2.getId();

        // user 2
        Long userId2 = -444L;
        Long userChatId2 = -444444L;
        String userName2 = "Тестовый юзер2";
        String userPhone2 = "+2234567892";
        DogUser expectedUser2 = new DogUser(userId2, userChatId2, userName2, userPhone2);
        expectedUser2 = dogUserController.createUser(expectedUser2).getBody();
        userId2 = expectedUser2.getId();
        dogUserService.changeStatusOfTheAdopter(userId2, petId2);

        // report 2
        Long id2 = -2L;
        LocalDate dateOfReport2 = LocalDate.now();
        String textOfReport2 = "Текст первого отчета";
        byte[] photo2 = {2,2,4,4,8,8,10};
        DogReport expected2 = new DogReport(id2, dateOfReport2, textOfReport2, photo2, expectedUser2);

        // create reports 1 & 2 in DB
        expected1 = dogReportController.createReport(expected1).getBody();
        id1 = expected1.getId();
        expected2 = dogReportController.createReport(expected2).getBody();
        id2 = expected2.getId();

        /*// Prepare acceptable media type
        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
        acceptableMediaTypes.add(MediaType.IMAGE_JPEG);

        // Prepare header
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(acceptableMediaTypes);
        HttpEntity<String> entity = new HttpEntity<String>(headers);*/

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(IMAGE_JPEG);
        headers.setContentLength(photo2.length);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        try {
            // test, looking for id=2
            ResponseEntity<byte[]> response = restTemplate.exchange("http://localhost:" + port + "/dog/report/photo/{id}", HttpMethod.GET, entity,
                    byte[].class, id2);
            Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody()).isEqualTo(photo2);
            Assertions.assertThat(response.getHeaders().getContentType()).isEqualTo(headers.getContentType());
            Assertions.assertThat(response.getHeaders().getContentLength()).isEqualTo(headers.getContentLength());
        } finally {
            dogReportService.deletePetReport(id1);
            dogReportService.deletePetReport(id2);

            dogUserController.deleteUser(userId1);
            dogUserController.deleteUser(userId2);

            dogController.deletePet(petId1);
            dogController.deletePet(petId2);
        }
    }
}