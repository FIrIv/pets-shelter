package com.telegrambot.jd501.service;


import com.telegrambot.jd501.configuration.TelegramBotConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.CreateChatInviteLink;
import org.telegram.telegrambots.meta.api.methods.groupadministration.ExportChatInviteLink;
import org.telegram.telegrambots.meta.api.methods.send.SendContact;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
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
            }
            else if (update.getMessage() != null && update.getMessage().hasContact()) {
                // --- if pressed key get contact call method getContact() -----
                // --- (1) Reply to user that his phone is saved
                SendMessage sendMessage = botService.replyAboutSavingContact(update);
                sendMessageToUser(sendMessage);
                // --- (2) Send user's phone number to volunteer
                sendMessage = botService.sendUserPhoneToVolunteer(update);
                sendMessageToUser(sendMessage);
            }
            else if (update.getMessage() != null && update.getMessage().hasDocument()) {
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

}
