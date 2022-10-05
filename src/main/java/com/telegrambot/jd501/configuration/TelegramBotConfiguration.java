package com.telegrambot.jd501.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.DeleteMyCommands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// ------------- конфигурируем Телеграм-Бот -----------------------
@Configuration
public class TelegramBotConfiguration {

    // --------------- Сам Токен Телеграм-Бота и его название прописаны в application.properties --------
    @Value("${telegram.bot.token}")
    private String token;

    @Bean
    public TelegramBot telegramBot() {
        TelegramBot bot = new TelegramBot(token);
        bot.execute(new DeleteMyCommands());
        return bot;
    }
}
