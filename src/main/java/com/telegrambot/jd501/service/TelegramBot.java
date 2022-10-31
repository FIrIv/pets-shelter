package com.telegrambot.jd501.service;


import com.telegrambot.jd501.configuration.TelegramBotConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    final TelegramBotConfiguration config;
    private final BotService botService;


    private final Logger logger = LoggerFactory.getLogger(TelegramBot.class);


    public TelegramBot(TelegramBotConfiguration config, BotService botService) {
        this.config = config;
        this.botService = botService;
    }

    /**
     * Get name of bot
     *
     * @return string with BotName
     */
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    /**
     * Get bot's token
     *
     * @return string with token's data
     */
    @Override
    public String getBotToken() {
        return config.getToken();
    }

    /**
     * Get incoming updates
     *
     * @param update list of incoming updates, must be not Null
     */
    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                // --- send incoming message (or pressed key) for checking -----
                SendMessage sendMessage = botService.checkInputMessage(update);
                sendMessageToUser(sendMessage);
            } else if (update.getMessage() != null && update.getMessage().hasContact()) {
                // --- if pressed key get contact call method getContact() -----
                // --- (1) Reply to user that his phone is saved
                SendMessage sendMessage = botService.replyAboutSavingContact(update);
                sendMessageToUser(sendMessage);
                // --- (2) Send user's phone number to volunteer
                sendMessage = botService.sendUserPhoneToVolunteer(update);
                sendMessageToUser(sendMessage);
            } else if (update.getMessage() != null && update.getMessage().hasDocument()) {
                // --- if pressed key get contact call method getContact() -----
                // --- (1) Reply to user that his phone is saved
                SendMessage sendMessage = botService.getPicture(update);
                sendMessageToUser(sendMessage);
            }
        } catch (Exception e) {
            logger.error("Error occured in method onUpdateReceived(): " + e.getMessage());
        }
    }

    // =========================================================================

    /**
     * Send message to user.
     *
     * @param message message to User
     */
    void sendMessageToUser(SendMessage message) {
        try {
            if (!message.getText().isEmpty()) {
                execute(message);
            }
        } catch (TelegramApiException e) {
            logger.error("Error occured in method sendMessageToUser: " + e.getMessage());
        }
    }

    /**
     * Prepare data to send user
     *
     * @param chatId      identificator of chat
     * @param textMessage message to User
     */
    public void sendMessageToUserByChatId(long chatId, String textMessage) {
        SendMessage mes = new SendMessage();
        mes.setChatId(chatId);
        mes.setText(textMessage);
        sendMessageToUser(mes);
    }

    /**
     * Every day check DB of catReports in 20:00 to check all yesterday reports are present in DB.
     * Photo and text about pet should be there.
     */
    @Scheduled(cron = "0 20 * * * *")
    public void sendToVolunteerIfSomethingWrongWithReportsCats() {
        List<SendMessage> messageList = botService.checkReportsOfTwoLastDaysCats();
        for (SendMessage message : messageList) {
            sendMessageToUser(message);
        }
    }

    /**
     * Every day check DB of dogReports in 20:00 to check all yesterday reports are present in DB.
     * Photo and text about pet should be there.
     */
    @Scheduled(cron = "0 20 * * * *")
    public void sendToVolunteerIfSomethingWrongWithReportsDogs() {
        List<SendMessage> messageList = botService.checkReportsOfTwoLastDaysDogs();
        for (SendMessage message : messageList) {
            sendMessageToUser(message);
        }
    }

    /**
     * Every day check DB of dogReports in 12:00 for trial period - is expired?
     * Photo and text about pet should be there.
     */
    @Scheduled(cron = "00 12 * * * *")
    public void sendToVolunteerToDecideAboutDogsAdopter() {
        List<SendMessage> messageList = botService.checkDogsAdoptersForTrialPeriodHasExpired();
        for (SendMessage message : messageList) {
            sendMessageToUser(message);
        }
    }

    /**
     * Every day check DB of catReports in 12:00 for trial period - is expired?
     * Photo and text about pet should be there.
     */
    @Scheduled(cron = "00 12 * * * *")
    public void sendToVolunteerToDecideAboutCatsAdopter() {
        List<SendMessage> messageList = botService.checkCatsAdoptersForTrialPeriodHasExpired();
        for (SendMessage message : messageList) {
            sendMessageToUser(message);
        }
    }


}
