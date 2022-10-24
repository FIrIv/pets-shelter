package com.telegrambot.jd501.controllers.dog;

import com.telegrambot.jd501.controllers.Dog.DogInformationMessageController;
import com.telegrambot.jd501.model.dog.DogInformationMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DogInformationMessageControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DogInformationMessageController dogInformationMessageController;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(dogInformationMessageController).isNotNull();
    }

    @Test
    void getAllDogInformationMessage() {
        DogInformationMessage infoMessage = new DogInformationMessage(-1L, "Тестовое info message.");
        long id = dogInformationMessageController.createInformationMessage(infoMessage).getBody().getId();

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/dog/informationMessage", String.class))
                .isNotNull();

        dogInformationMessageController.deleteInformationMessage(id);
    }

    @Test
    void createDogInformationMessage() {
        DogInformationMessage infoMessage = new DogInformationMessage(-1L, "Тестовое info message.");

        ResponseEntity<DogInformationMessage> response = restTemplate.postForEntity("http://localhost:" + port + "/dog/informationMessage", infoMessage, DogInformationMessage.class);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getText()).isEqualTo("Тестовое info message.");
        Assertions.assertThat(response.getBody().getId()).isEqualTo(-1L);

        dogInformationMessageController.deleteInformationMessage(response.getBody().getId());
    }

    @Test
    void updateInformationMessage() {
        DogInformationMessage infoMessage = new DogInformationMessage(-1L, "Тестовое info message.");
        long id = dogInformationMessageController.createInformationMessage(infoMessage).getBody().getId();

        DogInformationMessage infoMessageUp = new DogInformationMessage(id, "Перезаписанное тестовое info message.");

        HttpEntity<DogInformationMessage> entityUp = new HttpEntity<DogInformationMessage>(infoMessageUp);

        ResponseEntity<DogInformationMessage> response = restTemplate.exchange("/dog/informationMessage", HttpMethod.PUT, entityUp,
                DogInformationMessage.class);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getText()).isEqualTo("Перезаписанное тестовое info message.");
        Assertions.assertThat(response.getBody().getId()).isEqualTo(-1L);

        dogInformationMessageController.deleteInformationMessage(id);
    }

    @Test
    void deleteInformationMessage() {
        DogInformationMessage infoMessage = new DogInformationMessage(-1L, "Тестовое info message.");
        long id = dogInformationMessageController.createInformationMessage(infoMessage).getBody().getId();

        ResponseEntity<DogInformationMessage> response = restTemplate.exchange("/dog/informationMessage/{id}", HttpMethod.DELETE, null,
                DogInformationMessage.class, id);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }
}