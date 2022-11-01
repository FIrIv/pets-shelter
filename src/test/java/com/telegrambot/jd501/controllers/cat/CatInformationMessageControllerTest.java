package com.telegrambot.jd501.controllers.cat;

import com.telegrambot.jd501.model.cat.CatInformationMessage;
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
class CatInformationMessageControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CatInformationMessageController catInformationMessageController;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(catInformationMessageController).isNotNull();
    }

    @Test
    void getAllCatInformationMessage() throws Exception {
        CatInformationMessage infoMessage1 = new CatInformationMessage(-1L, "Тестовое info message1.");
        long id1 = catInformationMessageController.createInformationMessage(infoMessage1).getBody().getId();

        CatInformationMessage infoMessage2 = new CatInformationMessage(-2L, "Тестовое info message2.");
        long id2 = catInformationMessageController.createInformationMessage(infoMessage2).getBody().getId();

        try {
            ResponseEntity<List<CatInformationMessage>> response = restTemplate.exchange("http://localhost:" + port + "/cat/informationMessage", HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<CatInformationMessage>>() {
                    });
            Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody().size()).isGreaterThan(1);
            Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(infoMessage1);
            Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(infoMessage2);
        } finally {
            catInformationMessageController.deleteInformationMessage(id1);
            catInformationMessageController.deleteInformationMessage(id2);
        }
    }

    @Test
    void createCatInformationMessage() throws Exception {
        CatInformationMessage infoMessage = new CatInformationMessage(-1L, "Тестовое info message.");

        ResponseEntity<CatInformationMessage> response = restTemplate.postForEntity("http://localhost:" + port + "/cat/informationMessage", infoMessage, CatInformationMessage.class);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getText()).isEqualTo("Тестовое info message.");
        Assertions.assertThat(response.getBody().getId()).isEqualTo(-1L);

        catInformationMessageController.deleteInformationMessage(response.getBody().getId());
    }

    @Test
    void updateInformationMessage() throws Exception {
        CatInformationMessage infoMessage = new CatInformationMessage(-1L, "Тестовое info message.");
        long id = catInformationMessageController.createInformationMessage(infoMessage).getBody().getId();

        CatInformationMessage infoMessageUp = new CatInformationMessage(id, "Перезаписанное тестовое info message.");

        HttpEntity<CatInformationMessage> entityUp = new HttpEntity<CatInformationMessage>(infoMessageUp);

        try {
            ResponseEntity<CatInformationMessage> response = restTemplate.exchange("/cat/informationMessage", HttpMethod.PUT, entityUp,
                    CatInformationMessage.class);
            Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody().getId()).isNotNull();
            Assertions.assertThat(response.getBody().getText()).isEqualTo("Перезаписанное тестовое info message.");
            Assertions.assertThat(response.getBody().getId()).isEqualTo(-1L);
        } finally {
            catInformationMessageController.deleteInformationMessage(id);
        }
    }

    @Test
    void deleteCatInformationMessage() throws Exception {
        CatInformationMessage infoMessage = new CatInformationMessage(-1L, "Тестовое info message.");
        long id = catInformationMessageController.createInformationMessage(infoMessage).getBody().getId();

        ResponseEntity<CatInformationMessage> response = restTemplate.exchange("/cat/informationMessage/{id}", HttpMethod.DELETE, null,
                CatInformationMessage.class, id);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }
}