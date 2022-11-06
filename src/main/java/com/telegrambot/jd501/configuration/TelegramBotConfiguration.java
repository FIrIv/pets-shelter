package com.telegrambot.jd501.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * class for config Telegram bot.
 * Bot's name and UserName - see application.properties
 */
@Configuration
@Data
public class TelegramBotConfiguration {

    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String token;

}
