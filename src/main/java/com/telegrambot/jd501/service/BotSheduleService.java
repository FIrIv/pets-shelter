package com.telegrambot.jd501.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Service
public class BotSheduleService {

    private final MailingListService mailingListService;

    private final BotService botService;

    public BotSheduleService(MailingListService mailingListService, BotService botService) {
        this.mailingListService = mailingListService;
        this.botService = botService;
    }

    /**
     * Every day check DB of catReports in evening to check all yesterday reports are present in DB.
     * Photo and text about pet should be there.
     */
    @Scheduled(cron = "${cron.expression.cats.evening}")
    public void sendToVolunteerIfSomethingWrongWithReportsCats() {
        List<SendMessage> messageList = botService.checkReportsOfTwoLastDaysCats();
        for (SendMessage message : messageList) {
            if (message.getText() != null) {
                mailingListService.sendMessageToUserByChatId(Long.valueOf(message.getChatId()), message.getText());
            }
        }
    }

    /**
     * Every day check DB of dogReports in evening to check all yesterday reports are present in DB.
     * Photo and text about pet should be there.
     */
    @Scheduled(cron = "${cron.expression.dogs.evening}")
    public void sendToVolunteerIfSomethingWrongWithReportsDogs() {
        List<SendMessage> messageList = botService.checkReportsOfTwoLastDaysDogs();
        for (SendMessage message : messageList) {
            if (message.getText() != null) {        mailingListService.sendMessageToUserByChatId(Long.valueOf(message.getChatId()), message.getText());
            }
        }
    }

    /**
     * Every day check DB of dogReports in afternoon for trial period - is expired?
     * Photo and text about pet should be there.
     */
    @Scheduled(cron = "${cron.expression.dogs.day}")
    public void sendToVolunteerToDecideAboutDogsAdopter() {
        List<SendMessage> messageList = botService.checkDogsAdoptersForTrialPeriodHasExpired();
        for (SendMessage message : messageList) {
            if (message.getText() != null) {
                mailingListService.sendMessageToUserByChatId(Long.valueOf(message.getChatId()), message.getText());
            }
        }
    }

    /**
     * Every day check DB of catReports in afternoon for trial period - is expired?
     * Photo and text about pet should be there.
     */
    @Scheduled(cron = "${cron.expression.cats.day}")
    public void sendToVolunteerToDecideAboutCatsAdopter() {
        List<SendMessage> messageList = botService.checkCatsAdoptersForTrialPeriodHasExpired();
        for (SendMessage message : messageList) {
            if (message.getText() != null) {
                mailingListService.sendMessageToUserByChatId(Long.valueOf(message.getChatId()), message.getText());
            }
        }
    }
}
