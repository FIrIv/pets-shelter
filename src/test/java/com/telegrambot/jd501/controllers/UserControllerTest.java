package com.telegrambot.jd501.controllers;

import com.telegrambot.jd501.model.Pet;
import com.telegrambot.jd501.model.User;
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
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PetController petController;

    @Autowired
    private UserController userController;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(userController).isNotNull();
    }

    @Test
    void getAllUsers() {
        // user 1
        Long id1 = -1L;
        Long chatId1 = -12341L;
        String name1 = "Тестовый юзер1";
        String phone1 = "+1234567891";
        User expectedUser1 = new User();
        expectedUser1.setId(id1);
        expectedUser1.setName(name1);
        expectedUser1.setPet(null);
        expectedUser1.setChatId(chatId1);
        expectedUser1.setPhone(phone1);
        expectedUser1.setStartDate(null);
        expectedUser1.setFinishDate(null);
        expectedUser1.setAdopted(false);

        // user 2
        Long id2 = -2L;
        Long chatId2 = -12345L;
        String name2 = "Тестовый юзер2";
        String phone2 = "+1234567892";
        User expectedUser2 = new User();
        expectedUser2.setId(id2);
        expectedUser2.setName(name2);
        expectedUser2.setPet(null);
        expectedUser2.setChatId(chatId2);
        expectedUser2.setPhone(phone2);
        expectedUser2.setStartDate(null);
        expectedUser2.setFinishDate(null);
        expectedUser2.setAdopted(false);

        // create reports 1 & 2 in DB
        id1 = userController.createUser(expectedUser1).getBody().getId();
        expectedUser1.setId(id1);
        id2 = userController.createUser(expectedUser2).getBody().getId();
        expectedUser2.setId(id2);

       // test
        ResponseEntity<List<User>> response = restTemplate.exchange("http://localhost:" + port + "/user", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() {});
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().size()).isGreaterThan(1);
        Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(expectedUser1);
        Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(expectedUser2);

        userController.deleteUser(id1);
        userController.deleteUser(id2);
    }

    @Test
    void createUser() {
        // user 1
        Long id = -1L;
        Long chatId = -1234L;
        String name = "Тестовый юзер";
        String phone = "+1234567890";
        User expectedUser = new User();
        expectedUser.setId(id);
        expectedUser.setName(name);
        expectedUser.setPet(null);
        expectedUser.setChatId(chatId);
        expectedUser.setPhone(phone);
        expectedUser.setStartDate(null);
        expectedUser.setFinishDate(null);
        expectedUser.setAdopted(false);

        // test
        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:" + port + "/user", expectedUser, User.class);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getPet()).isNull();
        Assertions.assertThat(response.getBody().getStartDate()).isNull();
        Assertions.assertThat(response.getBody().getFinishDate()).isNull();
        Assertions.assertThat(response.getBody().getName()).isEqualTo(name);
        Assertions.assertThat(response.getBody().getChatId()).isEqualTo(chatId);
        Assertions.assertThat(response.getBody().getPhone()).isEqualTo(phone);
        Assertions.assertThat(response.getBody().getAdopted()).isFalse();

        userController.deleteUser(response.getBody().getId());
    }

    @Test
    void updateUser() {
        // user 1
        Long id = -1L;
        Long chatId = -1234L;
        String name = "Тестовый юзер";
        String phone = "+1234567890";
        User expectedUser = new User();
        expectedUser.setId(id);
        expectedUser.setName(name);
        expectedUser.setPet(null);
        expectedUser.setChatId(chatId);
        expectedUser.setPhone(phone);
        expectedUser.setStartDate(null);
        expectedUser.setFinishDate(null);
        expectedUser.setAdopted(false);

        // create user 1 in DB
       // user to change
        User userToChange  = userController.createUser(expectedUser).getBody();
        userToChange.setName("Новое имя");
        HttpEntity<User> entityUp = new HttpEntity<User>(userToChange);

        // test
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:" + port + "/user", HttpMethod.PUT, entityUp, User.class);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getName()).isEqualTo("Новое имя");
        Assertions.assertThat(response.getBody().getId()).isEqualTo(userToChange.getId());

        userController.deleteUser(userToChange.getId());
    }

    @Test
    void changeStatusOfTheAdopter() {
        // user 1
        Long id = -1L;
        Long chatId = -1234L;
        String name = "Тестовый юзер";
        String phone = "+1234567890";
        User expectedUser = new User();
        expectedUser.setId(id);
        expectedUser.setName(name);
        expectedUser.setPet(null);
        expectedUser.setChatId(chatId);
        expectedUser.setPhone(phone);
        expectedUser.setStartDate(null);
        expectedUser.setFinishDate(null);
        expectedUser.setAdopted(false);

        // create user 1 in DB
        // user to change
        Long userToChangeId  = userController.createUser(expectedUser).getBody().getId();
        Pet pet1 = new Pet();
        pet1.setId(-5L);
        pet1.setName("тестБакс1234567890");
        Long petToAddId = petController.createPet(pet1).getBody().getId();
        pet1.setId(petToAddId);

        // test
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:" + port + "/user/adoption/{userId}/{petId}", HttpMethod.PUT, null, User.class, userToChangeId, petToAddId);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getAdopted()).isTrue();
        Assertions.assertThat(response.getBody().getPet().getId()).isEqualTo(petToAddId);
        Assertions.assertThat(response.getBody().getId()).isEqualTo(userToChangeId);

        userController.deleteUser(userToChangeId);
        petController.deletePet(petToAddId);
    }

    @Test
    void probationPeriodExtension() {
        // user 1
        Long id = -1L;
        Long chatId = -1234L;
        String name = "Тестовый юзер";
        String phone = "+1234567890";
        User expectedUser = new User();
        expectedUser.setId(id);
        expectedUser.setName(name);
        expectedUser.setPet(null);
        expectedUser.setChatId(chatId);
        expectedUser.setPhone(phone);
        expectedUser.setStartDate(null);
        expectedUser.setFinishDate(null);
        expectedUser.setAdopted(false);

        // create user 1 in DB
        // user to change
        User userToChange = userController.createUser(expectedUser).getBody();
        Long userToChangeId = userToChange.getId();
        userToChange.setStartDate(LocalDate.now());
        userToChange.setFinishDate(LocalDate.now().plusDays(30));
        userController.updateUser(userToChange);
        Pet pet1 = new Pet();
        pet1.setId(-5L);
        pet1.setName("тестБакс1234567890");
        Long petToAddId = petController.createPet(pet1).getBody().getId();
        pet1.setId(petToAddId);
        userToChange.setPet(pet1);
        userToChange.setAdopted(true);

        int daysPlus = 15;
        // test
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:" + port + "/user/change_period/{id}/{days}", HttpMethod.PUT, null, User.class, userToChangeId, daysPlus);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isEqualTo(userToChangeId);
        Assertions.assertThat(response.getBody().getFinishDate()).isEqualTo(userToChange.getFinishDate().plusDays(15));

        userController.deleteUser(userToChangeId);
        petController.deletePet(petToAddId);
    }

    @Test
    void deleteUser() {
        // user 1
        Long id = -1L;
        Long chatId = -1234L;
        String name = "Тестовый юзер";
        String phone = "+1234567890";
        User expectedUser = new User();
        expectedUser.setId(id);
        expectedUser.setName(name);
        expectedUser.setPet(null);
        expectedUser.setChatId(chatId);
        expectedUser.setPhone(phone);
        expectedUser.setStartDate(null);
        expectedUser.setFinishDate(null);
        expectedUser.setAdopted(false);

        // create user 1 in DB
        Long idToDelete = userController.createUser(expectedUser).getBody().getId();

        // test
        ResponseEntity<User> response = restTemplate.exchange("/user/{id}", HttpMethod.DELETE, null,
                User.class, idToDelete);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }
}