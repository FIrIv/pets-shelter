package com.telegrambot.jd501.controllers;

import com.telegrambot.jd501.model.InformationMessage;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PetInformationMessageControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private InformationMessageController informationMessageController;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(informationMessageController).isNotNull();
    }

    @Test
    void getAllInformationMessage() throws Exception {
        InformationMessage infoMessage = new InformationMessage();
        infoMessage.setId(-1L);
        infoMessage.setText("Тестовое info message.");
        long id = informationMessageController.createInformationMessage(infoMessage).getBody().getId();

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/informationMessage", String.class))
                .isNotNull();

        informationMessageController.deleteInformationMessage(id);
    }

    @Test
    void createInformationMessage() throws Exception {
        InformationMessage infoMessage = new InformationMessage();
        infoMessage.setId(-1L);
        infoMessage.setText("Тестовое info message.");

        ResponseEntity<InformationMessage> response = restTemplate.postForEntity("http://localhost:" + port + "/informationMessage", infoMessage, InformationMessage.class);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getText()).isEqualTo("Тестовое info message.");
        Assertions.assertThat(response.getBody().getId()).isEqualTo(-1L);

        informationMessageController.deleteInformationMessage(response.getBody().getId());
    }

    @Test
    void updateInformationMessage() throws Exception {
        InformationMessage infoMessage = new InformationMessage();
        infoMessage.setId(-1L);
        infoMessage.setText("Тестовое info message.");
        long id = informationMessageController.createInformationMessage(infoMessage).getBody().getId();

        InformationMessage infoMessageUp = new InformationMessage();
        infoMessageUp.setText("Перезаписанное тестовое info message.");
        infoMessageUp.setId(id);
        HttpEntity<InformationMessage> entityUp = new HttpEntity<InformationMessage>(infoMessageUp);

        ResponseEntity<InformationMessage> response = restTemplate.exchange("/informationMessage", HttpMethod.PUT, entityUp,
                InformationMessage.class);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getText()).isEqualTo("Перезаписанное тестовое info message.");
        Assertions.assertThat(response.getBody().getId()).isEqualTo(-1L);

        informationMessageController.deleteInformationMessage(id);
    }

    @Test
    void deleteInformationMessage() throws Exception {
        InformationMessage infoMessage = new InformationMessage();
        infoMessage.setId(-1L);
        infoMessage.setText("Тестовое info message.");
        long id = informationMessageController.createInformationMessage(infoMessage).getBody().getId();

        ResponseEntity<InformationMessage> response = restTemplate.exchange("/informationMessage/{id}", HttpMethod.DELETE, null,
                InformationMessage.class, id);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }
}