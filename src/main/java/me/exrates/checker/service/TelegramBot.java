package me.exrates.checker.service;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface TelegramBot {
    void sendMessage(long chatId, String message) throws TelegramApiException;
}
