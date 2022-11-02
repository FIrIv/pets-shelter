package com.telegrambot.jd501.controllers.dog;

import com.telegrambot.jd501.model.dog.DogInformationMessage;
import com.telegrambot.jd501.service.dog_service.DogInformationMessageService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DogInformationMessageControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DogInformationMessageController dogInformationMessageController;

    @Autowired
    private DogInformationMessageService dogInformationMessageService;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(dogInformationMessageController).isNotNull();
    }

    @Test
    void getAllDogInformationMessage() {
        DogInformationMessage infoMessage1 = new DogInformationMessage(-1L, "Тестовое info message1.");
        long id1 = dogInformationMessageService.createInformationMessage(infoMessage1).getId();

        DogInformationMessage infoMessage2 = new DogInformationMessage(-2L, "Тестовое info message2.");
        long id2 = dogInformationMessageService.createInformationMessage(infoMessage2).getId();

        try {
            ResponseEntity<List<DogInformationMessage>> response = restTemplate.exchange("http://localhost:" + port + "/dog/informationMessage", HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<DogInformationMessage>>() {
                    });
            Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody().size()).isGreaterThan(1);
            Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(infoMessage1);
            Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(infoMessage2);
        } finally {
            dogInformationMessageService.deleteInformationMessage(id1);
            dogInformationMessageService.deleteInformationMessage(id2);
        }
    }

    /*@Test
    void createDogInformationMessage() {
        DogInformationMessage infoMessage = new DogInformationMessage(-1L, "Тестовое info message.");

        ResponseEntity<DogInformationMessage> response = restTemplate.postForEntity("http://localhost:" + port + "/dog/informationMessage", infoMessage, DogInformationMessage.class);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getText()).isEqualTo("Тестовое info message.");
        Assertions.assertThat(response.getBody().getId()).isEqualTo(-1L);

        dogInformationMessageService.deleteInformationMessage(response.getBody().getId());
    }*/

    @Test
    void updateInformationMessage() {
        DogInformationMessage infoMessage = new DogInformationMessage(-1L, "Тестовое info message.");
        long id = dogInformationMessageService.createInformationMessage(infoMessage).getId();

        DogInformationMessage infoMessageUp = new DogInformationMessage(id, "Перезаписанное тестовое info message.");

        HttpEntity<DogInformationMessage> entityUp = new HttpEntity<DogInformationMessage>(infoMessageUp);

        try {
            ResponseEntity<DogInformationMessage> response = restTemplate.exchange("/dog/informationMessage", HttpMethod.PUT, entityUp,
                    DogInformationMessage.class);
            Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody().getId()).isNotNull();
            Assertions.assertThat(response.getBody().getText()).isEqualTo("Перезаписанное тестовое info message.");
            Assertions.assertThat(response.getBody().getId()).isEqualTo(-1L);
        } finally {
            dogInformationMessageService.deleteInformationMessage(id);
        }
    }

    /*@Test
    void deleteInformationMessage() {
        DogInformationMessage infoMessage = new DogInformationMessage(-1L, "Тестовое info message.");
        long id = dogInformationMessageService.createInformationMessage(infoMessage).getId();

        ResponseEntity<DogInformationMessage> response = restTemplate.exchange("/dog/informationMessage/{id}", HttpMethod.DELETE, null,
                DogInformationMessage.class, id);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }*/
}