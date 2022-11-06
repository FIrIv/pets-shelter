package com.telegrambot.jd501.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Properties;

@Service
public class MessageTextService {
    private final Properties properties;

    public MessageTextService() throws IOException {
        this.properties = PropertiesLoaderUtils.loadProperties(new EncodedResource
                (new ClassPathResource("text.properties"), StandardCharsets.UTF_8));
    }

    public String get(String key) {
        return properties.getProperty(key);
    }
    public String get(String key, Object... args){
       return MessageFormat.format(get(key), args);
    }

}
