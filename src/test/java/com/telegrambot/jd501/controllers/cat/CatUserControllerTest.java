package com.telegrambot.jd501.controllers.cat;

import com.telegrambot.jd501.model.cat.Cat;
import com.telegrambot.jd501.model.cat.CatUser;
import com.telegrambot.jd501.service.MailingListService;
import com.telegrambot.jd501.service.cat_service.CatUserService;
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
class CatUserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CatController catController;

    @Autowired
    private CatUserController catUserController;

    @Autowired
    @Mock
    private MailingListService mailingListService;

    @Autowired
    @InjectMocks
    private CatUserService catUserService;

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
        try {
            ResponseEntity<List<CatUser>> response = restTemplate.exchange("http://localhost:" + port + "/cat/user", HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<CatUser>>() {
                    });
            Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

            Assertions.assertThat(response.getBody().size()).isGreaterThan(1);
            Assertions.assertThat(response.getBody()).contains(expectedUser1);
            Assertions.assertThat(response.getBody().stream().collect(Collectors.toSet())).contains(expectedUser2);
        } finally {
            catUserController.deleteUser(expectedUser1.getChatId());
            catUserController.deleteUser(expectedUser2.getChatId());
        }
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

        catUserController.deleteUser(response.getBody().getChatId());
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
        try {
            ResponseEntity<CatUser> response = restTemplate.exchange("http://localhost:" + port + "/cat/user", HttpMethod.PUT, entityUp, CatUser.class);
            Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody().getId()).isNotNull();
            Assertions.assertThat(response.getBody().getName()).isEqualTo("Новое имя");
            Assertions.assertThat(response.getBody().getId()).isEqualTo(userToChange.getId());
        } finally {
            catUserController.deleteUser(userToChange.getChatId());
        }
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
        try {
            ResponseEntity<CatUser> response = restTemplate.exchange("http://localhost:" + port + "/cat/user/adoption/{chatId}/{petId}", HttpMethod.PUT, null, CatUser.class, expectedUser.getChatId(), pet.getId());
            Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody().getId()).isNotNull();
            Assertions.assertThat(response.getBody().getAdopted()).isTrue();
            Assertions.assertThat(response.getBody().getPet().getId()).isEqualTo(pet.getId());
            Assertions.assertThat(response.getBody().getId()).isEqualTo(expectedUser.getId());
        } finally {
            catUserController.deleteUser(expectedUser.getChatId());
            catController.deletePet(pet.getId());
        }
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
        Long userToChangeChatId = userToChange.getChatId();
        userToChange.setStartDate(LocalDate.now());
        userToChange.setFinishDate(LocalDate.now().plusDays(30));
        catUserController.updateUser(userToChange);
        Cat pet1 = new Cat(-5L, "тестБакс1234567890");
        pet1 = catController.createPet(pet1).getBody();
        userToChange.setPet(pet1);
        userToChange.setAdopted(true);

        int daysPlus = 15;
        // test
        try {
            ResponseEntity<CatUser> response = restTemplate.exchange("http://localhost:" + port + "/cat/user/change_period/{chatId}/{days}", HttpMethod.PUT, null, CatUser.class, userToChangeChatId, daysPlus);
            Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody().getId()).isNotNull();
            Assertions.assertThat(response.getBody().getChatId()).isEqualTo(userToChangeChatId);
            Assertions.assertThat(response.getBody().getFinishDate()).isEqualTo(userToChange.getFinishDate().plusDays(15));
        } finally {
            catUserController.deleteUser(userToChange.getChatId());
            catController.deletePet(pet1.getId());
        }
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
        Long chatIdToDelete = catUserController.createUser(expectedUser).getBody().getChatId();

        // test
        ResponseEntity<CatUser> response = restTemplate.exchange("/cat/user/{chatId}", HttpMethod.DELETE, null,
                CatUser.class, chatIdToDelete);
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }

    @Test
    void sendMessageToUserWithChatId() {
        Long id = -149L;
        Long chatId = -1234L;
        String name = "Тестовый юзер";
        String phone = "+1234567890";
        CatUser expectedUser = new CatUser(id, chatId, name, phone);

        // create user 1 in DB
        Long chatIdToDelete = catUserController.createUser(expectedUser).getBody().getChatId();
        String message = "Отправляю сообщение";

        // test
        try {
            ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/cat/user/send_message_to_catUser/{chatId}/{message}", HttpMethod.PUT, null, String.class, chatId, message);
            Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody()).isNotNull();
            Assertions.assertThat(response.getBody()).isEqualTo("Message: " + message + "sent to User with chat Id: " + chatId);
        } finally {
            catUserController.deleteUser(chatIdToDelete);
        }
    }

    @Test
    void changeStatusUserPassedProbationPeriod() {
        Long id = -149L;
        Long chatId = -1234L;
        String name = "Тестовый юзер";
        String phone = "+1234567890";
        CatUser expectedUser = new CatUser(id, chatId, name, phone);

        // create user 1 in DB
        Long chatIdToDelete = catUserController.createUser(expectedUser).getBody().getChatId();
        String message = "Поздравляем!" +
                " вы успешно прошли испытательный срок" +
                " вам больше не нужно отправлять отчеты" +
                " Если у вас остались вопросы, мы с радостью ответим на них в нашем телеграмм боте";

        // test
        try {
            ResponseEntity<CatUser> response = restTemplate.exchange("http://localhost:" + port + "/cat/user/user_passed_probation_period/{chatId}", HttpMethod.PUT, null, CatUser.class, chatId);
            Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody()).isNotNull();
            Assertions.assertThat(response.getBody().getAdopted()).isEqualTo(false);
            Assertions.assertThat(response.getBody().getFinishDate()).isNull();
            Assertions.assertThat(response.getBody().getStartDate()).isNull();
            Assertions.assertThat(response.getBody().getPet()).isNull();
        } finally {
            catUserController.deleteUser(chatIdToDelete);
        }
    }

    @Test
    void changeStatusUserNotPassedProbationPeriod() {
        Long id = -149L;
        Long chatId = -1234L;
        String name = "Тестовый юзер";
        String phone = "+1234567890";
        CatUser expectedUser = new CatUser(id, chatId, name, phone);

        // create user 1 in DB
        Long chatIdToDelete = catUserController.createUser(expectedUser).getBody().getChatId();
        String message = "К сожалению вы не прошли испытательный срок." +
                " Мы не сможем оставить вам питомца." +
                " Если у вас остались вопросы, мы с радостью ответим на них в нашем телеграмм боте";

        // test
        try {
            ResponseEntity<CatUser> response = restTemplate.exchange("http://localhost:" + port + "/cat/user/user_not_passed_probation_period/{chatId}", HttpMethod.PUT, null, CatUser.class, chatId);
            Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody()).isNotNull();
            Assertions.assertThat(response.getBody().getAdopted()).isEqualTo(false);
            Assertions.assertThat(response.getBody().getFinishDate()).isNull();
            Assertions.assertThat(response.getBody().getStartDate()).isNull();
            Assertions.assertThat(response.getBody().getPet()).isNull();
        } finally {
            catUserController.deleteUser(chatIdToDelete);
        }
    }
}