package com.telegrambot.jd501.controllers.cat;

import com.telegrambot.jd501.model.cat.CatInformationMessage;
import com.telegrambot.jd501.service.cat_service.CatInformationMessageService;
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

    @Autowired
    private CatInformationMessageService catInformationMessageService;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(catInformationMessageController).isNotNull();
    }
    @Test
    void updateInformationMessage() throws Exception {
        CatInformationMessage infoMessage = new CatInformationMessage(-1L, "Тестовое info message.");
        long id = catInformationMessageService.createInformationMessage(infoMessage).getId();

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
            catInformationMessageService.deleteInformationMessage(id);
        }
    }
}