package com.telegrambot.jd501.controllers.dog;

import com.telegrambot.jd501.controllers.Dog.DogController;
import com.telegrambot.jd501.controllers.Dog.DogUserController;
import com.telegrambot.jd501.model.dog.Dog;
import com.telegrambot.jd501.model.dog.DogUser;
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
class DogUserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DogController dogController;

    @Autowired
    private DogUserController dogUserController;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(dogUserController).isNotNull();
    }

    @Test
    void getAllUsers() {
        // user 1
        Long id1 = -1L;
        Long chatId1 = -12341L;
        String name1 = "Тестовый юзер1";
        String phone1 = "+1234567891";
        DogUser expectedUser1 = new DogUser(id1, chatId1, name1, phone1);

        // user 2
        Long id2 = -2L;
        Long chatId2 = -12345L;
        String name2 = "Тестовый юзер2";
        String phone2 = "+1234567892";
        DogUser expectedUser2 = new DogUser(id2, chatId2, name2, phone2);

        // create reports 1 & 2 in DB
        expectedUser1 = dogUserController.createUser(expectedUser1).getBody();
        expectedUser2 = dogUserController.createUser(expectedUser2).getBody();

        // test
        ResponseEntity<List<DogUser>> response = restTemplate.exchange("http://localhost:" + port + "/dog/user", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<DogUser>>() {});
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        Assertions.assertThat(response.getBody().size()).isGreaterThan(1);
        Assertions.assertThat(response.getBody()).contains(expectedUser1);
        Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(expectedUser2);

        dogUserController.deleteUser(expectedUser1.getId());
        dogUserController.deleteUser(expectedUser2.getId());
    }

    @Test
    void createUser() {
        // user 1
        Long id = -1L;
        Long chatId = -1234L;
        String name = "Тестовый юзер";
        String phone = "+1234567890";
        DogUser expectedUser= new DogUser(id, chatId, name, phone);

        // test
        ResponseEntity<DogUser> response = restTemplate.postForEntity("http://localhost:" + port + "/dog/user", expectedUser, DogUser.class);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getPet()).isNull();
        Assertions.assertThat(response.getBody().getStartDate()).isNull();
        Assertions.assertThat(response.getBody().getFinishDate()).isNull();
        Assertions.assertThat(response.getBody().getName()).isEqualTo(name);
        Assertions.assertThat(response.getBody().getChatId()).isEqualTo(chatId);
        Assertions.assertThat(response.getBody().getPhone()).isEqualTo(phone);
        Assertions.assertThat(response.getBody().getAdopted()).isFalse();

        dogUserController.deleteUser(response.getBody().getId());
    }

    @Test
    void updateUser() {
        // user 1
        Long id = -1L;
        Long chatId = -1234L;
        String name = "Тестовый юзер";
        String phone = "+1234567890";
        DogUser expectedUser= new DogUser(id, chatId, name, phone);

        // create user 1 in DB
        // user to change
        DogUser userToChange  = dogUserController.createUser(expectedUser).getBody();
        userToChange.setName("Новое имя");
        HttpEntity<DogUser> entityUp = new HttpEntity<DogUser>(userToChange);

        // test
        ResponseEntity<DogUser> response = restTemplate.exchange("http://localhost:" + port + "/dog/user", HttpMethod.PUT, entityUp, DogUser.class);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getName()).isEqualTo("Новое имя");
        Assertions.assertThat(response.getBody().getId()).isEqualTo(userToChange.getId());

        dogUserController.deleteUser(userToChange.getId());
    }

    @Test
    void changeStatusOfTheAdopter() {
        // user 1
        Long id = -1L;
        Long chatId = -1234L;
        String name = "Тестовый юзер";
        String phone = "+1234567890";
        DogUser expectedUser = new DogUser(id, chatId, name, phone);

        // create user 1 in DB
        // user to change
        expectedUser =  dogUserController.createUser(expectedUser).getBody();
        Dog pet = new Dog(-5L, "тестБакс1234567890");
        pet = dogController.createPet(pet).getBody();

        // test
        ResponseEntity<DogUser> response = restTemplate.exchange("http://localhost:" + port + "/dog/user/adoption/{userId}/{petId}", HttpMethod.PUT, null, DogUser.class, expectedUser.getId(), pet.getId());
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getAdopted()).isTrue();
        Assertions.assertThat(response.getBody().getPet().getId()).isEqualTo(pet.getId());
        Assertions.assertThat(response.getBody().getId()).isEqualTo(expectedUser.getId());

        dogUserController.deleteUser(expectedUser.getId());
        dogController.deletePet(pet.getId());
    }

    @Test
    void probationPeriodExtension() {
        // user 1
        Long id = -1L;
        Long chatId = -1234L;
        String name = "Тестовый юзер";
        String phone = "+1234567890";
        DogUser expectedUser = new DogUser(id, chatId, name, phone);

        // create user 1 in DB
        // user to change
        DogUser userToChange = dogUserController.createUser(expectedUser).getBody();
        Long userToChangeId = userToChange.getId();
        userToChange.setStartDate(LocalDate.now());
        userToChange.setFinishDate(LocalDate.now().plusDays(30));
        dogUserController.updateUser(userToChange);
        Dog pet1 = new Dog(-5L, "тестБакс1234567890");
        pet1 = dogController.createPet(pet1).getBody();
        userToChange.setPet(pet1);
        userToChange.setAdopted(true);

        int daysPlus = 15;
        // test
        ResponseEntity<DogUser> response = restTemplate.exchange("http://localhost:" + port + "/dog/user/change_period/{id}/{days}", HttpMethod.PUT, null, DogUser.class, userToChangeId, daysPlus);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isEqualTo(userToChangeId);
        Assertions.assertThat(response.getBody().getFinishDate()).isEqualTo(userToChange.getFinishDate().plusDays(15));

        dogUserController.deleteUser(userToChangeId);
        dogController.deletePet(pet1.getId());
    }

    @Test
    void deleteUser() {
        // user 1
        Long id = -1L;
        Long chatId = -1234L;
        String name = "Тестовый юзер";
        String phone = "+1234567890";
        DogUser expectedUser = new DogUser(id, chatId, name, phone);

        // create user 1 in DB
        Long idToDelete = dogUserController.createUser(expectedUser).getBody().getId();

        // test
        ResponseEntity<DogUser> response = restTemplate.exchange("/dog/user/{id}", HttpMethod.DELETE, null,
                DogUser.class, idToDelete);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }
}