package com.telegrambot.jd501.configuration;

import com.telegrambot.jd501.service.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class TelegramBotInitializer {
    private final TelegramBot bot;
    private final Logger logger = LoggerFactory.getLogger(TelegramBot.class);

    public TelegramBotInitializer(TelegramBot bot) {
        this.bot = bot;
    }

    /**
     * Initialization of telegram's bot
     * @throws TelegramApiException
     */
    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException{
        logger.info("Process init() is running...");
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            logger.error("Error occured in method init(): " + e.getMessage());
        }
    }
}
