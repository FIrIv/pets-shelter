package com.telegrambot.jd501.model.dog;

import com.telegrambot.jd501.model.InformationMessage;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dog_information_message")
public class DogInformationMessage extends InformationMessage {

    @Id
    private Long id;
}
