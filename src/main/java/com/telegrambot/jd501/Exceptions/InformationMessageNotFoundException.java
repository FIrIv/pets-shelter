package com.telegrambot.jd501.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InformationMessageNotFoundException extends RuntimeException{
    public  InformationMessageNotFoundException(String message){
        super(message);
    }
}
