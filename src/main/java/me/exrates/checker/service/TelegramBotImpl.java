package me.exrates.checker.service;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBotImpl extends TelegramWebhookBot implements TelegramBot{

    @Override
    public void sendMessage(long chatId, String message) throws TelegramApiException {
        TelegramBotImpl bot = new TelegramBotImpl();
        bot.getBotToken();
        bot.execute(new SendMessage(chatId, message));
    }

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
