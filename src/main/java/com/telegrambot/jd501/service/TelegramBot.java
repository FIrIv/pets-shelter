package com.telegrambot.jd501.service;


import com.telegrambot.jd501.configuration.TelegramBotConfiguration;
import com.telegrambot.jd501.repository.InformationMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TelegramBot extends TelegramLongPollingBot {
    final TelegramBotConfiguration config;
    final InformationMessageRepository informationMessageRepository;
    private final Logger logger = LoggerFactory.getLogger(TelegramBot.class);
    /**
     * Массив с наименованиями начальных кнопок
     */
    private final List<String> START_KEY_NAMES = new ArrayList(List.of("Информация о приюте", "Как приютить питомца?",
            "Прислать отчет", "Оставить данные для связи", "Позвать волонтера"));

    //        1 - Информация о приюте
    //        2 - Как приютить питомца?
    //        3 - Прислать отчет
    //        4 - Оставить данные для связи
    //        5 - Позвать волонтера
    public TelegramBot(TelegramBotConfiguration config, InformationMessageRepository informationMessageRepository) {
        this.config = config;
        this.informationMessageRepository = informationMessageRepository;
    }

    /**
     * Получаем имя бота
     *
     * @return строка с BotUsername
     */
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    /**
     * Получаем токен бота
     *
     * @return строка с данными токена
     */
    @Override
    public String getBotToken() {
        return config.getToken();
    }

    /**
     * Получаем входящие обновления
     *
     * @param update список входящих обновлений, не может быть Null
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;

                case "Информация о приюте":
                    String example = "Нажата кнопка " + "''" + START_KEY_NAMES.get(0) + "''";
                    SendMessage message = makeSendingMessage(chatId, example);
                    sendMessage(message);
                    break;

                case "Как приютить питомца?":
                    String example1 = "Нажата кнопка " + "''" + START_KEY_NAMES.get(1) + "''";
                    SendMessage message1 = makeSendingMessage(chatId, example1);
                    sendMessage(message1);
                    break;

                case "Прислать отчет":
                    String example2 = "Нажата кнопка " + "''" + START_KEY_NAMES.get(2) + "''";
                    SendMessage message2 = makeSendingMessage(chatId, example2);
                    sendMessage(message2);
                    break;
                case "Оставить данные для связи":
                    String example3 = "Нажата кнопка " + "''" + START_KEY_NAMES.get(3) + "''";
                    SendMessage message3 = makeSendingMessage(chatId, example3);
                    sendMessage(message3);
                    break;
                case "Позвать волонтера":
                    String example4 = "Нажата кнопка " + "''" + START_KEY_NAMES.get(4) + "''";
                    //String example4 = informationMessageRepository.findById(10L).get().getText();
                    SendMessage message4 = makeSendingMessage(chatId, example4);
                    //message4.setParseMode("HTML");
                    /* не работают теги ul,ol,li,br,p - только
                      <b>bold</b>,
                        <strong>bold</strong>
                        <i>italic</i>,
                        <em>italic</em>
                        <a href="URL">inline URL</a>
                        <code>inline fixed-width code</code>
                        <pre>pre-formatted fixed-width code block</pre> */
                    sendMessage(message4);
                    break;

                default:
                    SendMessage messageDefault = makeSendingMessage(chatId, "Sorry, command was not recognized");
                    sendMessage(messageDefault);
                    break;
            }
        }
    }

    /**
     * При запуске бота, приветствуем пользователя,
     * рассказываем о нашем приюте и предлагаем выбрать пункт меню.
     *
     * @param chatId   идентификаор чата пользователя
     * @param userName имя пользователя
     */
    private void startCommandReceived(long chatId, String userName) {
        String greeting = "Здравствуйте! Это официальный телеграмм-бот приюта животных PetShelter. Мы помогаем людям, которые задумались приютить питомца. " +
                "Для многих из Вас это первый опыт. Не волнуйтесь. Мы поможем с этим нелегким, но важным делом!\n" +
                "*** Выберите интересующий пункт меню ***";
        //  String answer = "Hi, " + userName + ", nice to meet you!";
        SendMessage message = makeSendingMessage(chatId, greeting);
        setButtons(message, START_KEY_NAMES, 3);
        sendMessage(message);
    }

    /**
     * Подготавливаем произвольное сообщение
     *
     * @param chatId      идентификаор чата пользователя
     * @param messageText текст сообщения
     * @return сообщение
     */
    private SendMessage makeSendingMessage(long chatId, String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);
        return message;
    }

    /**
     * Отправляем сообщение пользователю
     *
     * @param message отправляемое сообщение
     * @throws TelegramApiException
     */
    private void sendMessage(SendMessage message) {
        try {
            execute(message);
            logger.info("Message to user: {}", message);
        } catch (TelegramApiException e) {
            logger.warn("Exception: ", e);
        }
    }

    /**
     * Создаем кнопки на экране
     *
     * @param sendMessage  Параметры отправляемых данных
     * @param namesOfKeys  Массив с названиями кнопок
     * @param numberOfRows Кол-во создаваемых рядов кнопок
     */
    public synchronized void setButtons(SendMessage sendMessage, List<String> namesOfKeys, int numberOfRows) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(new KeyboardButton(namesOfKeys.get(0)));
        keyboardFirstRow.add(new KeyboardButton(namesOfKeys.get(1)));

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(new KeyboardButton(namesOfKeys.get(2)));
        keyboardSecondRow.add(new KeyboardButton(namesOfKeys.get(3)));

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        // Если требуемое число рядов кнопок равно 3, создаем третий ряд клавиатуры
        if (numberOfRows == 3) {
            KeyboardRow keyboardThirdRow = new KeyboardRow();
            keyboardThirdRow.add(new KeyboardButton(namesOfKeys.get(4)));
            keyboard.add(keyboardThirdRow);
        }

        // Устанавливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

}
