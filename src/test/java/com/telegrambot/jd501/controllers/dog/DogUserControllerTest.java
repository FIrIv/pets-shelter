package com.telegrambot.jd501.controllers.dog;

import com.telegrambot.jd501.model.dog.Dog;
import com.telegrambot.jd501.model.dog.DogUser;
import com.telegrambot.jd501.service.MailingListService;
import com.telegrambot.jd501.service.dog_service.DogUserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class DogUserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DogController dogController;

    @Autowired
    private DogUserController dogUserController;

    @Autowired
    @Mock
    private MailingListService mailingListService;

    @Autowired
    @InjectMocks
    private DogUserService dogUserService;

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

        dogUserController.deleteUser(expectedUser1.getChatId());
        dogUserController.deleteUser(expectedUser2.getChatId());
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

        dogUserController.deleteUser(response.getBody().getChatId());
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

        dogUserController.deleteUser(userToChange.getChatId());
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
        ResponseEntity<DogUser> response = restTemplate.exchange("http://localhost:" + port + "/dog/user/adoption/{chatId}/{petId}", HttpMethod.PUT, null, DogUser.class, expectedUser.getChatId(), pet.getId());
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getAdopted()).isTrue();
        Assertions.assertThat(response.getBody().getPet().getId()).isEqualTo(pet.getId());
        Assertions.assertThat(response.getBody().getId()).isEqualTo(expectedUser.getId());

        dogUserController.deleteUser(expectedUser.getChatId());
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
        Long userToChangeChatId = userToChange.getChatId();
        userToChange.setStartDate(LocalDate.now());
        userToChange.setFinishDate(LocalDate.now().plusDays(30));
        dogUserController.updateUser(userToChange);
        Dog pet1 = new Dog(-5L, "тестБакс1234567890");
        pet1 = dogController.createPet(pet1).getBody();
        userToChange.setPet(pet1);
        userToChange.setAdopted(true);

        int daysPlus = 15;
        // test
        ResponseEntity<DogUser> response = restTemplate.exchange("http://localhost:" + port + "/dog/user/change_period/{chatId}/{days}", HttpMethod.PUT, null, DogUser.class, userToChangeChatId, daysPlus);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getChatId()).isEqualTo(userToChangeChatId);
        Assertions.assertThat(response.getBody().getFinishDate()).isEqualTo(userToChange.getFinishDate().plusDays(15));

        dogUserController.deleteUser(userToChangeChatId);
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
        Long chatIdToDelete = dogUserController.createUser(expectedUser).getBody().getChatId();

        // test
        ResponseEntity<DogUser> response = restTemplate.exchange("/dog/user/{chatId}", HttpMethod.DELETE, null,
                DogUser.class, chatIdToDelete);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }

    @Test
    void sendMessageToUserWithChatId() {
        Long id = -149L;
        Long chatId = -1234L;
        String name = "Тестовый юзер";
        String phone = "+1234567890";
        DogUser expectedUser = new DogUser(id, chatId, name, phone);

        // create user 1 in DB
        Long chatIdToDelete = dogUserController.createUser(expectedUser).getBody().getChatId();
        String message = "Отправляю сообщение";

        // test
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/dog/user/send_message_to_dogUser/{chatId}/{message}", HttpMethod.PUT, null, String.class, chatId, message);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody()).isEqualTo("Message: "+ message + "sent to User with chat Id: " + chatId);

        dogUserController.deleteUser(chatIdToDelete);
    }

    @Test
    void changeStatusUserPassedProbationPeriod() {
        Long id = -149L;
        Long chatId = -1234L;
        String name = "Тестовый юзер";
        String phone = "+1234567890";
        DogUser expectedUser = new DogUser(id, chatId, name, phone);

        // create user 1 in DB
        Long chatIdToDelete = dogUserController.createUser(expectedUser).getBody().getChatId();
        String message = "Поздравляем!" +
                " вы успешно прошли испытательный срок" +
                " вам больше не нужно отправлять отчеты" +
                " Если у вас остались вопросы, мы с радостью ответим на них в нашем телеграмм боте";

        // test
        ResponseEntity<DogUser> response = restTemplate.exchange("http://localhost:" + port + "/dog/user/user_passed_probation_period/{chatId}", HttpMethod.PUT, null, DogUser.class, chatId);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getAdopted()).isEqualTo(false);
        Assertions.assertThat(response.getBody().getFinishDate()).isNull();
        Assertions.assertThat(response.getBody().getStartDate()).isNull();
        Assertions.assertThat(response.getBody().getPet()).isNull();

        dogUserController.deleteUser(chatIdToDelete);
    }

    @Test
    void changeStatusUserNotPassedProbationPeriod() {
        Long id = -149L;
        Long chatId = -1234L;
        String name = "Тестовый юзер";
        String phone = "+1234567890";
        DogUser expectedUser = new DogUser(id, chatId, name, phone);

        // create user 1 in DB
        Long chatIdToDelete = dogUserController.createUser(expectedUser).getBody().getChatId();
        String message = "К сожалению вы не прошли испытательный срок." +
                " Мы не сможем оставить вам питомца." +
                " Если у вас остались вопросы, мы с радостью ответим на них в нашем телеграмм боте";

        // test
        ResponseEntity<DogUser> response = restTemplate.exchange("http://localhost:" + port + "/dog/user/user_not_passed_probation_period/{chatId}", HttpMethod.PUT, null, DogUser.class, chatId);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getAdopted()).isEqualTo(false);
        Assertions.assertThat(response.getBody().getFinishDate()).isNull();
        Assertions.assertThat(response.getBody().getStartDate()).isNull();
        Assertions.assertThat(response.getBody().getPet()).isNull();

        dogUserController.deleteUser(chatIdToDelete);
    }
}