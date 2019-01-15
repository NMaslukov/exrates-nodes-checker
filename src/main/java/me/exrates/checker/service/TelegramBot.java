package me.exrates.checker.service;


import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramBot extends TelegramWebhookBot {

    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {
        System.out.println(update);

        return new SendMessage(update.getMessage().getChatId(), "Tester");
    }

    @Override
    public String getBotUsername() {
        return "EXRATES_NODE_NOTIFICATION_bot";
    }

    @Override
    public String getBotToken() {
        return "739487263:AAE5xPADM-Q5lmKIaoYijyO-LU8-wCOp21E";
    }

    @Override
    public String getBotPath() {
        return null;
    }
}
