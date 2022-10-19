package com.telegrambot.jd501.model.cat;

import com.telegrambot.jd501.model.InformationMessage;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cat_information_message")
public class CatInformationMessage extends InformationMessage {

    @Id
    private Long id;
}
