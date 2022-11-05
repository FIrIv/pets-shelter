package com.telegrambot.jd501.service;


import com.telegrambot.jd501.configuration.TelegramBotConfiguration;
import com.telegrambot.jd501.model.MailingList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collection;

/**
 * class for Telegram bot.
 * config  - see TelegramBotConfiguration
 */

@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final TelegramBotConfiguration config;

    private final BotService botService;

    private final MailingListService mailingListService;

    private final Logger logger = LoggerFactory.getLogger(TelegramBot.class);


    public TelegramBot(TelegramBotConfiguration config, BotService botService, MailingListService mailingListService) {
        this.config = config;
        this.botService = botService;
        this.mailingListService = mailingListService;
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
                SendMessage sendMessage = botService.getPictureAsDocument(update);
                sendMessageToUser(sendMessage);
            }

            //  *** new ***
            else if (update.getMessage() != null && update.getMessage().hasPhoto()) {
                // --- if pressed key get contact call method getContact() -----
                // --- (1) Reply to user that his phone is saved
                SendMessage sendMessage = botService.getPictureAsPhoto(update);
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
    public void sendMessageToUser(SendMessage message) {
        try {
            if (!message.getText().isEmpty()) {
                execute(message);
            }
        } catch (TelegramApiException e) {
            logger.error("Error occurred in method sendMessageToUser: " + e.getMessage());
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
     * Every hour check mail list.
     * Send messages to users from this list (if they are exist)
     */
    @Scheduled(cron = "${cron.expression.hour}")
    public void sendToUsersReminders() {
        logger.info("Getting mailingListCollection...");
        Collection<MailingList> mailingListCollection = mailingListService.getAllMailingList();
        for (MailingList mailingList : mailingListCollection) {
            if (mailingList != null) {
                SendMessage mes = new SendMessage();
                mes.setChatId(mailingList.getChatId());
                mes.setText(mailingList.getMessage());
                logger.info("Send message to user (chatId): " + mes.getChatId());
                sendMessageToUser(mes);
                mailingListService.deleteMessageFromMailingList(mailingList.getId());
                logger.info("Message was deleted from DB");
            }
        }
    }
}
