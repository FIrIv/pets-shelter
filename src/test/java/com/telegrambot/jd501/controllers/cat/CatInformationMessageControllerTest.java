package com.telegrambot.jd501.controllers.cat;

import com.telegrambot.jd501.controllers.Cat.CatInformationMessageController;
import com.telegrambot.jd501.model.cat.CatInformationMessage;
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
        CatInformationMessage infoMessage = new CatInformationMessage(-1L, "Тестовое info message.");
        long id = catInformationMessageController.createCatInformationMessage(infoMessage).getBody().getId();

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/cat/informationMessage", String.class))
                .isNotNull();

        catInformationMessageController.deleteCatInformationMessage(id);
    }

    @Test
    void createCatInformationMessage() throws Exception {
        CatInformationMessage infoMessage = new CatInformationMessage(-1L, "Тестовое info message.");

        ResponseEntity<CatInformationMessage> response = restTemplate.postForEntity("http://localhost:" + port + "/cat/informationMessage", infoMessage, CatInformationMessage.class);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getText()).isEqualTo("Тестовое info message.");
        Assertions.assertThat(response.getBody().getId()).isEqualTo(-1L);

        catInformationMessageController.deleteCatInformationMessage(response.getBody().getId());
    }

    @Test
    void updateInformationMessage() throws Exception {
        CatInformationMessage infoMessage = new CatInformationMessage(-1L, "Тестовое info message.");
        long id = catInformationMessageController.createCatInformationMessage(infoMessage).getBody().getId();

        CatInformationMessage infoMessageUp = new CatInformationMessage(id, "Перезаписанное тестовое info message.");

        HttpEntity<CatInformationMessage> entityUp = new HttpEntity<CatInformationMessage>(infoMessageUp);

        ResponseEntity<CatInformationMessage> response = restTemplate.exchange("/cat/informationMessage", HttpMethod.PUT, entityUp,
                CatInformationMessage.class);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getText()).isEqualTo("Перезаписанное тестовое info message.");
        Assertions.assertThat(response.getBody().getId()).isEqualTo(-1L);

        catInformationMessageController.deleteCatInformationMessage(id);
    }

    @Test
    void deleteCatInformationMessage() throws Exception {
        CatInformationMessage infoMessage = new CatInformationMessage(-1L, "Тестовое info message.");
        long id = catInformationMessageController.createCatInformationMessage(infoMessage).getBody().getId();

        ResponseEntity<CatInformationMessage> response = restTemplate.exchange("/cat/informationMessage/{id}", HttpMethod.DELETE, null,
                CatInformationMessage.class, id);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }
}