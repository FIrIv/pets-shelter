package com.telegrambot.jd501.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Класс для конфигурирования и создания бота.
 * Имя бота и UserName указаны в application.properties
 */
@Configuration
@Data
public class TelegramBotConfiguration {

    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String token;


}
