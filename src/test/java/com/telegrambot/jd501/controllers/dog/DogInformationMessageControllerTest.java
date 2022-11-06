package com.telegrambot.jd501.controllers.dog;

import com.telegrambot.jd501.model.dog.DogInformationMessage;
import com.telegrambot.jd501.service.dog_service.DogInformationMessageService;
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
}