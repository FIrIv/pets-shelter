package com.telegrambot.jd501.controllers.cat;

import com.telegrambot.jd501.controllers.Cat.CatController;
import com.telegrambot.jd501.controllers.Cat.CatUserController;
import com.telegrambot.jd501.model.cat.Cat;
import com.telegrambot.jd501.model.cat.CatUser;
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

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CatUserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CatController catController;

    @Autowired
    private CatUserController catUserController;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(catUserController).isNotNull();
    }

    @Test
    void getAllUsers() {
        // user 1
        Long id1 = -1L;
        Long chatId1 = -12341L;
        String name1 = "Тестовый юзер1";
        String phone1 = "+1234567891";
        CatUser expectedUser1 = new CatUser(id1, chatId1, name1, phone1);

        // user 2
        Long id2 = -2L;
        Long chatId2 = -12345L;
        String name2 = "Тестовый юзер2";
        String phone2 = "+1234567892";
        CatUser expectedUser2 = new CatUser(id2, chatId2, name2, phone2);

        // create reports 1 & 2 in DB
        expectedUser1 = catUserController.createUser(expectedUser1).getBody();
        expectedUser2 = catUserController.createUser(expectedUser2).getBody();

        // test
        ResponseEntity<List<CatUser>> response = restTemplate.exchange("http://localhost:" + port + "/cat/user", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CatUser>>() {});
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        Assertions.assertThat(response.getBody().size()).isGreaterThan(1);
        Assertions.assertThat(response.getBody()).contains(expectedUser1);
        Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(expectedUser2);

        catUserController.deleteUser(expectedUser1.getId());
        catUserController.deleteUser(expectedUser2.getId());
    }

    @Test
    void createUser() {
        // user 1
        Long id = -1L;
        Long chatId = -1234L;
        String name = "Тестовый юзер";
        String phone = "+1234567890";
        CatUser expectedUser= new CatUser(id, chatId, name, phone);

        // test
        ResponseEntity<CatUser> response = restTemplate.postForEntity("http://localhost:" + port + "/cat/user", expectedUser, CatUser.class);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getPet()).isNull();
        Assertions.assertThat(response.getBody().getStartDate()).isNull();
        Assertions.assertThat(response.getBody().getFinishDate()).isNull();
        Assertions.assertThat(response.getBody().getName()).isEqualTo(name);
        Assertions.assertThat(response.getBody().getChatId()).isEqualTo(chatId);
        Assertions.assertThat(response.getBody().getPhone()).isEqualTo(phone);
        Assertions.assertThat(response.getBody().getAdopted()).isFalse();

        catUserController.deleteUser(response.getBody().getId());
    }

    @Test
    void updateUser() {
        // user 1
        Long id = -1L;
        Long chatId = -1234L;
        String name = "Тестовый юзер";
        String phone = "+1234567890";
        CatUser expectedUser= new CatUser(id, chatId, name, phone);

        // create user 1 in DB
        // user to change
        CatUser userToChange  = catUserController.createUser(expectedUser).getBody();
        userToChange.setName("Новое имя");
        HttpEntity<CatUser> entityUp = new HttpEntity<CatUser>(userToChange);

        // test
        ResponseEntity<CatUser> response = restTemplate.exchange("http://localhost:" + port + "/cat/user", HttpMethod.PUT, entityUp, CatUser.class);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getName()).isEqualTo("Новое имя");
        Assertions.assertThat(response.getBody().getId()).isEqualTo(userToChange.getId());

        catUserController.deleteUser(userToChange.getId());
    }

    @Test
    void changeStatusOfTheAdopter() {
        // user 1
        Long id = -1L;
        Long chatId = -1234L;
        String name = "Тестовый юзер";
        String phone = "+1234567890";
        CatUser expectedUser = new CatUser(id, chatId, name, phone);

        // create user 1 in DB
        // user to change
        expectedUser =  catUserController.createUser(expectedUser).getBody();
        Cat pet = new Cat(-5L, "тестБакс1234567890");
        pet = catController.createPet(pet).getBody();

        // test
        ResponseEntity<CatUser> response = restTemplate.exchange("http://localhost:" + port + "/cat/user/adoption/{userId}/{petId}", HttpMethod.PUT, null, CatUser.class, expectedUser.getId(), pet.getId());
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getAdopted()).isTrue();
        Assertions.assertThat(response.getBody().getPet().getId()).isEqualTo(pet.getId());
        Assertions.assertThat(response.getBody().getId()).isEqualTo(expectedUser.getId());

        catUserController.deleteUser(expectedUser.getId());
        catController.deletePet(pet.getId());
    }

    @Test
    void probationPeriodExtension() {
        // user 1
        Long id = -1L;
        Long chatId = -1234L;
        String name = "Тестовый юзер";
        String phone = "+1234567890";
        CatUser expectedUser = new CatUser(id, chatId, name, phone);

        // create user 1 in DB
        // user to change
        CatUser userToChange = catUserController.createUser(expectedUser).getBody();
        Long userToChangeId = userToChange.getId();
        userToChange.setStartDate(LocalDate.now());
        userToChange.setFinishDate(LocalDate.now().plusDays(30));
        catUserController.updateUser(userToChange);
        Cat pet1 = new Cat(-5L, "тестБакс1234567890");
        pet1 = catController.createPet(pet1).getBody();
        userToChange.setPet(pet1);
        userToChange.setAdopted(true);

        int daysPlus = 15;
        // test
        ResponseEntity<CatUser> response = restTemplate.exchange("http://localhost:" + port + "/cat/user/change_period/{id}/{days}", HttpMethod.PUT, null, CatUser.class, userToChangeId, daysPlus);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isEqualTo(userToChangeId);
        Assertions.assertThat(response.getBody().getFinishDate()).isEqualTo(userToChange.getFinishDate().plusDays(15));

        catUserController.deleteUser(userToChangeId);
        catController.deletePet(pet1.getId());
    }

    @Test
    void deleteUser() {
        // user 1
        Long id = -1L;
        Long chatId = -1234L;
        String name = "Тестовый юзер";
        String phone = "+1234567890";
        CatUser expectedUser = new CatUser(id, chatId, name, phone);

        // create user 1 in DB
        Long idToDelete = catUserController.createUser(expectedUser).getBody().getId();

        // test
        ResponseEntity<CatUser> response = restTemplate.exchange("/cat/user/{id}", HttpMethod.DELETE, null,
                CatUser.class, idToDelete);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }
}